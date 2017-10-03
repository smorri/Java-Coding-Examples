package com.examples.coding;

import java.util.Collection;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue<T> {
	
	/**
	 * Stack is a LIFO (last in first out) data structure.
	 * Queue is a FIFO (first in first out) data structure.
	 */
	
	private Stack<T> back,		// First In
		    		 front;		// First Out
	private AtomicInteger num_items;
	/** ================================================================================= **/
	/** ================================== CONSTRUCTORS ================================= **/
	/** ================================================================================= **/
	
	public Queue(){
		back = new Stack<T>();
		front = new Stack<T>();
		num_items = new AtomicInteger(0);
	}// end Queue()

	public Queue(T [] items){
		back = new Stack<T>();
		front = new Stack<T>();
		
		int i = 0,
			length = items.length;
	
		for(; i < length; i++){ back.push( items[i] ); }
		
		num_items = new AtomicInteger(length);
	}// end Queue(...)
	
	@SuppressWarnings("unchecked")
	public Queue(Collection<T> items){
		back = new Stack<T>();
		front = new Stack<T>();
		
		int i = 0,
			length = items.size();
		Object [] array =  items.toArray( new Object [0] );

		for(; i < length; i++){ back.push( (T) array[i] ); }
		num_items = new AtomicInteger(length);
	}// end Queue(...)
	
	/** ================================================================================= **/
	/** ============================== METHODS / FUNCTIONS ============================== **/
	/** ================================================================================= **/
	
	/** -------------------------------------------------------------------------------------
	 * size()
	 * 
	 * @return
	 */
	public int size(){
		return num_items.intValue();
	}// end size()
	
	/** ------------------------------------------------------------------------------------- 
	 * enqueue(item)
	 * 
	 * @param item
	 */
	public void enqueue(T item){
		if( !front.isEmpty() ){
			transferToBack();
		}
		
		back.push( item );
		num_items.incrementAndGet();
		System.out.println("[SUCCESS] dequeue() : Added " + item + "\n");
	}// end enqueue(...)
	
	/** -------------------------------------------------------------------------------------
	 * dequeue()
	 * 
	 */
	public void dequeue(){
		if( size() == 0 ){
			System.out.println("[FAIL] dequeue() : Queue is empty\n");
			return;
		}
		
		// ----------------------------------------------------------------------------------
		
		if( !back.isEmpty() ){ transferToFront(); }
		
		T item = front.pop();
		num_items.decrementAndGet();
		System.out.println("[SUCCESS] dequeue() : Removed " + item + "\n");
	}// end dequeue()
	
	/** -------------------------------------------------------------------------------------
	 * transferToBack()
	 * 
	 */
	public void transferToBack(){
		while( !front.isEmpty() ){
			back.push( front.pop() );
		}// end while(...)
	}// end transferToBack()
	
	/** ------------------------------------------------------------------------------------
	 * transferToFront()
	 * 
	 */
	public void transferToFront(){
		while( !back.isEmpty() ){
			front.push( back.pop() );
		}// end while(...)
	}// end transferToFront()
	
	/** ------------------------------------------------------------------------------------
	 * print()
	 * 
	 */
	public void print(){
		System.out.println( this );
	}// end print()

	/** ------------------------------------------------------------------------------------
	 * toString()
	 * 
	 * @return
	 */
	public String toString(){
		if( front.isEmpty() ){
			transferToFront();
		}
		
		if( size() == 0 ){
			return "[PRINT] Queue is empty";
		}
		
		int i = size() - 1;
		String string = "[PRINT]\n";
		T item;
		
		while( i >= 0 ){
			item = front.get( i );
			
			if( item != null ){
				string = string + item;
				
				if( i == 0 ){
					string = string + " <- Tail\n";
				} else if( i == size() - 1 ){
					string = string + " <- Head\n";
				} else {
					string = string + "\n";
				}// end if/else if/else	
			}// end if
		
			i--;
		}// end while(...)
		
		return string;
	}// end toString()
	
	public static void main(String[] args) {
		System.out.println(" ------------- QUEUE 1 ------------- \n");

		Queue<Object> q1 = new Queue<Object>();
		
		q1.dequeue();
		
		q1.enqueue(1);
		q1.enqueue(2);
		q1.enqueue(3);
		
		q1.print();
		
		q1.dequeue();

		q1.print();
		
		q1.enqueue('a');
		q1.enqueue('b');
		
		q1.print();
		
		q1.dequeue();

		q1.print();
		
		q1.enqueue('c');
		
		q1.print();
		
		q1.dequeue();
		q1.dequeue();
		q1.dequeue();
		q1.dequeue();
		
		q1.print();
				
		// -----------------------------------------------------------------------------------------
		System.out.println("\n ------------- QUEUE 2 ------------- \n");
		Queue<Object> q2 = new Queue<Object>( new Object[]{"One", "Two", "Three", "Four"} );
		
		q2.print();
		
		q2.dequeue();
		
		q2.print();
		
		q2.enqueue("Five");
		
		q2.print();
		
		q2.dequeue();
		q2.dequeue();
		
		q2.print();
	}

}
