package com.adjazent.defrac.sandbox.apps.model.scene;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7SceneSetList
{
	private LinkedList<Pro7SceneSet> _sceneSets;

	public Pro7SceneSetList()
	{
		_sceneSets = new LinkedList<Pro7SceneSet>();
	}

	public Pro7SceneSet add( Pro7SceneSet item )
	{
		if( has( item ) )
		{
			throw new ElementAlreadyExistsError( this + " " + item + " already exists" );
		}

		_sceneSets.addLast( item );

		return item;
	}

	public Pro7SceneSet remove( Pro7SceneSet item )
	{
		if( has( item ) )
		{
			throw new ElementDoesNotExistError( this + " " + item + " does not exist" );
		}

		_sceneSets.remove( item );

		return item;
	}

	public boolean has( Pro7SceneSet item )
	{
		return ( -1 != _sceneSets.indexOf( item ) );
	}

	public Pro7SceneSet getAt( int index )
	{
		return _sceneSets.get( index );
	}

	public int numSceneSets()
	{
		return _sceneSets.size();
	}

	@Override
	public String toString()
	{
		return "[Pro7SceneSetList]";
	}
}