package model;

public class ListQueue<T>
{
	private ListNode header; //sentinel node for front of queue
	private ListNode footer; //sentinel node for rear of queue
	
	/**
	 * Creates a new Queue
	 */
	public ListQueue()
	{
		footer = new ListNode(null, null, null);
		header = new ListNode(null, null, null);
		
		footer.prev = header;
		header.next = footer;
	}
	
	/**
	 * Adds the given data to the back of the queue
	 * @param data data to be added
	 */
	public void enqueue(T data)
	{
		ListNode newNode = new ListNode(data, footer.prev, footer); //create new node
		footer.prev.next = newNode; //insert at end of list
		footer.prev = newNode;
	}
	
	/**
	 * Removes the data at the front of the queue
	 * @return data removed, null if list is empty
	 */
	public T dequeue()
	{
		if(isEmpty()) //if list is empty return null
		{
			return null;
		}
		
		T data = header.next.data; //pull data out
		header.next = header.next.next;//remove this node
		header.next.prev = header;
		return data;
	}
	
	/**
	 * Checks whether this queue is empty
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty()
	{
		return (header.next == footer);
	}
	
	/**
	 * Class to represent a node in the queue
	 * @author Taylor Hogge
	 */
	private class ListNode
	{
		T data; //data in this node
		ListNode next; //pointer to next node
		ListNode prev; //pointer to previous node
		
		/**
		 * Creates a new node using the given information
		 * @param data data to be held in this node
		 * @param prev previous node in the list
		 * @param next next node in the list
		 */
		public ListNode(T data, ListNode prev, ListNode next)
		{
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	}
}
