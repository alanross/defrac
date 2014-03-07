package com.adjazent.defrac.sandbox.apps.model.scene;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7SceneSet
{
	public final Pro7Scene a;
	public final Pro7Scene b;

	public Pro7SceneSet( Pro7Scene a, Pro7Scene b )
	{
		this.a = a;
		this.b = b;
	}

	@Override
	public String toString()
	{
		return "[Pro7SceneSet]";
	}
}