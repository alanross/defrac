package com.adjazent.defrac.sandbox.apps.lite.playout;

import com.adjazent.defrac.core.notification.signals.ISignalReceiver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteData;
import com.adjazent.defrac.sandbox.apps.lite.scene.editor.LiteSceneViewer;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.widget.button.UIButton;
import com.adjazent.defrac.ui.widget.button.UIToggleButton;
import com.adjazent.defrac.ui.widget.text.UITextField;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LitePlayoutArea extends UISurface implements ISignalReceiver
{
	private UISurface _areaChallenge;
	private LiteSceneViewer _videoPlayer;
	private UIButton _buttonChallenge;
	private UIToggleButton _buttonRecord;
	private UIButton _buttonDelete;
	private UIButton _buttonSubmit;

	private UITextField _textField;
	private UISurface _textArea;

	public LitePlayoutArea()
	{
		super( LiteCore.ui.createSkin( "PanelGeneric" ) );

		resizeTo( 458, 718 );

		_areaChallenge = LiteCore.ui.createSurface( "AreaEmpty", 17, 12, 423, 103 );
		addChild( _areaChallenge );

		_videoPlayer = new LiteSceneViewer( LiteCore.ui.createSkin( "AreaEmpty" ) );
		_videoPlayer.moveTo( 17, 180 );
		_videoPlayer.resizeTo( 423, 240 );
		addChild( _videoPlayer );

		_buttonChallenge = LiteCore.ui.createButton( "ButtonChallenge", "ButtonChallenge" );
		_buttonChallenge.resizeTo( 423, 24 );
		_buttonChallenge.moveTo( 17, 116 );
		addChild( _buttonChallenge );

		_buttonRecord = LiteCore.ui.createToggleButton( "ButtonPlayoutRec", "ButtonPlayoutRec" );
		_buttonRecord.resizeTo( 124, 36 );
		_buttonRecord.moveTo( 15, 448 );
		addChild( _buttonRecord );

		_buttonDelete = LiteCore.ui.createButton( "ButtonPlayoutDelete", "ButtonPlayoutDelete" );
		_buttonDelete.resizeTo( 114, 36 );
		_buttonDelete.moveTo( 330, 448 );
		addChild( _buttonDelete );

		_buttonSubmit = LiteCore.ui.createButton( "ButtonPlayoutSubmit", "ButtonPlayoutSubmit" );
		_buttonSubmit.resizeTo( 114, 42 );
		_buttonSubmit.moveTo( 330, 662 );
		addChild( _buttonSubmit );

		_textField = new UITextField( LiteCore.ui.createSkin( "TextInputBackground" ), new UITextFormat( "Helvetica14" ) );
		_textField.resizeTo( 300, 32 );
		_textField.moveTo( 140, 502 );
		_textField.setText( "Your text goes here." );
		addChild( _textField );

		_textArea = new UISurface( LiteCore.ui.createSkin( "TextInputBackground" ) );
		_textArea.resizeTo( 300, 96 );
		_textArea.moveTo( 140, 552 );
		addChild( _textArea );

		LiteCore.data.addReceiver( this );
	}

	@Override
	public void onSignal( ISignalSource signalSource, int signalType )
	{
		if( signalType == LiteData.SELECT_SCENE_SET || signalType == LiteData.SELECT_SCENE_SLOT )
		{
			int type = LiteCore.data.selectedSceneSlotId();

			if( type == LiteData.SCENE_SLOT_A )
			{
				_videoPlayer.populate( LiteCore.data.selectedSceneSet().a );
			}
			if( type == LiteData.SCENE_SLOT_B )
			{
				_videoPlayer.populate( LiteCore.data.selectedSceneSet().b );
			}
		}
	}

	@Override
	public String toString()
	{
		return "[LitePlayoutArea]";
	}
}