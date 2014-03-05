package com.adjazent.defrac.sandbox.experiments.apps.playout;

import com.adjazent.defrac.sandbox.experiments.apps.theme.Pro7Theme;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.widget.button.UIButton;
import com.adjazent.defrac.ui.widget.button.UIToggleButton;
import com.adjazent.defrac.ui.widget.text.UILabel;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7PanelPlayout extends UISurface
{
	private UISurface _areaChallenge;

	private Pro7VideoPlayer _videoPlayer;

	private UIButton _buttonChallenge;
	private UIToggleButton _buttonRecord;
	private UIButton _buttonDelete;
	private UIButton _buttonSubmit;

	private UILabel _label;
//	private UITextField _textField;
//	private UITextArea _textArea;

	public Pro7PanelPlayout()
	{
		super( Pro7Theme.get().createSkin( "PanelGeneric" ) );

		resizeTo( 458, 718 );

		_areaChallenge = Pro7Theme.get().createSurface( "AreaEmpty", 17, 12, 423, 103 );
		addChild( _areaChallenge );

		_videoPlayer = new Pro7VideoPlayer();
		_videoPlayer.moveTo( 17, 180 );
		addChild( _videoPlayer );

		_buttonChallenge = Pro7Theme.get().createButton( "ButtonChallenge", "ButtonChallenge" );
		_buttonChallenge.resizeTo( 423, 24 );
		_buttonChallenge.moveTo( 17, 116 );
		addChild( _buttonChallenge );

		_buttonRecord = Pro7Theme.get().createToggleButton( "ButtonPlayoutRec", "ButtonPlayoutRec" );
		_buttonRecord.resizeTo( 124, 36 );
		_buttonRecord.moveTo( 15, 448 );
		addChild( _buttonRecord );

		_buttonDelete = Pro7Theme.get().createButton( "ButtonPlayoutDelete", "ButtonPlayoutDelete" );
		_buttonDelete.resizeTo( 114, 36 );
		_buttonDelete.moveTo( 330, 448 );
		addChild( _buttonDelete );

		_buttonSubmit = Pro7Theme.get().createButton( "ButtonPlayoutSubmit", "ButtonPlayoutSubmit" );
		_buttonSubmit.resizeTo( 114, 42 );
		_buttonSubmit.moveTo( 330, 662 );
		addChild( _buttonSubmit );

		_label = Pro7Theme.get().createLabelSmall();
		_label.setAutoSize( false );
		_label.setSize( 100, 32 );
		_label.moveTo( 20, 502 );
		_label.setText( "Label" );
		addChild( _label );
//
//		_textField = new UITextField( Pro7Theme.get().createSkin( "TextInputBackground" ), new TextFormat( "verdana", 14, 0x898989, false ) );
//		_textField.resizeTo( 300, 32 );
//		_textField.moveTo( 140, 502 );
//		_textField.text = "TextField";
//		addChild( _textField );
//
//		_textArea = new UITextArea( Pro7Theme.get().createSkin( "TextInputBackground" ), new TextFormat( "verdana", 14, 0x898989, false ) );
//		_textArea.resizeTo( 300, 96 );
//		_textArea.moveTo( 140, 552 );
//		_textArea.text = "TextArea";
//		addChild( _textArea );
	}

	@Override
	public String toString()
	{
		return "[Pro7PanelPlayout]";
	}
}

