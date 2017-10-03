package com.examples.coding;

import java.util.ArrayDeque;
import java.util.Arrays;

public class RotatedArray {

	public static void main(String[] args) {
		Integer [] array_AA = {},
				   array_AB = {1},
				   array_AC = {1,2,3,4},
				   array_AD = {1,2,3,5,6,7,8},
				   array_AE = {1,2,1,2,1,2},
				   array_AF = {2},
			
				   array_BA = {0},
				   array_BB = {0, 1},
				   array_BC = {3,4,2,1},
				   array_BD = {5,6,7,8,1,2,3},
				   array_BE = {2,1,2,1,2,1},
				   array_BF = {2};
		
		isRotatedArray( array_AA, array_BA );
		System.out.println("--------------------------------------------");	

		isRotatedArray( array_AB, array_BB );
		System.out.println("--------------------------------------------");	

		isRotatedArray( array_AC, array_BC );
		System.out.println("--------------------------------------------");	

		isRotatedArray( array_AD, array_BD );
		System.out.println("--------------------------------------------");	

		isRotatedArray( array_AE, array_BE );
		System.out.println("--------------------------------------------");	

		isRotatedArray( array_AF, array_BF );
	}

	private static boolean isRotatedArray(final Integer [] arr_A, final Integer [] arr_B){
		int a_length = arr_A.length,
			b_length = arr_B.length;
		
		System.out.println( "A : " + Arrays.toString( arr_A ) );
		System.out.println( "B : " + Arrays.toString( arr_B ) );		
		
		if(a_length == 0){
			if(b_length == 0){
				System.out.println("[FAIL] Array A and Array B are both empty");
				return false;
			} else {
				System.out.println("[FAIL] Array A is empty");
				return false;				
			}// end if(...){...} else {...}
		} else if(b_length == 0){
			System.out.println("[FAIL] Array B is empty");
			return false;
		} else if(a_length > b_length){
			System.out.println("[FAIL] Array A has more elements than Array B");
			return false;			
		} else if(a_length < b_length){
			System.out.println("[FAIL] Array B has more elements than Array A");
			return false;			
		} else if(a_length == 1 && a_length == b_length){
			if( arr_A[0] == arr_B[0] ){
				System.out.println("[SUCCESS] Array B is a rotated version of Array A | Number of Rotations = 0");
				return true;
			}// end if(...)
			
			System.out.println("[FAIL] Array B is NOT a rotated version of Array A");
			return false;
		} else if( Arrays.equals(arr_A, arr_B) ){
			System.out.println("[SUCCESS] Array B is a rotated version of Array A | Number of Rotations = 0");
			return true;
		} else {
			ArrayDeque<Integer> b_deque = createDeque(arr_B);
			int i = 0;
			
			for(;i < a_length; i++){
				b_deque.addLast(  b_deque.removeFirst() );
	
				Integer [] deque_array = b_deque.toArray( new Integer[0] );
				
				//System.out.println( Arrays.toString( deque_array ) );
				
				if( Arrays.equals( arr_A , deque_array) ){
					System.out.println("[SUCCESS] Array B is a rotated version of Array A | Number of Rotations = " + (i + 1));					
					return true;
				}
			}
		}

		System.out.println("[FAIL] Array B is NOT a rotated version of Array A");					
		return false;
	}
	
	private static ArrayDeque<Integer> createDeque(final Integer [] array){
		int length = array.length,
			i = 0;
		ArrayDeque<Integer> deque = new ArrayDeque<Integer>(length);
		
		for(;i < length; i++){
			deque.offer( array[i] );
		}
		
		return deque;
	}
	
}
