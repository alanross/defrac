package com.adjazent.defrac.ui.text.processing;

import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.font.UIFont;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IUITextComposer
{
	UITextLayout layoutText( LinkedList<UIGlyph> glyphs, LinkedList<UIGlyph> ellipsis, UIFont font, UITextFormat format, MRectangle maxSize );
}

