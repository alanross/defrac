package com.adjazent.defrac.ui.widget.pro7;

import com.adjazent.defrac.core.notification.signals.Signals;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UISignalTypes
{
	public static final int INPUT_TYPE_LIVE = Signals.createTypeID();
	public static final int INPUT_TYPE_ON_DEMAND = Signals.createTypeID();
	public static final int INPUT_TYPE_IMAGE = Signals.createTypeID();
	public static final int INPUT_TYPE_USER = Signals.createTypeID();

	public static final int SCENE_EDIT_1 = Signals.createTypeID();
	public static final int SCENE_EDIT_2 = Signals.createTypeID();
	public static final int SCENE_EDIT_3 = Signals.createTypeID();

	public static final int SCENE_EDIT_SETTINGS_A = Signals.createTypeID();
	public static final int SCENE_EDIT_SETTINGS_B = Signals.createTypeID();

	public static final int BUTTON_SELECT = Signals.createTypeID();
	public static final int BUTTON_DESELECT = Signals.createTypeID();
	public static final int BUTTON_CLICK = Signals.createTypeID();

	public static final int BUTTON_GROUP_SELECT = Signals.createTypeID();

	private UISignalTypes()
	{
	}

	@Override
	public String toString()
	{
		return "[UISignalTypes]";
	}
}