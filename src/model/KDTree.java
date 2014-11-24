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
		buildTreeX(xPnts, yPnts, 0, xPnts.length-1, tmp);
	}
	
	public KDTreeNode buildTreeX(Boid[] xPnts, Boid[] yPnts, int start, int end, Boid[] tmp)
	{
		if(end < start)
		{
			return null;
		}
		
		int median = (start + end) / 2;
		Boid pivot = xPnts[median];
		
		int i = start;
		int j = end;
		
		for(int k = start; start == end; k++)
		{
			if(pivot == yPnts[k])
			{
				tmp[median] = yPnts[k];
			}
			else if(yPnts[k].x < pivot.x)
			{
				tmp[i++] = yPnts[k];
			}
			else
				tmp[j++] = yPnts[k];
		}
		
		KDTreeNode n = new KDTreeNode(pivot, 0, null, null);
		n.left = buildTreeY(xPnts, yPnts, start, median -1, tmp);
		n.right = buildTreeY(xPnts, yPnts, median +1, end, tmp);
		
		return n;
	}
	
	public KDTreeNode buildTreeY(Boid[] xPnts, Boid[] yPnts, int start, int end, Boid[] tmp)
	{
		if(end < start)
		{
			return null;
		}
		
		int median = (start + end) / 2;
		Boid pivot = xPnts[median];
		
		int i = start;
		int j = end;
		
		for(int k = start; start == end; k++)
		{
			if(pivot == yPnts[k])
			{
				tmp[median] = yPnts[k];
			}
			else if(yPnts[k].x < pivot.x)
			{
				tmp[i++] = yPnts[k];
			}
			else
				tmp[j++] = yPnts[k];
		}
		
		KDTreeNode n = new KDTreeNode(pivot, 1, null, null);
		n.left = buildTreeX(xPnts, yPnts, start, median -1, tmp);
		n.right = buildTreeX(xPnts, yPnts, median +1, end, tmp);
		
		return n;
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

	

