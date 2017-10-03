package com.examples.coding;

import java.util.Arrays;
import java.util.Random;

public class FindLargestValueInArray {
	private static int LOWER_BOUND = -100,
					   ZERO = 0,
					   UPPER_BOUND = 100;	
	
	private static int VERY_SMALL = 3,
					   SMALL = 10,
					   LARGE = 55,
					   VERY_LARGE = 500;
	
	public static void main(String[] args) {
		int [] array_A = generateRandomArray(SMALL, false);		
		int largest_A_1 = trivial_findLargest( array_A );
		int largest_A_2 = recurssive_findLargest( array_A );
		
		System.out.println(Arrays.toString(array_A));
		System.out.println("(Largest) Trivial = " + largest_A_1);
		System.out.println("(Largest) Recursive = " + largest_A_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		int [] array_B = generateRandomArray(VERY_SMALL, false);		
		int largest_B_1 = trivial_findLargest( array_B );
		int largest_B_2 = recurssive_findLargest( array_B );
		
		System.out.println(Arrays.toString(array_B));
		System.out.println("Trivial = " + largest_B_1);
		System.out.println("Recursive = " + largest_B_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		int [] array_C = generateRandomArray(LARGE, false);		
		int largest_C_1 = trivial_findLargest( array_C );
		int largest_C_2 = recurssive_findLargest( array_C );
		
		System.out.println(Arrays.toString(array_C));
		System.out.println("Trivial = " + largest_C_1);
		System.out.println("Recursive = " + largest_C_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		int [] array_D = generateRandomArray(VERY_LARGE, false);		
		int largest_D_1 = trivial_findLargest( array_D );
		int largest_D_2 = recurssive_findLargest( array_D );
		
		System.out.println(Arrays.toString(array_D));
		System.out.println("Trivial = " + largest_D_1);
		System.out.println("Recursive = " + largest_D_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		int [] array_E = generateRandomArray(SMALL, true);		
		int largest_E_1 = trivial_findLargest( array_E );
		int largest_E_2 = recurssive_findLargest( array_E );
		
		System.out.println(Arrays.toString(array_E));
		System.out.println("(Largest) Trivial = " + largest_E_1);
		System.out.println("(Largest) Recursive = " + largest_E_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		int [] array_F = generateRandomArray(VERY_SMALL, true);		
		int largest_F_1 = trivial_findLargest( array_F );
		int largest_F_2 = recurssive_findLargest( array_F );
		
		System.out.println(Arrays.toString(array_F));
		System.out.println("(Largest) Trivial = " + largest_F_1);
		System.out.println("(Largest) Recursive = " + largest_F_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		int [] array_G = generateRandomArray(LARGE, true);		
		int largest_G_1 = trivial_findLargest( array_G );
		int largest_G_2 = recurssive_findLargest( array_G );
		
		System.out.println(Arrays.toString(array_G));
		System.out.println("(Largest) Trivial = " + largest_G_1);
		System.out.println("(Largest) Recursive = " + largest_G_2);
		System.out.println("--------------------------------------------");

		// ---------------------------------------------------------------
		
		int [] array_H = generateRandomArray(VERY_LARGE, true);		
		int largest_H_1 = trivial_findLargest( array_H );
		int largest_H_2 = recurssive_findLargest( array_H );
		
		System.out.println(Arrays.toString(array_H));
		System.out.println("(Largest) Trivial = " + largest_H_1);
		System.out.println("(Largest) Recursive = " + largest_H_2);
		System.out.println("--------------------------------------------");
		
	}

	private static int trivial_findLargest(final int[] array){
		int val,
			current,
			total = array.length,
			i = 1;
		
		val = array[0];
		
		for(; i < total; i++){
			current = array[i];
			
			if(current >= val){ val = current; }
		}
		
		return val;
	}// end trivial_findLargest(...)
	
	private static int recurssive_findLargest(final int[] array){		
		if( array.length == 1 ){ 
			return array[0];
		}
		
		if( array.length == 2 ){ 
			return ( array[0] >= array[1] ) ? array[0] : array[1];
		}
		
		int group_a_size = (int) Math.floor( array.length / 2 );
		
		//System.out.println("Group A Size = " + group_a_size);
		
		int [] group_a = Arrays.copyOfRange(array, 0, group_a_size),
			   group_b = Arrays.copyOfRange(array, group_a_size, array.length);
		
		//System.out.println("A : " + Arrays.toString(group_a));
		//System.out.println("B : " + Arrays.toString(group_b));
		
		return Math.max( recurssive_findLargest(group_a) , recurssive_findLargest(group_b));
	}
	
	private static int[] generateRandomArray(int size, boolean withNegatives){
		int [] array = new int[size];
		int min = (withNegatives) ? LOWER_BOUND : ZERO,
			max = UPPER_BOUND,
			i = 0;
		
		Random r = new Random();
		
		for(; i < size; i++){
			array[i] = r.nextInt((max - min) + 1) + min;
		}
		
		
		System.out.println(array);
		return array;
	}
	
}
