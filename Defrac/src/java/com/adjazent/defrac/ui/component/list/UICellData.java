package com.adjazent.defrac.ui.component.list;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UICellData
{
	boolean inViewRange = false;

	private String _text;
	private int _height;

	public UICellData( String text, int height )
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

	@Override
	public String toString()
	{
		return "[UICellData text:" + _text + "]";
	}
}

