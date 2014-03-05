package com.adjazent.defrac.ui.widget.list;

import com.adjazent.defrac.core.error.GenericError;
import defrac.display.event.UIEvent;
import defrac.display.event.UIEventType;
import defrac.display.event.UIHookInterrupt;
import defrac.display.event.UIProcessHook;
import defrac.event.EnterFrameEvent;
import defrac.event.Events;
import defrac.event.KeyboardEvent;
import defrac.lang.Procedure;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIListInteractions implements UIProcessHook
{
	private final Procedure<KeyboardEvent> keyDownHandler = new Procedure<KeyboardEvent>()
	{
		@Override
		public void apply( KeyboardEvent event )
		{
			onKeyDown( event );
		}
	};

	private final Procedure<KeyboardEvent> keyUpHandler = new Procedure<KeyboardEvent>()
	{
		@Override
		public void apply( KeyboardEvent event )
		{
			onKeyUp( event );
		}
	};

	private final Procedure<EnterFrameEvent> enterFrameHandler = new Procedure<EnterFrameEvent>()
	{
		@Override
		public void apply( EnterFrameEvent event )
		{
			onEnterFrame();
		}
	};

	private final int SPEED_SLOW = 3;
	private final int SPEED_FAST = 12;

	private UIList _list;

	private int _scrollSpeed = SPEED_SLOW;
	private int _keyCode;

	public UIListInteractions()
	{
	}

	@Override
	public void processEvent( @Nonnull UIEvent uiEvent ) throws UIHookInterrupt
	{
		if( uiEvent.currentTarget == _list )
		{
			if( uiEvent.type == UIEventType.FOCUS_IN )
			{
				Events.onKeyDown.attach( keyDownHandler );
				Events.onKeyUp.attach( keyUpHandler );
				Events.onEnterFrame.attach( enterFrameHandler );
			}
			else if( uiEvent.type == UIEventType.FOCUS_OUT )
			{
				Events.onKeyDown.detach( keyDownHandler );
				Events.onKeyUp.detach( keyUpHandler );
				Events.onEnterFrame.detach( enterFrameHandler );
			}
		}
	}

	void attach( UIList list )
	{
		if( _list != null )
		{
			throw new GenericError( this + " is already attached to: " + _list + " .Ignoring." );
		}

		_list = list;

		_list.addProcessHook( this );

	}

	void detach( UIList list )
	{
		if( _list == null )
		{
			throw new GenericError( this + " is not attached. Ignoring." );
		}

		_list.removeProcessHook( this );
		_list = null;
	}

	private void onEnterFrame()
	{
		if( _keyCode == 38 )
		{
			_list.setOffset( _list.getOffset() - _scrollSpeed );
		}
		if( _keyCode == 40 )
		{
			_list.setOffset( _list.getOffset() + _scrollSpeed );
		}
	}

	private void onKeyDown( KeyboardEvent event )
	{
		_keyCode = event.keyCode;

		if( event.keyCode == 16 )
		{
			_scrollSpeed = SPEED_FAST;
		}
	}

	private void onKeyUp( KeyboardEvent event )
	{
		_keyCode = 0;

		if( event.keyCode == 16 )
		{
			_scrollSpeed = SPEED_SLOW;
		}
	}

	@Override
	public String toString()
	{
		return "[UIListInteractions]";
	}
}