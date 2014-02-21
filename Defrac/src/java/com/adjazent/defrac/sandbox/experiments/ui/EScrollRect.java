package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.sandbox.events.IEnterFrame;
import com.adjazent.defrac.ui.utils.bitmap.UISimpleImage;
import defrac.display.Layer;
import defrac.display.Quad;
import defrac.geom.Rectangle;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EScrollRect extends Experiment implements IEnterFrame
{
	private Rectangle rectangle = new Rectangle( 0, 0, 0, 0 );
	private Layer container = new Layer();
	private Quad quadA = new Quad( 100, 100, 0xFFCC8989 );
	private Quad quadB = new Quad( 100, 100, 0xFF89CC89 );
	private Quad quadC = new Quad( 100, 100, 0xFF8989CC );
	private UISimpleImage image = new UISimpleImage( "fonts/helvetica24.png" );

	private float _size = 100;
	private float _dir = 1;

	public EScrollRect()
	{
	}

	@Override
	public void onInit()
	{
		quadB.moveTo( 50, 50 );
		quadC.moveTo( 100, 100 );
		image.moveTo( 80, 80 );

		container.addChild( quadA );
		container.addChild( quadB );
		container.addChild( quadC );
		container.addChild( image );
		container.moveTo( 100, 100 );

		addChild( container );
	}

	@Override
	public void onEnterFrame()
	{
		Log.info( Context.DEFAULT, this, container.width(), container.height() );
		if( _size < 10 || _size > 150 )
		{
			_dir *= -1;
		}

		_size += _dir;

		rectangle.set( _size/2, 0, _size, _size );

		container.scrollRect( rectangle );		// broken -> it scales??
		quadA.scrollRect( rectangle );			// works like a charm
		image.scrollRect( rectangle );			// works like a charm
	}

	/*
	 Another error i got here:
	 [error] Exception in thread "main" java.lang.RuntimeException: FRAMEBUFFER_INCOMPLETE_ATTACHMENT
	 [error] 	at defrac.display.render.RenderContentEmitter.createFrameBuffer(RenderContentEmitter.class:1008)
	 [error] 	at defrac.display.render.RenderContentEmitter.access$300(RenderContentEmitter.class:23)
	 [error] 	at defrac.display.render.RenderContentEmitter$6.apply(RenderContentEmitter$6.class:172)
	 [error] 	at defrac.display.render.RenderContentEmitter$6.apply(RenderContentEmitter$6.class:169)
	 [error] 	at defrac.display.render.FBOCache.get(FBOCache.class:100)
	 [error] 	at defrac.display.render.RenderContentEmitter.drawBuffered(RenderContentEmitter.class:690)
	 [error] 	at defrac.display.render.RenderContentEmitter.processBatch(RenderContentEmitter.class:287)
	 [error] 	at defrac.display.render.RenderContentEmitter.process(RenderContentEmitter.class:247)
	 [error] 	at defrac.display.render.RenderContentEmitter.emit(RenderContentEmitter.class:239)
	 [error] 	at defrac.display.DisplayList.render(DisplayList.class:177)
	 [error] 	at defrac.display.DisplayList.access$000(DisplayList.class:19)
	 [error] 	at defrac.display.DisplayList$1.apply(DisplayList$1.class:99)
	 [error] 	at defrac.display.DisplayList$1.apply(DisplayList$1.class:96)
	 [error] 	at defrac.signal.SignalBinding.apply(SignalBinding.class:30)
	 [error] 	at defrac.signal.Signal.dispatch(Signal.class:119)
	 [error] 	at defrac.display.Stage.dispatchRenderContinuously(Stage.class:294)
	 [error] 	at defrac.display.Stage.access$300(Stage.class:32)
	 [error] 	at defrac.display.Stage$1.apply(Stage$1.class:141)
	 [error] 	at defrac.display.Stage$1.apply(Stage$1.class:138)
	 [error] 	at defrac.signal.SignalBinding.apply(SignalBinding.class:30)
	 [error] 	at defrac.signal.Signal.dispatch(Signal.class:119)
	 [error] 	at defrac.event.EventDispatchers.onLoop(EventDispatchers.class:98)
	 [error] 	at defrac.event.EventDispatchers.access$000(EventDispatchers.class:12)
	 [error] 	at defrac.event.EventDispatchers$1.run(EventDispatchers$1.class:29)
	 [error] 	at defrac.concurrent.EnqueueingDispatcher.dispatchTasks(EnqueueingDispatcher.class:86)
	 [error] 	at defrac.jvm.JVMLoop.enterLoop(JVMLoop.class:48)
	 [error] 	at defrac.jvm.RuntimeFactory$1$1.run(RuntimeFactory$1$1.class:27)
	 [error] 	at defrac.app.App.enterLoop(App.class)
	 [error] 	at defrac.app.GenericApp.init(GenericApp.class:32)
	 [error] 	at defrac.app.Bootstrap$BootstrapConfiguration.run(Bootstrap$BootstrapConfiguration.class:43)
	 [error] 	at defrac.app.Bootstrap.run(Bootstrap.class:77)
	 [error] 	at com.adjazent.defrac.sandbox.Sandbox.main(Sandbox.class:56)

	 public void onEnterFrame()
	 	{
	 		if( _size < 0 || _size > 400 )
	 		{
	 			_dir *= -5;
	 		}

	 		_size += _dir;

	 		rectangle.set( 0, 0, _size, _size );
	 		container.scrollRect( rectangle );
	 		container.scaleToSize( 300, 300 );
	 	}
	 */

	@Override
	public String toString()
	{
		return "[EScrollRect]";
	}
}