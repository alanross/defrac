package com.adjazent.defrac.system.hidden;

public interface IHiddenCommand
{
	/**
	 * The code word to trigger the command.
	 * The code word is compared to lowercase keyboard input.
	 */
	String getCodeWord();

	/**
	 * Called if keyboard input and codeWord are identical.
	 */
	void execute();
}

