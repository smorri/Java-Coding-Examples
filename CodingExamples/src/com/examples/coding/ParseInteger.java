package com.examples.coding;

import java.util.HashMap;
import java.util.regex.Pattern;

/** ========================================================================================================
 * class parseInteger()
 * 
 * [CHALLENGE] Implement parseInt
 * 
 * Takes any object and returns its integer value. In order for this to occur, the object needs
 * to pass several validity tests and a normalization step:
 * 
 * - No `null` Objects can be converted
 * - Only Basic Latin characters can be used in Objects
 * - Empty Objects do not have an equivalent integer value
 * - Objects must be composed of digits. Other optional characters include positive signs (+),
 *   negative signs (-), commas (,), and decimal points (.) 
 * - If negative or positive signs are used, only 1 of each can appear in the Object, and they
 *   must appear at the beginning. Both signs can appear in the Object but the value will
 *   automatically default to negative
 * - If decimal signs appear in the string there must only be a count of 1. Decimals can
 *   appear anywhere in the Object (beginning, middle, or end)
 * - If decimals appear in the Object, they are truncated, since integers can only represent
 *   whole numbers
 * - If commas are used, they must follow the standard usage of commas in values. A comma must
 *   appear before every count of 3 digits. 1-3 digits can precede a comma.
 * 
 * @author  Samone Morris
 * @version 1.0.0
 * @done	9/30/17
 * 
 * @param <T>	generic object that could be of primitive type (int, char, etc), auto-boxed
 * 			    (Integer, String, etc) or another Object class type, as long as it implements toString()
 * ========================================================================================================
 */

public class ParseInteger<T>{
	/** =============================================================================================== **/
	/** ========================================== VARIABLES ========================================== **/
	/** =============================================================================================== **/	
	private final static String NULL = "null",
								ZERO = "0",
						 		POSITIVE_SIGN = "+",
						 		NEGATIVE_SIGN = "-",
						 		DECIMAL_POINT = ".",
						 		COMMA = ",",
						 		PATTERN_INTEGER_WITH_COMMAS = "^(\\d+|\\d{1,3}(\\,\\d{3})*)$";
	
	private static final double TEN = 10;
	
	public final static byte NO_DECIMAL_POINT = 0,
							 ONE_DECIMAL_POINT = 1,
							 MULTI_DECIMAL_POINTS = -1,
							 INVALID_DECIMAL_VALUE = -2;
	
	private HashMap<Character, Integer> map;
	private Character.UnicodeBlock BASIC_LATIN = Character.UnicodeBlock.BASIC_LATIN;
	private Pattern comma_pattern = Pattern.compile(PATTERN_INTEGER_WITH_COMMAS, Pattern.MULTILINE);
	
	/** =============================================================================================== **/
	/** ======================================== CONSTRUCTORS ========================================= **/
	/** =============================================================================================== **/	
	
	/** ---------------------------------------------------------------------------------------------------
	 * ParseInteger()
	 * 
	 * Creates a new instance. Populates Character to Integer map.
	 */
	public ParseInteger(){
		map = new HashMap<Character, Integer>(10);
		initialize();
	}// end ParseInteger()
	
	/** =============================================================================================== **/
	/** ===================================== METHODS / FUNCTIONS ===================================== **/
	/** =============================================================================================== **/
	
	/** ---------------------------------------------------------------------------------------------------
	 * initialize()
	 * 
	 * Populates map that converts Character values to their corresponding Integer values
	 */
	private void initialize(){
		map.put('0', 0);
		map.put('1', 1);
		map.put('2', 2);
		map.put('3', 3);
		map.put('4', 4);
		map.put('5', 5);
		map.put('6', 6);
		map.put('7', 7);
		map.put('8', 8);
		map.put('9', 9);
	}// end initialize()
	
