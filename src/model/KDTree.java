package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

import controller.Crowd;

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
	 * Use range^2
	 */
	public ArrayList<Crowd.Boid> findNeighbours(double range, Crowd.Boid target)
	{
		ArrayList<Crowd.Boid> neighbours = new ArrayList<Crowd.Boid>(); //list to hold neighbours
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
	private ArrayList<Crowd.Boid> findNeighbours(double range, ArrayList<Crowd.Boid> neighbours, Crowd.Boid target, KDTreeNode current)
	{
		double xDist = 0;
		double yDist = 0;
		double distance = 0;
		
		if(current == null || current.boid == target) //base case, fell off tree, or we are at the target boid.
		{
			return neighbours;
		}
		
		//calculate distance
		xDist = Math.abs(current.boid.posx - target.posx);
		yDist = Math.abs(current.boid.posy - target.posy);
		
		distance = (xDist * xDist) + (yDist * yDist); //distance formula, gives distance^2
		
		if(distance <= range) //too far away from each other. (comparing distance^2 and range^2)
		{
			neighbours.add(current.boid);
			neighbours = findNeighbours(range, neighbours, target, current.left);
			neighbours = findNeighbours(range, neighbours, target, current.right);
		}
		
		if(current.split == 0)
		{
			if(current.boid.posx < target.posx - Math.sqrt(range))
			{
				neighbours = findNeighbours(range, neighbours, target, current.right);
			}
			if(current.boid.posx > target.posx + Math.sqrt(range))
			{
				neighbours = findNeighbours(range, neighbours, target, current.left);
			}
		}
		else
		{
			if(current.boid.posy < target.posy - Math.sqrt(range))
			{
				neighbours = findNeighbours(range, neighbours, target, current.right);
			}
			if(current.boid.posy > target.posy + Math.sqrt(range))
			{
				neighbours = findNeighbours(range, neighbours, target, current.left);
			}
		}
		
		return neighbours;
	}
	
	/**
	 * Builds the tree using the given list of boids.
	 * This method must be called before using the tree.
	 */
	public void buildTree(ArrayList<Crowd.Boid> boids)
	{
		Crowd.Boid[] xList = (Crowd.Boid[])Array.newInstance(Crowd.Boid.class, boids.size()); //array to hold the boids sorted by their x values
		for(int count = 0; count < boids.size(); count++) //put the boids into their lists.
		{
			xList[count] = boids.get(count);
		}
				
		root = buildTreeX(xList, 0, xList.length-1);
	}
	
	/**
	 * Builds the tree comparing the x-values found in the x value sorted list.
	 */
	private KDTreeNode buildTreeX(Crowd.Boid[] xPnts, int start, int end)
	{
		if(end < start)
		{
			return null;
		}
		if(end == start)
		{
			return new KDTreeNode(xPnts[end], 0, null, null);
		}
		
		sortByX(xPnts, start, end);
		int median = (start + end) / 2;
		Crowd.Boid pivot = xPnts[median];
		
		KDTreeNode n = new KDTreeNode(pivot, 0, null, null);
		n.left = buildTreeY(xPnts, start, median -1);
		n.right = buildTreeY(xPnts, median +1, end);
		
		return n;
	}
	
	/**
	 * Builds the tree comparing y values in the y sorted list.
	 */
	private KDTreeNode buildTreeY(Crowd.Boid[] xPnts, int start, int end)
	{
		if(end < start)
		{
			return null;
		}
		if(end == start)
		{
			return new KDTreeNode(xPnts[end], 1, null, null);
		}
		
		sortByY(xPnts, start, end);
		int median = (start + end) / 2;
		Crowd.Boid pivot = xPnts[median];
		
		KDTreeNode n = new KDTreeNode(pivot, 1, null, null);
		n.left = buildTreeX(xPnts, start, median -1);
		n.right = buildTreeX(xPnts, median +1, end);
		
		return n;
	}
	
	/**
	 * Uses the quicksort algorithm on the given list of boids using the median of three elements as a pivot
	 * @param array list to be sorted
	 * @param start start of the list (inclusive)
	 * @param end end of the list (inclusive)
	 * @return boids sorted by their x values.
	 */
	private Crowd.Boid[] sortByX(Crowd.Boid[] array, int start, int end)
	{
		int length = end - start; //length of this sublist
		
		if(length < 2) //base case
		{
			return array;
		}
		
		int pivot = medianX(array, start, end); //find the median value at the first, middle and last index
		
		swap(array, pivot, end); //move pivot to the end of list to avoid special case
		pivot = end; //pivot has been moved.
		
		//reorder array
		int left = start; //start from beginning
		int right = end-1; //start from end
		while(true)
		{
			while(left < end-1 && array[left].posx < array[pivot].posx)//look for number out of place
			{
				left++; //move towards middle
			}
			while(right > start && array[pivot].posx < array[right].posx)//look for number out of place
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
	private Crowd.Boid[] sortByY(Crowd.Boid[] array, int start, int end)
	{
		int length = end - start; //length of this sublist
		
		if(length < 2) //base case
		{
			return array;
		}
		
		int pivot = medianY(array, start, end); //find the median value at the first, middle and last index
		
		swap(array, pivot, end); //move pivot to the end of list to avoid special case
		pivot = end; //pivot has been moved.
		
		//reorder array
		int left = start; //start from beginning
		int right = end-1; //start from end
		while(true)
		{
			while(left < end-1 && array[left].posy < array[pivot].posy)//look for number out of place
			{
				left++; //move towards middle
			}
			while(right > start && array[pivot].posy < array[right].posy)//look for number out of place
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
	private void swap(Crowd.Boid[] array, int a, int b)
	{
		Crowd.Boid temp = array[a];
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
	private int medianX(Crowd.Boid[] array, int start, int end)
	{
		int middle = (start + end)/2; //middle index in between start and end
		
		if(array[start].posx < array[middle].posx) //start is smaller than middle
	    {
	        if(array[start].posx >= array[end].posx) //start is bigger than end
	        {
	            return start;
	        }
	        
	        if(array[middle].posx < array[end].posx) //middle is smaller than end
	        {
	        	return middle;
	        }
	    }
	    else //start is bigger than middle
	    {
	        if(array[start].posx < array[end].posx) //start is smaller than end
	        {
	        	return start;
	        }
	        
	        if(array[middle].posx >= array[end].posx) //middle is bigger than end
	        {
	        	return middle;
	        }
	    }
	    return end; 
	}
	
	/**
	 * Finds the median of the y values contained within the first, middle, and ending index.
	 * @param array array to look in
	 * @param start where to start looking
	 * @param end where to stop looking
	 * @return index of the median value
	 */
	private int medianY(Crowd.Boid[] array, int start, int end)
	{
		int middle = (start + end)/2; //middle index in between start and end
		
		if(array[start].posy < array[middle].posy) //start is smaller than middle
	    {
	        if(array[start].posy >= array[end].posy) //start is bigger than end
	        {
	            return start;
	        }
	        
	        if(array[middle].posy < array[end].posy) //middle is smaller than end
	        {
	        	return middle;
	        }
	    }
	    else //start is bigger than middle
	    {
	        if(array[start].posy < array[end].posy) //start is smaller than end
	        {
	        	return start;
	        }
	        
	        if(array[middle].posy >= array[end].posy) //middle is bigger than end
	        {
	        	return middle;
	        }
	    }
	    return end; 
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
        System.out.println(n);
        return 1 + size(n.left) + size(n.right);
    }
    
    public void print()
    {
    	System.out.println(size());
    }
    
    private void printList(Crowd.Boid[] array)
    {
    	for(int count = 0; count < array.length; count++)
    	{
    		System.out.println(array[count]);
    	}
    }
	
	//-----------------------------KDTreeNode Class-------------------------------------------
	private class KDTreeNode
	{
		public Crowd.Boid boid; //position
		public int split; //0 = x 1 = y
		public KDTreeNode left, right; //left and right nodes.
		
		public KDTreeNode(Crowd.Boid boid, int split, KDTreeNode left, KDTreeNode right)
		{
			this.boid = boid;
			this.split = split;
			this.left = left;
			this.right = right;
		}
		
		@Override
		public String toString() 
		{
			return boid.toString();
		}
	}
}

	

