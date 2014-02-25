package com.adjazent.defrac.ui.widget;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIActionType
{
	public static final String BUTTON_CLICK = "BUTTON_CLICK";
	public static final String BUTTON_SELECT = "BUTTON_SELECT";

	public static final String SLIDER_VALUE_CHANGED = "SLIDER_VALUE_CHANGED";
	public static final String SLIDER_VALUE_COMPLETED = "SLIDER_VALUE_CHANGED";

	private UIActionType()
	{
	}

	@Override
	public String toString()
	{
		return "[UIActionType]";
	}
}