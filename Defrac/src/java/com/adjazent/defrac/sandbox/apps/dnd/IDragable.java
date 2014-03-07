package com.adjazent.defrac.sandbox.apps.dnd;

import com.adjazent.defrac.ui.surface.IUISkin;
import defrac.display.event.UIEventTarget;
import defrac.geom.Point;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IDragable extends UIEventTarget
{
	Point localToGlobal( Point point );

	double getX();

	double getY();

	double getWidth();

	double getHeight();

	IUISkin getSkin();
}