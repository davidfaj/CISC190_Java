/************************************************************************************
 * 																					*
 * CISC 190 - 02/13/2017															*
 * David - Month 13, 2017													*
 * 																					*
 * Brief Description:																*
 * This is project Programming Assignment 2 - Part 2. It assigns some number to		*
 * variables, make calculations with them and print the results.					*
 * 																					*
 * Special Classes:																	*
 * There are no Private or Protected Classes										*
 * 																					*
 * Detailed Notes on Code:															*
 * I used the Math class to calculate the Pi number, by calling the Pi constant.	*
 * Then I made the 4 calculations. For each calculation, I created a variable to	*
 * hold the result of each calculation. I defined all results data types as double	*
 * because it supports calculations with all given types (integer and doubles), as 	*
 * it is the largest data type.														*
 * EXTRA CREDITS:																	*
 * 1. needed to cast calculation as float, as it involved doubles (narrowing 		*
 * conversion)																		*
 * 2. and 3. no casting, as calculations involved doubles and int, and int is		* 
 * already widened converted by Java to double in the calculation					*
 * 4. needed to cast calculation as int, as it involved doubles (narrowing			* 
 * conversion)																		*
 ************************************************************************************/

public class ProgrammingAssignment2 {
	public static void main(String[] args) {
		// Create 5 variables and assign numbers to them
		int number1 = 50;
		double number2 = 3e10;
		double number3 = Math.PI;
		double number4 = 25.00;
		double number5 = -3.4e38;
		
		// 1. Calculate result1 = number2 x number3
		double result1;		// define result1 as double
		result1 = number2 * number3;		// make the calculation
		System.out.println("1. result1 = " + result1);		// print the result
		
		// 2. Calculate result2 = number2 / number1
		double result2;		// define result2 as double
		result2 = number2 / number1;		// make the calculation
		System.out.println("2. result2 = " + result2);		// print the result
		
		// 3. Calculate result3 = (number1 + number2 + number3) / number3
		double result3;		// define result3 as double
		result3 = ((number1 + number2 + number3) / number3);		// make the calculation
		System.out.println("3. result3 = " + result3);		// print the result
		
		// 4. Calculate result4 = (result1 + result2 + result3) / result2 % result1 
		double result4;		// define result4 as double
		result4 = (result1 + result2 + result3) / result2 % result1;		// make the calculation
		System.out.println("4. result4 = " + result4);		// print the result
		
		
		// EXTRA CREDIT
		System.out.println("\nEXTRA CREDIT\n");
		// 1EC. Calculate result1_float = number2 x number3 to a float
		float result1_float;		// define result1_float as float
		result1_float = (float)(number2 * number3);		// make the calculation
		System.out.println("1EC. result1_float = " + result1);		// print the result
		
		// 2EC. Calculate result2_double = number2 / number1 to a double
		double result2_double;		// define result2_double as double
		result2_double = number2 / number1;		// make the calculation
		System.out.println("2EC. result2_double = " + result2_double);		// print the result
		
		// 3EC. Calculate result3_double = (number1 + number2 + number3) / number3 to a double
		double result3_double;		// define result3_double as double
		result3_double = ((number1 + number2 + number3) / number3);		// make the calculation
		System.out.println("3EC. result3_double = " + result3_double);		// print the result
		
		// 4EC. Calculate result4_int = (result1_float + result2_double + result3_double) / result2_double % result1_float to an int
		int result4_int;		// define result4 as 
		result4_int = (int) ((result1_float + result2_double + result3_double) / result2 % result1_float);		// make the calculation
		System.out.println("4EC. result4_int = " + result4_int);		// print the result

		
		
	}
}
