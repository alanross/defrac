package com.adjazent.defrac.ui.texture;

import com.adjazent.defrac.core.error.SingletonError;
import com.adjazent.defrac.ds.atlas.Atlas;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextureManager extends Atlas
{
	private static UITextureManager _instance;

	public static void initialize()
	{
		if( _instance != null )
		{
			throw new SingletonError( "UITextureManager" );
		}

		_instance = new UITextureManager();
	}

	public static UITextureManager get()
	{
		return _instance;
	}

	private UITextureManager()
	{
	}

	public Boolean addAtlas( UITextureAtlas element )
	{
		return addElement( element );
	}

	public Boolean removeAtlas( UITextureAtlas element )
	{
		return removeElement( element );
	}

	public UITextureAtlas getAtlas( String id )
	{
		return ( UITextureAtlas ) getElement( id );
	}

	public boolean hasAtlas( UITextureAtlas element )
	{
		return hasElement( element.getId() );
	}

	public boolean hasAtlas( String id )
	{
		return hasElement( id );
	}

	@Override
	public String toString()
	{
		return "[UITextureManager]";
	}
}

