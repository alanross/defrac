package com.adjazent.defrac.ui.widget;

import com.adjazent.defrac.core.notification.action.Action;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIActionType
{
	public static final int BUTTON_CLICK = Action.createUniqueType();
	public static final int BUTTON_SELECT = Action.createUniqueType();

	public static final int SLIDER_VALUE_CHANGED = Action.createUniqueType();
	public static final int SLIDER_VALUE_COMPLETED = Action.createUniqueType();

	public static final int CELL_CLICK = Action.createUniqueType();
	public static final int CELL_SELECT = Action.createUniqueType();

	public static final int TEXT_CHANGE = Action.createUniqueType();
	public static final int TEXT_SELECTION_CHANGE = Action.createUniqueType();

	private UIActionType()
	{
	}

	@Override
	public String toString()
	{
		return "[UIActionType]";
	}
}