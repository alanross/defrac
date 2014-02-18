package com.adjazent.defrac.ui.resource;

import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.core.job.Job;
import com.adjazent.defrac.core.xml.XML;
import defrac.event.ResourceEvent;
import defrac.lang.Procedure;
import defrac.resource.BinaryResource;

import java.nio.charset.Charset;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UILoadXmlJob extends Job
{
	private BinaryResource _loader;
	private String _url;
	private XML _xml;

	public UILoadXmlJob( String url )
	{
		_url = url;
	}

	@Override
	protected void onStart()
	{
		if( _loader != null )
		{
			throw new GenericError( this, "Already loading" );
		}

		_loader = BinaryResource.from( _url );

		_loader.onStart.attach( new Procedure<ResourceEvent.Start>()
		{
			@Override
			public void apply( ResourceEvent.Start event )
			{
			}
		} );
		_loader.onProgress.attach( new Procedure<ResourceEvent.Progress>()
		{
			@Override
			public void apply( ResourceEvent.Progress event )
			{
			}
		} );
		_loader.onError.attach( new Procedure<ResourceEvent.Error>()
		{
			@Override
			public void apply( ResourceEvent.Error event )
			{
				fail( new Error( "LoadError" + event.toString() ) );
			}
		} );
		_loader.onComplete.attach( new Procedure<ResourceEvent.Complete<byte[]>>()
		{
			@Override
			public void apply( ResourceEvent.Complete<byte[]> event )
			{
				_xml = new XML( new String( event.content, Charset.defaultCharset() ) );
				complete();
			}
		} );

		_loader.load();
	}

	@Override
	public void dispose()
	{
		super.dispose();

		_loader = null;
		_xml = null;
	}

	public XML getXml()
	{
		return _xml;
	}

	public String getUrl()
	{
		return _url;
	}

	@Override
	public String toString()
	{
		return "[UIXmlLoader]";
	}
}

