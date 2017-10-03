package com.examples.coding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkedList<T> {

	@SuppressWarnings("hiding")
	private class Node<T> {
		
		private T data;
		private Node<T> next;
		
		public Node(T _data){
			this.data = _data;
			this.next = null;
		}
		
		public Node(T _data, Node<T> _next){
			this.data = _data;
			this.next = _next;			
		}		
	}// end class Node<T>
	
	// ---------------------------------------------------------------
	
	private final static String NULL_NODE = "[ \\0 ]";
	
	private Node<T> head;
	private AtomicInteger total_nodes;
	
	// ---------------------------------------------------------------
	
	public LinkedList(){
		this.head = null;
		this.total_nodes = new AtomicInteger(0);
	}
	
	public LinkedList(T [] items){
		System.out.println( Arrays.toString( items ) );
		
		int i = 0,
			length = items.length;
		
		this.head = null;
		this.total_nodes = new AtomicInteger(0);
		
		for(;i < length; i++){ add( items[i] ); }
		
	}// end LinkedList(...)
		
	public LinkedList(Collection<T> items){
		@SuppressWarnings("unchecked")
		T [] array = (T[]) items.toArray();
		
		System.out.println( Arrays.toString( array ) );
		
		int i = 0,
				length = array.length;
			
			this.head = null;
			this.total_nodes = new AtomicInteger(0);
			
			for(;i < length; i++){ add( array[i] ); }
	}
	
	// ---------------------------------------------------------------	
	
	private boolean isEmpty(){
		return size() == 0;
	}
	
	public int size(){
		return this.total_nodes.intValue();
	}
		
	public boolean isValidList(){ 
		int i = 0;
		Node <T> temp = this.head;
		
		if( this.size() < 0 ){ return false; } 
		
		while( i < size() ){
			if( temp.data == null ){
				// Node data cannot be false
				System.out.println("[FAIL] isValidList() : LinkedList Nodes cannot be `null`\n");
				return false;
			}
			
			temp = temp.next;
			i++;
		}
		
		System.out.println("[SUCCESS] isValidList() : LinkedList is valid\n");
		return true;
	}	
	
	public void print(){
		System.out.println("LinkedList : " + this + "\n");
	}
	
	public Object [] toArray(){
		if( isEmpty() ){
			return new Object[0];
		}
		
		int i = 0;
		Object [] array = new Object[ size() ];
		
		while( i < size() ){
			array[i] = get(i);
			i++;
		}
		
		return array;
	}
	
	public String toString(){
		String string = "";
		
		if( isEmpty() ){
			return NULL_NODE;
		}
		
		Node<T> temp = this.head;
		string = "[ " + temp.data + " ] -> ";
		
		
		while( temp.next != null ){
			temp = temp.next;
			
			string = string + "[ " + temp.data + " ] -> ";
		}
		
		return string + NULL_NODE;
	}
	
	// ---------------------------------------------------------------
	
	public void add(T item){
		if( isEmpty() ){
			this.head = new Node<T>(item, null);
		} else {
			Node<T> temp = this.head;
			
			while(temp.next != null){ temp = temp.next; }
			
			temp.next = new Node<T>(item);
		}// end if(...){...} else {...}
		
		this.total_nodes.incrementAndGet();
		
		System.out.println("[SUCCESS] add() : Added Node [ " + item + " ]");
		print();
	}
	
	public void add(LinkedList<T> list){
		if( isEmpty() ){
			this.head = list.head;
			this.total_nodes = list.total_nodes;
		} else {
			Node <T> temp = getLastNode();
			
			if( temp != null ){
				temp.next = list.head;
				this.total_nodes.addAndGet( list.size() );
			} else {
				System.out.println("[FAIL] add() : Cannot add LinkedList " + list + " to current list\n");
				return;
			}
		}
		
		System.out.println("[SUCCESS] add() : Added LinkedList " + list + " to current list \n");
		print();
	}
	
	public void add(T item, int index){
		if( index < 0 ){
			System.out.println("[FAIL] add() : Index(" + index + ") is invalid\n");
			return;			
		}		
		
		if( index >= size() ){
			System.out.println("[FAIL] add() : Index (" + index + ") out of bounds\n");
			return;						
		}

		// ---------------------------------------------------------------

		System.out.println("-------------------------------- add() ------------------------------");
		
		int count = 0;
		Node <T> temp = this.head,
				 prev = null;
		
		while(count < size()){
			if( count == index ){
				System.out.println("[SUCCESS] add() : Added Node [ " + item + 
								   " ] at Index (" + index + ")");

				prev.next = new Node<T>(item); 
				(prev.next).next = temp;
				this.total_nodes.incrementAndGet();
				
				break;
			} else {
				prev = temp;
				temp = temp.next;
				count++;
			}// end if(...){...} else {...} 
		}// end while(...)
		
		print();
	}
	
	public void add(LinkedList<T> list, int index){
		if( index < 0 ){
			System.out.println("[FAIL] add() : Index(" + index + ") is invalid\n");
			return;			
		}		
		
		if( index >= size() ){
			System.out.println("[FAIL] add() : Index (" + index + ") out of bounds\n");
			return;						
		}
		
		// ---------------------------------------------------------------
		
		System.out.println("-------------------------------- add() ------------------------------");
		
		int count = 0;
		Node <T> temp = this.head,
				 prev = null;
		
		while( count < size() ){
			if( count == index ){
				System.out.println("[SUCCESS] add() : Added Node [ " + list + 
								   " ] at Index (" + index + ")");

				prev.next = list.head; 
				(list.getLastNode()).next = temp;
				this.total_nodes.addAndGet( list.size() );

				break;
			} else {
				prev = temp;
				temp = temp.next;
				count++;
			}// end if(...){...} else {...} 
		}// end while(...)
		
		print();
	}
	
	private void addNode(Node<T> node){
		if( isEmpty() ){
			this.head = node;
			this.head.next = null;
		} else {
			Node<T> temp = this.getLastNode();
			
			if( temp.next.data != node.data ){
				System.out.println("[FAIL] addNode() : Pointer mismatch ; Next [ " + temp.next.data + " ] " + 
								   "does not match current [ " + node.data + " ]");
				return;
			}
			
			temp.next = node;
		}// end if(...){...} else {...}
		
		this.total_nodes.incrementAndGet();
		
		System.out.println("[SUCCESS] addNode() : Added Node [ " + node.data + " ]");
		print();		
	}
	
	public void addLast(T item){
		add(item);
	}
	
	public void addLast(LinkedList<T> list){
		add(list);
	}
	
	public void addFirst(T item){
		if( isEmpty() ){
			add(item);
		} else {
			System.out.println("-------------------------------- addFirst() ------------------------------");
			
			Node <T> temp = this.head;
			this.head = new Node<T>(item);
			this.head.next = temp;
			
			System.out.println("[SUCCESS] addFirst() : Added Node [ " + item + " ]");
			this.total_nodes.incrementAndGet();
		}
		
		print();
	}

	public void addFirst(LinkedList<T> list){
		if( isEmpty() ){
			add(list);
		} else {
			System.out.println("-------------------------------- addFirst() ------------------------------");

			Node <T> temp = getFirstNode(),
					 end = list.getLastNode();

			this.head = list.head;
			end.next = temp;
			
			System.out.println("[SUCCESS] addFirst() : Added LinkedList " + list + " to current list\n");
			this.total_nodes.addAndGet( list.size() );
		}
		
		print();
	}
	
	public LinkedList<T> copy(){
		LinkedList <T> list = new LinkedList <T>();
		
		System.out.println("-------------------------------- copy() ------------------------------");
		
		int i = 0;
		
		while( i < size() ){
			list.add( get(i) );
			
			i++;
		}
		
		return list;
	}
	
	/** -----------------------------------------------------------------------------------------------
	 * copy(start_index, end_index)
	 * @param start_index
	 * @param end_index
	 * @return
	 */
	public LinkedList<T> copy(int start_index, int end_index){
		if( start_index < 0 ){
			System.out.println("[FAIL] copy() : Start Index(" + start_index + ") is invalid\n");
			return null;			
		}
		
		if( isEmpty() ){
			System.out.println("[FAIL] copy() : LinkedList contains no nodes\n");
			return null;			
		}
		
		if( end_index >= size() ){
			System.out.println("[FAIL] copy() : End Index (" + end_index + ") out of bounds\n");
			return null;					
		}
		
		if( end_index < start_index ){
			System.out.println("[FAIL] copy() : End Index (" + end_index + ") " + 
					   		   "comes before Start Index (" + start_index + ")\n");
			return null;
		}
		
		if( start_index >= size() - 1 ){
			System.out.println("[FAIL] copy() : Start Index (" + start_index + ") out of bounds\n");
			return null;
		}		
		
		// --------------------------------------------------------------------------------------------
			
		if( (start_index == 0) && (end_index == size() - 1) ){
			return copy();
		}
		
		LinkedList <T> list = new LinkedList<T>();
		int i = start_index;
		
		while( i <= end_index ){
			list.add( get(i) );
			i++;
		}// end while(...)
		
		System.out.println("[SUCCESS] copy() : Nodes copied from Start Index (" + start_index + ") " + 
						   "to End Index (" + end_index + ") of current list\n");
		list.print();
		return list;
	}// end copy(...)
	
	/** --------------------------------------------------------------------------------------------
	 * delete() 
	 * 
	 */
	public void delete(){
		deleteLast();
	}// end delete()
	
	/** -----------------------------------------------------------------------------------------------
	 * delete(index)
	 * 
	 * @param index
	 */
	public void delete(int index){
		if( index < 0 ){
			System.out.println("[FAIL] delete() : Index(" + index + ") is invalid\n");
			return;			
		}
		
		if( isEmpty() ){
			System.out.println("[FAIL] delete() : LinkedList contains no nodes\n");
			return;			
		}
		
		if( index >= size() ){
			System.out.println("[FAIL] delete() : Index (" + index + ") out of bounds\n");
			return;						
		}
		
		if( index == 0 ){
			deleteFirst();
			return;
		}
		
		if( index == size() - 1 ){
			deleteLast();
			return;
		}
		
		// ---------------------------------------------------------------
		
		int count = 0;
		Node <T> temp = this.head,
				 prev = null;
		 
		while(count < size()){
			if( count == index ){
				System.out.println("[SUCCESS] delete() : Deleted Node [ " + temp.data + 
								   " ] from index (" + index + ")");

				prev.next = temp.next;
				this.total_nodes.decrementAndGet();
				
				break;
			} else {
				prev = temp;
				temp = temp.next;
				count++;
			}// end if(...){...} else {...} 
		}// end while(...)
		
		print();
	}// end delete(...)
	
	/**	-----------------------------------------------------------------------------------------------
	 * delete(start_index, end_index)
	 * 
	 * @param start_index
	 * @param end_index
	 */
	public void delete(int start_index, int end_index){
		if( start_index < 0 ){
			System.out.println("[FAIL] delete() : Start Index(" + start_index + ") is invalid\n");
			return;			
		}
		
		if( isEmpty() ){
			System.out.println("[FAIL] delete() : LinkedList contains no nodes\n");
			return;			
		}
		
		if( end_index >= size() ){
			System.out.println("[FAIL] delete() : End Index (" + end_index + ") out of bounds\n");
			return;						
		}
		
		if( end_index < start_index ){
			System.out.println("[FAIL] delete() : End Index (" + end_index + ") " + 
					   		   "comes before Start Index (" + start_index + ")\n");
			return;
		}
		
		if( start_index >= size() - 1 ){
			System.out.println("[FAIL] delete() : Start Index (" + start_index + ") out of bounds\n");
			return;
		}		
		
		// --------------------------------------------------------------------------------------------
				
		Node <T> start_node, end_node;
		int start = start_index - 1,
			end = end_index + 1;
		
		start_node = ( start > 0 ) ? getNode(start) : getNode(start_index);
		end_node = ( end < size() ) ? getNode(end) : getNode(end_index);
		
		if( end_node.next == null ){
			start_node.next = null;
		} else {
			start_node.next = end_node;
		}// end if/else
		
		System.out.println("[SUCCESS] delete() : Removed Nodes");
		
		this.total_nodes.addAndGet( -(end_index - start_index + 1));
		print();
	    return;
	}// end delete(...)
	
	/**	-----------------------------------------------------------------------------------------------
	 * deleteFirst()
	 * 
	 */
	public void deleteFirst(){
		if( isEmpty() ){
			System.out.println("[FAIL] deleteFirst() : LinkedList contains no nodes\n");
			return;
		}
		
		// --------------------------------------------------------------------------------------------

		Node <T> temp = this.head;
		
		this.head = temp.next;
		this.total_nodes.decrementAndGet();
		
		System.out.println("[SUCCESS] deleteFirst() : Removed Node [ " + temp.data + " ]");
		print();
	}// end deleteFirst()
		
	public void deleteLast(){
		if( isEmpty() ){
			System.out.println("[FAIL] deleteLast() : LinkedList contains no nodes\n");
			return;
		}
		
		Node <T> temp = this.head;
		
		while( (temp.next).next != null ){ temp = temp.next; }
		
		System.out.println("[SUCCESS] deleteLast() : Removed Node [ " + temp.next.data + " ]");
		this.total_nodes.decrementAndGet();
		
		temp.next = null;
		print();
	}// end deleteLast()
	
	public void deleteAll(T item){
		Integer [] index = findAll(item);
		int i = size() - 1,
			total_deleted = 0,
			length = index.length;

		if( isEmpty() ){
			System.out.println("[FAIL] deleteAll() : LinkedList contains no nodes\n");
			return;		
		}
		
		if( length == 0){
			System.out.println("[FAIL] deleteAll() : Node [ " + item + " ] not found in LinkedList\n");
			return;		
		}
		
		// ---------------------------------------------------------------

		System.out.println("------------------------------ deleteAll() -----------------------------");		
	
		if( length == 1 ){
			delete( index[0] );
			System.out.println("[FAIL] deleteAll() : Node [ " + item + " ] deleted from Index (" + index[0] + ")\n");
			return;					
		}		
		
		String list = "";
				
		for(;i >= 0; i--){
			if( i <= length - 1 ){
				if( index[i] == 0 ){
					deleteFirst();
				} else if( index[i] == size() - 1 ){
					deleteLast();
				} else {
					delete( index[i] );
				}

				list = "(" + index[i] + "), " + list;
			}
		}

		if( total_deleted > 0 ){ total_nodes.addAndGet( -total_deleted ); }			
		list = list.substring(0, list.lastIndexOf(',') );
		System.out.println("[SUCCESS] deleteAll() : Node [ " + item + " ] deleted from Index " + list + "\n");
		print();
	}// end deleteAll(...)
	
	public T get(int index){
		if( index < 0 ){
			System.out.println("[FAIL] get() : Index(" + index + ") is invalid\n");
			return null;			
		}
		
		if( isEmpty() ){
			System.out.println("[FAIL] get() : LinkedList contains no nodes\n");
			return null;
		}
		
		if( index >= size() ){
			System.out.println("[FAIL] get() : Index (" + index + ") out of bounds\n");
			return null;				
		}
		
		if( index == 0 ){
			return getFirst();
		}
		
		if( index == size() - 1 ){
			return getLast();
		}
		
		// ---------------------------------------------------------------
		
		int count = 0;
		Node <T> temp = this.head;
		
		while( count < size() ){
			if( count == index ){
				System.out.println("[SUCCESS] get() : Node [ " + temp.data + " ] at Index (" + index + ")\n");
				return temp.data;
			} else {
				temp = temp.next;
				count++;
			}// end if(...){...} else {...}
		}// end while(...)
	
		System.out.println("[FAIL] get() : Critical Error! \n");
		return null;
	}// end get(...)
	
	public LinkedList<T> get(int start_index, int end_index){
		return copy(start_index, end_index);
	}
	
	public T getFirst(){
		if( isEmpty() ){
			System.out.println("[FAIL] getFirst() : LinkedList contains no nodes\n");
			return null;
		}
		
		System.out.println("[SUCCESS] getFirst() : Node [ " + this.head.data + " ]\n");
		return this.head.data;
	}// end getFirst()
	
	/** -----------------------------------------------------------------------------------------------
	 * getLast()
	 * 
	 * @return
	 */
	public T getLast(){
		if( isEmpty() ){
			System.out.println("[FAIL] getLast() : LinkedList contains no nodes\n");
			return null;			
		}
		
		// --------------------------------------------------------------------------------------------
		
		Node <T> temp = this.head;
		
		while( temp.next != null ){ temp = temp.next; }
		
		System.out.println("[SUCCESS] getLast() : Node [ " + temp.data + " ]\n");
		return temp.data;
	}// end getLast()
	
	/** -----------------------------------------------------------------------------------------------
	 * getNode(index)
	 * 
	 * @param index
	 * @return
	 */
	private Node<T> getNode(int index){
		if( isEmpty() ){
			System.out.println("[FAIL] getNode() : LinkedList contains no nodes\n");
			return null;			
		}
		
		if( index >= size() ){
			System.out.println("[FAIL] getNode() : Index (" + index + ") out of bounds\n");
			return null;				
		}
		
		// --------------------------------------------------------------------------------------------
		
		Node <T> temp = this.head;
		int count = 0;
		
		while( count < size() ){
			if( count == index ){
				System.out.println("[SUCCESS] getNode() : Retrieved Node [ " + temp.data +" ] " + 
								   "at Index (" + index + ")\n");
				return temp;
			}
			
			temp = temp.next;
			count++;
		}
		
		return temp;	
	}// end getNode(...)
 	
	/** -----------------------------------------------------------------------------------------------
	 * getFirstNode()
	 * 
	 * @return
	 */
	private Node<T> getFirstNode(){
		if( isEmpty() ){
			System.out.println("[SUCCESS] getFirstNode() : LinkedList contains no nodes\n");
			return null;			
		}
		
		// --------------------------------------------------------------------------------------------
		
		return getNode( 0 );
	}// end getFirstNode()
	
	/** -----------------------------------------------------------------------------------------------
	 * getLastNode()
	 * 
	 * @return
	 */
	private Node<T> getLastNode(){
		if( isEmpty() ){
			System.out.println("[SUCCESS] getLastNode() : LinkedList contains no nodes\n");
			return null;			
		}
		
		// --------------------------------------------------------------------------------------------
		return getNode( size() - 1 );
	}// end getLastNode()
	
	/** -----------------------------------------------------------------------------------------------
	 * insert(item, index)
	 * 
	 * @param item
	 * @param index
	 */
	// Similar to add() version but if index > size just add node to the end. add() will throw
	// an exception
	public void insert(T item, int index){
		if( index < 0 ){
			System.out.println("[FAIL] insert() : Index (" + index + ") is invalid\n");
			return;			
		}		
		
		if( index >= size() ){
			System.out.println("[SUCCESS] insert() : Node [ " + item + " ] placed at " + 
							   "end of LinkedList\n");
			add(item);
			return;						
		}
		
		// -------------------------------------------------------------------------------------------
		
		add(item, index);
	}// end insert(...)
	
	// Similar to add() version but if index > size just add node to the end. add() will throw
	// an exception
	public void insert(LinkedList<T> list, int index){
		if( index < 0 ){
			System.out.println("[FAIL] insert() : Index (" + index + ") is invalid\n");
			return;			
		}		
		
		if( index >= size() ){
			System.out.println("[SUCCESS] insert() : Inserted list " + list + " at end " +
							   "of current list \n");
			add(list);
			return;						
		}		
		
		add(list, index);
	}// end insert(...)
	
	/**	--------------------------------------------------------------------------------------------
	 * find(item)
	 * 
	 * @param item
	 * @return
	 */
	public int find(T item){
		if( isEmpty() ){
			System.out.println("[FAIL] find() : LinkedList contains no nodes\n");
			return -1;
		}
		
		// --------------------------------------------------------------------------------------------
		
		int count = 0;
		boolean found = false;
		Node <T> temp = this.head;
		
		while( count < size() ){
			if( temp.data == item ){
				found = true;
				break;
			} else {
				temp = temp.next;
				count++;
			}// end if/else
		}// end while(...)
		
		if(found){
			System.out.println("[SUCCESS] find() : Found Node [ " + item + " ] " + 
					   "at Index (" + count + ")\n");
			return count;
		}
		
		System.out.println("[FAIL] find() :  Cannot find Node [ " + item + " ]\n");		
		return -1;
	}
	
	/**	-----------------------------------------------------------------------------------------------
	 * find(item, from_index)
	 * 
	 * @param item
	 * @param from_index
	 * @return
	 */
	public int find(T item, int from_index){
		if( from_index < 0 ){
			System.out.println("[FAIL] find() : Index(" + from_index + ") is invalid\n");
			return -1;			
		}
		
		if( isEmpty() ){
			System.out.println("[FAIL] find() : LinkedList contains no nodes\n");
			return -1;
		}
		
		if( from_index >= size() ){
			System.out.println("[FAIL] find() : Index (" + from_index + ") out of bounds\n");
			return -1;			
		}
		
		// --------------------------------------------------------------------------------------------
		
		int count = from_index;
		boolean found = false;
		
		Node <T> temp = getNode(from_index);
		
		/* ----- REMOVED : Implemented getNode() ----- */
		// Node <T> temp = this.head;
		
		/* while( count < from_index ){
			temp = temp.next;
			count++;
		}// end while(...)*/
		
		/*while( count < size() ){
			if( temp.data == item ){
				found = true;
				break;
			} else {
				temp = temp.next;
				count++;
			}// end if/else
		}// end while(...)*/
		
		while( temp.next != null ){
			if( temp.data == item ){
				found = true;
				break;
			} else {
				temp = temp.next;
				count++;
			}
		}
		
		if( found ){
			System.out.println("[SUCCESS] find() : Found Node [ " + item + " ] " + 
			   		   		   "at Index (" + count + ") starting at Index (" + from_index +")\n");
			return count;			
		}
		
		System.out.println("[FAIL] find() : Cannot find Node [ " + item + " ] " +
						   "from Index (" + from_index + ")\n");					
		return -1;	
	}// end find(...)
	
	/**	-----------------------------------------------------------------------------------------------
	 * find(item, start_index, end_index)
	 * 
	 * @param item
	 * @param start_index
	 * @param end_index
	 * @return
	 */
	public int find(T item, int start_index, int end_index){
		if( isEmpty() ){
			System.out.println("[FAIL] find() : LinkedList contains no nodes\n");
			return -1;
		}
		
		if( start_index < 0 ){
			System.out.println("[FAIL] find() : Start Index(" + start_index + ") is invalid\n");
			return -1;			
		}
		
		if( end_index >= size() ){
			System.out.println("[FAIL] find() : End Index (" + end_index + ") out of bounds\n");
			return -1;			
		}
		
		if( end_index < start_index ){
			System.out.println("[FAIL] find() : End Index (" + end_index + ") " + 
							   "comes before Start Index (" + start_index + ")\n");
			return -1;			
		}	
		
		if( start_index >= size() ){
			System.out.println("[FAIL] find() : Start Index (" + start_index + ") out of bounds\n");

			return -1;			
		}			
	
		// --------------------------------------------------------------------------------------------
				
		int index = find(item, start_index);
		
		if( index == -1 || index > end_index ){
			System.out.println("[FAIL] find() : Node [ " + item + " ] not found between Index range" + 
							   "(" + start_index + ", " + end_index + ")\n");
			return -1;
		} else {			
			System.out.println("[SUCCESS] find() : Node [ " + item + " ] appears at Index (" + index  + 
							   ") between Index range (" + start_index + ", " + end_index + ")\n");			
		}// end if/else
		
		return index;
	}// end find(...)
	
	/** -----------------------------------------------------------------------------------------------
	 * findFirst(item)
	 * 
	 * @param item
	 * @return
	 */
	public int findFirst(T item){
		return find(item);
	}// end findFirst(...)
	
	/**	-----------------------------------------------------------------------------------------------
	 * findLast(item)
	 * @param item
	 * @return
	 */
	public int findLast(T item){
		if( isEmpty() ){
			System.out.println("[FAIL] findLast() : LinkedList contains no nodes\n");
			return -1;
		}
		
		// --------------------------------------------------------------------------------------------
		
		int count = 0,
			last_index = find(item, 0),
			temp_index = -1;
		
		if( last_index == -1 ){
			System.out.println("[FAIL] findLast() : Cannot find Node [ " + item + " ] \n");			
			return -1;
		} else {
			last_index = last_index + 1;
		}// end if/else
		
		boolean found = false;
		
		while( count < size() ){
			temp_index = find(item, last_index);
						
			if(temp_index == -1){
				System.out.println("[SUCCESS] findLast() : Found Node [ " + item + " ] " + 
		   		   		   "at Index (" + (last_index - 1) + ")\n");
				found = true;
				break;
			} else {
				found = true;
				last_index = temp_index + 1;
				count++;
			}// end if/else
		}// end while(...)
		
		if( found ){
			return last_index -1;
		}
		
		System.out.println("[FAIL] findLast() : Cannot find Node [ " + item + " ] \n");					
		return -1;
	}// end findLast(...)
	
	/** -----------------------------------------------------------------------------------------------
	 * findBefore(item, index)
	 * 
	 * @param item
	 * @param index
	 * @return
	 */
	public int findBefore(T item, int index){
		if( index < 0 ){
			System.out.println("[FAIL] findBefore() : Index(" + index + ") is invalid\n");
			return -1;			
		}
		
		if( isEmpty() ){
			System.out.println("[FAIL] findBefore() : LinkedList contains no nodes\n");
			return -1;
		}
		
		if( index >= size() ){
			System.out.println("[FAIL] findBefore() : Index (" + index + ") out of bounds\n");
			return -1;			
		}	
		
		// --------------------------------------------------------------------------------------------
				
		int count = 0;
		Node <T> temp = this.head;		
		
		while( count <= index ){
			if( temp.data == item ){
				System.out.println("[SUCCESS] findBefore() : Found Node [ " + item + " ] " + 
								   "at Index (" + count + ") before Index (" + index + ")\n");
				return count;
			} else {
				temp = temp.next;
				count++;
			}// end if/else
		}// end while(...)
		
		System.out.println("[FAIL] findBefore() : Cannot find Node [ " + item + " ] " + 
						   "before Index (" + index + ")\n");
		return -1;				
	}// end findBefore(...)
	
	/** -----------------------------------------------------------------------------------------------
	 * findAll(item, index)
	 * 
	 * @param item
	 * @param index
	 * @return
	 */
	public int findAfter(T item, int index){
		return find(item, index);
	}// end findAfter(...)
	
	/** -----------------------------------------------------------------------------------------------
	 * findAll(item)
	 * 
	 * @param item
	 * @return
	 */
	public Integer[] findAll(T item){
		ArrayList <Integer> array = new ArrayList<Integer>();
		
		if( isEmpty() ){
			System.out.println("[FAIL] findAll() : LinkedList contains no nodes\n");
			return null;
		}	
		
		// --------------------------------------------------------------------------------------------
				
		String list = "";
		int index = find(item);
		
		if( index == -1){
			System.out.println("[FAIL] findAll() : Node [ " + item + " ] not found in LinkedList\n");
		} else {
			array.add( index );
			list = list + "(" + index + "), ";
			
			while( index != -1 ){
				index = find(item, index + 1);
				
				if(index == -1){ break; }
				
				array.add( index );
				list = list + "(" + index + "), ";
			}
			
			list = list.substring(0, list.lastIndexOf(',') );
			System.out.println("[SUCCESS] findAll() : Node [ " + item + " ] appears at Index " +
							   list  + " \n");			
		}// end if/else
		
		return array.toArray( new Integer[0] );		
	}// end findAll(...)
	
	/** -----------------------------------------------------------------------------------------------
	 * findAll(item, start_index, end_index)
	 * 
	 * @param item
	 * @param start_index
	 * @param end_index
	 * @return
	 */
	public Integer[] findAll(T item, int start_index, int end_index){
		ArrayList <Integer> array = new ArrayList<Integer>();
		
		if( isEmpty() ){
			System.out.println("[FAIL] findAll() : LinkedList contains no nodes\n");
			return null;
		}
		
		if( start_index < 0 ){
			System.out.println("[FAIL] findAll() : Start Index(" + start_index + ") is invalid\n");
			return null;			
		}
		
		if( end_index >= size() ){
			System.out.println("[FAIL] findAll() : End Index (" + end_index + ") out of bounds\n");
			return null;			
		}
		
		if( end_index < start_index ){
			System.out.println("[FAIL] findAll() : End Index (" + end_index + ") " + 
							   "comes before Start Index (" + start_index + ")\n");
			return null;			
		}	
		
		if( start_index >= size() ){
			System.out.println("[FAIL] findAll() : Start Index (" + start_index + ") out of bounds\n");

			return null;			
		}			
	
		// --------------------------------------------------------------------------------------------
			
		String list = "";
		int index = find(item, start_index);
		
		if( index == -1 ){
			System.out.println("[FAIL] findAll() : Node [ " + item + " ] not found between Index" + 
							   " range (" + start_index + ", " + end_index + ")\n");
		} else {
			array.add( index );
			list = list + "(" + index + "), ";
			
			while( index != -1 ){
				index = find(item, index + 1);
				
				if(index == -1 || index > end_index){ break; }
				
				array.add( index );
				list = list + "(" + index + "), ";
			}
			
			list = list.substring(0, list.lastIndexOf(',') );
			System.out.println("[SUCCESS] findAll() : Node [ " + item + " ] appears at Index " + list + 
							   " between Index range (" + start_index + ", " + end_index + ")\n");			
		}// end if/else
		
		return array.toArray( new Integer[0] );		
	}// end findAll(...)

	/** -----------------------------------------------------------------------------------------------
	 * reverseNode(node)
	 * 
	 * @param node
	 * @return
	 */
	private Node<T> reverseNode(Node <T> node){
		if( node == null ){
			return null;
		}
		
		if( node.next == null ){
			return node;
		}
		
		// --------------------------------------------------------------------------------------------
		
		Node <T> next_node = node.next;		// Retrieve next node and store
		node.next = null;					// Unlink next node to prevent cyclic behavior
				
		// Recursively reverse list from the next node and onward
		Node <T> temp = reverseNode( next_node );	

		// Join next node 'sublist' with current list 
		next_node.next = node;
		
		return temp;
	}// end reverseNode(...)
	
	/** -----------------------------------------------------------------------------------------------
	 * reverse()
	 * 
	 */
	public void reverse(){
		if( isEmpty() ){
			System.out.println("[FAIL] reverse() : LinkedList contains no nodes\n");
			return;
		}
		
		if( size() == 1 ){
			System.out.println("[SUCCESS] reverse() : LinkedList contains only 1 Node\n");
			return;
		}
		
		if( !this.isValidList() ){
			return;
		}
		
		// --------------------------------------------------------------------------------------------
		
		LinkedList <T> list = new LinkedList<T>();
		int i = size() - 1;
		
		while( i >= 0 ){			
			list.add( get(i) );
			i--;
		}// end while(...)
		
		this.head = list.head;
		
		System.out.println("[SUCCESS] reverse() : LinkedList is reversed\n");
		print();
	}// end reverse()

	/** -----------------------------------------------------------------------------------------------
	 * recursiveReverse()
	 * 
	 */
	public void recursiveReverse(){
		if( isEmpty() ){
			System.out.println("[FAIL] recursiveReverse() : LinkedList contains no nodes\n");
			return;
		}		
		
		if( size() == 1 ){
			System.out.println("[SUCCESS] recursiveReverse() : LinkedList contains only 1 Node\n");
			return;
		}
		
		if( !this.isValidList() ){
			return;
		}
		
		// --------------------------------------------------------------------------------------------

		this.head = reverseNode( this.head );
		
		System.out.println("[SUCCESS] recursiveReverse() : LinkedList is reversed\n");
		print();
	}// end recursiveReverse()
	
	/** -----------------------------------------------------------------------------------------------
	 * flip()
	 * 
	 * @return
	 */
	// Similiar to reverse() but returns a reversed LinkedList. Does not modify the current
	// LinkedList.
	public LinkedList<T> flip(){
		if( isEmpty() ){
			System.out.println("[FAIL] reverse() : LinkedList contains no nodes\n");
			return null;
		}
		
		if( size() == 1 ){
			System.out.println("[SUCCESS] reverse() : LinkedList contains only 1 Node\n");
			return null;
		}
		
		if( !this.isValidList() ){
			return null;
		}
		
		// --------------------------------------------------------------------------------------------
		
		LinkedList <T> list = new LinkedList<T>();
		int i = size() - 1;
		
		while( i >= 0 ){			
			list.add( get(i) );
			i--;
		}//end while(...)
				
		System.out.println("[SUCCESS] reverse() : LinkedList is reversed\n");
		list.print();
		
		return list;
	}// end flip()
	
	/** -----------------------------------------------------------------------------------------------
	 * recursiveFlip()
	 * 
	 * @return
	 */
	// Similiar to reverse() but returns a reversed LinkedList. Does not modify the current
	// LinkedList.
	public LinkedList<T> recursiveFlip(){
		if( isEmpty() ){
			System.out.println("[FAIL] recursiveFlip() : LinkedList contains no nodes\n");
			return null;
		}		
		
		if( size() == 1 ){
			System.out.println("[SUCCESS] recursiveFlip() : LinkedList contains only 1 Node\n");
			return null;
		}
		
		if( !this.isValidList() ){
			return null;
		}
		
		// --------------------------------------------------------------------------------------------

		LinkedList <T> list = this.copy();
		
		list.head = reverseNode( list.head );
		
		System.out.println("[SUCCESS] recursiveFlip() : LinkedList is reversed\n");
		list.print();
		
		return list;
	}// end recursiveFlip()

	/** -----------------------------------------------------------------------------------------------
	 * shuffle()
	 * 
	 */
	public void shuffle(){
		if( isEmpty() ){
			System.out.println("[FAIL] shuffle() : LinkedList contains no nodes\n");
			return;
		}
		
		if( size() == 1 ){
			System.out.println("[FAIL] shuffle() : LinkedList contains 1 Node\n");
			return;
		}
		
		// --------------------------------------------------------------------------------------------

		int i = size() - 1,
			index;
		Node <T> temp, current;
		Random random = new Random();
		
		while(i > 0){
			index = random.nextInt( i + 1 );
			
			temp = new Node<T>( getNode( index ).data );
			current = new Node<T>( getNode( i ).data );
			
			( getNode(index) ).data = current.data;
			( getNode( i ).data ) = temp.data;
		
			i--;
		}// end while(...)
		
		System.out.println("[SUCCESS] shuffle() : LinkedList is shuffled\n");
		print();
	}// end shuffle()
	
	/** -----------------------------------------------------------------------------------------------
	 * isPalindrome()
	 * 
	 * @return
	 */
	public boolean isPalindrome(){
		if( isEmpty() ){
			System.out.println("[FAIL] isPalindrome() : LinkedList contains no nodes\n");
			return false;
		}
		
		if( size() == 1 ){
			System.out.println("[SUCCESS] isPalindrome() : LinkedList is a palindrome\n");
			return true;
		}	
		
		// -------------------------------------------------------------------------------------------
		
		int i = 0,
			j = size() - 1;
		boolean match = true;
		
		while( i <= j ){
			if( i == j ){
				break;
			}
					
			if( get(i) != get(j) ){
				match = false;
				break;
			}
			
			i++;
			j--;
		}// end while(...)
		
		if(match){
			System.out.println("[SUCCESS] isPalindrome() : LinkedList is a palindrome\n");
		} else {
			System.out.println("[FAIL] isPalindrome() : LinkedList is not a palindrome\n");
		}// end if/else
		
		return match;
	}// end isPalindrome()

	// TODO : COMPLETE SOON	;)
	public void replace(T old_item, T new_item){  }
	
	// TODO : COMPLETE SOON	;)
	public void replaceAll(T old_item, T new_item){  }
	
	// TODO : COMPLETE SOON	;)
	public void replace(T new_item, int index){ }
	
	// TODO : COMPLETE SOON	;)
	public void replace(T [] new_items, int from_index){  }
	
	// TODO : COMPLETE SOON	;)
	public void replace(LinkedList <T> list, int from_index){  }
	
	// TODO : COMPLETE SOON	;)
	
	// TODO : NOT IMPLEMENTED YET ;)
	public boolean isCircularList(){
		if( isEmpty() ){
			System.out.println("[FAIL] isCircularList() : LinkedList contains no nodes\n");
			return false;
		}
		
		if( size() == 1 ){
			System.out.println("[FAIL] isCircularList() : LinkedList contains only 1 node\n");
			return true;
		}
		
		// ----------------------------
		
		if( this.getFirstNode() == this.getLastNode() ){
			System.out.println("[FAIL] isCircularList() : LinkedList is a circular list\n");
			return true;
		}
		
		return false;
	}
	
	// TODO : NOT IMPLEMENTED YET ;)
	@SuppressWarnings("rawtypes")
	public boolean containsCycle(){
		ArrayList<Node> next_list = new ArrayList<Node>( size() );
		Node <T> node;
		int i = 0;
		
		for(; i < size(); i++){
			node = getNode(i).next;
			
			if( next_list.contains( node ) ){
				return true;
			} else {
				next_list.add( node );
			}// end if/else		
		}// end for(...)
		
		return false;
	}
	
	/** =========================================================================================== **/
	/** =========================================== MAIN ========================================== **/
	/** =========================================================================================== **/
	
	public static void main(String[] args) {
		LinkedList<Object> l1 = new LinkedList<Object>();
		LinkedList<Object> l2 = new LinkedList<Object>( new Object[]{1, 2, 3, 'a', 'b', 'c'} );
		@SuppressWarnings("serial")
		LinkedList<Object> l3 = new LinkedList<Object>( new ArrayList<Object>(){{ add(9);
																				  add(8);
																				  add(7);
																				  add('x');
																				  add('y');
																				  add('z');
		}});
		
		l2.print();
		l3.print();
		System.out.println("LinkedList Size : " + l1.size());
		
		// -------------------------------------------------------------------------------------------
		
		l1.delete(2);
		
		// -------------------------------------------------------------------------------------------
		
		l1.add(0);
		l1.add(1);
		l1.add(2);
		l1.add(3);
		l1.add(-1);
		
		l1.getFirst();
		
		System.out.println("LinkedList Size : " + l1.size());

		// -------------------------------------------------------------------------------------------
		
		printHeader("get()");
		LinkedList<Object> l1_a = l1.get(2, 4);		
		
		// -------------------------------------------------------------------------------------------
		
		l1.deleteFirst();
		System.out.println("LinkedList Size : " + l1.size());

		l1.deleteLast();
		System.out.println("LinkedList Size : " + l1.size());
		
		// -------------------------------------------------------------------------------------------
		
		l1.add('a');
		l1.add('b');
				
		l1.delete();
		l1.delete(10);
		l1.delete(3);
		
		System.out.println("LinkedList Size : " + l1.size());
		
		// -------------------------------------------------------------------------------------------
		
		l1.add('c', 10);
		l1.add('a', 2);
		
		l1.getLast();
		
		System.out.println("LinkedList Size : " + l1.size());
	
		// -------------------------------------------------------------------------------------------
		
		l1.addLast('d');
		l1.add('a');
		l1.addFirst('e');
		System.out.println("LinkedList Size : " + l1.size());
		
		// -------------------------------------------------------------------------------------------
		
		l1.add('e');
		l1.add(1);
		l1.add(5);
		l1.add('a');
		l1.add(6);
		
		System.out.println("LinkedList Size : " + l1.size());
		
		// -------------------------------------------------------------------------------------------

		l1.find(100);
		l1.find(2);
		l1.find('e', 2);
		l1.find('e', 7);
		l1.find('a', 2, 9);
		l1.find(0, 2, 9);
		
		// -------------------------------------------------------------------------------------------

		l1.findLast('e');
		l1.findFirst('e');
		
		// -------------------------------------------------------------------------------------------
		
		l1.findBefore('e', 3);
		l1.findBefore(2, 1);
		l1.findAfter(1, 4);
		l1.findAfter(1, 8);
		
		l1.add('e');
		System.out.println("LinkedList {1} Size : " + l1.size());

		// -------------------------------------------------------------------------------------------
		
		l1.findAll('e');
		l1.findAll('d');
		l1.findAll(0);
		
		System.out.println(l1 + "\n");
		
		l1.findAll('a', 2, 7);
		l1.findAll(1, 1, 1);
		
		// -------------------------------------------------------------------------------------------
		
		System.out.println("LinkedList {1} Size : " + l1.size());

		l1.deleteAll('e');

		System.out.println("LinkedList {1} Size : " + l1.size());

		// -------------------------------------------------------------------------------------------
		
		System.out.println("LinkedList {1} Size : " + l1.size());

		l1.deleteAll(3);

		System.out.println("LinkedList {1} Size : " + l1.size());
		
		// -------------------------------------------------------------------------------------------

		l1.add( new LinkedList<Object>( new Object[]{9, 8, 7, 'x', 'y', 'z'} ) );
		
		System.out.println("LinkedList {1} Size : " + l1.size());

		// -------------------------------------------------------------------------------------------
		
		l1.isValidList();
		l2.isValidList();
		l3.isValidList();
		
		// -------------------------------------------------------------------------------------------

		l2.add( l3, 4 );
		l1.addLast(new LinkedList<Object>( new Object[]{"ONE", "TWO"} ));
		l2.addFirst(new LinkedList<Object>( new Object[]{"AA", "BB"} ));
		
		l2.print();
		System.out.println("LinkedList {2} Size : " + l2.size());
		
		// -------------------------------------------------------------------------------------------

		printHeader("toArray()");
		System.out.println("LinkedList {1} Array : " + Arrays.toString( l1.toArray() ) );
		
		// -------------------------------------------------------------------------------------------

		printHeader("copy()");
		LinkedList<Object> l4 = l2.copy();
		
		printHeader("add(item)");
		l4.add('a');
		
		l4.print();

		// -------------------------------------------------------------------------------------------

		l1.print();
		
		printHeader("delete(start, end)");
		l1.delete(9,0);
		
		printHeader("delete(start, end)");
		l1.delete(5,30);
		
		printHeader("delete(start, end)");
		l1.delete(5,9);
		
		System.out.println("LinkedList {1} Size : " + l1.size());

		// -------------------------------------------------------------------------------------------
		
		printHeader("copy(start, end)");
		l1.copy(6, 3);
		
		printHeader("copy(start, end)");
		l1.copy(1, 90);
		
		printHeader("copy(start, end)");
		LinkedList<Object> l5 = l1.copy(2, 6);
		
		System.out.println("LinkedList {5} Size : " + l5.size());

		// -------------------------------------------------------------------------------------------
		
		printHeader("insert(item, index)");
		l4.insert("THREE", 3);
		
		printHeader("insert(item, index)");
		l5.insert(new LinkedList<Object>( new Object[]{"FOUR", "FIVE"} ), 4);
		
		System.out.println("LinkedList {4} Size : " + l4.size());
		System.out.println("LinkedList {5} Size : " + l5.size());

		// -------------------------------------------------------------------------------------------
		
		printHeader("reverse()");
		l5.reverse();
		
		printHeader("recursiveReverse()");
		l5.recursiveReverse();
		
		printHeader("flip()");
		LinkedList<Object> l6_a = l4.flip();
		l4.print();
		
		printHeader("recursiveFlip()");
		LinkedList<Object> l6_b = l4.recursiveFlip();
		l4.print();
		
		// -------------------------------------------------------------------------------------------
		
		printHeader("isPalindrome()");
		l5.isPalindrome();
		
		LinkedList<Object> l7 = 
				new LinkedList<Object>( new Object[]{1, 2, 3, 'A', "B", 'A', 3, 2, 1} );
		
		printHeader("isPalindrome()");
		l7.isPalindrome();
		
		// -------------------------------------------------------------------------------------------
	
		l6_b.shuffle();
		l4.print();
		
		// -------------------------------------------------------------------------------------------
	}
	
	/** ----------------------------------------------------------------------------------------------
	 * printHeader(header)
	 * 
	 * @param header
	 */
	private static void printHeader(String header){
		System.out.println("-------------------------------- " +
						   header +
						   " --------------------------------\n");
	}// end printHeader(...)

}
