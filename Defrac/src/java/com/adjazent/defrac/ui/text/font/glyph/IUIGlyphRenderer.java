package com.adjazent.defrac.ui.text.font.glyph;

import com.adjazent.defrac.core.utils.IDisposable;
import com.adjazent.defrac.ui.text.UITextFormat;
import defrac.geom.Rectangle;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IUIGlyphRenderer extends IDisposable
{
	/**
	 * Renders the given glyphs list to an output.
	 */
	void renderGlyphs( LinkedList<UIGlyph> glyphs, UITextFormat format, Rectangle bounds );
}

