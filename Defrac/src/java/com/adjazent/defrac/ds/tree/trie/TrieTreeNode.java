package com.adjazent.defrac.ds.tree.trie;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class TrieTreeNode
{
	public static final char EMPTY = ' ';

	public char glyph;

	public int level;

	public boolean wordEnd = false;

	// points to the left most child of the node
	public TrieTreeNode down = null;

	// points to the right sibling of the node
	public TrieTreeNode next = null;

	/**
	 * Creates a new instance of TrieTreeNode.
	 */
	public TrieTreeNode( char glyph, int level, TrieTreeNode next )
	{
		this.glyph = glyph;
		this.level = level;
		this.next = next;
	}

	/**
	 * Creates a new instance of TrieTreeNode.
	 */
	public TrieTreeNode( char glyph, int level )
	{
		this.glyph = glyph;
		this.level = level;
		this.next = null;
	}

	/**
	 * Creates and returns a string representation of the TrieTreeNode object.
	 */
	@Override
	public String toString()
	{
		return "[TrieTreeNode char:" + glyph + " level:" + level + "wordEnd:" + wordEnd + "]";
	}
}