	/** ---------------------------------------------------------------------------------------------------
	 * isValidDecimalNumber(item)
	 * 
	 * Determines if a String representation of an object is a valid decimal number.
	 * 
	 * First, the sum of decimal characters are collected. Valid numbers can have 0 or 1 decimal
	 * point.
	 * 
	 * Then, the decimal portion is analyzed. The decimal portion must only contain characters
	 * that are numeric.
	 * 
	 * @param item		the String item to be evaluated
	 * @return			code that defines the type of decimal number for this item :
	 * 					`NO_DECIMAL_POINT`		= no decimal points appear in the item. This code
	 * 											  is a 'passable' state
	 * 					`MULTI_DECIMAL_POINTS`	= more than one decimal points appear in the item.
	 * 											  This is code represents a 'fail' state
	 * 					`ONE_DECIMAL_POINT`		= a single decimal point was discovered in the
	 * 											  item. This code is a 'passable' state
	 * 					`INVALID_DECIMAL_VALUE` = if the decimal portion contains characters that 
	 * 											  are not numeric. This code represents a 'fail'
	 * 											  state
	 */
	private byte isValidDecimalNumber(String item){
		int num_decimals = 0,
			decimal_index = -1;
		
		char [] decimal_portion;
		
		while( (decimal_index = item.indexOf(DECIMAL_POINT, decimal_index + 1)) > -1 ){
			//System.out.println("Decimal @ - " + decimal_index);
			num_decimals++;
		}// end while(...)
		
		//System.out.println( item + " - num = " + num_decimals);
		
		if( num_decimals == 0 ){
			return NO_DECIMAL_POINT;
		} else if( num_decimals >  1 ){
			return MULTI_DECIMAL_POINTS;
		} else {
			decimal_index = item.indexOf( DECIMAL_POINT );
			decimal_portion = item.substring( decimal_index + 1 ).toCharArray();
			
			//System.out.println( "INDEX = " + decimal_index + " | Portion = " + Arrays.toString( decimal_portion ));
			
			if( !containsDigits(decimal_portion) ){
				return INVALID_DECIMAL_VALUE;
			}// end if
		}// end if / else if / else
		
		return ONE_DECIMAL_POINT;
	}// end isValidDecimalNumber(...)
	
	/** ---------------------------------------------------------------------------------------------------
	 * isASCIIDigit(c)
	 * 
	 * Checks if a character is an ASCII digit.
	 * 
	 * @param c		the character to be evaluated
	 * @return		true if this character is an ASCII digit; false otherwise
	 */
	private boolean isASCIIDigit(char c){
		return Character.isDigit( c ) && isBasicLatin( c );
	}// end isASCIIDigit(...)
	
	/** ---------------------------------------------------------------------------------------------------
	 * isBasicLatin(c)
	 * 
	 * Checks if a character is found in the `Basic Latin` Unicode Block
	 * 
	 * @param c		the character to be evaluated
	 * @return		true if this character falls anywhere in the `Basic Latin` Unicode Block; false
	 * 				otherwise
	 */
	private boolean isBasicLatin(char c){
		return Character.UnicodeBlock.of( c ) == BASIC_LATIN;
	}// end isBasicLatin(...)
	
	/** ---------------------------------------------------------------------------------------------------
	 * containsCommas(item)
	 * 
	 * Checks if the String representation of an object contains commas
	 * 
	 * @param item		the String item to be evaluated
	 * @return			true if at least 1 comma is discovered in the item; false otherwise
	 */
	private boolean containsCommas(final String item){
		return item.indexOf( COMMA ) >= 0;
	}// end containsCommas(...)	
	
	/** ---------------------------------------------------------------------------------------------------
	 * containsDigits(array)
	 * 
	 * Checks if the character array representation of an object is composed of only ASCII digits
	 * 
	 * @param array		the character array of an item
	 * @return			true if the array contains only ASCII digitis; false otherwise
	 */
	private boolean containsDigits(char [] array){
		int i = 0,
			length = array.length;
		boolean has_digits = true;
		
		for(; i < length; i++){
			if( !isASCIIDigit(array[i]) ){
				has_digits = false;
				break;
			}// end if
		}// end for(...)
		
		return has_digits;
	}// end containsDigits(...)	
	
	/** ---------------------------------------------------------------------------------------------------
	 * normalize(item, decimal_code)
	 * 
	 * Normalizes the String representation of an object. Normalizing takes care of two tasks:
	 * 
	 * First, if a decimal point appears in the String, values after the point are removed. This is done
	 * because actual integers can only be represented as whole numbers. If after removing the decimal
	 * portion the normalized String is empty, a value of 0 is returned. If an item only contains a
	 * decimal point, then it is invalid, and a value of `null` is returned.
	 * 
	 * Then, any commas that appear in the String are removed. In Java, integers cannot contain commas.
	 * Any items that do not follow the proper usage for commas are invalid. A comma must
	 * appear before every count of 3 digits. 1-3 digits can precede a comma.
	 * 
	 * @param item				the String item to be evaluated
	 * @param decimal_code		the code determined by the method @see isValidDecimalNumber() during a 
	 * 							previous step
	 * @return					the normalized version of the item, without commas and without decimals
	 */
	private String normalize(String item, byte decimal_code){
		if( decimal_code == ONE_DECIMAL_POINT ){
			int decimal_index = item.indexOf(DECIMAL_POINT);
			String decimal_portion = item.substring( decimal_index + 1 ),
				   truncate = item.substring( 0, decimal_index );
			
			//System.out.println( truncate );
			if( decimal_portion.length() == 0 && truncate.length() == 0 ){
				return NULL;
			}// end if
			
			if ( truncate.isEmpty() ){
				return ZERO;
			}// end if
			
			item = truncate;
		}// end if
		
		if( containsCommas(item) ){
			if( !comma_pattern.matcher( item ).find() ){
				return NULL;
			}// end if
			
			item = item.replaceAll( COMMA, "" );
			
			//System.out.println("----> " + item);
			//if( item.isEmpty() ){
			//	return NULL;
			//}// end if
		}// end if
		
		return item;
	}// end normalize(...)

