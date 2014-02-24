package com.adjazent.defrac.ui.widget.pro7;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIActionType
{
	public static final String BUTTON_SELECT = "BUTTON_SELECT";
	public static final String BUTTON_CLICK = "BUTTON_CLICK";
	public static final String SLIDER_VALUE_CHANGED = "SLIDER_VALUE_CHANGED";
	public static final String SLIDER_VALUE_COMPLETED = "SLIDER_VALUE_CHANGED";

	public static final String LIST_CELL_SELECT = "LIST_CELL_SELECT";
	public static final String LIST_CELL_CLICK = "LIST_CELL_CLICK";

	private UIActionType()
	{
	}

	@Override
	public String toString()
	{
		return "[UIActionType]";
	}
}