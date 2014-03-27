package com.adjazent.defrac.ui.widget.text;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.math.geom.MPoint;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.text.*;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import com.adjazent.defrac.ui.text.processing.IUITextRenderer;
import com.adjazent.defrac.ui.text.processing.UITextLayout;
import com.adjazent.defrac.ui.text.processing.UITextLine;
import com.adjazent.defrac.ui.widget.UIActionType;
import defrac.display.DisplayObject;
import defrac.display.Image;
import defrac.display.Layer;
import defrac.display.Quad;
import defrac.display.event.*;
import defrac.geom.Point;
import defrac.geom.Rectangle;

import javax.annotation.Nonnull;
import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextField extends UISurface implements IUITextRenderer
{
	public final Action onText = new Action( UIActionType.TEXT_CHANGE );
	public final Action onSelection = new Action( UIActionType.TEXT_SELECTION_CHANGE );

	private final LinkedList<Image> _images = new LinkedList<Image>();

	private final UITextProcessor _processor;
	private final Quad _bgSelection;
	private final Quad _bgCaret;
	private final Layer _container;

	private final UITextSelection _selection;
	private int _caretIndex = 0;

	private boolean _dragEnabled = false;
	private boolean _dragActive = false;
	private MPoint _dragOrigin = new MPoint();

	private boolean _enabled = true;
	private boolean _hasFocus = false;

	private boolean _shift = false;

	public UITextField( IUISkin skin, UITextFormat textFormat )
	{
		super( skin );

		_processor = UITextProcessor.createSingleLine( textFormat, this );
		_processor.setSize( Integer.MAX_VALUE, Integer.MAX_VALUE );

		_bgSelection = new Quad( 1, 1, 0xFF000000 );
		_bgSelection.visible( false );

		_bgCaret = new Quad( 1, 1, 0xFFFFFFFF );
		_bgCaret.visible( false );

		_container = new Layer();

		_selection = new UITextSelection( -1, -1 );

		addChild( _bgSelection );
		addChild( _container );
		addChild( _bgCaret );
	}

	private void onActionEvent( UIActionEvent actionEvent )
	{
		Point local = this.globalToLocal( new Point( actionEvent.pos.x, actionEvent.pos.y ) );
		MPoint p = new MPoint( local.x, local.y );

		if( actionEvent.type == UIEventType.ACTION_BEGIN )
		{
			_dragEnabled = true;
			_dragOrigin.setTo( p.x, p.y );

			setCaretIndex( _processor.getCaretIndexAtPoint( p ) );
			setSelection( -1, -1 );
		}
		if( actionEvent.type == UIEventType.ACTION_MOVE )
		{
			_dragActive = _dragEnabled; //only activate if mouse is down

			if( _dragActive )
			{
				UITextSelection selection = new UITextSelection();
				_processor.selectChars( _dragOrigin, p, selection );
				setSelection( selection.firstIndex, selection.lastIndex );
			}
		}
		if( actionEvent.type == UIEventType.ACTION_END )
		{
			if( _dragActive )
			{
				UITextSelection selection = new UITextSelection();
				_processor.selectChars( _dragOrigin, p, selection );
				setSelection( selection.firstIndex, selection.lastIndex );
			}

			_dragActive = false;
			_dragEnabled = false;
		}
		if( actionEvent.type == UIEventType.ACTION_DOUBLE )
		{
			UITextSelection selection = new UITextSelection();
			_processor.selectWord( p, selection );
			setSelection( selection.firstIndex, selection.lastIndex );
		}
	}

	private void onFocusEvent( UIFocusEvent focusEvent )
	{
		if( focusEvent.type == UIEventType.FOCUS_IN )
		{
			_hasFocus = true;
		}
		else if( focusEvent.type == UIEventType.FOCUS_OUT )
		{
			_hasFocus = false;
		}

		_processor.requestRenderAction();
	}

	private void onKeyEvent( UIKeyboardEvent keyboardEvent )
	{
		final int charCode = keyboardEvent.charCode;
		final int keyCode = keyboardEvent.keyCode;

		if( keyboardEvent.type == UIEventType.KEY_UP )
		{
			if( keyCode == UIKeyCode.SHIFT )
			{
				_shift = false;
			}
		}
		else if( keyboardEvent.type == UIEventType.KEY_DOWN )
		{
			if( keyCode == UIKeyCode.SHIFT )
			{
				_shift = true;
				return;
			}

			int ci = getCaretIndex();
			int i0 = getSelectionFirst();
			int i1 = getSelectionLast();

			boolean textSelected = ( i0 > -1 && i1 > -1 );

			StringBuilder s = new StringBuilder( getText() );

			setSelection( -1, -1 );

			if( UIKeyCode.isSpecial( keyCode ) )
			{
				if( keyCode == UIKeyCode.ARROW_LEFT )
				{
					if( textSelected && _shift )
					{
						Log.info( Context.DEFAULT, this, "LEFT: SHIFT SELECT" );
					}
					else if( textSelected )
					{
						Log.info( Context.DEFAULT, this, "LEFT: SELECT" );
//						if( i0 < ci )
//						{
//							setSelection( --i0, i1 );
//						}
//						else
//						{
//							setSelection( i0, --i1 );
//						}
					}
					else if( _shift )
					{
						Log.info( Context.DEFAULT, this, "LEFT: SHIFT CURSOR" );
					}
					else
					{
						if( -1 < --ci )
						{
							setCaretIndex( ci );
						}
					}
				}
				else if( keyCode == UIKeyCode.ARROW_RIGHT )
				{
					if( textSelected && _shift )
					{
						Log.info( Context.DEFAULT, this, "RIGHT: SHIFT SELECT" );
					}
					else if( textSelected )
					{
						Log.info( Context.DEFAULT, this, "RIGHT: SELECT" );
//						if( i0 < ci )
//						{
//							setSelection( ++i0, i1 );
//						}
//						else
//						{
//							setSelection( i0, ++i1 );
//						}
					}
					else if( _shift )
					{
						Log.info( Context.DEFAULT, this, "RIGHT: SHIFT CURSOR" );
					}
					else
					{
						if( _processor.getTextLength() >= ++ci )
						{
							setCaretIndex( ci );
						}
					}
				}
				else if( keyCode == UIKeyCode.DEL )
				{
					if( textSelected )
					{
						s.delete( i0, i1 );
						setText( s.toString() );
						setCaretIndex( i0 );
					}
					else if( ci > 0 && s.length() > 0 )
					{
						s.deleteCharAt( --ci );
						setText( s.toString() );
						setCaretIndex( ci );
					}
				}
			}
			else
			{
				if( textSelected )
				{
					s.delete( i0, i1 );
					ci = i0;
				}

				char c = UICharCode.toChar( charCode );

				s.insert( ci, c );
				setText( s.toString() );
				setCaretIndex( ++ci );
			}
		}
	}

	@Override
	public UIEventTarget captureEventTarget( Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		return ( containsPoint( local.x, local.y ) ) ? this : null;
	}

	@Override
	protected void processEvent( @Nonnull final UIEvent uiEvent )
	{
		if( uiEvent.target == this )
		{
			if( ( uiEvent.type & UIEventType.ACTION ) != 0 )
			{
				onActionEvent( ( UIActionEvent ) uiEvent );
			}
			else if( ( uiEvent.type & UIEventType.FOCUS ) != 0 )
			{
				onFocusEvent( ( UIFocusEvent ) uiEvent );
			}
			else if( ( uiEvent.type & UIEventType.KEYBOARD ) != 0 )
			{
				onKeyEvent( ( UIKeyboardEvent ) uiEvent );
			}
		}
	}

	@Override
	public void renderText( UITextLayout block, UITextFormat format )
	{
		while( !_images.isEmpty() )
		{
			removeChild( _images.removeLast() );
		}

		if( block.bounds.width <= 0 || block.bounds.height <= 0 )
		{
			return;
		}

		LinkedList<UITextLine> lines = block.lines;

		for( UITextLine line : lines )
		{
			LinkedList<UIGlyph> glyphs = line.glyphs;

			for( UIGlyph g : glyphs )
			{
				Image image = new Image( g.getTexture() );

				image.moveTo( g.getX(), g.getY() );

				_images.addLast( image );

				addChild( image );
			}
		}
	}

	@Override
	public void renderTextDependantAction()
	{
		MRectangle caretBounds = new MRectangle();

		_processor.getCaretRectAtIndex( _caretIndex, caretBounds );

		_bgCaret.moveTo( ( float ) caretBounds.x, ( float ) caretBounds.y );
		_bgCaret.scaleToSize( ( float ) caretBounds.width, ( float ) caretBounds.height );

		if( _selection.firstIndex != -1 && _selection.lastIndex != -1 )
		{
			// Note: We will have several rectangles for multi line, though not implemented yet
			LinkedList<MRectangle> rectangles = _processor.getSelectionRect( _selection );

			for( MRectangle r : rectangles )
			{
				_bgSelection.moveTo( ( float ) r.x, ( float ) r.y );
				_bgSelection.scaleToSize( ( float ) r.width, ( float ) r.height );
			}

			_bgSelection.visible( true );
			_bgCaret.visible( false );
		}
		else
		{
			_bgSelection.moveTo( 0, 0 );
			_bgSelection.scaleToSize( 0, 0 );

			_bgSelection.visible( false );
			_bgCaret.visible( _hasFocus );
		}

		_bgSelection.alpha( _hasFocus ? 1.0f : 0.3f );
	}

	public DisplayObject resizeTo( float width, float height )
	{
		super.resizeTo( width, height );

		scrollRect( new Rectangle( 0, 0, width, height ) );

		return this;
	}

	public void setCaretIndex( int index )
	{
		if( index < 0 || index > _processor.getTextLength() )
		{
			index = _processor.getTextLength();
		}

		_caretIndex = index;

		// if the text is already rendered renderTextDependantAction will be called directly
		// else renderTextDependantAction will be called after next render
		_processor.requestRenderAction();
	}

	public void setSelection( int i0, int i1 )
	{
		if( i0 != -1 && i1 != -1 )
		{
			i0 = MMath.clampInt( i0, 0, _processor.getTextLength() );
			i1 = MMath.clampInt( i1, 0, _processor.getTextLength() );
		}

		_selection.setTo( i0, i1 );

		_processor.requestRenderAction();

		onSelection.send( this );
	}

	public void setFormat( UITextFormat value )
	{
		_processor.setFormat( value );
	}

	public void setText( String value )
	{
		_processor.setText( value );

		onText.send( this );
	}

	public String getText()
	{
		return _processor.getText();
	}

	public int getCaretIndex()
	{
		return _caretIndex;
	}

	public int getSelectionFirst()
	{
		return _selection.firstIndex;
	}

	public int getSelectionLast()
	{
		return _selection.lastIndex;
	}

	public void setSelectionColor( int color )
	{
		_bgSelection.color( color );
	}

	public void setCaretColor( int color )
	{
		_bgCaret.color( color );
	}

	public boolean getEnabled()
	{
		return _enabled;
	}

	public void setEnabled( boolean _enabled )
	{
		_enabled = _enabled;
	}

	@Override
	public String toString()
	{
		return "[UITextField id:" + id + "]";
	}
}