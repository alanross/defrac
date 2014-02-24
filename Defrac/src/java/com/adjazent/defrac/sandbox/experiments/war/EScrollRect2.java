package com.adjazent.defrac.sandbox.experiments.war;

import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.ui.utils.bitmap.UISimpleImage;
import defrac.display.Layer;
import defrac.display.Quad;
import defrac.geom.Rectangle;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EScrollRect2 extends Experiment
{
	public EScrollRect2()
	{
	}

	@Override
	public void onInit()
	{
		// Issue: applying scrollRect to a container

		UISimpleImage imageA = new UISimpleImage( "fonts/helvetica24.png" ); // image with transparency
		imageA.moveTo( 120, 120 );

		UISimpleImage imageB = new UISimpleImage( "fonts/helvetica24.png" ); // image with transparency
		imageB.moveTo( 120, 120 );

		Quad quadA = new Quad( 300, 200, 0xFF338822 );
		quadA.moveTo( 80, 80 );

		Quad quadB = new Quad( 300, 200, 0xFF338822 );
		quadB.moveTo( 80, 80 );

		Layer layerA = new Layer();
		layerA.addChild( quadA );
		layerA.addChild( imageA );
		layerA.moveTo( 100, 100 );

		Layer layerB = new Layer();
		layerB.addChild( quadB );
		layerB.addChild( imageB );
		layerB.moveTo( 550, 100 );

		Layer root = new Layer();
		root.addChild( layerA );
		root.addChild( layerB );

		// broken -> alpha channel, also wrong scaling
		layerA.scrollRect( new Rectangle( 0, 0, 1100, 700 ) );

		addChild( root );
	}

	@Override
	public String toString()
	{
		return "[EScrollRect2]";
	}
}