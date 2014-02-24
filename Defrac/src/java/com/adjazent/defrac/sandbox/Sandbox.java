package com.adjazent.defrac.sandbox;

import com.adjazent.defrac.sandbox.experiments.core.EArrayUtils;
import com.adjazent.defrac.sandbox.experiments.core.EDateUtils;
import com.adjazent.defrac.sandbox.experiments.core.EStringUtils;
import com.adjazent.defrac.sandbox.experiments.core.job.EJob;
import com.adjazent.defrac.sandbox.experiments.lang.EDataTypes;
import com.adjazent.defrac.sandbox.experiments.samples.EQuad;
import com.adjazent.defrac.sandbox.experiments.samples.canvas.ECanvas;
import com.adjazent.defrac.sandbox.experiments.samples.event.EUIEvent;
import com.adjazent.defrac.sandbox.experiments.samples.flutterman.EFlutterman;
import com.adjazent.defrac.sandbox.experiments.samples.texture.ETextures;
import com.adjazent.defrac.sandbox.experiments.system.EFileResource;
import com.adjazent.defrac.sandbox.experiments.system.EXMLResource;
import com.adjazent.defrac.sandbox.experiments.ui.*;
import com.adjazent.defrac.sandbox.experiments.war.EContainer;
import com.adjazent.defrac.sandbox.experiments.war.EScrollRect;
import com.adjazent.defrac.sandbox.experiments.war.EScrollRect2;
import com.adjazent.defrac.sandbox.experiments.war.Warzone;
import defrac.app.Bootstrap;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Sandbox extends AbstractSandbox
{
	@Override
	protected void onCreateComplete()
	{
		//----------------------------- samples
		add( new EQuad() );
		add( new ETextures() );
		add( new ECanvas() );
		add( new EFlutterman() );
		add( new EUIEvent() );
		//----------------------------- experiments
		add( new EXMLResource() );
		add( new EFileResource() );
		add( new EDataTypes() );
		add( new EJob() );
		add( new EUIImages() );
		add( new EUIText() );
		add( new EUIResourceLoading() );
		add( new EArrayUtils() );
		add( new EDateUtils() );
		add( new EStringUtils() );
		add( new EGSLS01() );
		add( new EGSLS02() );
		add( new EUIGLPainter() );
		add( new EUISkinning() );
		add( new EUIList() );
		//----------------------------- warzone
		add( new EScrollRect() );
		add( new EScrollRect2() );
		add( new EContainer() );
		add( new Warzone() );

		activate( EUIList.class );
	}

	public static void main( final String[] args )
	{
		Bootstrap.run( new Sandbox() );
	}

	@Override
	public String toString()
	{
		return "[Sandbox]";
	}
}
