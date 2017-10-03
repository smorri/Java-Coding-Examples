package com.examples.coding;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class FindSumPairsInArray {
	private static int LOWER_BOUND = -100,
					   ZERO = 0,
					   UPPER_BOUND = 100;	
	
	private static int LARGE = 55,
				   	   VERY_LARGE = 500;

	public static void main(String[] args) {
		int [] array_A = generateRandomArray(VERY_LARGE, false);
		System.out.println(Arrays.toString( array_A ));
		String array_A_pairs = findPairs( array_A, randomSum(false) );
		System.out.println( array_A_pairs );
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		int [] array_B = generateRandomArray(VERY_LARGE, true);
		System.out.println(Arrays.toString( array_B ));
		String array_B_pairs = findPairs( array_B, randomSum(true) );
		System.out.println( array_B_pairs );
		System.out.println("--------------------------------------------");
	
		// ---------------------------------------------------------------
		
		int [] array_C = generateRandomArray(LARGE, false);
		System.out.println(Arrays.toString( array_C ));
		String array_C_pairs = findPairs( array_C, randomSum(false) );
		System.out.println( array_C_pairs );
		System.out.println("--------------------------------------------");	
		
		// ---------------------------------------------------------------
		
		int [] array_D = generateRandomArray(LARGE, true);
		System.out.println(Arrays.toString( array_D ));
		String array_D_pairs = findPairs( array_D, randomSum(true) );
		System.out.println( array_D_pairs );
		System.out.println("--------------------------------------------");	
	}
	
	private static String findPairs(final int[] array, final int sum){
		int length = array.length;
		
		if(length == 0){
			return "Array is Empty! No pairs found";
		}
		
		if(length == 1){
			return "Array contains 1 element; No pairs found";
		}
		
		// ---------------------------------------------------------------
		
		Map<Integer, AtomicInteger> map = findRepeats( array ); 
		Set<Integer> duplicates = new HashSet<Integer>( length );
		// ---------------------------------------------------------------
		
		int i = 0,
			num,
			diff,
			freq,
			freq_diff,
			freq_num;
		String result = "";
		ArrayDeque<Integer> deque = createDeque( array );
		
		for(;i < length; i++){
			num = deque.pollFirst();
			diff = sum - num;
						
			if( deque.contains(diff) && !duplicates.contains( num )){
				freq_diff = map.get(diff).intValue();
				freq_num = map.get(num).intValue();
				
				if( freq_diff == freq_num ){
					freq = freq_diff;
				} else {
					freq = freq_diff * freq_num;
				}
				
				result = result + "There are " + freq + 
						          " Pair(s) of ( " + num + " , " + diff + " ) that sum to " +
						          sum + " \n";
				
				duplicates.add( num );
			}
			
			deque.offerLast( num );
		}
		
		// ---------------------------------------------------------------		
		
		return (result.length() == 0) ? "No pairs found" : result;
	}

	private static ArrayDeque<Integer> createDeque(final int [] array){
		int i = 0,
			length = array.length;
		ArrayDeque<Integer> deque = new ArrayDeque <Integer>(length);

		for(;i < length; i++){ deque.push( array[i] ); }
		
		return deque;
	}
	
	private static HashMap<Integer, AtomicInteger> findRepeats(final int[] array){
		int length = array.length,
			val,
			highest_count = 0,
			count = 0,
			i = 0;
		
		if( length == 0 ){
			return new HashMap<Integer, AtomicInteger>();
		}
	
		HashMap<Integer, AtomicInteger> map = new HashMap<Integer, AtomicInteger>(length);
		
		for(; i < length; i++){
			val = array[i];
			
			if(!map.containsKey( val )){
				map.put( val, new AtomicInteger(1) );
				count = 1;
			} else {
				count = map.get( val ).incrementAndGet();
			}
			
			if( count > highest_count ){ highest_count = count; }
		}
		
		return map;
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
		
		return array;
	}
	
	private static int randomSum(boolean isNegative){
		int min = (isNegative) ? LOWER_BOUND : ZERO,
				max = UPPER_BOUND;		
		
		return new Random().nextInt((max - min) + 1) + min;
	}
	
}
