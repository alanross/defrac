package com.adjazent.defrac.sandbox.apps.lite.core.dnd;

import com.adjazent.defrac.core.error.SingletonError;
import defrac.display.Stage;
import defrac.display.event.*;
import defrac.geom.Point;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteDnDManager implements UIProcessHook
{
	private static LiteDnDManager _instance;

	private ILiteDragItem _currentDrag = null;
	private ILiteDropTarget _currentDrop = null;
	private UIEventTarget _lastTarget; //hack, for more info see onActionEnd
	boolean _dropAccepted = false;

	private Point _dragStart = new Point();
	private Point _dragOffset = new Point();

	private LiteDnDGhost _ghost;
	private Stage _stage;

	public static void initialize( Stage stage, LiteDnDGhost ghost )
	{
		if( _instance != null )
		{
			throw new SingletonError( "LiteDnDManager" );
		}

		_instance = new LiteDnDManager( stage, ghost );
	}

	public LiteDnDManager( Stage stage, LiteDnDGhost ghost )
	{
		_ghost = ghost;
		_stage = stage;
		_stage.addProcessHook( this );
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

			_lastTarget = uiEvent.target;
		}
	}

	private void onActionBegin( UIActionEvent event )
	{
		if( !( event.target instanceof ILiteDragItem ) )
		{
			return;
		}

		_currentDrag = ( ILiteDragItem ) event.target;

		_dragStart = _currentDrag.localToGlobal( new Point( _currentDrag.x(), _currentDrag.y() ) );

		_dragOffset.set( event.pos.x, event.pos.y );

		_dropAccepted = false;

		_ghost.onDragStart(
				_dragStart.x,
				_dragStart.y,
				_currentDrag.width(),
				_currentDrag.height(),
				_currentDrag.getDnDSkin().clone()
		);

		_stage.addChild( _ghost );
	}

	private void onActionMove( UIActionEvent event )
	{
		if( _currentDrag != null )
		{
			_ghost.moveTo( _dragStart.x + ( event.pos.x - _dragOffset.x ), _dragStart.y + ( event.pos.y - _dragOffset.y ) );
		}
	}

	private void onActionEnd( UIActionEvent event )
	{
		if( _currentDrag == null )
		{
			return;
		}

		_stage.removeChild( _ghost );

		//--------------------------
		// currently dragOut action is dispatched before end action is dispatched.
		// end action then has drag as target not drop. So we keep the target of
		// the last event and use it here to check if it – or any parent of it –
		// is of type ILiteDropTarget and guestimate that this was the one that we want.
		//
		// dnd manager has to be rewritten once native dnd events are in place.
		//--------------------------

		if( !_dropAccepted )
		{
			return;
		}

		UIEventTarget t = _lastTarget;

		while( t != null )
		{
			if( t instanceof ILiteDropTarget )
			{
				( ( ILiteDropTarget ) t ).onDragDone( _currentDrag, new Point( _ghost.x(), _ghost.y() ) );
			}

			t = t.eventParent();
		}
		//--------------------------

		_currentDrag = null;
		_currentDrop = null;
	}

	private void onActionDragIn( UIActionEvent event )
	{
		// only consider targets that are of type ILiteDropTarget
		if( _currentDrag == null || !( event.target instanceof ILiteDropTarget ) )
		{
			return;
		}

		_currentDrop = ( ILiteDropTarget ) event.target;

		_dropAccepted = _currentDrop.onDragIn( _currentDrag, new Point( _ghost.x(), _ghost.y() ) );

		_ghost.onDropTargetIn( _dropAccepted );
	}

	private void onActionDragOut( UIActionEvent event )
	{
		if( _currentDrag == null || _currentDrop == null )
		{
			return;
		}

		// ignore out if out occurs on child of current drop target.
		UIEventTarget t = event.target.eventParent();

		while( t != null )
		{
			if( t instanceof ILiteDropTarget )
			{
				return;
			}

			t = t.eventParent();
		}

		_ghost.onDropTargetOut();

		_currentDrop.onDragOut( _currentDrag, new Point( _ghost.x(), _ghost.y() ) );
		_currentDrop = null;
	}

	@Override
	public String toString()
	{
		return "[LiteDnDManager]";
	}
}