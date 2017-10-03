package com.examples.coding;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/** ========================================================================================================
 * class ArrayUtils()
 * 
 * [CHALLENGES]
 * 
 * [1] Find the most frequent Integer in an array
 * [2] Find the smallest Integer in an array (Bonus : do it recursively)
 * [3] Find the largest Integer in an array(Bonus : do it recursively)
 * [4] Find pairs in an Integer array whose sum is equal to 'x' (Bonus : do it in linear time)
 * [5] Given two Integer arrays, determine if the 2nd array is a rotated version of the 1st array
 * 	   (ex. A: {1,2,3,5,6,7,8} and B: {5,6,7,8,1,2,3})
 * [6]
 * 
 * 
 * @author  Samone Morris
 * @version 1.2.0
 * @done	9/22/17
 * ========================================================================================================
 */
public class ArrayUtils{
	public static final int LOWER_BOUND = -100,
	   		   		  		ZERO = 0,
	   		   		  		UPPER_BOUND = 100;	

	public static final int VERY_SMALL = 3,
					   		SMALL = 10,
					   		LARGE = 55,
					   		VERY_LARGE = 500;

	/** ---------------------------------------------------------------------------------------------------
	 * generateRandomArray(size, withNegatives)
	 * 
	 * @param size
	 * @param withNegatives
	 * @return
	 */
	public static Integer [] generateRandomArray(int size, boolean withNegatives){
		Integer [] array = new Integer[size];
		int min = (withNegatives) ? LOWER_BOUND : ZERO,
			max = UPPER_BOUND,
			i = 0;
		
		Random r = new Random();
		
		for(; i < size; i++){
			array[i] = r.nextInt((max - min) + 1) + min;
		}// end for(...)
		
		return array;
	}// end generateRandomArray(...)
	
	/** --------------------------------------------------------------------------------------------------
	 * generateRandomNumber(isNegative)
	 * 
	 * @param isNegative		if the random number should be negative
	 * @return
	 */
	public static int generateRandomNumber(boolean isNegative){
		int min = (isNegative) ? LOWER_BOUND : ZERO,
				max = UPPER_BOUND;		
		
		return new Random().nextInt((max - min) + 1) + min;
	}// end generateRandomNumber(...)	
	
	/** ---------------------------------------------------------------------------------------------------
	 * findFrequentValue(array)
	 * 
	 * Locates which Integer(s) appears the most frequently in an array.
	 * 
	 * If 2 or more Integers have the same number of frequency (and have the highest rate), then each
	 * value will be displayed. Therefore, an array with all unique values, will post all array results
	 * with a frequency of 1.
	 * 
	 * @param array			the array of Integers to be analyzed
	 * @return				a string result which details which Integer(s) appear the most frequent
	 * 						in the array
	 */
	public static String findFrequentValue(final Integer [] array){
		if( array.length == 0 ){
			return "No Repeats -> Array Length = 0";
		}// end if
		
		if( array.length == 1 ){
			return "Value = " + array[0] + " | Frequency = 1 time -> Only 1 element \n" +
				   "in Array"; 
		}// end if
		
		/* -------------------------------------------------------------------------------------------------
		 * [1] COLLECTION
		 * 
		 * Walk through array and calculate frequencies for each value into a map.
		 */		
		Integer val;
		int highest_count = 0,
		    count = 0,
		    total = array.length,
		    i = 0;
		String result = "";
		Map<Integer, AtomicInteger> map = new HashMap<Integer, AtomicInteger>(total);
		
		for(; i < total; i++){
			val = array[i];
			
			if( !map.containsKey(val) ){
				map.put( val, new AtomicInteger(1) );
				count = 1;
			} else {
				count = map.get( val ).incrementAndGet();
			}// end if / else
			
			if( count > highest_count ){ highest_count = count; }
		}// end for(...)
		
		/* -------------------------------------------------------------------------------------------------
		 * [2] CALCULATION
		 * 
		 * Choose which values occurred the most in the array; store result(s)
		 */
		for( Map.Entry<Integer, AtomicInteger> entry : map.entrySet() ){
			if( entry.getValue().intValue() == highest_count ){
				result = result + "Value = " + entry.getKey() + " | " + 
								  "Frequency = " + highest_count + " times\n";
			}// end if
		}// end for(...)
		
		return result;
	}// end find(...)
	
