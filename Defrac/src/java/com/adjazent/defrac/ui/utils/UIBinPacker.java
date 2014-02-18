package com.adjazent.defrac.ui.utils;

import defrac.geom.Rectangle;

/**
 * Performs 'discrete online rectangle packing into a rectangular bin' by maintaining
 * a binary tree of used and free rectangles of the bin. There are several variants
 * of bin packing problems, and this packer is characterized  by* - We're solving the 'online' version of the problem, which means that when we're adding
 * a rectangle, we have no information of the sizes of the rectangles that are going to
 * be packed after this one.
 * - We are packing rectangles that are not rotated. I.e. the algorithm will not flip
 * a rectangle of (w,h) to be stored if it were a rectangle of size (h, w). There is no
 * restriction conserning UV mapping why this couldn't be done to achieve better
 * occupancy, but it's more work. Feel free to try it out.
 * - The packing is done in discrete integer coordinates and not in rational/real numbers (floats).
 * <p/>
 * Internal memory usage is linear to the number of rectangles we've already packed.
 * <p/>
 * For more information, see
 * - Rectangle http packing //www.gamedev.net/community/forums/topic.asp?topic_id=392413
 * - Packing http lightmaps //www.blackpawn.com/texts/lightmaps/default.html
 * <p/>
 * Instead Idea of just picking the first free rectangle to insert the new rect into,
 * check all free ones (or maintain a sorted order) and pick the one that minimizes
 * the resulting leftover area. There is no real reason to maintain a tree - in fact
 * it's just redundant structuring. We (well)could have two lists - one for free
 * rectangles and one for used rectangles. This method would be faster and might
 * even achieve a considerably better occupancy rate.
 * <p/>
 * http//clb.demon.fi/projects/rectangle-bin-packing
 *
 * @author Jukka Jylänki
 * @author Alan Ross (Port to AS3)
 * @version 0.1
 */
public final class UIBinPacker
{
	private int _binWidth;
	private int _binHeight;

	private BinNode _root;


	/**
	 * Creates a new instance of RectangleBinPacker.
	 */
	public void UIBinPacker( int binWidth, int binHeight )
	{
		_binWidth = binWidth;
		_binHeight = binHeight;

		_root = new BinNode();
		_root.x = 0;
		_root.y = 0;
		_root.width = binWidth;
		_root.height = binHeight;
		_root.left = _root.right = null;
	}

	/**
	 * Inserts a new rectangle of the given size into the bin.
	 * Running time is linear to the number of rectangles that have been already packed.
	 * The x and y of rectangle are modified if the rectangle fits into the bin
	 */
	public boolean insert( Rectangle rect )
	{
		BinNode node = internalInsert( _root, ( int ) rect.width, ( int ) rect.height );

		if( node != null )
		{
			rect.x = node.x;
			rect.y = node.y;

			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Computes the ratio of used surface area.
	 */
	public float occupancy()
	{
		float totalSurfaceArea = _binWidth * _binHeight;
		float usedSurfaceArea = getUsedSurfaceArea( _root );

		return usedSurfaceArea / totalSurfaceArea;
	}


	/**
	 * Return the surface area used by the subtree rooted at node.
	 */
	private float getUsedSurfaceArea( BinNode node )
	{
		if( node.left != null || node.right != null )
		{
			float usedSurfaceArea = node.width * node.height;

			if( node.left != null )
			{
				usedSurfaceArea += getUsedSurfaceArea( node.left );
			}
			if( node.right != null )
			{
				usedSurfaceArea += getUsedSurfaceArea( node.right );
			}

			return usedSurfaceArea;
		}

		// This is a leaf node, it doesn't finalitute to the total surface area.
		return 0.0f;
	}

	/**
	 * Inserts a new rectangle in the subtree rooted at the given node.
	 */
	private BinNode internalInsert( BinNode node, int width, int height )
	{
		// If this node is an node, try both leaves for possible space.
		// (The rectangle in an node stores used space, the leaves store free space)
		if( node.left != null || node.right != null )
		{
			BinNode newNode = null;

			if( node.left != null )
			{
				newNode = internalInsert( node.left, width, height );

				if( newNode != null )
				{
					return newNode;
				}
			}
			if( node.right != null )
			{
				newNode = internalInsert( node.right, width, height );

				if( newNode != null )
				{
					return newNode;
				}
			}

			// Didn't fit into either subtree!
			return null;
		}

		// This node is a leaf, but can we fit the new rectangle here?
		if( width > node.width || height > node.height )
		{
			return null; // Too bad, no space.
		}

		// The new cell will fit, split the remaining space along the shorter axis,
		// that is probably more optimal.
		int w = node.width - width;
		int h = node.height - height;

		node.left = new BinNode();
		node.right = new BinNode();

		if( w <= h ) // Split the remaining space in horizontal direction.
		{
			node.left.x = node.x + width;
			node.left.y = node.y;
			node.left.width = w;
			node.left.height = height;

			node.right.x = node.x;
			node.right.y = node.y + height;
			node.right.width = node.width;
			node.right.height = h;
		}
		else // Split the remaining space in vertical direction.
		{
			node.left.x = node.x;
			node.left.y = node.y + height;
			node.left.width = width;
			node.left.height = h;

			node.right.x = node.x + width;
			node.right.y = node.y;
			node.right.width = w;
			node.right.height = node.height;
		}

		// Note (a)that result of the above, it can happen that node.left or node.right
		// is now a degenerate (zero area) rectangle. No need to do anything about it,
		// like removeTextureSkin the nodes as "unnecessary" since they need to (children)exist of
		// this node (this node can't be a leaf anymore).

		// This node is now a non-leaf, so shrink its area - it now denotes
		// *occupied* space instead of free space. Its children spawn the resulting
		// area of free space.
		node.width = width;
		node.height = height;

		return node;
	}

	@Override
	public String toString()
	{
		return "[UIBinPacker]";
	}


	/**
	 * A node of a binary tree. Each node represents a rectangular area of the texture
	 * we surface. Internal nodes store rectangles of used data, whereas leaf nodes track
	 * rectangles of free space. All the rectangles stored in the tree are disjoint.
	 */
	private class BinNode
	{
		// Left and right child. We don't really distinguish which is which, so these could
		// as well be child1 and child2.
		public BinNode left;
		public BinNode right;

		// The top-left coordinate of the rectangle.
		public int x;
		public int y;

		// The dimension of the rectangle.
		public int width;
		public int height;

		/**
		 * Creates a new instance of BinNode.
		 */
		public BinNode()
		{
		}

		/**
		 * Creates and returns a string representation of the BinNode object.
		 */
		@Override
		public String toString()
		{
			return "[BinNode]";
		}
	}
}
