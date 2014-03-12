package com.adjazent.defrac.sandbox.apps.lite.core.dnd;

import com.adjazent.defrac.ui.surface.IUISkin;
import defrac.display.event.UIEventTarget;
import defrac.geom.Point;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface ILiteDragItem extends UIEventTarget
{
	float x();

	float y();

	float width();

	float height();

	Point localToGlobal( Point point );

	Object getDndData();

	IUISkin getDnDSkin();
}