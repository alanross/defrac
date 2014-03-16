package com.adjazent.defrac.ds.tree.trie;

import com.adjazent.defrac.core.error.MissingImplementationError;
import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.core.utils.StringUtils;

import java.util.LinkedList;

/**
 * A Trie tree implementation to check, in a timely manner, if a word exists
 * in a large collection of words and to return a list of words of which given
 * word is a prefix of.
 *
 * Each word is split up into single chars. Each char is then stored in a
 * single node. The root node does not store a char.
 *
 * This example stores the words aa, ab, ac, mm, mn and z.
 * "..." indicates a node can hold link to as many nodes as characters in alphabet.
 *
 *                  root
 *         /   ...   |   ...   \
 *        a          m          z
 *      / | \       / \
 *     a  b  c     m   n
 *
 * Usages:
 * - auto completion
 * - type-ahead requests
 *
 * Java version on which this port is based on:
 * http://logos.cs.uic.edu/340/assignments/Solutions/Wordpopup/curso/trie.java
 *
 * @author Alan Ross
 * @version 0.1
 */
public final class TrieTree
{
	private TrieTreeNode _root;

	private int _numEntries;

	/**
	 * Creates a new instance of TrieTree.
	 */
	public TrieTree()
	{
		_root = null;

		_numEntries = 0;
	}

	private String cleanString( String str )
	{
		int index = 0; // starting index is 0

		// convert the string to lower case
		str = str.toLowerCase();

		// convert the String to an array of chars to easily
		// manipulate each char
		char[] chars = str.toCharArray(); // holds the old String
		char[] result = new char[ str.length() ]; // will make up the new String

		// loop until every char in myChars is tested
		for( char c : chars )
		{
			// accept all alphabetic characters only
			if( c >= 'a' && c <= 'z' )
			{
				result[ index++ ] = c;
			}
		}

		return ( new String( result ) );
	}

	/**
	 * Follows the tree from root until the prefix is found.
	 * Ignores if string is full word or only prefix of word.
	 */
	private TrieTreeNode findLastNodeForString( String str )
	{
		if( StringUtils.isEmpty( str ) )
		{
			return null;
		}

		TrieTreeNode node = _root;

		final int n = str.length();

		//go through every char of string
		for( int i = 0; i < n; ++i )
		{
			//move node down each time
			if( node.down != null )
			{
				node = node.down;
			}
			//if node can't go down, and str hasn't been found yet, its not in the tree
			else
			{
				return null;
			}

			//search the children lists for the correct path to follow
			while( str.charAt( i ) != node.glyph )
			{
				//if the correct path hasn't been found str is not in tree
				if( node.next == null )
				{
					return null;
				}

				node = node.next;
			}
		}

		return node;
	}

	/**
	 * Add each complete word which can be reached from given node to a result list.
	 * E.g. Starting from 'love' the node will contain 'e' and list all word of
	 * which 'love' is prefix, like 'lovely', 'loved'.
	 */
	private void recursiveList( TrieTreeNode node, String str, LinkedList<String> result )
	{
		while( node != null && node.glyph != TrieTreeNode.EMPTY )
		{
			recursiveList( node.down, str + node.glyph, result );

			if( node.wordEnd )
			{
				result.addLast( str + node.glyph );
			}

			node = node.next;
		}
	}

	/**
	 * Inserts given words list into dictionary.
	 */
	public void build( LinkedList<String> words )
	{
		for( String word : words )
		{
			insert( word );
		}
	}

	/**
	 * Insert a string into the dictionary.
	 */
	public void insert( String str )
	{
		//remove punctuation and special characters
		str = cleanString(str);

		if( StringUtils.isEmpty( str ) )
		{
			return;
		}

		int level = 0;

		if( _root == null )
		{
			_root = new TrieTreeNode( TrieTreeNode.EMPTY, level );
		}

		TrieTreeNode node = _root;
		TrieTreeNode nextNode = _root;
		TrieTreeNode parentNode = _root;

		char glyph;
		int index = 0;

		// loop until every char in str is inserted
		while( index < str.length() )
		{
			level++;

			glyph = str.charAt( index );

			// there are no chars on this tree level yet. create a new node with given char
			if( node.down == null )
			{
				node.down = new TrieTreeNode( glyph, level );

				//this will make a lot of null check obsolete
				node.down.next = new TrieTreeNode( TrieTreeNode.EMPTY, level );
				node.down.next.down = node;

				node = node.down;
			}
			// No chars on this tree level yet, insert it at right position or ignore if this char already exists
			else
			{
				parentNode = node;
				node = node.down;

				// char has smallest value, so prepend before existing ones
				if( glyph < node.glyph )
				{
					node = new TrieTreeNode( glyph, level, node );
					parentNode.down = node;
				}
				// find correct place where to append char
				else if( glyph > node.glyph )
				{
					// find the correct position to insert char based on alphabetical order
					while( node.glyph != TrieTreeNode.EMPTY && glyph > node.glyph )
					{
						nextNode = node;
						node = node.next;
					}

					// this char does not exist yet, so append it
					if( glyph != node.glyph )
					{
						node = new TrieTreeNode( glyph, level, node );
						nextNode.next = node;
					}
				}
			}

			index++;
		}


		if( !node.wordEnd )
		{
			//at this point it will always be at the start of a word
			node.wordEnd = true;

			_numEntries++;
		}
		else
		{
			Log.trace( Context.DEFAULT, this, "ignoring insert as " + str + " already exists" );
		}
	}

	/**
	 * Removes a given string from the tree
	 *
	 * @param str
	 */
	public void remove( String str )
	{
		_numEntries--;

		throw new MissingImplementationError();
	}

	/**
	 * Returns true if string is found; false otherwise.
	 */
	public boolean has( String str )
	{
		return ( findLastNodeForString( str ) != null );
	}

	/**
	 * list all words in the dictionary.
	 */
	public LinkedList<String> list()
	{
		LinkedList<String> result = new LinkedList<String>();

		if( _root == null )
		{
			return result;
		}

		recursiveList( _root.down, "", result );

		return result;
	}

	/**
	 * Returns a list of all strings of which given string is prefix.
	 * The list is empty if string is not a prefix of any word.
	 */
	public LinkedList<String> find( String str )
	{
		LinkedList<String> result = new LinkedList<String>();

		final TrieTreeNode node = findLastNodeForString( str );

		// nothing found
		if( node == null )
		{
			return result;
		}

		if( node.wordEnd )
		{
			result.addLast( str );
		}

		//populate result list with all words of which str is prefix
		recursiveList( node.down, str, result );

		return result;
	}

	/**
	 * Clear the TrieTree.
	 */
	public void empty()
	{
		_root = null;

		_numEntries = 0;
	}

	/**
	 * @inheritDoc
	 */
	public void dispose()
	{
		_root = null;

		_numEntries = 0;
	}

	/**
	 * Returns the number of word entries in the dictionary.
	 */
	public int getSize()
	{
		return _numEntries;
	}

	/**
	 * Returns true if the dictionary is empty
	 */
	public boolean isEmpty()
	{
		return ( 0 == _numEntries );
	}

	/**
	 * Creates and returns a string representation of the TrieTree object.
	 */
	@Override
	public String toString()
	{
		return "[TrieTree size:" + getSize() + "]";
	}
}