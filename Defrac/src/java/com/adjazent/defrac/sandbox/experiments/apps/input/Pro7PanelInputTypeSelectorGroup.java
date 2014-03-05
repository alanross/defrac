package com.adjazent.defrac.sandbox.experiments.apps.input;

import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.core.notification.signals.Signals;
import com.adjazent.defrac.sandbox.experiments.apps.Pro7SignalTypes;
import com.adjazent.defrac.sandbox.experiments.apps.theme.Pro7Theme;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.widget.UIActionType;
import com.adjazent.defrac.ui.widget.button.UIToggleButton;
import com.adjazent.defrac.ui.widget.button.UIToggleGroup;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7PanelInputTypeSelectorGroup extends UISurface implements ISignalSource, IActionObserver
{
	private UIToggleGroup _groupInputs;

	private UIToggleButton _buttonLive;
	private UIToggleButton _buttonOnDemand;
	private UIToggleButton _buttonImage;
	private UIToggleButton _buttonUser;

	public Pro7PanelInputTypeSelectorGroup()
	{
		super();

		resizeTo( 180, 48 );

		_buttonLive = Pro7Theme.get().createToggleButton( "ButtonSourceLiveDeselected", "ButtonSourceLiveSelected" );
		_buttonLive.resizeTo( 45, 36 );
		_buttonLive.moveTo( 0, 0 );
		addChild( _buttonLive );

		_buttonOnDemand = Pro7Theme.get().createToggleButton( "ButtonSourceOnDemandDeselected", "ButtonSourceOnDemandSelected" );
		_buttonOnDemand.resizeTo( 45, 36 );
		_buttonOnDemand.moveTo( 45, 0 );
		addChild( _buttonOnDemand );

		_buttonImage = Pro7Theme.get().createToggleButton( "ButtonSourceImagesDeselected", "ButtonSourceImagesSelected" );
		_buttonImage.resizeTo( 45, 36 );
		_buttonImage.moveTo( 90, 0 );
		addChild( _buttonImage );

		_buttonUser = Pro7Theme.get().createToggleButton( "ButtonSourceUserDeselected", "ButtonSourceUserSelected" );
		_buttonUser.resizeTo( 45, 36 );
		_buttonUser.moveTo( 135, 0 );
		addChild( _buttonUser );

		_groupInputs = new UIToggleGroup( _buttonLive, _buttonOnDemand, _buttonImage, _buttonUser );
		_groupInputs.onSelect.add( this );
	}

	@Override
	public void onActionEvent( Action action )
	{
		if( action.type == UIActionType.BUTTON_SELECT )
		{
			if( action.origin == _buttonLive )
			{
				Signals.send( Pro7SignalTypes.INPUT_TYPE_LIVE, this );
			}
			if( action.origin == _buttonOnDemand )
			{
				Signals.send( Pro7SignalTypes.INPUT_TYPE_ON_DEMAND, this );
			}
			if( action.origin == _buttonImage )
			{
				Signals.send( Pro7SignalTypes.INPUT_TYPE_IMAGE, this );
			}
			if( action.origin == _buttonUser )
			{
				Signals.send( Pro7SignalTypes.INPUT_TYPE_USER, this );
			}
		}
	}

	public void selectLive()
	{
		_buttonLive.setSelected( true );
	}

	public void selectOnDemand()
	{
		_buttonOnDemand.setSelected( true );
	}

	public void selectImage()
	{
		_buttonImage.setSelected( true );
	}

	public void selectUser()
	{
		_buttonUser.setSelected( true );
	}

	@Override
	public String toString()
	{
		return "[Pro7PanelInputTypeSelectorGroup]";
	}
}

