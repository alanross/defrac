package com.adjazent.defrac.sandbox.apps.lite.scene;

import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.core.notification.signals.ISignalReceiver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteData;
import com.adjazent.defrac.sandbox.apps.lite.core.data.ILiteSceneObserver;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteScene;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteSceneItem;
import com.adjazent.defrac.sandbox.apps.lite.scene.settings.LiteSceneItemCellData;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.widget.UIActionType;
import com.adjazent.defrac.ui.widget.button.UIButton;
import com.adjazent.defrac.ui.widget.list.UIList;

import java.util.Hashtable;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSceneSetSettingsArea extends UISurface implements IActionObserver, ISignalReceiver, ILiteSceneObserver
{
	private UIButton _buttonAdd;
	private UIButton _buttonDel;
	private UIList _list;
	private UISurface _arrow;

	private Hashtable<LiteSceneItem, LiteSceneItemCellData> _items;
	private LiteScene _model;

	public LiteSceneSetSettingsArea()
	{
		super( LiteCore.ui.createSkin( "PanelSettings" ) );

		resizeTo( 791, 284 );

		_items = new Hashtable<LiteSceneItem, LiteSceneItemCellData>();

		_arrow = LiteCore.ui.createSurface( "PanelSettingsArrow", 183, 0, 23, 16 );

		_list = LiteCore.ui.createList();
		_list.resizeTo( 230, 185 );
		_list.moveTo( 4, 60 );
		_list.onSelect.add( this );

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

		LiteCore.data.addReceiver( this );
	}

	@Override
	public void onActionEvent( Action action )
	{
		if( action.type == UIActionType.BUTTON_CLICK )
		{
			if( action.origin == _buttonAdd )
			{
			}
			if( action.origin == _buttonDel )
			{
				if( _list.getLastSelectedItem() != null )
				{
					LiteSceneItemCellData d = ( LiteSceneItemCellData ) _list.getLastSelectedItem();

					_model.remove( d.model );
				}
			}
		}
		if( action.type == UIActionType.CELL_SELECT )
		{
			if( action.origin == _list )
			{
				( ( LiteSceneItemCellData ) _list.getLastSelectedItem() ).model.selected( true );
			}
		}
	}

	@Override
	public void onSignal( ISignalSource signalSource, int signalType )
	{
		if( signalType == LiteData.SELECT_SCENE_SET || signalType == LiteData.SELECT_SCENE_SLOT )
		{
			int type = LiteCore.data.selectedSceneSlotId();

			if( type == LiteData.SCENE_SLOT_A )
			{
				_arrow.moveTo( 183, 0 );

				populate( LiteCore.data.selectedSceneSet().a );
			}
			if( type == LiteData.SCENE_SLOT_B )
			{
				_arrow.moveTo( 570, 0 );

				populate( LiteCore.data.selectedSceneSet().b );
			}
		}
	}

	@Override
	public void onLiteSceneModified( LiteSceneItem item, int type )
	{
		if( type == LiteScene.ITEM_ATTACHED )
		{
			addItem( item );
		}
		if( type == LiteScene.ITEM_DETACHED )
		{
			removeItem( item );
		}
		if( type == LiteScene.ITEM_SELECTED )
		{
			selectItem( item );
		}
	}

	private void populate( LiteScene scene )
	{
		_list.removeAllItems();

		if( _model != null )
		{
			_model.removeObserver( this );
		}

		_model = scene;
		_model.addObserver( this );

		for( int j = 0; j < _model.numItems(); j++ )
		{
			addItem( _model.get( j ) );
		}
	}

	private void addItem( LiteSceneItem item )
	{
		LiteSceneItemCellData cellData = new LiteSceneItemCellData( item );

		_items.put( item, cellData );

		_list.addItem( cellData );
	}

	private void removeItem( LiteSceneItem item )
	{
		LiteSceneItemCellData cellData = _items.remove( item );

		_list.removeItem( cellData );
	}

	private void selectItem( LiteSceneItem item )
	{
		LiteSceneItemCellData cellData = _items.get( item );

		cellData.selected( true );
	}

	@Override
	public String toString()
	{
		return "[LiteSceneSetSettingsArea]";
	}
}