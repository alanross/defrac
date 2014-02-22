package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.ui.utils.UIGLPainter;
import com.adjazent.defrac.ui.utils.bitmap.UISimpleImage;
import defrac.display.Canvas;
import defrac.display.Layer;
import defrac.geom.Rectangle;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EScrollRect2 extends Experiment
{
	private Rectangle rectangle = new Rectangle( 0, 0, 990, 890 );

	private Layer root = new Layer();
	private Layer child = new Layer();

	private UIGLPainter painter = new UIGLPainter();
	private Canvas canvas = new Canvas( 100, 100, painter );
	private UISimpleImage image = new UISimpleImage( "fonts/helvetica24.png" );

	private float _offset = 100;
	private float _dir = 1;

	public EScrollRect2()
	{
	}

	@Override
	public void onInit()
	{
		canvas.moveTo( 100, 100 );

		image.moveTo( 120, 120 );
		painter.fillRect( 0, 0, 100, 100, 0xFFFF3388 );

		child.addChild( canvas );
		child.addChild( image );
		child.moveTo( 100, 100 );

		root.addChild( child );
		root.scrollRect( rectangle );        // broken -> alpha channel?

		addChild( root );
	}

	@Override
	public void onEnterFrame()
	{
		if( _offset < 10 || _offset > 150 )
		{
			_dir *= -1;
		}

		_offset += _dir;

	}

	@Override
	public String toString()
	{
		return "[EScrollRect2]";
	}
}