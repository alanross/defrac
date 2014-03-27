package com.adjazent.defrac.sandbox.samples.text;

import com.adjazent.defrac.sandbox.Experiment;
import defrac.display.Label;
import defrac.text.BitmapFont;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class ENativeLabel extends Experiment
{
	public ENativeLabel()
	{

	}

	@Override
	protected void onInit()
	{
		GlyphDisplayObject textRenderer = new GlyphDisplayObject();

		textRenderer.x( 50 );
		textRenderer.y( 50 );

		Label label = new Label();
		label.font( BitmapFont.fromFnt( "fonts/helvetica24.fnt", "fonts/helvetica24.png" ) );
		label.text( "Hello World" );
		label.autoSize( Label.AutoSize.AUTO );
		label.color( 0xFFFFFFFF );

		label.x( 150 );
		label.y( 50 );

		addChild( label );
		addChild( textRenderer );
	}

	@Override
	public String toString()
	{
		return "[ENativeLabel]";
	}
}