package com.examples.coding;

import java.math.BigInteger;

public class FibonacciSequence {
	private static final double ZERO = 0,
							 	ONE = 1;
	
	// 1, 2, 3, 4, 5, 6,  7
	// 1, 1, 2, 3, 5, 8, 13, 
	
	/**
	 * calculate(step)
	 * 
	 * @param step
	 * @return
	 */
	public static BigInteger trivial(int step){
		int i = 1;
		BigInteger prev_val = new BigInteger("1"),
				   total = new BigInteger("0");
		
		if( step == 0 ){ return total; }
		
		while( i <= step ){
			total = total.add( prev_val );
			prev_val = total.subtract( prev_val );
			
			//System.out.println("prev = " + prev_val + " | total = " + total);
			
			i++;
		}// end while(...)
		
		return total;
	}// end trivial_calculate(...)
	
	public static BigInteger recursive(int step){
		System.out.println( step );
		
		if( step <= ZERO ){ return new BigInteger("0"); }
		if( step == ONE ) { return new BigInteger("1"); }
	
		return recursive(step - 1).add( recursive(step - 2) );
	}// end calculate(...)	
	
	public static void main(String[] args) {
		int rec_A = 0,
			rec_B = 1,
			rec_C = 7,
			rec_D = 25;
		
		System.out.println("(Trivial)   : " + FibonacciSequence.trivial(rec_A) );
		System.out.println("(Recursive) : " + FibonacciSequence.recursive(rec_A) );
		System.out.println("--------------------------------------------");	

		System.out.println("(Trivial)   : " + FibonacciSequence.trivial(rec_B) );
		System.out.println("(Recursive) : " + FibonacciSequence.recursive(rec_B) );		
		System.out.println("--------------------------------------------");	
		
		System.out.println("(Trivial)   : " + FibonacciSequence.trivial(rec_C) );
		System.out.println("(Recursive) : " + FibonacciSequence.recursive(rec_C) );
		System.out.println("--------------------------------------------");	

		System.out.println("(Trivial)   : " + FibonacciSequence.trivial(rec_D) );
		System.out.println("(Recursive) : " + FibonacciSequence.recursive(rec_D) );		
		System.out.println("--------------------------------------------");	

	}// end main(...)

}// end class FibonacciSequence
