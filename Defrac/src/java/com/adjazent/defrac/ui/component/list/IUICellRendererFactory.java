package com.adjazent.defrac.ui.component.list;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IUICellRendererFactory
{
	UICellRenderer create( int index );

	void release( UICellRenderer renderer );
}