package com.adjazent.defrac.sandbox.apps.lite.scene;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.core.notification.signals.ISignalReceiver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteState;
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
public final class LiteSceneSetSettingsArea extends UISurface implements IActionObserver, ISignalReceiver
{
	private UIButton _buttonAdd;
	private UIButton _buttonDel;

	private UIList _list;

	private UISurface _arrow;

	public LiteSceneSetSettingsArea()
	{
		super( LiteCore.ui.createSkin( "PanelSettings" ) );

		resizeTo( 791, 284 );

		_arrow = LiteCore.ui.createSurface( "PanelSettingsArrow", 183, 0, 23, 16 );

		_list = LiteCore.ui.createList();
		_list.resizeTo( 230, 185 );
		_list.moveTo( 4, 60 );

		_buttonAdd = LiteCore.ui.createButton( "ButtonListAdd", "ButtonListAdd" );
		_buttonAdd.resizeTo( 16, 16 );
		_buttonAdd.moveTo( 12, 257 );
		_buttonAdd.onClick.add( this );

		_buttonDel = LiteCore.ui.createButton( "ButtonListDelete", "ButtonListDelete" );
		_buttonDel.resizeTo( 16, 16 );
		_buttonDel.moveTo( 202, 257 );
		_buttonDel.onClick.add( this );

		addChild( _arrow );
		addChild( _list );
		addChild( _buttonAdd );
		addChild( _buttonDel );

		for( int i = 0; i < 3; ++i )
		{
			addCell( "Ebene " + _list.getNumItems() );
		}

		LiteCore.state.addReceiver( this );
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

	@Override
	public void onSignal( ISignalSource signalSource, int signalType )
	{
		if( signalType == LiteState.SELECT_SCENE_SLOT )
		{
			int type = LiteCore.state.sceneSlot();

			if( type == LiteState.STATE_SCENE_A )
			{
				_arrow.moveTo( 183, 0 );
			}
			if( type == LiteState.STATE_SCENE_B )
			{
				_arrow.moveTo( 570, 0 );
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
		return "[LiteSceneSetSettingsArea]";
	}
}