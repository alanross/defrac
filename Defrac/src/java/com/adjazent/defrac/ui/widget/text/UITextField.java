package com.adjazent.defrac.ui.widget.text;

import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.math.geom.MPoint;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.UITextProcessor;
import com.adjazent.defrac.ui.text.UITextSelection;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import com.adjazent.defrac.ui.text.processing.IUITextRenderer;
import com.adjazent.defrac.ui.text.processing.UITextLayout;
import com.adjazent.defrac.ui.text.processing.UITextLine;
import defrac.display.Image;
import defrac.display.Layer;
import defrac.display.Quad;
import defrac.display.event.UIActionEvent;
import defrac.display.event.UIEvent;
import defrac.display.event.UIEventTarget;
import defrac.display.event.UIEventType;
import defrac.geom.Point;

import javax.annotation.Nonnull;
import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextField extends UISurface implements IUITextRenderer
{
	private final LinkedList<Image> _images = new LinkedList<Image>();

	private UITextProcessor _processor;
	private Quad _bgSelection;
	private Quad _bgCaret;
	private Layer _container;

	private UITextSelection _selection;

	private boolean _dragEnabled = false;
	private boolean _drag = false;
	private MPoint _dragOrigin = new MPoint();

	public UITextField( IUISkin skin, UITextFormat textFormat )
	{
		super( skin );

		_processor = UITextProcessor.createSingleLine( textFormat, this );
		_processor.setSize( Integer.MAX_VALUE, Integer.MAX_VALUE );

		_bgSelection = new Quad( 1, 1, 0xFF000000 );
		_bgSelection.visible( false );

		_bgCaret = new Quad( 1, 1, 0xFFFF0000 );
		_bgCaret.visible( true );

		_container = new Layer();

		_selection = new UITextSelection();

		addChild( _bgSelection );
		addChild( _container );
		addChild( _bgCaret );
	}

	private void updateCaret( MPoint mousePos )
	{
		MRectangle caretBounds = new MRectangle();

		_processor.getCaretRectAtPoint( mousePos, caretBounds );

		_bgCaret.visible( true );
		_bgCaret.moveTo( ( float ) caretBounds.x, ( float ) caretBounds.y );
		_bgCaret.scaleToSize( ( float ) caretBounds.width, ( float ) caretBounds.height );
	}

	private void updateSelection( int i0, int i1 )
	{
		_selection.setTo( i0, i1 );

		if( i0 != -1 && i1 != -1 )
		{
			UITextSelection selection = new UITextSelection(
					MMath.clampInt( i0, 0, _processor.getTextLength() - 1 ),
					MMath.clampInt( i1, 0, _processor.getTextLength() - 1 )
			);

			// Note: We will have several rectangles for multi line, though not implemented yet
			LinkedList<MRectangle> rectangles = _processor.getSelectionRect( selection );

			for( MRectangle r : rectangles )
			{
				_bgSelection.visible( true );
				_bgSelection.moveTo( ( float ) r.x, ( float ) r.y );
				_bgSelection.scaleToSize( ( float ) r.width, ( float ) r.height );
			}

			_bgCaret.visible( false );
		}
		else
		{
			_bgSelection.visible( false );
			_bgSelection.moveTo( 0, 0 );
			_bgSelection.scaleToSize( 0, 0 );
		}
	}

	private void findSelection( MPoint p0, MPoint p1 )
	{
		if( p0.x >= p1.x ) //negative selection
		{
			updateSelection(
					_processor.getCaretIndexAtPoint( p1 ) + 1,
					_processor.getCaretIndexAtPoint( p0 ) );
		}
		else //positive selection
		{
			updateSelection(
					_processor.getCaretIndexAtPoint( p0 ) + 1,
					_processor.getCaretIndexAtPoint( p1 ) );
		}
	}

	@Override
	public UIEventTarget captureEventTarget( Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		return ( containsPoint( local.x, local.y ) ) ? this : null;
	}

	@Override
	protected void processEvent( @Nonnull final UIEvent event )
	{
		if( event.target != this || ( event.type & UIEventType.ACTION ) == 0 )
		{
			return;
		}

		UIActionEvent actionEvent = ( UIActionEvent ) event;
		Point local = this.globalToLocal( new Point( actionEvent.pos.x, actionEvent.pos.y ) );
		MPoint p = new MPoint( local.x, local.y );

		if( event.type == UIEventType.ACTION_BEGIN )
		{
			_dragEnabled = true;

			_dragOrigin.setTo( p.x, p.y );

			updateCaret( p );
			updateSelection( -1, -1 );
		}
		if( event.type == UIEventType.ACTION_END )
		{
			if( _drag )
			{
				findSelection( _dragOrigin, p );
			}

			_drag = false;
			_dragEnabled = false;
		}
		if( event.type == UIEventType.ACTION_MOVE )
		{
			_drag = _dragEnabled;

			if( _drag )
			{
				findSelection( _dragOrigin, p );
			}
		}
		if( event.type == UIEventType.ACTION_DOUBLE )
		{
			UITextSelection selection = new UITextSelection();
			_processor.selectWordAtPoint( p, selection );
			updateSelection( selection.firstIndex, selection.lastIndex );
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

	public void setFormat( UITextFormat value )
	{
		_processor.setFormat( value );
	}

	public void setText( String value )
	{
		_processor.setText( value );
	}

	public String getText()
	{
		return _processor.getText();
	}

	public void setSelectionColor( int color )
	{
		_bgSelection.color( color );
	}

	@Override
	public String toString()
	{
		return "[UITextField id:" + id + "]";
	}
}