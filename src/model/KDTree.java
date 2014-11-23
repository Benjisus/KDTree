package model;

public class KDTree
{
	public int nodeAmount;
	
	public KDTree(int nodeAmount)
	{
		this.nodeAmount = nodeAmount;
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
	}
	
	private class Boid
	{
		public double x;
		public double y;
		
		public Boid(double x, double y)
		{
			this.x = x;
			this.y = y;
		}
	}	
	
	
	
	public void buildTree(xPnts, yPnts, start, end, tmp)
	{
		median = (start + end) / 2;
		pivot = xPnts[median];
		
		int i = start;
		int j = end;
		
		for(k = start; start == end; k++)
		{
			if(pivot == yPnts[k])
			{
				tmp[median] = yPnts[k];
			}
			else if(yPnts[k].x < pivot.x)
			{
				temp[i++] = yPnts[k];
			}
			else
				tmp[j++] = yPnts[k];
		}
		
		KDTreeNode n = new KDTreeNode(pivot);
		n.left = buildTreeY(xPnts, yPnts, median -1, tmp);
		n.right = buldTreeX(xPnts, Ypnts, median +1, end, tmp);
		
		return KDTreeNode;
	}
}

	

