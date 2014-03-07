package com.adjazent.defrac.sandbox.apps.dnd;

import com.adjazent.defrac.core.error.ValueError;
import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import defrac.display.event.UIEventTarget;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class DNDPair
{
	private IDragable _dragable;

	private LinkedList<IDropTarget> _targets;

	private IDropTarget _currentTarget;

	public DNDPair( IDragable dragable, LinkedList<IDropTarget> targets )
	{
		_dragable = dragable;

		_targets = targets;

		if( _targets.size() == 0 )
		{
			throw new ValueError( "At least one drop target has to be defined to given dragable item." );
		}
	}

	public void processDragEnter( UIEventTarget element )
	{
		_currentTarget = isDropTarget( element );

		if( _currentTarget != null )
		{
			_currentTarget.onDragEnter( _dragable );
		}
	}

	public void processDragExit( UIEventTarget element )
	{
		_currentTarget = isDropTarget( element );

		if( _currentTarget != null )
		{
			_currentTarget.onDragExit( _dragable );
		}
	}

	public void processDragDone()
	{
		if( _currentTarget != null )
		{
			_currentTarget.onDragDone( _dragable );
		}

		_currentTarget = null;
	}

	public boolean hasDragable( UIEventTarget element )
	{
		return ( _dragable == element );
	}

	public IDropTarget isDropTarget( UIEventTarget eventTarget )
	{
		Log.info( Context.DEFAULT, this, "---------------" );
		for( IDropTarget dropTarget : _targets )
		{
			Log.info( Context.DEFAULT, this, "isDropTarget", eventTarget );

			if( dropTarget == eventTarget )
			{
				return dropTarget;
			}
		}

		return null;
	}

	@Override
	public String toString()
	{
		return "[DNDPair]";
	}
}

