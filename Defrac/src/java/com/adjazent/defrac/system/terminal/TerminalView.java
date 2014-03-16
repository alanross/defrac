package com.adjazent.defrac.system.terminal;

import com.adjazent.defrac.core.utils.ArrayUtils;
import com.adjazent.defrac.core.utils.StringUtils;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.utils.render.IUIRenderListener;
import com.adjazent.defrac.ui.utils.render.UIRenderRequest;
import com.adjazent.defrac.ui.widget.text.UILabel;
import defrac.display.Layer;
import defrac.display.Quad;
import defrac.event.KeyboardEvent;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class TerminalView extends Layer implements ITerminalView, IUIRenderListener
{
	private final String prompt = "$ ";
	private final String empty = "";
	private final String newLine = "\n";
	private final String welcome = "Welcome!";

	private final Quad _background;
	private final UILabel _output;


	private final UIRenderRequest _renderRequest;

	private String _buffer = "";

	private int _historyIndex = -1;
	private String _historyTemp = "";

	/**
	 * Creates a new instance of TerminalView.
	 */
	public TerminalView( UITextFormat textFormat )
	{
		_renderRequest = new UIRenderRequest( this );

		_output = new UILabel( textFormat, true );
		_output.setText( welcome );

		_background = new Quad( 1, 1, 0x88000000 );

		addChild( _background );
		addChild( _output );

//		_prompt = new TextField();
//		_prompt.defaultTextFormat = textFormat;
//		_prompt.type = TextFieldType.DYNAMIC;
//		_prompt.gridFitType = GridFitType.NONE;
//		_prompt.autoSize = TextFieldAutoSize.LEFT;
//		_prompt.multiline = false;
//		_prompt.wordWrap = false;
//		_prompt.selectable = false;
//		_prompt.height = 19;
//		_prompt.text = prompt;
//
//		_input = new TextField();
//		_input.defaultTextFormat = textFormat;
//		_input.type = TextFieldType.INPUT;
//		_input.gridFitType = GridFitType.NONE;
//		_input.multiline = false;
//		_input.wordWrap = false;
//		_input.selectable = true;
//		_input.tabEnabled = false;
//		_input.height = 19;
//		_input.text = empty;
//
//		addChild( _output );
//		addChild( _prompt );
//		addChild( _input );

//		addEventListener( KeyboardEvent.KEY_UP, onKeyUp );
//		addEventListener( KeyboardEvent.KEY_DOWN, onKeyDown );
//		addEventListener( TextEvent.TEXT_INPUT, onTextInput );
	}

	/**
	 *
	 */
//	private void onTextInput( TextEvent event )
//	{
//		_historyTemp += event.text;
//	}

	/**
	 *
	 */
	private void onKeyDown( KeyboardEvent event )
	{

	}

	/**
	 *
	 */
	private void onKeyUp( KeyboardEvent event )
	{
//		switch( event.keyCode )
//		{
//			case Keyboard.ENTER:
//				triggerCommand( _input.text );
//				break;
//
//			case Keyboard.TAB:
//				findCandidates( _input.text );
//				break;
//
//			case Keyboard.PAGE_UP:
//				_output.scrollV -= ( _output.bottomScrollV - _output.scrollV );
//				break;
//
//			case Keyboard.PAGE_DOWN:
//				_output.scrollV += ( _output.bottomScrollV - _output.scrollV );
//				break;
//
//			case Keyboard.UP:
//				useHistory( _historyIndex - 1 );
//				break;
//
//			case Keyboard.DOWN:
//				useHistory( _historyIndex + 1 );
//				break;
//		}
	}

	/**
	 *
	 */
	private void triggerCommand( String input )
	{
		Terminal.exec( input );

//		//_input.text = empty;

		_historyIndex = Terminal.getHistory().size();
		_historyTemp = empty;
	}

	/**
	 *
	 */
	private void findCandidates( String input )
	{
		if( StringUtils.isEmpty( input ) )
		{
			return;
		}

		LinkedList<String> candidates = Terminal.findCandidates( input );

		if( candidates.size() == 0 )
		{
			write( empty );
		}
		else if( candidates.size() == 1 )
		{
//			_input.text = candidates.get( 0 );

//			int caretIndex = _input.length;
//			_input.setSelection( caretIndex, caretIndex );
		}
		else
		{
			write( ArrayUtils.join( candidates, "\t" ) );
		}
	}

	/**
	 *
	 */
	private void useHistory( int index )
	{
		LinkedList<CommandResult> h = Terminal.getHistory();

		if( h.size() == 0 )
		{
			return;
		}

		_historyIndex = index;

		if( index < 0 )
		{
//			_input.text = h[ 0 ].command;
			_historyIndex = 0;
		}
		else if( index >= h.size() )
		{
//			_input.text = _historyTemp;
			_historyIndex = h.size();
		}
		else
		{
//			_input.text = h[ index ].command;
		}

//		int caretIndex = _input.length;
//		_input.setSelection( caretIndex, caretIndex );
	}

	/**
	 *
	 */
	@Override
	public void render()
	{
		_output.setText( _buffer );
//		_output.scrollV = _output.bottomScrollV;
	}

	/**
	 *
	 */
	public void resizeTo( float width, float height )
	{
		_background.scaleToSize( width, height );
		_output.resizeTo( width, height - 20 ); //height - _input.height
//
//		_prompt.y = height - _prompt.height;
//
//		_input.x = _prompt.x + _prompt.width;
//		_input.y = height - _input.height;
//		_input.width = width - _prompt.width;
//
//		_output.width = width;
//		_output.height = height - _input.height;
//		_output.getCharBoundaries( 0 );
//		_output.scrollV = _output.bottomScrollV;
	}

	/**
	 *
	 */
	public void write( String output )
	{
		_buffer += prompt + output + newLine;

		_renderRequest.invalidate();
	}

	/**
	 *
	 */
	public void execute( CommandResult result )
	{
		if( !StringUtils.isEmpty( result.getOutput() ) )
		{
			_buffer += prompt + result.getCommand() + newLine + result.getOutput() + newLine;
		}
		else
		{
			_buffer += prompt + result.getCommand() + newLine;
		}

		_renderRequest.invalidate();
	}

	/**
	 *
	 */
	public void clear()
	{
		_buffer = empty;

		_renderRequest.invalidate();
	}

	/**
	 * Frees all references of the object.
	 */
	public void dispose()
	{
	}

	/**
	 * Creates and returns a string representation of the TerminalView object.
	 */
	@Override
	public String toString()
	{
		return "[TerminalView]";
	}
}

