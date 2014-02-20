package com.adjazent.defrac.ui.text.processing;

import com.adjazent.defrac.math.geom.MRectangle;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextLayout
{
	public LinkedList<UITextLine> lines;
	public MRectangle bounds = new MRectangle();

	public UITextLayout( LinkedList<UITextLine> lines, MRectangle bounds )
	{
		this.lines = ( lines != null ) ? lines : new LinkedList<UITextLine>();
		this.bounds = bounds;
	}

	@Override
	public String toString()
	{
		return "[UITextLayout]";
	}
}