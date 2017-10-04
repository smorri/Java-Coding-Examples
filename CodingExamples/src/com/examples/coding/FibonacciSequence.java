package com.examples.coding;

import java.math.BigInteger;

/** ========================================================================================================
 * class FibonacciSequence()
 * 
 * [CHALLENGES] Write Fibonacci sequence iteratively and recursively (bonus: use dynamic programming)
 * 
 * The first few iterations of the Fibonacci sequence is as follows:
 * 
 * STEP 	[ 0 | 1 | 2 | 3 | 4 | 5 | 6 |  7 | ... ]
 * VALUE	[ 0 | 1 | 1 | 2 | 3 | 5 | 8 | 13 | ... ]
 * 
 * This class provides methods to compute the Fibonacci value for step 's' using an iterative process and
 * a recursive process.
 * 
 * For the iterative process, a trivial and optimized method are available.
 * For the recursive process, dynamic programming is utilized.
 * 
 * @author  Samone Morris
 * @version 1.3.0
 * @done	10/3/17
 * ========================================================================================================
 */
public class FibonacciSequence {
	private static final double ZERO = 0,
							 	ONE = 1;
	
	/** ---------------------------------------------------------------------------------------------------
	 * trivial(step)
	 * 
	 * Iteratively computes the Fibonacci value at step 's' rather..trivially.
	 * 
	 * The total is the summation of all previous values. Use a dummy variable to keep track of the 
	 * previous value, but make we compute the new previous value (by subtracting the current previous
	 * value from the total).
	 * 
	 * @param step		Fibonacci value at this step which must be computed
	 * @return			final computated value
	 */
	public static BigInteger trivial(int step){
		int i = 1;
		BigInteger prev_val = new BigInteger("1"),
				   total = new BigInteger("0");
		
		if( step == 0 ){ return total; }
		
		while( i <= step ){
			total = total.add( prev_val );
			prev_val = total.subtract( prev_val );
						
			i++;
		}// end while(...)
		
		return total;
	}// end trivial(...)
	
	/** ---------------------------------------------------------------------------------------------------
	 * optimized(step)
	 * 
	 * Iteratively computes the Fibonacci value at step 's', but in a much more optimized manner.
	 * 
	 * We know the Fibonacci value at step 's' is simply the summation of Fib(s-1) + Fib(s-2). As long
	 * as we keep track of these previous steps, sum them, and update them, then we can compute the 
	 * value much more efficiently.
	 * 
	 * Is better than performing the subtraction step seen in @see {@link #trivial(int)}
	 * 
	 * @param step		Fibonacci value at this step which must be computed
	 * @return			final computated value
	 */
	public static BigInteger optimized(int step){
		int i = 1;
		BigInteger total = new BigInteger("0"),
				   prev_1 = new BigInteger("1"),
				   prev_2 = new BigInteger("0");
		
		if( step == ZERO ){ return prev_2; }
		if( step == ONE ){ return prev_1; }
		
		while( i <= step ){
			total = prev_1.add( prev_2 );
			prev_1 = prev_2;
			prev_2  = total;
			
			i++;
		}// end while(...)
		
		return total;
	}// end optimized(...)
	
	/**  ---------------------------------------------------------------------------------------------------
	 * recursive(step)
	 * 
	 * Recursivey computes the Fibonacci value at step 's'. Again, using the same method as described in 
	 * the optimized iterative-version @see {@link #optimized(int)} , Fib(s-1) + Fib(s-2)
	 * 
	 * Performance-wise, this method is not good to use for several reasons:
	 * [1] This method performs a lot of repeats.There is a high chance we compute the value of Fib(s-1)
	 * 	   twice, which is wasteful. 
	 * [2] The higher the step, the slower this method is. Often times, it has ended in a dead-lock 
	 * 	   (an infinite loop of sorts)
	 * 
	 * Because of these two reasons, this method is deprecated. Just use the method linked below.
	 * 
	 * @param step		Fibonacci value at this step which must be computed
	 * @return			final computated value
	 * @deprecated		@use {@link #recursive(int, BigInteger[])} instead.
	 */
	@Deprecated
	public static BigInteger recursive(int step){
		if( step == ZERO ){ return new BigInteger("0"); }
		if( step == ONE ) { return new BigInteger("1"); }
	
		return recursive(step - 1).add( recursive(step - 2) );
	}// end recursive(...)
	
	/** ---------------------------------------------------------------------------------------------------
	 * recursive(step, storage)
	 * 
	 * Recursivey computes the Fibonacci value at step 's'. Again, using the same method as described in 
	 * the optimized iterative-version @see {@link #optimized(int)} , Fib(s-1) + Fib(s-2).
	 * 
	 * This method performs better than the previously implemented version. Solutions were discovered
	 * for the optimization and bottle neck problems experienced in the previous version.
	 * 
	 * We now keep track of the entire Fibonacci sequence in an array. 
	 * And if we have already computed the value at some step 's_n', then just return 's_n' without
	 * recomputing anything.
	 * 
	 * @param step			Fibonacci value at this step which must be computed
	 * @param storage		the entire fibonacci sequence, which gets updated when values are 
	 * 					    calculated
	 * @return
	 */
	public static BigInteger recursive(int step, final BigInteger [] storage){	
		if( step == ZERO ){ return new BigInteger("0"); }
		if( step == ONE ) { storage[0] = new BigInteger("1"); return storage[0]; }	
		
		if( storage[step - 1] != null ){ return storage[step - 1]; }
		
		storage[step - 1] = recursive(step - 1, storage).add( recursive(step - 2, storage) );
			
		return storage[step - 1];
	}// end calculate(...)
	
	public static void main(String[] args) {
		int fib_A = 0,
			fib_B = 1,
			fib_C = 7,
			fib_D = 25,
			fib_E = 100;
		
		System.out.println("(Trivial)   : " + FibonacciSequence.trivial(fib_A) );
		System.out.println("(Optimized) : " + FibonacciSequence.optimized(fib_A) );
		System.out.println("(Recursive) : " +
				FibonacciSequence.recursive(fib_A, new BigInteger[fib_A]) );
		System.out.println("--------------------------------------------");	

		System.out.println("(Trivial)   : " + FibonacciSequence.trivial(fib_B) );
		System.out.println("(Optimized) : " + FibonacciSequence.optimized(fib_B) );
		System.out.println("(Recursive) : " +
				FibonacciSequence.recursive(fib_B, new BigInteger[fib_B]) );		
		System.out.println("--------------------------------------------");	
		
		System.out.println("(Trivial)   : " + FibonacciSequence.trivial(fib_C) );
		System.out.println("(Optimized) : " + FibonacciSequence.optimized(fib_C) );
		System.out.println("(Recursive) : " +
				FibonacciSequence.recursive(fib_C, new BigInteger[fib_C]) );
		System.out.println("--------------------------------------------");	

		System.out.println("(Trivial)   : " + FibonacciSequence.trivial(fib_D) );
		System.out.println("(Optimized) : " + FibonacciSequence.optimized(fib_D) );
		System.out.println("(Recursive) : " +
				FibonacciSequence.recursive(fib_D, new BigInteger[fib_D]) );		
		System.out.println("--------------------------------------------");	
		
		System.out.println("(Trivial)   : " + FibonacciSequence.trivial(fib_E) );
		System.out.println("(Optimized) : " + FibonacciSequence.optimized(fib_E) );
		System.out.println("(Recursive) : " +
				FibonacciSequence.recursive(fib_E, new BigInteger[fib_E]) );		
		System.out.println("--------------------------------------------");	

	}// end main(...)

}// end class FibonacciSequence
