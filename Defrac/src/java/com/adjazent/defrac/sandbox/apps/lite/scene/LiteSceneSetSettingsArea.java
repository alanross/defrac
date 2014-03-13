package com.adjazent.defrac.sandbox.apps.lite.scene;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.core.notification.signals.ISignalReceiver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteState;
import com.adjazent.defrac.sandbox.apps.lite.core.data.ILiteSceneObserver;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteScene;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteSceneElement;
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
public final class LiteSceneSetSettingsArea extends UISurface implements IActionObserver, ISignalReceiver, ILiteSceneObserver
{
	private UIButton _buttonAdd;
	private UIButton _buttonDel;

	private UIList _list;

	private UISurface _arrow;

	private LiteScene _model;

	public LiteSceneSetSettingsArea()
	{
		super( LiteCore.ui.createSkin( "PanelSettings" ) );

		resizeTo( 791, 284 );

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

		LiteCore.state.addReceiver( this );
	}

	@Override
	public void onActionEvent( Action action )
	{
		if( action.type == UIActionType.BUTTON_CLICK )
		{
			if( action.origin == _buttonAdd )
			{
				info( Context.DEFAULT, "Add cell to list" );
			}
			if( action.origin == _buttonDel )
			{
				info( Context.DEFAULT, "Remove cell from list" );
			}
		}
		if( action.type == UIActionType.CELL_SELECT )
		{
			if( action.origin == _list )
			{
				( ( LiteCellData ) _list.getLastSelectedItem() ).model.selected( true );
			}
		}
	}

	@Override
	public void onSignal( ISignalSource signalSource, int signalType )
	{
		if( signalType == LiteState.SELECT_SCENE_SET || signalType == LiteState.SELECT_SCENE_SLOT )
		{
			int type = LiteCore.state.sceneSlotType();

			if( type == LiteState.STATE_SCENE_A )
			{
				_arrow.moveTo( 183, 0 );

				populate( LiteCore.state.sceneSet().a );
			}
			if( type == LiteState.STATE_SCENE_B )
			{
				_arrow.moveTo( 570, 0 );

				populate( LiteCore.state.sceneSet().b );
			}
		}
	}

	@Override
	public void onLiteSceneModified( LiteSceneElement item, int type )
	{
		if( type == LiteScene.ELEMENT_ADDED )
		{
			add( item );
		}
		if( type == LiteScene.ELEMENT_REMOVED )
		{
			remove( item );
		}
		if( type == LiteScene.ELEMENT_SELECTED )
		{
			select( item );
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

		for( int j = 0; j < _model.numElements(); j++ )
		{
			add( _model.get( j ) );
		}
	}

	private void add( LiteSceneElement element )
	{
		_list.addItem( new LiteCellData( element ) );
	}

	private void remove( LiteSceneElement element )
	{
		UICellData cellData = getAssociatedData( element );

		if( element != null )
		{
			_list.removeItem( cellData );
		}
	}

	private void select( LiteSceneElement element )
	{
		UICellData cellData = getAssociatedData( element );

		if( element != null )
		{
			cellData.selected( true );
		}
	}

	private LiteCellData getAssociatedData( LiteSceneElement element )
	{
		for( int i = 0; i < _list.numItems(); i++ )
		{
			LiteCellData cellData = ( LiteCellData ) _list.getItem( i );

			if( cellData.model == element )
			{
				return cellData;
			}
		}

		return null;
	}

	@Override
	public String toString()
	{
		return "[LiteSceneSetSettingsArea]";
	}

	private class LiteCellData extends UICellData
	{
		final LiteSceneElement model;

		LiteCellData( LiteSceneElement model )
		{
			super( model.id, 20 );

			this.model = model;
		}
	}
}