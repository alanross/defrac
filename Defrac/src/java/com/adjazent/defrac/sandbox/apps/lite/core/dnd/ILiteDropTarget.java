package com.adjazent.defrac.sandbox.apps.lite.core.dnd;

import defrac.display.event.UIEventTarget;
import defrac.geom.Point;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface ILiteDropTarget extends UIEventTarget
{
	boolean onDragIn( ILiteDragItem dragItem, Point pos );

	void onDragOut( ILiteDragItem dragItem, Point pos );

	void onDragDone( ILiteDragItem dragItem, Point pos );
}