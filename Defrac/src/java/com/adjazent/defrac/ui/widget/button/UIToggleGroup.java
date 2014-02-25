package com.adjazent.defrac.ui.widget.button;

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
		final int n = buttons.length;

		for( int i = 0; i < n; ++i )
		{
			if( buttons[ i ] instanceof UIToggleButton )
			{
				UIToggleButton button = ( UIToggleButton ) buttons[ i ];

				button.setLockIfSelected( true );
				button.onSelect.add( this );

				_buttons.addLast( button );
			}
		}
	}

	@Override
	public void onActionEvent( Action action )
	{
		Object origin = action.getOrigin();

		if( _buttons.indexOf( origin ) == -1 )
		{
			return;
		}

		if( action.getType() == UIActionType.BUTTON_SELECT )
		{
			if( origin != _current )
			{
				if( _current != null )
				{
					onSelect.send( _current );

					_current.setSelected( false );
				}

				_current = ( UIToggleButton ) origin;

				onSelect.send( _current );
			}

		}
	}

	@Override
	public String toString()
	{
		return "[UIToggleGroup]";
	}
}

