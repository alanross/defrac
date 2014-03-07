package com.adjazent.defrac.sandbox.apps.dnd;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.core.error.SingletonError;
import com.adjazent.defrac.core.error.ValueError;
import defrac.display.Stage;
import defrac.display.event.*;
import defrac.geom.Point;

import javax.annotation.Nonnull;
import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class DnDManager implements UIProcessHook
{
	private static DnDManager _instance;

	private LinkedList<DNDPair> _pairs;

	private boolean _dragging;
	private Point _dragOrig = new Point();
	private Point _dragOffset = new Point();
	private DNDPair _dragPair;

	private DnDGhost _ghost;
	private Stage _stage;

	public static void initialize( Stage stage )
	{
		if( _instance != null )
		{
			throw new SingletonError( "DnDManager" );
		}

		_instance = new DnDManager( stage );
	}

	public static void register( IDragable dragable, Object... dropTargets )
	{
		LinkedList<IDropTarget> targets = new LinkedList<IDropTarget>();

		for( int i = 0; i < dropTargets.length; ++i )
		{
			if( dropTargets[ i ] instanceof IDropTarget )
			{
				IDropTarget target = ( IDropTarget ) dropTargets[ i ];

				targets.addLast( target );
			}
			else
			{
				throw new ValueError( "List of drop targets may only contain elements of type IDropTarget" );
			}
		}

		_instance.addPair( dragable, targets );
	}

	public DnDManager( Stage stage )
	{
		_pairs = new LinkedList<DNDPair>();

		_ghost = new DnDGhost();

		_stage = stage;

		_stage.addProcessHook( this );
	}

	private void addPair( IDragable dragable, LinkedList<IDropTarget> targets )
	{
		if( getPair( dragable ) != null )
		{
			throw new ElementAlreadyExistsError();
		}

		_pairs.addFirst( new DNDPair( dragable, targets ) );
	}

	public DNDPair getPair( IDragable dragable )
	{
		int n = _pairs.size();

		DNDPair pair;

		while( --n > -1 )
		{
			pair = _pairs.get( n );

			if( pair.hasDragable( dragable ) )
			{
				return pair;
			}
		}

		return null;
	}

	@Override
	public void processEvent( @Nonnull UIEvent uiEvent ) throws UIHookInterrupt
	{
		if( ( uiEvent.type & UIEventType.ACTION ) != 0 )
		{
			switch( uiEvent.type )
			{
				case UIEventType.ACTION_BEGIN:
					onActionBegin( ( UIActionEvent ) uiEvent );
					break;

				case UIEventType.ACTION_MOVE:
					onActionMove( ( UIActionEvent ) uiEvent );
					break;

				case UIEventType.ACTION_END:
					onActionEnd( ( UIActionEvent ) uiEvent );
					break;

				case UIEventType.ACTION_DRAG_IN:
					onActionDragIn( ( UIActionEvent ) uiEvent );
					break;
				case UIEventType.ACTION_DRAG_OUT:
					onActionDragOut( ( UIActionEvent ) uiEvent );
					break;
			}
		}
	}

	private void onActionBegin( UIActionEvent event )
	{
		if( _dragging )
		{
			throw new GenericError( "DND. Shit" );
		}

		if( !( event.target instanceof IDragable ) )
		{
			return;
		}

		final IDragable element = ( IDragable ) event.target;

		_dragPair = getPair( element );

		if( _dragPair == null )
		{
			return;
		}

		_dragOrig = element.localToGlobal( new Point( ( float ) element.getX(), ( float ) element.getY() ) );

		_dragOffset.x = event.pos.x;
		_dragOffset.y = event.pos.y;

		_ghost.alpha( 0.6f );
		_ghost.moveTo( _dragOrig.x, _dragOrig.y );
		_ghost.resizeTo( ( float ) element.getWidth(), ( float ) element.getHeight() );
		//_ghost.skin = element.skin; //should clone the skin
		_ghost.setSkin( element.getSkin().clone() );

		_stage.addChild( _ghost );

		_dragging = true;
	}

	private void onActionMove( UIActionEvent event )
	{
		if( !_dragging )
		{
			return;
		}

		Point pos = event.pos;

		_ghost.moveTo( _dragOrig.x + ( pos.x - _dragOffset.x ), _dragOrig.y + ( pos.y - _dragOffset.y ) );
	}

	private void onActionEnd( UIActionEvent event )
	{
		if( !_dragging )
		{
			return;
		}

		_dragPair.processDragDone();

		_stage.removeChild( _ghost );

		_dragPair = null;
		_dragging = false;
	}

	private void onActionDragIn( UIActionEvent event )
	{
		if( !_dragging )
		{
			return;
		}

		_dragPair.processDragEnter( event.target );
	}

	private void onActionDragOut( UIActionEvent event )
	{
		if( !_dragging )
		{
			return;
		}

		_dragPair.processDragExit( event.target );
	}

	@Override
	public String toString()
	{
		return "[DnDManager]";
	}
}