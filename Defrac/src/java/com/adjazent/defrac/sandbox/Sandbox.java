package com.adjazent.defrac.sandbox;

import com.adjazent.defrac.sandbox.experiments.core.job.EJob;
import com.adjazent.defrac.sandbox.experiments.core.utils.EArrayUtils;
import com.adjazent.defrac.sandbox.experiments.core.utils.EDateUtils;
import com.adjazent.defrac.sandbox.experiments.core.utils.EStringUtils;
import com.adjazent.defrac.sandbox.experiments.lang.EDataTypes;
import com.adjazent.defrac.sandbox.experiments.system.EFileResource;
import com.adjazent.defrac.sandbox.experiments.system.EXMLResource;
import com.adjazent.defrac.sandbox.experiments.ui.canvas.ECanvas;
import com.adjazent.defrac.sandbox.experiments.ui.event.EUIEvent;
import com.adjazent.defrac.sandbox.experiments.ui.font.EUIFonts;
import com.adjazent.defrac.sandbox.experiments.ui.gl.EGSLS01;
import com.adjazent.defrac.sandbox.experiments.ui.gl.EGSLS02;
import com.adjazent.defrac.sandbox.experiments.ui.image.EUIImages;
import com.adjazent.defrac.sandbox.experiments.ui.image.EUISkinning;
import com.adjazent.defrac.sandbox.experiments.ui.quad.EQuad;
import com.adjazent.defrac.sandbox.experiments.ui.resource.EUIResourceLoading;
import com.adjazent.defrac.sandbox.experiments.ui.texture.ETextures;
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
		add( new EXMLResource() );
		add( new EFileResource() );
		add( new EQuad() );
		add( new EUIEvent() );
		add( new ETextures() );
		add( new EDataTypes() );
		add( new EJob() );
		add( new EUIImages() );
		add( new EUIFonts() );
		add( new EUIResourceLoading() );
		add( new EArrayUtils() );
		add( new EDateUtils() );
		add( new EStringUtils() );
		add( new Warzone() );
		add( new ECanvas() );
		add( new EGSLS01() );
		add( new EGSLS02() );
		add( new EUISkinning() );

		activate( ECanvas.class );
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
