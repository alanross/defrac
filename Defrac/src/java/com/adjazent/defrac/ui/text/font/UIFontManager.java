package com.adjazent.defrac.ui.text.font;

import com.adjazent.defrac.core.error.SingletonError;
import com.adjazent.defrac.ds.atlas.Atlas;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIFontManager extends Atlas
{
	private static UIFontManager _instance;

	public static void initialize()
	{
		if( _instance != null )
		{
			throw new SingletonError( "UIFontManager" );
		}

		_instance = new UIFontManager();
	}

	public static UIFontManager get()
	{
		return _instance;
	}

	private UIFontManager()
	{
	}

	public Boolean addFont( UIFont element )
	{
		return addElement( element );
	}

	public Boolean removeFont( UIFont element )
	{
		return removeElement( element );
	}

	public UIFont getFont( String id )
	{
		return ( UIFont ) getElement( id );
	}

	public boolean hasFont( UIFont element )
	{
		return hasElement( element.getId() );
	}

	public boolean hasFont( String id )
	{
		return hasElement( id );
	}

	@Override
	public String toString()
	{
		return "[UIFontManager]";
	}
}