	/** ---------------------------------------------------------------------------------------------------
	 * get(c)
	 * 
	 * Gets the corresponding Integer value for a Character
	 * 
	 * @param c		the character to be evaluated
	 * @return		the integer value mapped to the character
	 */
	public int get(char c){
		return map.get(c);
	}// end get(...)
	
	/** ---------------------------------------------------------------------------------------------------
	 * parse(item)
	 * 
	 * @param item				the Object to be evaluated
	 * @return					the integer representation for this Object
	 * @throws Exception		if any of the following invalid conditions are met :
	 * 							`NULL`					  = Objects cannot be `null`; no integer can 
	 * 										 	  			represent a `null` Object
	 * 							`EMPTY`					  = Objects cannot be empty; no integer can 
	 * 										  	  			represent an empty Object
	 * 							`MISPLACED SIGNS`  		  = Objects cannot contain positive (+) or
	 * 													    negative (-) signs that do not appear
	 * 														at the start
	 * 							`IDENTICAL DOUBLE SIGNS`  =	Objects cannot contain two of the same
	 * 														sign; `--` and `++` are invalid
	 * 							`MULTIPLE DECIMAL POINTS` = Objects that contain more than one decimal
	 * 														points are invalid
	 * 							`INVALID DECIMAL VALUE`   = if the decimal portion contains characters 
	 * 											  		    that are not numeric, then this is invalid
	 * 							`SINGLE DECIMAL POINT`	  = Objects containing only a single decimal 
	 * 														point with no digits are invalid
	 * 							`NON-NUMERIC CHARACTERS`  = with the exception of signs (+, -),
	 * 														commas (,), and decimal points (.), Objects
	 * 														cannot contain non-numeric characters (ie
	 * 														alphabetic, or unicode outside of the ASCII
	 * 														range)
	 * @return					the integer value of this Object
	 */
	public int parse(T item) throws Exception{
		/* ------------------------------------------------------------------------------------------------
		 * [0] SANITY CHECK
		 * Ensure any items are non-null.
		 * ------------------------------------------------------------------------------------------------
		 */
		if( item == null ){
	        throw new Exception("[EXCEPTION] Cannot parse as an integer because value is null");
		}// end if / throws
		
		/* ------------------------------------------------------------------------------------------------
		 * [1] INTEGER VALUES
		 * If any item is already an integer (either primitive (int) or boxed object (Integer), 
		 * just return the value. No parsing needed.
		 * ------------------------------------------------------------------------------------------------
		 */
		if( item.getClass() == Integer.class ){
			return (int) item;
		}// end if
		
		/* ------------------------------------------------------------------------------------------------
		 * [2] STRING CAST
		 * Cast item as a String. Ensure String value is non-null or empty.
		 * ------------------------------------------------------------------------------------------------
		 */
		String item_string = String.valueOf( item );
		
		if( item_string == NULL || item_string.isEmpty() ){
	        throw new Exception("[EXCEPTION] Cannot parse as an integer because value is null or empty");
		}// end if / throws
				
		/* ------------------------------------------------------------------------------------------------
		 * [3] SIGN CHECK
		 * 
		 * Check if negative sign is positioned correctly (if one exists).
		 * Check if positive sign is positioned correctly (if one exists).
		 * Check if items contain mis-positioned signs (3-2, 9+2) or 2 consecutive signs of the same
		 * type (if any exists).
		 * If items contain both negative and positive signs, the number should default to negative
		 * (+-d or -+d)
		 * If index of the negative sign and positive sign are both non-negative, create substring absent
		 * of one or both signs.
		 * ------------------------------------------------------------------------------------------------
		 */
		int neg_sign,
			pos_sign,
			sum,
			abs_sum;
		
		boolean isNegative;
		
		neg_sign = item_string.lastIndexOf( NEGATIVE_SIGN );
		pos_sign = item_string.lastIndexOf( POSITIVE_SIGN );
		
		if( neg_sign > 1 ){
	        throw new Exception("[EXCEPTION] Discovered 1 or more negative (-) signs, but are not \n" + 
	        					"positioned at Index 0");
		}// end if / throws
		
		if( pos_sign > 1 ){
	        throw new Exception("[EXCEPTION] Discovered 1 or more positive (+) signs, but are not \n" + 
					"positioned at Index 0");
		}// end if / throws
		
		sum = neg_sign + pos_sign;
		abs_sum = Math.abs( sum );
		
		if( abs_sum == 0 ){
	        throw new Exception("[EXCEPTION] Positive (+) or negative (-) signs are not positioned \n" +
	        					"properly OR 2 consecutive signs of the same type discovered");			
		}// end if / throws
		
		isNegative = ( neg_sign >= 0 ) ? true : false;

		if( abs_sum == 1 ){
			item_string = ( sum < 0 ) ? item_string.substring(1) : item_string.substring(2);
		}// end if
		
		//System.out.println("W/O POSITIVE SIGN | W/O NEGATIVE SIGN STRING = " +  item_string);
		
		/* ------------------------------------------------------------------------------------------------
		 * [4] DECIMAL POINT CHECK
		 * Check if decimal point is found
		 * Valid items can only contain 0 or 1 decimal point
		 * Ensure 0 or more digits appear after the decimal point (if one is found)
		 * ------------------------------------------------------------------------------------------------
		 */
		
		byte valid_decimal = isValidDecimalNumber( item_string );
		//System.out.println("VALID = " + valid_decimal);

		if( valid_decimal == MULTI_DECIMAL_POINTS || valid_decimal == INVALID_DECIMAL_VALUE ){
	        throw new Exception("[EXCEPTION] Not a valid decimal number");		
		} // end if
		
		
		/* ------------------------------------------------------------------------------------------------
		 * [4] NORMALIZE
		 * Remove commas from item string (if any)
		 * Truncate decimal portion from item string (if any)
		 * ------------------------------------------------------------------------------------------------
		 */
		item_string = normalize( item_string, valid_decimal );
		if( item_string == NULL){
	        throw new Exception("[EXCEPTION] Cannot normalize ; Invalid number format");		
		}// end if
		
		//System.out.println("NORMALIZED = " + item_string);

		/* ------------------------------------------------------------------------------------------------
		 * [6] CONVERT TO CHAR ARRAY & NORMALIZE
		 * Convert String as a Char Array. Using the index for each character, it will be easier to
		 * calculate the sum (based on the place value)
		 * 
		 * Verify the item contains only digits. Once a non-digit is discovered, then terminate early.
		 * Checking first will save time during calculations.
		 * 
		 * ------------------------------------------------------------------------------------------------
		 */
		char [] integer_part = item_string.toCharArray();
		
		if( !containsDigits(integer_part) ){
	        throw new Exception("[EXCEPTION] Characters that are not representative of ASCII digits \n" +						
	        					"discovered");
		}// end if / throws
				
		/* ------------------------------------------------------------------------------------------------
		 * [7] COMPUTE
		 * Verify the item contains only digits. Once a non-digit is discovered, then terminate early.
		 * Checking first will save time during calculations.
		 * ------------------------------------------------------------------------------------------------
		 */		
		return compute( integer_part, isNegative );
	}// end parse(...)

