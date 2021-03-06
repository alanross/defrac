package com.adjazent.defrac.ui.text.processing;

import com.adjazent.defrac.ui.text.UITextFormat;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IUITextRenderer
{
	public void renderText( UITextLayout block, UITextFormat format );

	public void renderTextDependantAction();
}

