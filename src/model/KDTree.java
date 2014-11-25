package model;

import java.lang.reflect.Array;

public class KDTree
{
	private KDTreeNode root;
	
	public KDTree()
	{
		root = null;
	}
	
	public void buildTree(Boid[] xPnts, Boid[] yPnts)
	{
		Boid[] tmp = (Boid[])Array.newInstance(KDTree.Boid.class, xPnts.length);
		root = buildTreeX(xPnts, yPnts, 0, xPnts.length-1, tmp);
		
		System.out.println(tmp.length);
		for(int count = 0; count < tmp.length; count++)
		{
			System.out.println(tmp[count]);
		}
	}
	
	public KDTreeNode buildTreeX(Boid[] xPnts, Boid[] yPnts, int start, int end, Boid[] tmp)
	{
		System.out.println("X");
		if(end < start)
		{
			return null;
		}
		
		int median = (start + end) / 2;
		Boid pivot = xPnts[median];
		
		int i = start;
		int j = end;
		
		for(int k = start; k == end; k++)
		{
			if(pivot == xPnts[k])
			{
				tmp[median] = xPnts[k];
			}
			else if(xPnts[k].x < pivot.x)
			{
				tmp[i++] = xPnts[k];
			}
			else
				tmp[j++] = xPnts[k];
		}
		
		KDTreeNode n = new KDTreeNode(pivot, 0, null, null);
		n.right = buildTreeY(xPnts, yPnts, start, median -1, tmp);
		n.left = buildTreeY(xPnts, yPnts, median +1, end, tmp);
		
		return n;
	}
	
	public KDTreeNode buildTreeY(Boid[] xPnts, Boid[] yPnts, int start, int end, Boid[] tmp)
	{
		System.out.println("Y");
		if(end < start)
		{
			return null;
		}
		
		int median = (start + end) / 2;
		Boid pivot = xPnts[median];
		
		int i = start;
		int j = end;
		
		for(int k = start; k == end; k++)
		{
			if(pivot == yPnts[k])
			{
				tmp[median] = yPnts[k];
			}
			else if(yPnts[k].y < pivot.y)
			{
				tmp[i++] = yPnts[k];
			}
			else
				tmp[j++] = yPnts[k];
		}
		
		KDTreeNode n = new KDTreeNode(pivot, 1, null, null);
		n.right = buildTreeX(xPnts, yPnts, start, median -1, tmp);
		n.left = buildTreeX(xPnts, yPnts, median +1, end, tmp);
		
		return n;
	}
	
	public void print()
	{
		KDTreeNode current = root.right.right;
		System.out.println("Left: " + current.left);
		System.out.println("Right: " + current.right);
	}
	
	/**
	 * Determines the size of this tree
	 */
	public int size() 
	{
        return size(root);
    }
	/**
	 * Recursively counts every node held in this tree.
	 */
    private int size(KDTreeNode n)
    {
        if (n == null) return 0;
        return 1 + size(n.left) + size(n.right);
    }
	
	//-----------------------------KDTreeNode Class-------------------------------------------
	private class KDTreeNode
	{
		public Boid boid; //position
		public int split; //0 = x 1 = y
		public KDTreeNode left, right; //left and right nodes.
		
		/**
		 * Creates a new KDTreeNode. Used to hold data within a KDTree.
		 * @param x - x position of this node
		 * @param y - y position of this node
		 * @param split - 0 if x oriented, 1 if y oriented.
		 * @param left - left child
		 * @param right - right child
		 */
		public KDTreeNode(double x, double y, int split, KDTreeNode left, KDTreeNode right)
		{
			boid = new Boid(x, y);
			this.split = split;
			this.left = left;
			this.right = right;
		}
		
		public KDTreeNode(Boid boid, int split, KDTreeNode left, KDTreeNode right)
		{
			this.boid = boid;
			this.split = split;
			this.left = left;
			this.right = right;
		}
		
		@Override
		public String toString() 
		{
			String string = "X: " + boid.x;
			string += "\nY: " + boid.y;
			return string;
		}
	}
	
	public class Boid
	{
		public double x;
		public double y;
		
		public Boid(double x, double y)
		{
			this.x = x;
			this.y = y;
		}
	}

}

	

