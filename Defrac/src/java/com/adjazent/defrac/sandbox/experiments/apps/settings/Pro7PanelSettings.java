package com.adjazent.defrac.sandbox.experiments.apps.settings;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.core.notification.signals.ISignalReceiver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.core.notification.signals.Signals;
import com.adjazent.defrac.sandbox.experiments.apps.Pro7SignalTypes;
import com.adjazent.defrac.sandbox.experiments.apps.theme.Pro7Theme;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.widget.UIActionType;
import com.adjazent.defrac.ui.widget.button.UIButton;
import com.adjazent.defrac.ui.widget.list.UICellData;
import com.adjazent.defrac.ui.widget.list.UIList;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7PanelSettings extends UISurface implements IActionObserver, ISignalReceiver
{
	private UIButton _buttonAdd;
	private UIButton _buttonDel;

	private UIList _list;

	private UISurface _arrow;

	public Pro7PanelSettings()
	{
		super( Pro7Theme.get().createSkin( "PanelSettings" ) );

		resizeTo( 791, 284 );

		_arrow = Pro7Theme.get().createSurface( "PanelSettingsArrow", 183, 0, 23, 16 );
		addChild( _arrow );

		_list = Pro7Theme.get().createList();
		_list.resizeTo( 230, 185 );
		_list.moveTo( 4, 60 );
		addChild( _list );

		_buttonAdd = Pro7Theme.get().createButton( "ButtonListAdd", "ButtonListAdd" );
		_buttonAdd.resizeTo( 16, 16 );
		_buttonAdd.moveTo( 12, 257 );
		_buttonAdd.onClick.add( this );
		addChild( _buttonAdd );

		_buttonDel = Pro7Theme.get().createButton( "ButtonListDelete", "ButtonListDelete" );
		_buttonDel.resizeTo( 16, 16 );
		_buttonDel.moveTo( 202, 257 );
		_buttonDel.onClick.add( this );
		addChild( _buttonDel );

		for( int i = 0; i < 4; ++i )
		{
			addCell( "Ebene " + _list.getNumItems() );
		}
		Signals.addReceiver( this );
	}

	public void onSignal( int signalType, ISignalSource signalSource )
	{
		switch( signalType )
		{
			case Pro7SignalTypes.SCENE_EDIT_SETTINGS_A:
				_arrow.moveTo( 183, 0 );
				break;
			case Pro7SignalTypes.SCENE_EDIT_SETTINGS_B:
				_arrow.moveTo( 570, 0 );
				break;
		}
	}

	@Override
	public void onActionEvent( Action action )
	{
		if( action.type == UIActionType.BUTTON_CLICK )
		{
			if( action.origin == _buttonAdd )
			{
				addCell( "Ebene " + _list.getNumItems() );
			}
			if( action.origin == _buttonDel )
			{
				info( Context.DEFAULT, "Remove cell from list" );
			}
		}
	}

	private void addCell( String text )
	{
		_list.addItem( new UICellData( text, 20 ) );
	}

	@Override
	public String toString()
	{
		return "[Pro7PanelSettings]";
	}
}