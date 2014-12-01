package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class KDTree
{
	private KDTreeNode root; //root node of the tree.
	
	/**
	 * Creates a new KDTree. BuildTree should be called before using this tree.
	 */
	public KDTree()
	{
		root = null;
	}
	
	/**
	 * Finds all neighbours whose distance is from the target boid is smaller than the given range.
	 */
	public ArrayList<Boid> findNeighbours(double range, Boid target)
	{
		ArrayList<Boid> neighbours = new ArrayList<Boid>(); //list to hold neighbours
		range = range*range; //need to use range^2
		return findNeighbours(range, neighbours, target, root); //recursive method
	}
	
	/**
	 * Recursively determines the neighbours that are within a certain range of the target Boid.
	 * @param range - distance^2 from the target boid.
	 * @param neighbours - list to hold qualifying neighbours
	 * @param target - target boid to compare to
	 * @param current - currently accessed node in the tree.
	 * @return list of neighbours within the given range.
	 */
	private ArrayList<Boid> findNeighbours(double range, ArrayList<Boid> neighbours, Boid target, KDTreeNode current)
	{
		if(current == null) //base case, fell off tree.
		{
			return neighbours;
		}
		
		//calculate distance
		double xDist = 0; //difference on the x-axis
		if(target.x > current.boid.x)
		{
			xDist = target.x - current.boid.x;
		}
		else
		{
			xDist = current.boid.x - target.x;
		}
		
		double yDist = 0; //difference on the y-axis
		if(target.y > current.boid.y)
		{
			yDist = target.y - current.boid.y;
		}
		else
		{
			yDist = current.boid.y - target.y;
		}
		
		double distance = (xDist * xDist) + (yDist * yDist); //distance formula, gives distance^2
		
		if(distance > range) //too far away from each other. (comparing distance^2 and range^2)
		{
			return neighbours;
		}
		else //within range, add this to the list.
		{
			neighbours.add(current.boid);
		}
		
		if(current.split == 0) //current level ordered by x-axis
		{
			if(xDist > root.boid.x)
			{
				findNeighbours(range, neighbours, target, current.left);
			}
			else
			{
				findNeighbours(range, neighbours, target, current.right);
			}
		}
		else //current level ordered by y-axis
		{
			if(yDist > root.boid.y)
			{
				findNeighbours(range, neighbours, target, current.left);
			}
			else
			{
				findNeighbours(range, neighbours, target, current.right);
			}
		}
		
		return neighbours;
	}
	
	/**
	 * Builds the tree using the given list of boids.
	 * This method must be called before using the tree.
	 */
	public void buildTree(ArrayList<Boid> boids)
	{
		Boid[] xList = (Boid[])Array.newInstance(KDTree.Boid.class, boids.size()); //array to hold the boids sorted by their x values
		Boid[] yList = (Boid[])Array.newInstance(KDTree.Boid.class, boids.size()); //array to hold the boids sorted by their y values
		for(int count = 0; count < boids.size(); count++) //put the boids into their lists.
		{
			xList[count] = boids.get(count);
			yList[count] = boids.get(count);
		}
		
		//sort them.
		sortByX(xList, 0, xList.length-1);
		sortByY(yList, 0, yList.length-1);
		Boid[] tmp = (Boid[])Array.newInstance(KDTree.Boid.class, xList.length); //temporary array
		root = buildTreeX(xList, yList, 0, xList.length-1, tmp);
	}
	
	/**
	 * Builds the tree comparing the x-values found in the x value sorted list.
	 */
	private KDTreeNode buildTreeX(Boid[] xPnts, Boid[] yPnts, int start, int end, Boid[] tmp)
	{
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
	
	/**
	 * Builds the tree comparing y values in the y sorted list.
	 */
	private KDTreeNode buildTreeY(Boid[] xPnts, Boid[] yPnts, int start, int end, Boid[] tmp)
	{
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
	 * Uses the quicksort algorithm on the given list of boids using the median of three elements as a pivot
	 * @param array list to be sorted
	 * @param start start of the list (inclusive)
	 * @param end end of the list (inclusive)
	 * @return boids sorted by their x values.
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
	 * Uses the quicksort algorithm on the given list of boids using the median of three elements as a pivot
	 * @param array list to be sorted
	 * @param start start of the list (inclusive)
	 * @param end end of the list (inclusive)
	 * @return list of boids sorted by their y values.
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
	 * Finds the median of the x values contained within the first, middle, and ending index.
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
	 * Finds the median of the y values contained within the first, middle, and ending index.
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

	

