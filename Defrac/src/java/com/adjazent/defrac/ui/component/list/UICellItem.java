package com.adjazent.defrac.ui.component.list;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UICellItem
{
	boolean inViewRange = false;

	private String _text;
	private int _height;

	public UICellItem( String text, int height )
	{
		_text = text;
		_height = height;
	}

	public String getText()
	{
		return _text;
	}

	public int getHeight()
	{
		return _height;
	}

	public boolean getIsInViewRange()
	{
		return inViewRange;
	}

	@Override
	public String toString()
	{
		return "[UICellItem  text" + _text + "]";
	}
}

