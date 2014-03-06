package com.adjazent.defrac.ui.text;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextSelection
{
	public int firstIndex;
	public int lastIndex;

	/**
	 * Creates a new instance of UITextSelection.
	 */
	public UITextSelection( int firstIndex, int lastIndex )
	{
		this.firstIndex = firstIndex;
		this.lastIndex = lastIndex;
	}

	/**
	 * Creates a new instance of UITextSelection.
	 */
	public UITextSelection()
	{
		this.firstIndex = 0;
		this.lastIndex = 0;
	}

	public void setTo( int firstIndex, int lastIndex )
	{
		this.firstIndex = firstIndex;
		this.lastIndex = lastIndex;
	}

	/**
	 * Creates and returns a string representation of the UITextSelection object.
	 */
	@Override
	public String toString()
	{
		return "[UITextSelection" +
				", firstIndex:" + firstIndex +
				", lastIndex:" + lastIndex +
				"]";
	}
}

