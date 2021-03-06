package com.adjazent.defrac.sandbox;

import com.adjazent.defrac.sandbox.apps.lite.LiteStudio;
import com.adjazent.defrac.sandbox.experiments.core.EArrayUtils;
import com.adjazent.defrac.sandbox.experiments.core.EDateUtils;
import com.adjazent.defrac.sandbox.experiments.core.EStringUtils;
import com.adjazent.defrac.sandbox.experiments.core.job.EJob;
import com.adjazent.defrac.sandbox.experiments.lang.EDataTypes;
import com.adjazent.defrac.sandbox.experiments.macros.EMacros;
import com.adjazent.defrac.sandbox.experiments.system.EFileResource;
import com.adjazent.defrac.sandbox.experiments.system.ETerminal;
import com.adjazent.defrac.sandbox.experiments.system.EXMLResource;
import com.adjazent.defrac.sandbox.experiments.ui.*;
import com.adjazent.defrac.sandbox.experiments.war.Warzone;
import com.adjazent.defrac.sandbox.issues.EAddChild;
import com.adjazent.defrac.sandbox.issues.EKeyboardEvents;
import com.adjazent.defrac.sandbox.samples.text.ENativeLabel;
import com.adjazent.defrac.sandbox.issues.EResourcePath;
import com.adjazent.defrac.sandbox.samples.canvas.ECanvas;
import com.adjazent.defrac.sandbox.samples.event.EUIEvent;
import com.adjazent.defrac.sandbox.samples.flutterman.EFlutterman;
import com.adjazent.defrac.sandbox.samples.quad.EQuad;
import com.adjazent.defrac.sandbox.samples.texture.ETextures;
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
		add( new EVideo() );
		add( new EUIGLPainter() );
		add( new EUISkinning() );
		add( new EUIList() );
		add( new EUIWidgets() );
		add( new EMacros() );
		add( new ETerminal() );
		//----------------------------- apps
		add( new LiteStudio() );
		//----------------------------- issues
		add( new EAddChild() );
		add( new EResourcePath() );
		add( new EKeyboardEvents() );
		add( new ENativeLabel() );
		//----------------------------- warzone
		add( new Warzone() );

		activate( EUIText.class );
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
