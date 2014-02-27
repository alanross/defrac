package com.adjazent.defrac.ui.widget.list;

import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.ui.widget.UIActionType;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UICellData
{
	public final Action onSelect = new Action( UIActionType.CELL_SELECT );

	boolean inViewRange = false;

	private String _text;
	private int _height;
	private boolean _selected;
	private boolean _lockSelected;

	public UICellData( String text, int height )
	{
		_text = text;
		_height = height;
	}

	public boolean getSelected()
	{
		return _selected;
	}

	public void setSelected( boolean value )
	{
		if( _selected != value )
		{
			if( _lockSelected && !value )
			{
				return;
			}

			_selected = value;

			onSelect.send( this );
		}
	}

	public boolean getLockIfSelected()
	{
		return _lockSelected;
	}

	public void setLockIfSelected( boolean value )
	{
		_lockSelected = value;
	}

	public String getText()
	{
		return _text;
	}

	public int getHeight()
	{
		return _height;
	}

	@Override
	public String toString()
	{
		return "[UICellData text:" + _text + "]";
	}
}

