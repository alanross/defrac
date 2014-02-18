package com.adjazent.defrac.sandbox.experiments.system;

import com.adjazent.defrac.core.xml.XML;
import com.adjazent.defrac.core.xml.XMLNode;
import com.adjazent.defrac.sandbox.experiments.Experiment;
import defrac.event.ResourceEvent;
import defrac.lang.Procedure;
import defrac.resource.BinaryResource;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EXMLResource extends Experiment
{
	public EXMLResource()
	{
	}

	@Override
	protected void onInit()
	{
		final BinaryResource resource = BinaryResource.from( "file.xml" );

		resource.onComplete.attach( new Procedure<ResourceEvent.Complete<byte[]>>()
		{
			@Override
			public void apply( ResourceEvent.Complete<byte[]> event )
			{
				String data = new String( event.content, Charset.defaultCharset() );

				XML xml = new XML( data );

				System.out.println( "XML: " + xml.getInfo() );

				System.out.println( "FIND NAME: staff" );
				printAll( xml.getAllByName( "staff" ) );

				System.out.println( "FIND ATTRIBUTE: number" );
				printAll( xml.getAllByAttribute( "number" ) );
			}
		} );

		resource.load();
	}

	private void printAll( LinkedList<XMLNode> nodes )
	{
		Iterator<XMLNode> it = nodes.iterator();

		while( it.hasNext() )
		{
			System.out.println( it.next() );
		}
	}

	@Override
	public String toString()
	{
		return "[EXMLResource]";
	}
}