	/** ---------------------------------------------------------------------------------------------------
	 * compute(array, isNegative)
	 * 
	 * Calculates the integer value for an object using expanded notation
	 * 
	 * @param array			the character array of an item to access individual digits and access their 
	 * 						indicies (to find the exponent value needed for expanded notation)	
	 * @param isNegative	if the item was negative or positive
	 * @return				the integer value for an Object			
	 */
	private int compute(char [] array, boolean isNegative){
		int i = 0,
			value = 0,
			length = array.length,
			d;
		
		for(; i < length; i++){
			d = get(array[i]);
			
			if(i == length - 1){
				value += d;
			} else {
				//System.out.println( "-->" + Math.pow(TEN, (double) length - i - 1) * d);
				value += Math.pow(TEN, (double) length - i - 1) * d; 
			}// end if / else
		}// end for(...)
		
		return (isNegative) ? value * -1 : value;
	}// end compute(...)
	
	/** =============================================================================================== **/
	/** ============================================ MAIN ============================================= **/
	/** =============================================================================================== **/	
	
	public static void main(String[] args){
		ParseInteger<Object> parser = new ParseInteger<Object>();
		
		/* ------------------------------------------ NUMBERS ------------------------------------------ */
		
		try {
			System.out.println("V1  = 0            | Parsed = " + parser.parse(0));
			System.out.println("V2  = 1            | Parsed = " + parser.parse(1));
			System.out.println("V3  = -1           | Parsed = " + parser.parse(-1));
			System.out.println("V4  = +-2          | Parsed = " + parser.parse(+-2));
			System.out.println("V5  = -+5          | Parsed = " + parser.parse(-+5));
			System.out.println("V6  = 10 + 20      | Parsed = " + parser.parse(10 + 20));
		} catch (Exception e) {
			e.printStackTrace();
		}// end try / catch
	
		/* ------------------------------------------ STRINGS ------------------------------------------ */
		try {
			System.out.println("V7  = 1,000 	   | Parsed = " + parser.parse("1,000"));
			System.out.println("V8  = -500         | Parsed = " + parser.parse("-500"));
			System.out.println("V9  = 73506 	   | Parsed = " + parser.parse("73506"));
			System.out.println("V10 = 7533.907533  | Parsed = " + parser.parse("7533.907533"));
			System.out.println("V11 = .4           | Parsed = " + parser.parse(".4"));
			System.out.println("V12 = +-54,256,999 | Parsed = " + parser.parse("+-54,256,999"));
			System.out.println("V13 = -+199.       | Parsed = " + parser.parse("-+199."));
		} catch (Exception e) {
			e.printStackTrace();
		}// end try / catch
		
		/* ---------------------------------------- CHARACTERS ---------------------------------------- */
		try {
			System.out.println("V14  = 9           | Parsed = " + parser.parse('9'));
			System.out.println("V15  = 1           | Parsed = " + parser.parse('1'));
		} catch (Exception e) {
			e.printStackTrace();
		}// end try / catch		
		
		/* ----------------------------------------- OBJECTS ----------------------------------------- */
		try {
			System.out.println("V16  = 600   	   | Parsed = " + parser.parse(new Integer(600)));
			System.out.println("V17  = 55          | Parsed = " + parser.parse(new Byte((byte) 55)));
			System.out.println("V18  = 5           | Parsed = " + parser.parse(new Character('5')));
			System.out.println("V19  = +-96        | Parsed = " + parser.parse(new Long(+-96)));
			System.out.println("V20  = 2891.90     | Parsed = " + parser.parse(new Double(2891.90)));
			System.out.println("V21  = -600.12 	   | Parsed = " + parser.parse(new Float(-600.12)));
			System.out.println("V22  = 3260 	   | Parsed = " + parser.parse(new Short((short) 3260)));
			
			//System.out.println("V17  = -5500       | Parsed = " + parser.parse( new Integer(-5500) ));
			//System.out.println("V18  = -+9600      | Parsed = " + parser.parse( new Integer(-+9600) ));
			//System.out.println("V19  = +96         | Parsed = " + parser.parse( new Integer(+96) ));
			//System.out.println("V20  = +-128       | Parsed = " + parser.parse( new Integer(+-128) ));
		} catch (Exception e) {
			e.printStackTrace();
		}// end try / catch	
		
		/* --------------------------------------- EXCEPTIONS ---------------------------------------- */
		try {
			//System.out.println("VB1 = false       | Parsed = " + parser.parse(false));
			//System.out.println("VS1 =             | Parsed = " + parser.parse(""));
			//System.out.println("VS2 = --4         | Parsed = " + parser.parse("--4"));
			//System.out.println("VS3 = ++7         | Parsed = " + parser.parse("++7"));
			//System.out.println("VS4 = 9,9,9       | Parsed = " + parser.parse("9,9,9"));
			//System.out.println("VS5 = null        | Parsed = " + parser.parse(null));
			//System.out.println("VS6 = abc         | Parsed = " + parser.parse("abc"));
			//System.out.println("VS7 = 7.31.2      | Parsed = " + parser.parse("7.31.2"));
			//System.out.println("VS7 = ...         | Parsed = " + parser.parse("..."));
			//System.out.println("VS8 = ,,,         | Parsed = " + parser.parse(",,,"));
			//System.out.println("VC1 = a           | Parsed = " + parser.parse('a'));
			//System.out.println("VC2 = !           | Parsed = " + parser.parse('!'));
			//System.out.println("VC3 = ,           | Parsed = " + parser.parse(','));
			//System.out.println("VC4 = .           | Parsed = " + parser.parse('.')); 						
			//System.out.println("VO1 = true        | Parsed = " + parser.parse(new Boolean(true)));
			//System.out.println("VO2 = [Object]    | Parsed = " + parser.parse(new Object()));
		} catch (Exception e) {
			e.printStackTrace();
		}// end try / catch	

	}// end main(...)
}// end class ParseInteger()
