package com.adjazent.defrac.sandbox.experiments.system;

import com.adjazent.defrac.sandbox.Experiment;
import defrac.event.ResourceEvent;
import defrac.lang.Procedure;
import defrac.resource.BinaryResource;

import java.nio.charset.Charset;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EFileResource extends Experiment
{
	public EFileResource()
	{
	}

	@Override
	protected void onInit()
	{
		final BinaryResource resource = BinaryResource.from( "file.txt" );

		resource.onComplete.attach( new Procedure<ResourceEvent.Complete<byte[]>>()
		{
			@Override
			public void apply( ResourceEvent.Complete<byte[]> event )
			{
				final String value = new String( event.content, Charset.defaultCharset() );
				System.out.println( value );
			}
		} );

		resource.load();
	}

	@Override
	public String toString()
	{
		return "[EFileResource]";
	}
}