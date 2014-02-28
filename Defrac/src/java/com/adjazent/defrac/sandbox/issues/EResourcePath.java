package com.adjazent.defrac.sandbox.issues;

import com.adjazent.defrac.sandbox.Experiment;
import defrac.event.ResourceEvent;
import defrac.lang.Procedure;
import defrac.resource.BinaryResource;

import java.nio.charset.Charset;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EResourcePath extends Experiment
{
	public EResourcePath()
	{
	}

	@Override
	protected void onInit()
	{
		// put a copy of file.txt in resources and one in resources/misc.
		// jvm version will find both, web version will only find /misc/file.txt

		// Works
		//final BinaryResource resource = BinaryResource.from( "file.txt" );

		// File not found
		final BinaryResource resource = BinaryResource.from( "misc/file.txt" );

		resource.onComplete.attach( new Procedure<ResourceEvent.Complete<byte[]>>()
		{
			@Override
			public void apply( ResourceEvent.Complete<byte[]> event )
			{
				System.out.println( "SUCCESS: " + new String( event.content, Charset.defaultCharset() ) );
			}
		} );

		resource.onError.attach( new Procedure<ResourceEvent.Error>()
		{
			@Override
			public void apply( ResourceEvent.Error event )
			{
				System.out.println( "FAILURE: " + event );
			}
		} );

		resource.load();
	}

	@Override
	public String toString()
	{
		return "[EResourcePath]";
	}
}