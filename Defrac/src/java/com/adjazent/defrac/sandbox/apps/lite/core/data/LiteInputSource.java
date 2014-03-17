package com.adjazent.defrac.sandbox.apps.lite.core.data;

import com.adjazent.defrac.ui.surface.IUISkin;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteInputSource
{
	public final String id;
	public final IUISkin preview;
	public final IUISkin full;

	public LiteInputSource( String id, IUISkin preview, IUISkin full )
	{
		this.id = id;
		this.preview = preview;
		this.full = full;
	}

	@Override
	public String toString()
	{
		return "[LiteInputSource]";
	}
}