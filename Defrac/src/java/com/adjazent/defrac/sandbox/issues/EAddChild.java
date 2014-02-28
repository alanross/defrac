package com.adjazent.defrac.sandbox.issues;

import com.adjazent.defrac.sandbox.Experiment;
import defrac.display.Layer;
import defrac.display.Quad;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EAddChild extends Experiment
{
	public EAddChild()
	{
	}

	@Override
	protected void onInit()
	{
		MyLayer myLayer = new MyLayer();

		myLayer.addChild( new Quad( 100, 100, 0xFFFF0000 ) );
		myLayer.addChildAt( new Quad( 100, 100, 0xFFFF0000 ), 0 );

		addChild( myLayer );
	}

	class MyLayer extends Layer
	{
		Layer layer = new Layer();

		public MyLayer()
		{
			super();

			super.addChild( layer );
		}

		@Override
		public <D extends defrac.display.DisplayObject> D addChild( @javax.annotation.Nonnull D child )
		{
			return layer.addChild( child );
		}

		// overriding this function will cause stack overflow
//		@Override
//		public <D extends defrac.display.DisplayObject> D addChildAt( @javax.annotation.Nonnull D child, int index )
//		{
//			return layer.addChildAt( child, index );
//		}
	}

	@Override
	public String toString()
	{
		return "[EAddChild]";
	}
}