package com.adjazent.defrac.sandbox.experiments.war;

import defrac.geom.Point;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Issues
{
	public Issues()
	{
		// get stage()
		// background color
		// quad vs layer
		// layer.resize()?
		// quad alpha ?

		//issueDouble();
	}

	public void wishlist()
	{
		// - Field[] fields = object.getClass().getDeclaredFields();
		// - exceptions.getStackTrace() / printStackTrace();
		// - process xml / json
		// - text rendering
		// - more info about the platform the running is on ( language, monitor info, available memory)
		// - System.err.println( "Error" ); (doesn't show in console, at least not in my browser)
	}

	public void issueIDE()
	{
		// - After an error occurred (e.g. java_lang_Object) the code doesn't seem to be recompiled on new changes,
		//   calling 'clean' helps...
		// - Is there an option to get more info out of a java_lang_Object error?
	}

	public void issueLong()
	{
		long[] longs = new long[]{ 1L, 2L, 3L };

		for( int i = 0; i < longs.length; i++ )
		{
			System.out.println( longs[ i ] );
		}
		// OUTPUT:
		// Unexpected error -> Type "last-error" for more details ->
		// java.lang.IllegalArgumentException: cannot find symbol named '<init>'...
	}

	public void issueDouble()
	{
		double d = 0.0;
		System.out.println( d ); // 0.0

		d = 1.1;
		System.out.println( d ); // 1.1

		d = 10.1;
		System.out.println( d );  // error
	}

	public void issuePoint()
	{
		Point p1 = new Point( 0.0f, 0.0f );
		Point p2 = new Point( 11.0f, 11.0f );

		System.out.println( p1 );     // works
		System.out.println( p2 );     // error
	}

	public void issueFloat()
	{
		float f_works = Float.parseFloat( "1.1" );
		System.out.println( f_works );

		float f_broken = Float.parseFloat( "11.1" );
		System.out.println( f_broken );

		// OUTPUT:
		// Uncaught #<java_lang_Object>
		// NOTE: float with a value larger than 10 seems to be generally broken?

		float f = 0.0f;

		System.out.println( "works f:" + f );
		for( int i = 0; i < 10; i++ )
		{
			System.out.println( "broken i:" + i + ", f:" + f ); // if you uncomment this it breaks
			f += 0.1f;
		}
		System.out.println( "works f:" + f );

		// OUTPUT:
		// Uncaught #<java_lang_Object>
	}

	public void issueType()
	{
		Object string = new String( "Hello" );
		Object primitiveArray = new int[]{ 1, 2, 3 };

		if( string.getClass() == String.class )
		{
			System.out.println( "Works: .class" );
		}
		if( string instanceof String )
		{
			System.out.println( "Works: instanceof" );
		}
		if( primitiveArray.getClass() == int[].class )
		{
			System.out.println( "Works: .class" );
		}
		if( primitiveArray instanceof int[] )
		{
			System.out.println( "Broken: instanceof" );
			// OUTPUT:
			// Uncaught SyntaxError: Unexpected token ) :8080/DefracDemo/defracdemo.js:32819
			// Uncaught ReferenceError: main is not defined
		}
	}

	public void issueDate()
	{
		DateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
		// -> Uncaught #<java_lang_Object>

		Date date = new Date();
		//-> java.util.concurrent.ExecutionException: java.lang.IllegalArgumentException: cannot find symbol named '<init>'

		System.out.println( dateFormat.format( date ) );
	}

	public void issueTime()
	{
		System.currentTimeMillis();
		//-> java.util.concurrent.ExecutionException: java.lang.IllegalArgumentException: cannot find symbol named '<init>'
	}

	@Override
	public String toString()
	{
		return "[Issues]";
	}
}