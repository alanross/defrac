package com.adjazent.defrac.sandbox.experiments.apps.input;

import com.adjazent.defrac.core.notification.signals.ISignalReceiver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.core.notification.signals.Signals;
import com.adjazent.defrac.sandbox.experiments.apps.Pro7SignalTypes;
import com.adjazent.defrac.sandbox.experiments.apps.dnd.IDragable;
import com.adjazent.defrac.sandbox.experiments.apps.theme.Pro7Theme;
import com.adjazent.defrac.sandbox.experiments.apps.video.Pro7VideoSourceManager;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.surface.skin.UISkinFactory;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7PanelInputs extends UISurface implements ISignalReceiver
{
	private UISurface _inputSlot1;
	private UISurface _inputSlot2;
	private UISurface _inputSlot3;
	private UISurface _inputSlot4;

	private UISurface _arrow;

	private Pro7DemoInput _demoContent1;
	private Pro7DemoInput _demoContent2;
	private Pro7DemoInput _demoContent3;

	public Pro7PanelInputs()
	{
		super( Pro7Theme.get().createSkin( "PanelGeneric" ) );

		resizeTo( 788, 128 );

		_inputSlot1 = Pro7Theme.get().createSurface( "AreaInput", 12, 12, 184, 103 );
		addChild( _inputSlot1 );

		_inputSlot2 = Pro7Theme.get().createSurface( "AreaInput", 206, 12, 184, 103 );
		addChild( _inputSlot2 );

		_inputSlot3 = Pro7Theme.get().createSurface( "AreaInput", 400, 12, 184, 103 );
		addChild( _inputSlot3 );

		_inputSlot4 = Pro7Theme.get().createSurface( "AreaInput", 594, 12, 184, 103 );
		addChild( _inputSlot4 );

		_arrow = Pro7Theme.get().createSurface( "PanelGenericArrow", 0, 0, 17, 12 );
		addChild( _arrow );

		IUISkin skin;

		skin = UISkinFactory.create( Pro7VideoSourceManager.get().createTextureTile( 400, 100, 180, 100 ) );
		_demoContent1 = new Pro7DemoInput( skin );
		_demoContent1.moveTo( 2, 2 );
		_demoContent1.id = "DEMO 1";
		_inputSlot1.addChild( _demoContent1 );

		skin = UISkinFactory.create( Pro7VideoSourceManager.get().createTextureTile( 200, 200, 180, 100 ) );
		_demoContent2 = new Pro7DemoInput( skin );
		_demoContent2.moveTo( 2, 2 );
		_demoContent2.id = "DEMO 2";
		_inputSlot2.addChild( _demoContent2 );

		skin = UISkinFactory.create( Pro7VideoSourceManager.get().createTextureTile( 0, 0, 180, 100 ) );
		_demoContent3 = new Pro7DemoInput( skin );
		_demoContent3.moveTo( 2, 2 );
		_demoContent3.id = "DEMO 3";
		_inputSlot3.addChild( _demoContent3 );

		Signals.addReceiver( this );
	}

	@Override
	public void onSignal( int signalType, ISignalSource signalSource )
	{
		switch( signalType )
		{
			case Pro7SignalTypes.INPUT_TYPE_LIVE:
				_arrow.moveTo( 24, -9 );
				break;
			case Pro7SignalTypes.INPUT_TYPE_ON_DEMAND:
				_arrow.moveTo( 70, -9 );
				break;
			case Pro7SignalTypes.INPUT_TYPE_IMAGE:
				_arrow.moveTo( 116, -9 );
				break;
			case Pro7SignalTypes.INPUT_TYPE_USER:
				_arrow.moveTo( 162, -9 );
				break;
		}
	}

	public IDragable getDemoContent1()
	{
		return _demoContent1;
	}

	public IDragable getDemoContent2()
	{
		return _demoContent2;
	}

	public IDragable getDemoContent3()
	{
		return _demoContent3;
	}

	@Override
	public String toString()
	{
		return "[Pro7PanelInputs]";
	}
}

