package com.adjazent.defrac.sandbox.apps;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7SignalTypes
{
	public static final int SCENE_EDIT_1 = 1;
	public static final int SCENE_EDIT_2 = 2;
	public static final int SCENE_EDIT_3 = 3;

	public static final int INPUT_TYPE_LIVE = 4;
	public static final int INPUT_TYPE_ON_DEMAND = 5;
	public static final int INPUT_TYPE_IMAGE = 6;
	public static final int INPUT_TYPE_USER = 7;

	public static final int SCENE_EDIT_SETTINGS_A = 8;
	public static final int SCENE_EDIT_SETTINGS_B = 9;

	public static final int BUTTON_SELECT = 10;
	public static final int BUTTON_DESELECT = 11;
	public static final int BUTTON_CLICK = 12;

	public Pro7SignalTypes()
	{
	}

	@Override
	public String toString()
	{
		return "[Pro7SignalTypes]";
	}
}