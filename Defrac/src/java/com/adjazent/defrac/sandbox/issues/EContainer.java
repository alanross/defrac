package com.adjazent.defrac.sandbox.issues;

import com.adjazent.defrac.sandbox.Experiment;
import defrac.display.Layer;
import defrac.display.Quad;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EContainer extends Experiment
{

	public EContainer()
	{
	}

	@Override
	protected void onInit()
	{
		LinkedList<Quad> quads = new LinkedList<Quad>();
		Layer container = new Layer();

		for( int i = 0; i < 10; i++ )
		{
			Quad q = new Quad( 100, 20, 0xFFFF0000 );
			q.y( i * 21 );

			quads.add( q );

			container.addChild( q );
		}

		addChild( container );

		for( int j = 0; j < quads.size(); j++ )
		{
			Quad q = quads.get( j );

			container.removeChild( q ); // error
		}

		/*
		[Error] [object Object]
			removeChild_Ldefrac_display_DisplayObject__Ldefrac_display_DisplayObject_ (defrac.js, line 46591)
			onKeyDown_Ldefrac_event_KeyboardEvent__V (defrac.js, line 55923)
			apply_Ldefrac_event_KeyboardEvent__V (defrac.js, line 31753)
			apply_Ljava_lang_Object__V (defrac.js, line 31758)
			apply_Ljava_lang_Object__V (defrac.js, line 2097)
			dispatch_Ljava_lang_Object__V (defrac.js, line 1885)
			dispatchKeyDown_IC_V (defrac.js, line 1686)
			apply_Lintrinsic_KeyboardEvent__V (defrac.js, line 25850)
			apply_Ljava_lang_Object__V (defrac.js, line 25855)
			f (defrac.js, line 710)
		 */
	}

	@Override
	public String toString()
	{
		return "[EContainer]";
	}
}