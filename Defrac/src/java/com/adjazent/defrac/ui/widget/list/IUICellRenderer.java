package com.adjazent.defrac.ui.widget.list;

import defrac.display.DisplayObjectContainer;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IUICellRenderer
{
	void onAttach( UICellData data, float cellWidth, float cellHeight );

	void onDetach();

	UICellData getData();

	DisplayObjectContainer getContainer();

	void setY( int value );

	void setX( int value );
}