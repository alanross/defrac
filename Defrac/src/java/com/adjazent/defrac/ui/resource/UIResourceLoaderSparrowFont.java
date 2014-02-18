package com.adjazent.defrac.ui.resource;

import com.adjazent.defrac.core.error.NullError;
import com.adjazent.defrac.core.error.SyncError;
import com.adjazent.defrac.core.job.IJob;
import com.adjazent.defrac.core.job.IJobObserver;
import com.adjazent.defrac.core.job.Job;
import com.adjazent.defrac.core.job.JobQueue;
import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.core.xml.XML;
import com.adjazent.defrac.core.xml.XMLNode;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import com.adjazent.defrac.ui.text.font.UIFont;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import com.adjazent.defrac.ui.text.font.glyph.UIKerningPair;
import defrac.display.TextureData;
import defrac.geom.Point;
import defrac.geom.Rectangle;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIResourceLoaderSparrowFont extends Job implements IUIResourceLoader, IJobObserver
{
	private UILoadTexJob _texLoader;
	private UILoadXmlJob _xmlLoader;
	private JobQueue _loadQueue;
	private String _id;

	public UIResourceLoaderSparrowFont( String fontTextureUrl, String fontXmlUrl, String id )
	{
		_id = id;
		_texLoader = new UILoadTexJob( fontTextureUrl );
		_xmlLoader = new UILoadXmlJob( fontXmlUrl );

		_loadQueue = new JobQueue();
		_loadQueue.addJob( _texLoader );
		_loadQueue.addJob( _xmlLoader );
		_loadQueue.addObserver( this );
	}

	private void clear()
	{
		_loadQueue.removeJob( _texLoader );
		_loadQueue.removeJob( _xmlLoader );

		_texLoader.dispose();
		_texLoader = null;

		_xmlLoader.dispose();
		_xmlLoader = null;
	}

	private UIFont createFont( TextureData textureData, XML xml )
	{
		XMLNode common = xml.getFirstByName( "common" );
		XMLNode info = xml.getFirstByName( "info" );
		XMLNode glyphs = xml.getFirstByName( "chars" );
		XMLNode kernings = xml.getFirstByName( "kernings" );

		String face = info.getAttribute( "fontId" );
		boolean bold = ( info.getAttribute( "bold" ) != null && !info.getAttribute( "bold" ).equals( "0" ) );
		boolean italic = ( info.getAttribute( "italic" ) != null && !info.getAttribute( "italic" ).equals( "0" ) );
		int size = Integer.parseInt( info.getAttribute( "size" ) );
		int lineHeight = Integer.parseInt( common.getAttribute( "lineHeight" ) );
		int base = Integer.parseInt( common.getAttribute( "base" ) );

		UIFont font = new UIFont( textureData, _id, face, size, bold, italic, lineHeight );

		int n = glyphs.getNumChildren();

		while( --n > -1 )
		{
			font.addGlyph( createGlyph( font, lineHeight, base, glyphs.getChildAt( n ), kernings.children ) );
		}

		if( !font.hasGlyph( 10 ) ) // new-line character
		{
			font.addGlyph( createNewLineGlyph( font ) );
		}

		return font;
	}

	private UIGlyph createGlyph( UIFont font, int lineHeight, int base, XMLNode glyph, LinkedList<XMLNode> kernings )
	{
		String id = glyph.getAttribute( "id" );

		int code = Integer.parseInt( id );

		int xAdvance = Integer.parseInt( glyph.getAttribute( "xadvance" ) );

		LinkedList<XMLNode> matchingKernings = new LinkedList<XMLNode>();

		int n = kernings.size();

		while( --n > -1 )
		{
			String first = kernings.get( n ).getAttribute( "first" );

			if( first.equals( id ) )
			{
				matchingKernings.addLast( kernings.get( n ) );
			}
		}

		LinkedList<UIKerningPair> kerningPairs = createKerningPairs( matchingKernings );

		Point offset = new Point(
				Integer.parseInt( glyph.getAttribute( "xoffset" ) ),
				Integer.parseInt( glyph.getAttribute( "yoffset" ) )
		);

		Rectangle sourceRect = new Rectangle(
				Integer.parseInt( glyph.getAttribute( "x" ) ),
				Integer.parseInt( glyph.getAttribute( "y" ) ),
				Integer.parseInt( glyph.getAttribute( "width" ) ),
				Integer.parseInt( glyph.getAttribute( "height" ) )
		);

		// non-white space characters
		if( sourceRect.width > 0 && sourceRect.height > 0 )
		{
			sourceRect.y -= 2;
			sourceRect.width += 1;
			sourceRect.height += 1;
		}

		Rectangle bounds = new Rectangle( 0, 0, sourceRect.width, sourceRect.height );

		return new UIGlyph( font, code, sourceRect, bounds, offset, xAdvance, lineHeight, base, kerningPairs );
	}

	private UIGlyph createNewLineGlyph( UIFont font )
	{
		// try to prepare a new line character by using the info from the space character

		if( !font.hasGlyph( ' ' ) ) // space character
		{
			throw new NullError( this, "No space character present in the font atlas." );
		}

		UIGlyph glyph = font.getGlyph( ' ' );

		int code = UIGlyph.NEW_LINE;
		int xAdvance = 0;
		int lineHeight = glyph.getLineHeight();
		int base = glyph.getBase();
		Point offset = new Point();
		Rectangle sourceRect = new Rectangle();
		Rectangle bounds = new Rectangle();
		LinkedList<UIKerningPair> kerningPairs = new LinkedList<UIKerningPair>();

		return new UIGlyph( font, code, sourceRect, bounds, offset, xAdvance, lineHeight, base, kerningPairs );
	}

	private LinkedList<UIKerningPair> createKerningPairs( LinkedList<XMLNode> pairs )
	{
		LinkedList<UIKerningPair> result = new LinkedList<UIKerningPair>();

		int n = pairs.size();

		while( --n > -1 )
		{
			XMLNode pair = pairs.get( n );

			result.addLast( new UIKerningPair(
					Integer.parseInt( pair.getAttribute( "first" ) ),
					Integer.parseInt( pair.getAttribute( "second" ) ),
					Integer.parseInt( pair.getAttribute( "amount" ) )
			) );
		}

		return result;
	}

	@Override
	public void dispose()
	{
		super.dispose();

		clear();

		_loadQueue.dispose();
		_loadQueue = null;
	}

	@Override
	protected void onStart()
	{
		if( _loadQueue.isRunning() )
		{
			throw new SyncError( this, " is already running" );
		}

		_loadQueue.start();
	}

	@Override
	protected void onCancel()
	{
		if( _loadQueue.isRunning() )
		{
			_loadQueue.cancel();
		}

		clear();
	}

	@Override
	protected void onComplete()
	{
		clear();
	}

	@Override
	protected void onFail()
	{
		clear();
	}

	@Override
	public void onJobProgress( IJob job, float progress )
	{
	}

	@Override
	public void onJobCompleted( IJob job )
	{
		UIFontManager.get().addFont( createFont( _texLoader.getTextureData(), _xmlLoader.getXml() ) );

		Log.trace( Context.UI, "Completed loading Font: " + _texLoader.getUrl() + " | " + _xmlLoader.getUrl() );

		complete();
	}

	@Override
	public void onJobCancelled( IJob job )
	{
	}

	@Override
	public void onJobFailed( IJob job, Error error )
	{
		fail( error );
	}

	@Override
	public String toString()
	{
		return "[UIResourceLoaderSparrowFont]";
	}
}

