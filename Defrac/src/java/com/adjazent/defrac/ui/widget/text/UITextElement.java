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
import defrac.display.DisplayObject;
import defrac.display.Image;
import defrac.display.Layer;
import defrac.display.Quad;
import defrac.display.event.UIActionEvent;
import defrac.display.event.UIEvent;
import defrac.display.event.UIEventTarget;
import defrac.display.event.UIEventType;
import defrac.geom.Point;
import defrac.geom.Rectangle;

import javax.annotation.Nonnull;
import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public abstract class UITextElement extends UISurface implements IUITextRenderer
{
	private final LinkedList<Image> _images = new LinkedList<Image>();

	protected final UITextProcessor processor;
	protected final Quad bgSelection;
	protected final Quad bgCaret;
	protected final Layer container;

	private final UITextSelection _selection;
	private int _caretIndex = -1;

	private boolean _dragEnabled = false;
	private boolean _dragActive = false;
	private MPoint _dragOrigin = new MPoint();

	public UITextElement( IUISkin skin, UITextFormat textFormat )
	{
		super( skin );

		processor = UITextProcessor.createSingleLine( textFormat, this );
		processor.setSize( Integer.MAX_VALUE, Integer.MAX_VALUE );

		bgSelection = new Quad( 1, 1, 0xFF000000 );
		bgSelection.visible( false );

		bgCaret = new Quad( 1, 1, 0xFFFFFFFF );
		bgCaret.visible( true );

		container = new Layer();

		_selection = new UITextSelection( -1, -1 );

		addChild( bgSelection );
		addChild( container );
		addChild( bgCaret );
	}

	private void renderCaret()
	{
		MRectangle caretBounds = new MRectangle();

		processor.getCaretRectAtIndex( _caretIndex, caretBounds );

		bgCaret.moveTo( ( float ) caretBounds.x, ( float ) caretBounds.y );
		bgCaret.scaleToSize( ( float ) caretBounds.width, ( float ) caretBounds.height );
		bgCaret.visible( true );
	}

	private void renderSelection()
	{
		if( _selection.firstIndex != -1 && _selection.lastIndex != -1 )
		{
			// Note: We will have several rectangles for multi line, though not implemented yet
			LinkedList<MRectangle> rectangles = processor.getSelectionRect( _selection );

			for( MRectangle r : rectangles )
			{
				bgSelection.visible( true );
				bgSelection.moveTo( ( float ) r.x, ( float ) r.y );
				bgSelection.scaleToSize( ( float ) r.width, ( float ) r.height );
			}

			bgCaret.visible( false );
		}
		else
		{
			bgSelection.visible( false );
			bgSelection.moveTo( 0, 0 );
			bgSelection.scaleToSize( 0, 0 );
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

			setCaretIndex( processor.getCaretIndexAtPoint( p ) );
			setSelection( -1, -1 );
		}
		if( event.type == UIEventType.ACTION_MOVE )
		{
			_dragActive = _dragEnabled; //only activate if mouse is down

			if( _dragActive )
			{
				setSelection( _dragOrigin, p );
			}
		}
		if( event.type == UIEventType.ACTION_END )
		{
			if( _dragActive )
			{
				setSelection( _dragOrigin, p );
			}

			_dragActive = false;
			_dragEnabled = false;
		}
		if( event.type == UIEventType.ACTION_DOUBLE )
		{
			UITextSelection selection = new UITextSelection();
			processor.selectWordAtPoint( p, selection );
			setSelection( selection.firstIndex, selection.lastIndex );
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
		renderCaret();
		renderSelection();
	}

	public DisplayObject resizeTo( float width, float height )
	{
		super.resizeTo( width, height );

		scrollRect( new Rectangle( 0, 0, width, height ) );

		return this;
	}

	public void setCaretIndex( int index )
	{
		_caretIndex = index;

		processor.requestRenderAction();
	}

	public void setSelection( int i0, int i1 )
	{
		if( i0 != -1 && i1 != -1 )
		{
			i0 = MMath.clampInt( i0, 0, processor.getTextLength() - 1 );
			i1 = MMath.clampInt( i1, 0, processor.getTextLength() - 1 );

			_selection.setTo( i0, i1 );
		}
		else
		{
			_selection.setTo( -1, -1 );
		}

		processor.requestRenderAction();
	}

	public void setSelection( MPoint p0, MPoint p1 )
	{
		if( p0.x >= p1.x ) //negative selection
		{
			setSelection(
					processor.getCaretIndexAtPoint( p1 ) + 1,
					processor.getCaretIndexAtPoint( p0 ) );
		}
		else //positive selection
		{
			setSelection(
					processor.getCaretIndexAtPoint( p0 ) + 1,
					processor.getCaretIndexAtPoint( p1 ) );
		}
	}

	public void setFormat( UITextFormat value )
	{
		processor.setFormat( value );
	}

	public void setText( String value )
	{
		processor.setText( value );
	}

	public String getText()
	{
		return processor.getText();
	}

	public int getCaretIndex()
	{
		return _caretIndex;
	}

	public void setSelectionColor( int color )
	{
		bgSelection.color( color );
	}

	public void setCaretColor( int color )
	{
		bgCaret.color( color );
	}

	@Override
	public String toString()
	{
		return "[UITextElement id:" + id + "]";
	}
}