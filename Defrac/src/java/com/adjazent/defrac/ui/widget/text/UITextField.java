package com.adjazent.defrac.ui.widget.text;

import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import defrac.display.event.UIEvent;
import defrac.display.event.UIEventType;
import defrac.event.Events;
import defrac.event.KeyboardEvent;
import defrac.lang.Procedure;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextField extends UITextElement
{
	private static final int SPECIAL = 49; // all beneath this key code are special codes
	private static final int DEL = 8;
	private static final int LEFT = 37;
	private static final int RIGHT = 39;
	private static final int UP = 38;
	private static final int DOWN = 40;

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

	private boolean _enabled = true;

	public UITextField( IUISkin skin, UITextFormat textFormat )
	{
		super( skin, textFormat );
	}

	@Override
	protected void processEvent( @Nonnull final UIEvent uiEvent )
	{
		super.processEvent( uiEvent );

		if( uiEvent.target != this || ( uiEvent.type & UIEventType.FOCUS ) == 0 )
		{
			return;
		}

		if( uiEvent.type == UIEventType.FOCUS_IN )
		{
			Events.onKeyDown.attach( keyDownHandler );
			Events.onKeyUp.attach( keyUpHandler );
		}
		else if( uiEvent.type == UIEventType.FOCUS_OUT )
		{
			Events.onKeyDown.detach( keyDownHandler );
			Events.onKeyUp.detach( keyUpHandler );
		}
	}

	private void onKeyDown( KeyboardEvent event )
	{
		int keyCode = event.keyCode;
		int caretIndex = getCaretIndex();
		StringBuilder s = new StringBuilder( getText() );

		final int i0 = getSelectionFirst();
		final int i1 = getSelectionLast();


		if( keyCode < SPECIAL )
		{
			switch( keyCode )
			{
				case DEL:
					if( i0 > -1 && i1 > 0 )
					{
						s.delete( i0, i1+1 );
						processor.setText( s.toString() );
						setSelection( -1, -1 );
						setCaretIndex( i0 );
					}
					else if( caretIndex > -1 && s.length() > 0 )
					{
						s.deleteCharAt( caretIndex );
						processor.setText( s.toString() );
						setCaretIndex( --caretIndex );
					}

					return;

				case LEFT:
					setCaretIndex( --caretIndex );
					return;

				case RIGHT:
					setCaretIndex( ++caretIndex );
					return;
			}
		}
		else
		{
			char c = UIGlyph.codeToChar( keyCode );
			s.insert( ++caretIndex, c );
			processor.setText( s.toString() );
			setCaretIndex( caretIndex );
		}
	}

	private void onKeyUp( KeyboardEvent event )
	{
	}

	public boolean getEnabled()
	{
		return _enabled;
	}

	public void setEnabled( boolean _enabled )
	{
		this._enabled = _enabled;
	}

	@Override
	public String toString()
	{
		return "[UITextField id:" + id + "]";
	}
}