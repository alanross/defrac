package com.adjazent.defrac.ui.widget;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIActionType
{
	public static final int BUTTON_CLICK = 1;
	public static final int BUTTON_SELECT = 2;

	public static final int SLIDER_VALUE_CHANGED = 3;
	public static final int SLIDER_VALUE_COMPLETED = 4;

	public static final int CELL_CLICK = 5;
	public static final int CELL_SELECT = 6;

	private UIActionType()
	{
	}

	@Override
	public String toString()
	{
		return "[UIActionType]";
	}
}