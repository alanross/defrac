package com.adjazent.defrac.ui.text.font.glyph;

import com.adjazent.defrac.core.utils.IDisposable;
import com.adjazent.defrac.ui.text.UITextSelection;
import defrac.geom.Point;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IUIGlyphInteraction extends IDisposable
{
	/**
	 *
	 */
	int getCursorIndexForPoint( LinkedList<UIGlyph> glyphs, Point point );

	/**
	 *
	 */
	UIGlyph getGlyphUnderPoint( LinkedList<UIGlyph> glyphs, Point point );

	/**
	 *
	 */
	void getWordUnderPoint( LinkedList<UIGlyph> glyphs, Point point, UITextSelection selection );
}