	/** ---------------------------------------------------------------------------------------------------
	 * trivial_findSmallest(array)
	 * 
	 * @param array
	 * @return
	 */
	public static int trivial_findSmallest(final Integer [] array){
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
	
	/** ---------------------------------------------------------------------------------------------------
	 * recursive_findSmallest(array)
	 * 
	 * @param array
	 * @return
	 */
	public static int recursive_findSmallest(final Integer [] array){
		if( array.length == 1 ){ 
			return array[0];
		}
		
		if( array.length == 2 ){ 
			return ( array[0] <= array[1] ) ? array[0] : array[1];
		}
		
		int group_a_size = (int) Math.floor( array.length / 2 );
		
		//System.out.println("Group A Size = " + group_a_size);
		
		Integer [] group_a = Arrays.copyOfRange(array, 0, group_a_size),
			       group_b = Arrays.copyOfRange(array, group_a_size, array.length);
		
		//System.out.println("A : " + Arrays.toString(group_a));
		//System.out.println("B : " + Arrays.toString(group_b));
		
		return Math.min( recursive_findSmallest(group_a) , recursive_findSmallest(group_b) );
	}// end recursive_findSmallest(...)
	
	/** ---------------------------------------------------------------------------------------------------
	 * trivial_findLargest(array)
	 * 
	 * @param array
	 * @return
	 */
	public static int trivial_findLargest(final Integer [] array){
		int val,
			current,
			total = array.length,
			i = 1;
		
		val = array[0];
		
		for(; i < total; i++){
			current = array[i];
			
			if(current >= val){ val = current; }
		}// end for(...)
		
		return val;
	}// end trivial_findLargest(...)
	
	/** ----------------------------------------------------------------------------------------------------
	 * recursive_findLargest(array)
	 * 
	 * @param array
	 * @return
	 */
	public static int recursive_findLargest(final Integer [] array){
		if( array.length == 1 ){ 
			return array[0];
		}// end if
		
		if( array.length == 2 ){ 
			return ( array[0] >= array[1] ) ? array[0] : array[1];
		}// end if
		
		/* -------------------------------------------------------------------------------------------------
		 * [1] SPLIT INTO GROUPS
		 * 
		 * Divide array into 2 halves; if array contains an odd number of elements, one group will be 
		 * 1 element smaller than the other.
		 */		
		int group_a_size = (int) Math.floor( array.length / 2 );
		
		//System.out.println("Group A Size = " + group_a_size);
		
		Integer [] group_a = Arrays.copyOfRange(array, 0, group_a_size),
				   group_b = Arrays.copyOfRange(array, group_a_size, array.length);
		
		//System.out.println("A : " + Arrays.toString(group_a));
		//System.out.println("B : " + Arrays.toString(group_b));
		
		return Math.max( recursive_findLargest(group_a) , recursive_findLargest(group_b));
	}// end recursive_findLargest(...)
	
	/** ---------------------------------------------------------------------------------------------------
	 * findPairs(array, sum)
	 * 
	 * @param array
	 * @param sum
	 * @return
	 */
	public static String findPairs(final Integer [] array, final int sum){
		int length = array.length;
		
		if(length == 0){
			return "Array is Empty! No pairs found";
		}// end if
		
		if(length == 1){
			return "Array contains 1 element; No pairs found";
		}// end if
		
		// ----------------------------------------------------------------------------------
		
		Map<Integer, AtomicInteger> map = findRepeats( array ); 
		Set<Integer> duplicates = new HashSet<Integer>( length );
		
		// ----------------------------------------------------------------------------------
		
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
				}// end if / else
				
				result = result + "There are " + freq + 
						          " Pair(s) of ( " + num + " , " + diff + " ) that sum to " +
						          sum + " \n";
				
				duplicates.add( num );
			}// end if
			
