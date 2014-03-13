package com.adjazent.defrac.ui.widget.button;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;
import com.adjazent.defrac.core.error.NullError;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.ui.widget.UIActionType;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIToggleGroup implements IActionObserver
{
	public final Action onSelect = new Action( UIActionType.BUTTON_SELECT );

	private LinkedList<UIToggleButton> _buttons = new LinkedList<UIToggleButton>();
	private UIToggleButton _current;

	public UIToggleGroup( Object... buttons )
	{
		for( Object o : buttons )
		{
			if( o instanceof UIToggleButton )
			{
				add( ( UIToggleButton ) o );
			}
		}
	}

	@Override
	public void onActionEvent( Action action )
	{
		if( action.type == UIActionType.BUTTON_SELECT )
		{
			if( action.origin != _current )
			{
				if( _current != null )
				{
					_current.selected( false );
				}

				_current = ( UIToggleButton ) action.origin;

				onSelect.send( _current );
			}
		}
	}

	public UIToggleButton add( UIToggleButton toggleButton )
	{
		if( null == toggleButton )
		{
			throw new NullError( this + " button can not be null" );
		}

		if( has( toggleButton ) )
		{
			throw new ElementAlreadyExistsError( this + " " + toggleButton + " already exists" );
		}

		toggleButton.lockIfSelected( true );
		toggleButton.onSelect.add( this );

		_buttons.addLast( toggleButton );

		return toggleButton;
	}

	public UIToggleButton remove( UIToggleButton toggleButton )
	{
		if( null == toggleButton )
		{
			throw new NullError( this + " button can not be null" );
		}

		if( !has( toggleButton ) )
		{
			throw new ElementDoesNotExistError( this + " " + toggleButton + " does not exist" );
		}

		toggleButton.onSelect.remove( this );

		_buttons.remove( toggleButton );

		return toggleButton;
	}

	public boolean has( UIToggleButton toggleButton )
	{
		if( null == toggleButton )
		{
			throw new NullError( this + " observer can not be null" );
		}

		return ( -1 != _buttons.indexOf( toggleButton ) );
	}

	public UIToggleButton get( int index )
	{
		return _buttons.get( index );
	}

	public UIToggleButton getSelected()
	{
		return _current;
	}

	public int getSelectedIndex()
	{
		return ( _current != null ) ? _buttons.indexOf( _current ) : -1;
	}

	@Override
	public String toString()
	{
		return "[UIToggleGroup]";
	}
}

