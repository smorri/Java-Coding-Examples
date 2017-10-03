package com.examples.coding;

import java.util.Arrays;
import java.util.Random;

public class FindSmallestValueInArray {
	private static int LOWER_BOUND = -100,
					   ZERO = 0,
					   UPPER_BOUND = 100;	
	
	private static int VERY_SMALL = 3,
					   SMALL = 10,
					   LARGE = 55,
					   VERY_LARGE = 500;
	
	public static void main(String[] args) {
		int [] array_A = generateRandomArray(SMALL, false);		
		int smallest_A_1 = trivial_findSmallest( array_A );
		int smallest_A_2 = recurssive_findSmallest( array_A );
		
		System.out.println(Arrays.toString(array_A));
		System.out.println("Trivial = " + smallest_A_1);
		System.out.println("Recurssive = " + smallest_A_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		int [] array_B = generateRandomArray(VERY_SMALL, false);		
		int smallest_B_1 = trivial_findSmallest( array_B );
		int smallest_B_2 = recurssive_findSmallest( array_B );
		
		System.out.println(Arrays.toString(array_B));
		System.out.println("Trivial = " + smallest_B_1);
		System.out.println("Recurssive = " + smallest_B_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		int [] array_C = generateRandomArray(LARGE, false);		
		int smallest_C_1 = trivial_findSmallest( array_C );
		int smallest_C_2 = recurssive_findSmallest( array_C );
		
		System.out.println(Arrays.toString(array_C));
		System.out.println("Trivial = " + smallest_C_1);
		System.out.println("Recurssive = " + smallest_C_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		int [] array_D = generateRandomArray(VERY_LARGE, false);		
		int smallest_D_1 = trivial_findSmallest( array_D );
		int smallest_D_2 = recurssive_findSmallest( array_D );
		
		System.out.println(Arrays.toString(array_D));
		System.out.println("Trivial = " + smallest_D_1);
		System.out.println("Recurssive = " + smallest_D_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		int [] array_E = generateRandomArray(SMALL, true);		
		int smallest_E_1 = trivial_findSmallest( array_E );
		int smallest_E_2 = recurssive_findSmallest( array_E );
		
		System.out.println(Arrays.toString(array_E));
		System.out.println("Trivial = " + smallest_E_1);
		System.out.println("Recurssive = " + smallest_E_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		int [] array_F = generateRandomArray(VERY_SMALL, true);		
		int smallest_F_1 = trivial_findSmallest( array_F );
		int smallest_F_2 = recurssive_findSmallest( array_F );
		
		System.out.println(Arrays.toString(array_F));
		System.out.println("Trivial = " + smallest_F_1);
		System.out.println("Recurssive = " + smallest_F_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		int [] array_G = generateRandomArray(LARGE, true);		
		int smallest_G_1 = trivial_findSmallest( array_G );
		int smallest_G_2 = recurssive_findSmallest( array_G );
		
		System.out.println(Arrays.toString(array_G));
		System.out.println("Trivial = " + smallest_G_1);
		System.out.println("Recurssive = " + smallest_G_2);
		System.out.println("--------------------------------------------");

		// ---------------------------------------------------------------
		
		int [] array_H = generateRandomArray(VERY_LARGE, true);		
		int smallest_H_1 = trivial_findSmallest( array_H );
		int smallest_H_2 = recurssive_findSmallest( array_H );
		
		System.out.println(Arrays.toString(array_H));
		System.out.println("Trivial = " + smallest_H_1);
		System.out.println("Recurssive = " + smallest_H_2);
		System.out.println("--------------------------------------------");
		
	}

	private static int trivial_findSmallest(final int[] array){
		int val,
			current,
			total = array.length,
			i = 1;
		
		val = array[0];
		
		for(; i < total; i++){
			current = array[i];
			
			if(current <= val){ val = current; }
		}
		
		return val;
	}// end trivial_findSmallest(...)
	
	private static int recurssive_findSmallest(final int[] array){		
		if( array.length == 1 ){ 
			return array[0];
		}
		
		if( array.length == 2 ){ 
			return ( array[0] <= array[1] ) ? array[0] : array[1];
		}
		
		int group_a_size = (int) Math.floor( array.length / 2 );
		
		//System.out.println("Group A Size = " + group_a_size);
		
		int [] group_a = Arrays.copyOfRange(array, 0, group_a_size),
			   group_b = Arrays.copyOfRange(array, group_a_size, array.length);
		
		//System.out.println("A : " + Arrays.toString(group_a));
		//System.out.println("B : " + Arrays.toString(group_b));
		
		return Math.min( recurssive_findSmallest(group_a) , recurssive_findSmallest(group_b));
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
