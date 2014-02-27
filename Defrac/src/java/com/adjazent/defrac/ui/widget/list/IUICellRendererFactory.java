package com.adjazent.defrac.ui.widget.list;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IUICellRendererFactory
{
	IUICellRenderer create( int rowIndex );

	void release( IUICellRenderer renderer );
}