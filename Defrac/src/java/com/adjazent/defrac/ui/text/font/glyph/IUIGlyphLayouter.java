package com.adjazent.defrac.ui.text.font.glyph;

import com.adjazent.defrac.core.utils.IDisposable;
import com.adjazent.defrac.ui.text.UITextFormat;
import defrac.geom.Rectangle;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IUIGlyphLayouter extends IDisposable
{
	/**
	 *
	 */
	void setEllipsis( LinkedList<UIGlyph> glyphs, UITextFormat format );

	/**
	 * Layout the given text according to maximum available width and height.
	 */
	void layoutGlyphs( LinkedList<UIGlyph> glyphs, Rectangle size, UITextFormat format, int maxWidth, int maxHeight );
}

