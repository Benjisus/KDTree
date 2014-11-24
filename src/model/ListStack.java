package model;

public class ListStack<T>
{
	private ListNode header; //header node used to gain access to list
	
	/**
	 * Creates a new stack.
	 */
	public ListStack()
	{
		header = new ListNode(null, null);
	}
	
	/**
	 * Pushes the given data onto the stack
	 * @param data data to be added to the top of the stack
	 */
	public void push(T data)
	{
		header.next = new ListNode(data, header.next);
	}
	
	/**
	 * Pops the data of the top of the stack
	 * @return the data that was popped, null if the stack is empty.
	 */
	public T pop()
	{
		if(isEmpty())
		{
			return null;
		}
		else
		{
			T data = header.next.data;
			header.next = header.next.next;
			return data;
		}
	}
	
	/**
	 * Lets the user look at the top of the stack without removing it
	 * @return data held at the top of the stack
	 */
	public T peek()
	{
		if(isEmpty())
		{
			return null;
		}
		else
		{
			return header.next.data;
		}
	}
	
	/**
	 * Whether this stack is empty or not.
	 * @return true if stack is empty, false otherwise.
	 */
	public boolean isEmpty()
	{
		return (header.next == null);
	}
	
	/**
	 * Class representing a single node in the list.
	 * @author Taylor Hogge
	 */
	private class ListNode
	{
		ListNode next; //pointer to next in the list
		T data; //data held in this node
		
		/**
		 * Creates a new ListNode with the given data and the next node
		 * @param data
		 * @param next
		 */
		public ListNode(T data, ListNode next)
		{
			this.data = data;
			this.next = next;
		}
	}
}