			deque.offerLast( num );
		}// end for(...)
		
		// ----------------------------------------------------------------------------------
		
		return (result.length() == 0) ? "No pairs found" : result;
	}// end findPairs(...)
	
	/** ---------------------------------------------------------------------------------------------------
	 * createDeque(array)
	 * 
	 * @param array
	 * @return
	 */
	private static ArrayDeque<Integer> createDeque(final Integer [] array){
		int i = 0,
			length = array.length;
		ArrayDeque<Integer> deque = new ArrayDeque <Integer>(length);

		for(;i < length; i++){ deque.push( array[i] ); }
		
		return deque;
	}//  end createDeque(...)
	
	/** -----------------------------------------------------------------------------------------------------
	 * findRepeats(array)
	 * 
	 * @param array
	 * @return
	 */
	private static HashMap<Integer, AtomicInteger> findRepeats(final Integer [] array){
		int length = array.length,
			val,
			highest_count = 0,
			count = 0,
			i = 0;
		
		if( length == 0 ){
			return new HashMap<Integer, AtomicInteger>();
		}// end if		
		
		/* -------------------------------------------------------------------------------------------------
		 * [1] COLLECTION
		 * 
		 * Walk through array and calculate frequencies for each value into a map.
		 */	
		
		HashMap<Integer, AtomicInteger> map = new HashMap<Integer, AtomicInteger>(length);

		for(; i < length; i++){
			val = array[i];
			
			if(!map.containsKey( val )){
				map.put( val, new AtomicInteger(1) );
				count = 1;
			} else {
				count = map.get( val ).incrementAndGet();
			}// end if / else
			
			if( count > highest_count ){ highest_count = count; }
		}// end for(...)
		
		return map;
	}// end findRepeats(...)
	
	/** ----------------------------------------------------------------------------------------------------
	 * isRotatedArray(arr_A, arr_B)
	 * 
	 * @param arr_A
	 * @param arr_B
	 * @return
	 */
	public static boolean isRotatedArray(final Integer [] arr_A, final Integer [] arr_B){
		int a_length = arr_A.length,
			b_length = arr_B.length;
		
		System.out.println( "A : " + Arrays.toString( arr_A ) );
		System.out.println( "B : " + Arrays.toString( arr_B ) );		

		/* -------------------------------------------------------------------------------------------------
		 * [1] SANITY CHECKS
		 * 
		 * Ensure both arrays have the same number of elements in each
		 * Ensure boh arrays are non-empty
		 * If both elements have only a single element, make sure the value of each element is equal. By
		 * default, return true
		 * If both arrays are identical, by default, return true
		 */			
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
				System.out.println("[SUCCESS] Array B is a rotated version of Array A | " + 
								   "Number of Rotations = 0");
				return true;
			}// end if(...)
			
			System.out.println("[FAIL] Array B is NOT a rotated version of Array A");
			return false;
		} else if( Arrays.equals(arr_A, arr_B) ){
			System.out.println("[SUCCESS] Array B is a rotated version of Array A | " + 
							   "Number of Rotations = 0");
			return true;
		}
		
		/* -------------------------------------------------------------------------------------------------
		 * [2] ANALYZE
		 * 
		 * Rotate the array at a maximum equivalent to the total number of elements. If we find a match 
		 * after 'x' amount of rotations, return true. Otherwise, return false
		 */		
		else {
			ArrayDeque<Integer> b_deque = createDeque(arr_B);
			int i = 0;
			
			for(;i < a_length; i++){
				b_deque.addLast(  b_deque.removeFirst() );
	
				Integer [] deque_array = b_deque.toArray( new Integer[0] );
				
				//System.out.println( Arrays.toString( deque_array ) );
				
				if( Arrays.equals( arr_A , deque_array) ){
					System.out.println("[SUCCESS] Array B is a rotated version of Array A | " + 
									   "Number of Rotations = " + (i + 1));					
					return true;
				}// end if
			}// end for(...)
		}// end if / else if / else

		System.out.println("[FAIL] Array B is NOT a rotated version of Array A");					
		return false;
	}// end isRotatedArray(...)

	
	/** =============================================================================================== **/
	/** ============================================ MAIN ============================================= **/
	/** =============================================================================================== **/	
	public static void main(String[] args) {

		findMostFrequent();
		findSmallest();
		findLargest();
		findSumPairs();
		verifyRotatedArray();
		
	}// end main(...)
	/* --------------------------------------------------------------------------------------------- */
	/* ------------------------------------ FIND FREQUENT VALUE ------------------------------------ */
	/* --------------------------------------------------------------------------------------------- */
	
	private static void findMostFrequent(){
		System.out.println("------------------------- FIND FREQUENT VALUE -------------------------\n");
		
		/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ NO REPEATS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
		Integer [] array_A = {20, 64, 75},
			       array_B = {10, 45, 40, 29, 94, 100, 13, 32, 70, 49};
		
		/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 1 OR MORE REPEATS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
		Integer [] array_C = {10, 45, 40, 27, 94, 100, 13, 40, 70, 49},
			       array_D = {1, -50, -47, -23, 18, 18, 3, 1, -47, -47, 2, 10, 100, -23};
		
		/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ALL REPEATS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
		Integer [] array_E = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
		
		/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ EMPTY / ONE VALUE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
		Integer [] array_F = {};
		Integer [] array_G = {1};	

		// --------------------------------------------------------------------------------------------
		
		System.out.println( Arrays.toString(array_A) );
		String repeats_A = ArrayUtils.findFrequentValue(array_A);
		System.out.println(repeats_A);
		System.out.println("--------------------------------------------");

		// -------------------------------------------------------------------------
		
		System.out.println( Arrays.toString(array_B) );
		String repeats_B = ArrayUtils.findFrequentValue(array_B);
		System.out.println(repeats_B);
		System.out.println("--------------------------------------------");

		// -------------------------------------------------------------------------
		
		System.out.println( Arrays.toString(array_C) );
		String repeats_C = ArrayUtils.findFrequentValue(array_C);
		System.out.println(repeats_C);
		System.out.println("--------------------------------------------");

		// -------------------------------------------------------------------------
		
		System.out.println( Arrays.toString(array_D) );
		String repeats_D = ArrayUtils.findFrequentValue(array_D);
		System.out.println(repeats_D);	
		System.out.println("--------------------------------------------");

		// -------------------------------------------------------------------------
		
		System.out.println( Arrays.toString(array_E) );
		String repeats_E = ArrayUtils.findFrequentValue(array_E);
		System.out.println(repeats_E);		
		System.out.println("--------------------------------------------");

		// -------------------------------------------------------------------------
		
		System.out.println( Arrays.toString(array_F) );
		String repeats_F = ArrayUtils.findFrequentValue(array_F);
		System.out.println(repeats_F);				
		System.out.println("--------------------------------------------");
		
		// -------------------------------------------------------------------------
		
		System.out.println( Arrays.toString(array_G) );
		String repeats_G = ArrayUtils.findFrequentValue(array_G);
		System.out.println(repeats_G);			
	}// end findMostFrequent()
	
	/* --------------------------------------------------------------------------------------------- */
	/* ------------------------------------ FIND SMALLEST VALUE ------------------------------------ */
	/* --------------------------------------------------------------------------------------------- */	
	
	private static void findSmallest(){
		System.out.println("------------------------- FIND SMALLEST VALUE -------------------------\n");

		Integer [] array_H = ArrayUtils.generateRandomArray(SMALL, false);		
		int smallest_H_1 = ArrayUtils.trivial_findSmallest( array_H );
		int smallest_H_2 = ArrayUtils.recursive_findSmallest( array_H );
		
		System.out.println( Arrays.toString(array_H) );
		System.out.println("(Smallest) Trivial = " + smallest_H_1);
		System.out.println("(Smallest) Recursive = " + smallest_H_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		Integer [] array_I = ArrayUtils.generateRandomArray(VERY_SMALL, false);		
		int smallest_I_1 = ArrayUtils.trivial_findSmallest( array_I );
		int smallest_I_2 = ArrayUtils.recursive_findSmallest( array_I );
		
		System.out.println(Arrays.toString(array_I));
		System.out.println("(Smallest) Trivial = " + smallest_I_1);
		System.out.println("(Smallest) Recursive = " + smallest_I_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		Integer [] array_J = ArrayUtils.generateRandomArray(LARGE, false);		
		int smallest_J_1 = ArrayUtils.trivial_findSmallest( array_J );
		int smallest_J_2 = ArrayUtils.recursive_findSmallest( array_J );
		
		System.out.println(Arrays.toString(array_J));
		System.out.println("(Smallest) Trivial = " + smallest_J_1);
		System.out.println("(Smallest) Recursive = " + smallest_J_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		Integer [] array_K = ArrayUtils.generateRandomArray(VERY_LARGE, false);		
		int smallest_K_1 = ArrayUtils.trivial_findSmallest( array_K );
		int smallest_K_2 = ArrayUtils.recursive_findSmallest( array_K );
		
		System.out.println(Arrays.toString(array_K));
		System.out.println("(Smallest) Trivial = " + smallest_K_1);
		System.out.println("(Smallest) Recursive = " + smallest_K_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		Integer [] array_L = ArrayUtils.generateRandomArray(SMALL, true);		
		int smallest_L_1 = ArrayUtils.trivial_findSmallest( array_L );
		int smallest_L_2 = ArrayUtils.recursive_findSmallest( array_L );
		
		System.out.println(Arrays.toString(array_L));
		System.out.println("(Smallest) Trivial = " + smallest_L_1);
		System.out.println("(Smallest) Recursive = " + smallest_L_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		Integer [] array_M = ArrayUtils.generateRandomArray(VERY_SMALL, true);		
		int smallest_M_1 = ArrayUtils.trivial_findSmallest( array_M );
		int smallest_M_2 = ArrayUtils.recursive_findSmallest( array_M );
		
		System.out.println(Arrays.toString(array_M));
		System.out.println("(Smallest) Trivial = " + smallest_M_1);
		System.out.println("(Smallest) Recursive = " + smallest_M_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		Integer []  array_N = ArrayUtils.generateRandomArray(LARGE, true);		
		int smallest_N_1 = ArrayUtils.trivial_findSmallest( array_N );
		int smallest_N_2 = ArrayUtils.recursive_findSmallest( array_N );
		
		System.out.println(Arrays.toString(array_N));
		System.out.println("(Smallest) Trivial = " + smallest_N_1);
		System.out.println("(Smallest) Recursive = " + smallest_N_2);
		System.out.println("--------------------------------------------");

		// ---------------------------------------------------------------
		
		Integer [] array_O = ArrayUtils.generateRandomArray(VERY_LARGE, true);		
		int smallest_O_1 = ArrayUtils.trivial_findSmallest( array_O );
		int smallest_O_2 = ArrayUtils.recursive_findSmallest( array_O );
		
		System.out.println(Arrays.toString(array_O));
		System.out.println("(Smallest) Trivial = " + smallest_O_1);
		System.out.println("(Smallest) Recursive = " + smallest_O_2);
	}// end findSmallest()
	
	/* --------------------------------------------------------------------------------------------- */
	/* ------------------------------------ FIND LARGEST VALUE ------------------------------------- */
	/* --------------------------------------------------------------------------------------------- */
	
	private static void findLargest(){
		System.out.println("------------------------- FIND LARGEST VALUE -------------------------\n");

		Integer [] array_P = ArrayUtils.generateRandomArray(SMALL, false);		
		int largest_P_1 = ArrayUtils.trivial_findLargest( array_P );
		int largest_P_2 = ArrayUtils.recursive_findLargest( array_P );
		
		System.out.println(Arrays.toString(array_P));
		System.out.println("(Largest) Trivial = " + largest_P_1);
		System.out.println("(Largest) Recursive = " + largest_P_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		Integer [] array_Q = ArrayUtils.generateRandomArray(VERY_SMALL, false);		
		int largest_Q_1 = ArrayUtils.trivial_findLargest( array_Q );
		int largest_Q_2 = ArrayUtils.recursive_findLargest( array_Q );
		
		System.out.println(Arrays.toString(array_Q));
		System.out.println("(Largest) Trivial = " + largest_Q_1);
		System.out.println("(Largest) Recursive = " + largest_Q_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		Integer [] array_R = ArrayUtils.generateRandomArray(LARGE, false);		
		int largest_R_1 = ArrayUtils.trivial_findLargest( array_R );
		int largest_R_2 = ArrayUtils.recursive_findLargest( array_R );
		
		System.out.println(Arrays.toString(array_R));
		System.out.println("(Largest) Trivial = " + largest_R_1);
		System.out.println("(Largest) Recursive = " + largest_R_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		Integer [] array_S = ArrayUtils.generateRandomArray(VERY_LARGE, false);		
		int largest_S_1 = ArrayUtils.trivial_findLargest( array_S );
		int largest_S_2 = ArrayUtils.recursive_findLargest( array_S );
		
		System.out.println(Arrays.toString(array_S));
		System.out.println("(Largest) Trivial = " + largest_S_1);
		System.out.println("(Largest) Recursive = " + largest_S_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		Integer [] array_T = ArrayUtils.generateRandomArray(SMALL, true);		
		int largest_T_1 = ArrayUtils.trivial_findLargest( array_T );
		int largest_T_2 = ArrayUtils.recursive_findLargest( array_T );
		
		System.out.println(Arrays.toString(array_T));
		System.out.println("(Largest) Trivial = " + largest_T_1);
		System.out.println("(Largest) Recursive = " + largest_T_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		Integer [] array_U = ArrayUtils.generateRandomArray(VERY_SMALL, true);		
		int largest_U_1 = trivial_findLargest( array_U );
		int largest_U_2 = recursive_findLargest( array_U );
		
		System.out.println(Arrays.toString(array_U));
		System.out.println("(Largest) Trivial = " + largest_U_1);
		System.out.println("(Largest) Recursive = " + largest_U_2);
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		Integer [] array_V = ArrayUtils.generateRandomArray(LARGE, true);		
		int largest_V_1 = ArrayUtils.trivial_findLargest( array_V );
		int largest_V_2 = ArrayUtils.recursive_findLargest( array_V );
		
		System.out.println(Arrays.toString(array_V));
		System.out.println("(Largest) Trivial = " + largest_V_1);
		System.out.println("(Largest) Recursive = " + largest_V_2);
		System.out.println("--------------------------------------------");

		// ---------------------------------------------------------------
		
		Integer [] array_W = ArrayUtils.generateRandomArray(VERY_LARGE, true);		
		int largest_W_1 = ArrayUtils.trivial_findLargest( array_W );
		int largest_W_2 = ArrayUtils.recursive_findLargest( array_W );
		
		System.out.println(Arrays.toString(array_W));
		System.out.println("(Largest) Trivial = " + largest_W_1);
		System.out.println("(Largest) Recursive = " + largest_W_2);
		System.out.println("--------------------------------------------");
	}// end findLargest()
	
	/* --------------------------------------------------------------------------------------------- */
	/* -------------------------------------- FIND SUM PAIRS --------------------------------------- */
	/* --------------------------------------------------------------------------------------------- */	
	
	private static void findSumPairs(){
		System.out.println("-------------------------- FIND SUM PAIRS ---------------------------\n");
		
		Integer [] array_X = ArrayUtils.generateRandomArray(VERY_LARGE, false);
		System.out.println(Arrays.toString( array_X ));
		String array_X_pairs = ArrayUtils.findPairs( array_X,
													 ArrayUtils.generateRandomNumber(false) );
		System.out.println( array_X_pairs );
		System.out.println("--------------------------------------------");
		
		// ---------------------------------------------------------------
		
		Integer [] array_Y = ArrayUtils.generateRandomArray(VERY_LARGE, true);
		System.out.println(Arrays.toString( array_Y ));
		String array_Y_pairs = ArrayUtils.findPairs( array_Y,
													 ArrayUtils.generateRandomNumber(true) );
		System.out.println( array_Y_pairs );
		System.out.println("--------------------------------------------");
	
		// ---------------------------------------------------------------
		
		Integer [] array_Z = ArrayUtils.generateRandomArray(LARGE, false);
		System.out.println(Arrays.toString( array_Z ));
		String array_Z_pairs = ArrayUtils.findPairs( array_Z,
										  ArrayUtils.generateRandomNumber(false) );
		System.out.println( array_Z_pairs );
		System.out.println("--------------------------------------------");	
		
		// ---------------------------------------------------------------
		
		Integer [] array_ZZ = ArrayUtils.generateRandomArray(LARGE, true);
		System.out.println(Arrays.toString( array_ZZ ));
		String array_ZZ_pairs = findPairs( array_ZZ,
										   ArrayUtils.generateRandomNumber(true) );
		System.out.println( array_ZZ_pairs );
	}// end findSumPairs()

	/* --------------------------------------------------------------------------------------------- */
	/* --------------------------------------- ROTATED ARRAY --------------------------------------- */
	/* --------------------------------------------------------------------------------------------- */
	
	private static void verifyRotatedArray(){
		System.out.println("-------------------------- ROTATED ARRAYS ---------------------------\n");

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
		
		ArrayUtils.isRotatedArray( array_AA, array_BA );
		System.out.println("--------------------------------------------");	

		// ---------------------------------------------------------------
		
		ArrayUtils.isRotatedArray( array_AB, array_BB );
		System.out.println("--------------------------------------------");	

		// ---------------------------------------------------------------

		ArrayUtils.isRotatedArray( array_AC, array_BC );
		System.out.println("--------------------------------------------");	

		// ---------------------------------------------------------------

		ArrayUtils.isRotatedArray( array_AD, array_BD );
		System.out.println("--------------------------------------------");	

		// ---------------------------------------------------------------

		ArrayUtils.isRotatedArray( array_AE, array_BE );
		System.out.println("--------------------------------------------");	

		// ---------------------------------------------------------------

		ArrayUtils.isRotatedArray( array_AF, array_BF );
	}// end verifyRotatedArray()

	/* --------------------------------------------------------------------------------------------- */
	/* -------------------------------------  ------------------------------------ */
	/* --------------------------------------------------------------------------------------------- */
	
	
}// end class FrequentValueInArray
