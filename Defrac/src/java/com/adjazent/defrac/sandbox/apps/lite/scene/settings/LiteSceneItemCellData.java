package com.adjazent.defrac.sandbox.apps.lite.scene.settings;

import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteSceneItem;
import com.adjazent.defrac.ui.widget.list.UICellData;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSceneItemCellData extends UICellData
{
	public final LiteSceneItem model;

	public LiteSceneItemCellData( LiteSceneItem model )
	{
		super( model.id, 20 );

		this.model = model;
	}

	@Override
	public String toString()
	{
		return "[LiteSceneItemCellData]";
	}
}