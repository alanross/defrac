package com.adjazent.defrac.ui.texture;

import com.adjazent.defrac.core.error.ValueError;
import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.math.geom.MRectangle;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextureUtil
{
	/**
	 * validates the size of a potential texture (bitmap)
	 * and returns a valid size a texture for the gpu can have.
	 * <p/>
	 * supported size are 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048
	 * <p/>
	 * 145 Example would return 512.
	 */
	public static int validateSize( int size )
	{
		if( size <= 0 )
		{
			throw new ValueError( size + " is not a valid size, it is smaller than 0." );
		}
		if( size < 2 )
		{
			return 2;
		}
		if( size > 2048 )
		{
			return 2048;
		}

		return MMath.nextPowerOfTwo( size );
	}

	/**
	 * Creates a Vector containing the UV coordinates of given sprite MRectangle in relation to
	 * the texture MRectangle.
	 */
	public static double[] createUVs( MRectangle textureRect, MRectangle spriteRect )
	{
		double x = spriteRect.x / textureRect.width;
		double y = spriteRect.y / textureRect.height;
		double w = spriteRect.width / textureRect.width;
		double h = spriteRect.height / textureRect.height;

		double[] uvs = new double[ 8 ];

		// bl, tl, tr, br
		uvs[ 0 ] = x;
		uvs[ 1 ] = y + h;
		uvs[ 2 ] = x;
		uvs[ 3 ] = y;
		uvs[ 4 ] = x + w;
		uvs[ 5 ] = y;
		uvs[ 6 ] = x + w;
		uvs[ 7 ] = y + h;

		return uvs;
	}

	/**
	 * Creates a new MRectangle containing the normalized uv coordinates defined by the spriteRect.
	 */
	public static MRectangle createUVRect( MRectangle textureRect, MRectangle spriteRect )
	{
		return new MRectangle(
				spriteRect.x / textureRect.width,
				spriteRect.y / textureRect.height,
				spriteRect.width / textureRect.width,
				spriteRect.height / textureRect.height
		);
	}

	/**
	 * Creates a new instance of UITextureUtil.
	 */
	private UITextureUtil()
	{
	}

	/**
	 * Generates and returns the string representation of the UITextureUtil object.
	 */
	@Override
	public String toString()
	{
		return "[UITextureUtil]";
	}
}

