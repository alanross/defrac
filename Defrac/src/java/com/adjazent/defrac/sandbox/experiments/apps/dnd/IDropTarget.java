package com.adjazent.defrac.sandbox.experiments.apps.dnd;

import defrac.display.event.UIEventTarget;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IDropTarget extends UIEventTarget
{
	void onDragEnter( IDragable dragable );

	void onDragExit( IDragable dragable );

	void onDragDone( IDragable dragable );
}