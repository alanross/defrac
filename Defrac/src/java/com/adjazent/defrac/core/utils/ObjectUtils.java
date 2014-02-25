package com.adjazent.defrac.core.utils;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class ObjectUtils
{


	public static String obj2Str( Object obj )
	{
		//http://stackoverflow.com/questions/1526826/printing-all-variables-value-from-a-class

//		int n = Array.getLength( array )
//		    for (int i = 0; i < n; i++) {
//		        if (Array.get(array, i).equals(item)) {
//		            return true;
//		        }
//		    }


		StringBuilder result = new StringBuilder();
		String newLine = System.lineSeparator();

		result.append( obj.getClass().getName() );
		result.append( "{" );
		result.append( newLine );

		//determine fields declared in this class only (no fields of superclass)
//		Field[] fields = this.getClass().getDeclaredFields();

//		//print field names paired with their values
//		for( Field field : fields )
//		{
//			result.append( "  " );
//			try
//			{
//				result.append( field.getName() );
//				result.append( ": " );
//				//requires access to private field:
//				result.append( field.get( this ) );
//			}
//			catch( IllegalAccessException ex )
//			{
//				System.out.println( ex );
//			}
//			result.append( newLine );
//		}
		result.append( "}" );
//
		return result.toString();
	}

	private ObjectUtils()
	{
	}

	@Override
	public String toString()
	{
		return "[ObjectUtils]";
	}
}