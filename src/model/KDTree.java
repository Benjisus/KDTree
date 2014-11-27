package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class KDTree
{
	private KDTreeNode root;
	
	public KDTree()
	{
		root = null;
	}
	
	public ArrayList<Boid> findNeighbours(double range, Boid target)
	{
		ArrayList<Boid> neighbours = new ArrayList<Boid>();
		range = range*range;
		return findNeighbours(range, neighbours, target, root);
	}
	
	private ArrayList<Boid> findNeighbours(double range, ArrayList<Boid> neighbours, Boid target, KDTreeNode current)
	{
		if(current.left == null && current.right == null)
		{
			neighbours.add(current.boid);
			return neighbours;
		}
		
		double xDist = 0;
		if(target.x > current.boid.x)
		{
			xDist = target.x - current.boid.x;
		}
		else
		{
			xDist = current.boid.x - target.x;
		}
		
		double yDist = 0;
		if(target.y > current.boid.y)
		{
			yDist = target.y - current.boid.y;
		}
		else
		{
			yDist = current.boid.y - target.y;
		}
		
		double distance = (xDist * xDist) + (yDist * yDist); //distance formula
		
		if(distance > range)
		{
			return neighbours;
		}
		else
		{
			neighbours.add(current.boid);
		}
		
		findNeighbours(range, neighbours, target, current.left);
		findNeighbours(range, neighbours, target, current.right);
		
		return neighbours;
	}
	
	public void buildTree(ArrayList<Boid> boids)
	{
		Boid[] xList = (Boid[])Array.newInstance(KDTree.Boid.class, boids.size());
		Boid[] yList = (Boid[])Array.newInstance(KDTree.Boid.class, boids.size());
		for(int count = 0; count < boids.size(); count++)
		{
			xList[count] = boids.get(count);
			yList[count] = boids.get(count);
		}
		
		sortByX(xList, 0, xList.length-1);
		sortByY(yList, 0, yList.length-1);
		Boid[] tmp = (Boid[])Array.newInstance(KDTree.Boid.class, xList.length);
		root = buildTreeX(xList, yList, 0, xList.length-1, tmp);
	}
	
	private KDTreeNode buildTreeX(Boid[] xPnts, Boid[] yPnts, int start, int end, Boid[] tmp)
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
	
	private KDTreeNode buildTreeY(Boid[] xPnts, Boid[] yPnts, int start, int end, Boid[] tmp)
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
	
	/**
	 * Uses the quicksort algorithm on the given list using the median of three elements as a pivot
	 * @param array list to be sorted
	 * @param start start of the list (inclusive)
	 * @param end end of the list (inclusive)
	 * @return sorted list
	 */
	private Boid[] sortByX(Boid[] array, int start, int end)
	{
		int length = end - start; //length of this sublist
		
		if(length < 2) //base case
		{
			return array;
		}
		
		int pivot = medianX(array, start, end); //find the median value at the first, middle and last index
		
		swap(array, pivot, end-1); //move pivot to the end of list to avoid special case
		pivot = end-1; //pivot has been moved.
		
		//reorder array
		int left = start; //start from beginning
		int right = end-1; //start from end
		while(true)
		{
			while(left < end-1 && array[left].x < array[pivot].x)//look for number out of place
			{
				left++; //move towards middle
			}
			while(right > start && array[pivot].x < array[right].x)//look for number out of place
			{
				right--; //move towards middle
			}
			
			if(left >= right) //pointers have passed each other, no other swaps are needed
			{
				break;
			}
			else
			{
				swap(array, left, right); //switch the numbers that are at the current pointers
				right--; //move the pointers along
				left++;
			}
		}
		//put pivot back
		swap(array, left, pivot);
		
		//split into two subproblems
		sortByX(array, start, left);
		sortByX(array, left+1, end);
		
		return array;
	}
	
	/**
	 * Uses the quicksort algorithm on the given list using the median of three elements as a pivot
	 * @param array list to be sorted
	 * @param start start of the list (inclusive)
	 * @param end end of the list (inclusive)
	 * @return sorted list
	 */
	private Boid[] sortByY(Boid[] array, int start, int end)
	{
		int length = end - start; //length of this sublist
		
		if(length < 2) //base case
		{
			return array;
		}
		
		int pivot = medianY(array, start, end); //find the median value at the first, middle and last index
		
		swap(array, pivot, end-1); //move pivot to the end of list to avoid special case
		pivot = end-1; //pivot has been moved.
		
		//reorder array
		int left = start; //start from beginning
		int right = end-1; //start from end
		while(true)
		{
			while(left < end-1 && array[left].y < array[pivot].y)//look for number out of place
			{
				left++; //move towards middle
			}
			while(right > start && array[pivot].y < array[right].y)//look for number out of place
			{
				right--; //move towards middle
			}
			
			if(left >= right) //pointers have passed each other, no other swaps are needed
			{
				break;
			}
			else
			{
				swap(array, left, right); //switch the numbers that are at the current pointers
				right--; //move the pointers along
				left++;
			}
		}
		//put pivot back
		swap(array, left, pivot);
		
		//split into two subproblems
		sortByY(array, start, left);
		sortByY(array, left+1, end);
		
		return array;
	}
	
	/**
	 * Swaps the two indexes in the given list
	 * @param array array to swap them in
	 * @param a index of first value to be swapped
	 * @param b index of second value to be swapped
	 */
	private void swap(Boid[] array, int a, int b)
	{
		Boid temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}
	

	/**
	 * Finds the median of the values contained within the first, middle, and ending index.
	 * @param array array to look in
	 * @param start where to start looking
	 * @param end where to stop looking
	 * @return index of the median value
	 */
	private int medianX(Boid[] array, int start, int end)
	{
		int middle = (start + end)/2; //middle index in between start and end
		
		if(array[start].x < array[middle].x) //start is smaller than middle
	    {
	        if(array[start].x >= array[end-1].x) //start is bigger than end
	        {
	            return start;
	        }
	        
	        if(array[middle].x < array[end-1].x) //middle is smaller than end
	        {
	        	return middle;
	        }
	    }
	    else //start is bigger than middle
	    {
	        if(array[start].x < array[end-1].x) //start is smaller than end
	        {
	        	return start;
	        }
	        
	        if(array[middle].x >= array[end-1].x) //middle is bigger than end
	        {
	        	return middle;
	        }
	    }
	    return end-1; 
	}
	
	/**
	 * Finds the median of the values contained within the first, middle, and ending index.
	 * @param array array to look in
	 * @param start where to start looking
	 * @param end where to stop looking
	 * @return index of the median value
	 */
	private int medianY(Boid[] array, int start, int end)
	{
		int middle = (start + end)/2; //middle index in between start and end
		
		if(array[start].y < array[middle].y) //start is smaller than middle
	    {
	        if(array[start].y >= array[end-1].y) //start is bigger than end
	        {
	            return start;
	        }
	        
	        if(array[middle].y < array[end-1].y) //middle is smaller than end
	        {
	        	return middle;
	        }
	    }
	    else //start is bigger than middle
	    {
	        if(array[start].y < array[end-1].y) //start is smaller than end
	        {
	        	return start;
	        }
	        
	        if(array[middle].y >= array[end-1].y) //middle is bigger than end
	        {
	        	return middle;
	        }
	    }
	    return end-1; 
	}
	
	public void print()
	{
		KDTreeNode current = root.left.left;
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

	

