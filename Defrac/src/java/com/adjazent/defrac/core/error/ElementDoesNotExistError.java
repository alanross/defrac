package com.adjazent.defrac.core.error;

import com.adjazent.defrac.core.utils.ArrayUtils;

/**
 * @author Alan Ross
 * @version 0.1
 */
@SuppressWarnings( "serial" )
public final class ElementDoesNotExistError extends Error
{
	public ElementDoesNotExistError( Object... params )
	{
		super( ArrayUtils.join( params, " " ) );

		System.out.println( "Throw error: " + this );
	}

	@Override
	public String toString()
	{
		return "[ElementDoesNotExistError message:" + getMessage() + "]";
	}
}

