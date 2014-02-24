package com.adjazent.defrac.ui.widget.pro7;

import com.adjazent.defrac.core.notification.signals.ISignalReceiver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.core.notification.signals.Signals;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIToggleGroup implements ISignalReceiver
{
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
				_buttons.addLast( button );

			}
		}

		Signals.addReceiver( this );
	}

	public void onSignal( int signalType, ISignalSource signalSource )
	{
		if( signalType == UISignalTypes.BUTTON_SELECT )
		{
			if( _buttons.indexOf( signalSource ) == -1 )
			{
				return;
			}

			if( signalSource != _current )
			{
				UIToggleButton button = ( UIToggleButton ) signalSource;

				if( button != null )
				{
					if( _current != null )
					{
						_current.setSelected( false );
					}

					_current = button;

					Signals.send( UISignalTypes.BUTTON_GROUP_SELECT, _current );
				}
			}
		}
	}

	@Override
	public String toString()
	{
		return "[UIToggleGroup]";
	}
}

