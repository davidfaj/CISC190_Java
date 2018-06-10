/************************************************************************************
 * 																					*
 * CISC 190 - 02/13/2017															*
 * David - Month 13, 2017													*
 * 																					*
 * Brief Description:																*
 * This is project Programming Assignment 2 - Part 1. It prompts the user for their	*
 *  age, annual income and name. Then it prints a sentence with all the answers.	*
 * 																					*
 * Special Classes:																	*
 * There are no Private or Protected Classes										*
 * 																					*
 * Detailed Notes on Code:															*
 * A "Scanner" is used to allow input of from the keyboard.							*
 * The method nextInt() is used to get the age input, nextDouble() is used to get	*
 * the income input and nextLine() is used to get the name input.					*
 * When the user types the income and hits [ENTER] (newline) in the keyboard, both	*
 * the typed value and the newline goes to the keyboard buffer. The method 			*
 * nextDouble() reads only the value and let the newline in the buffer. So I added 	*
 * one nextLine() first just to consume this remaining newline, and only after that,*
 * I asked the name question and used again the nextLine() to read the user's name,	*
 * this time with an empty buffer.													*
 ************************************************************************************/
import java.util.Scanner;	// Needed for the scanner class

public class ProgrammingAssignment2Part1 {
	public static void main(String[] args){
		String name; // To hold the user's name
		int age; // To hold the user's age
		double income; // To hold the user's income
		
		// Create a Scanner object to read input.
		Scanner keyboard = new Scanner(System.in);
		
		// Get the user's age.
		System.out.print("What is your age? ");
		age = keyboard.nextInt();
		
		// Get the user's income
		System.out.print("What is your annual income? ");
		income = keyboard.nextDouble();
		
		// Consume the remaining newline
		keyboard.nextLine();
		
		// Get the user's name.
		System.out.print("What is your name? ");
		name = keyboard.nextLine();
		
		// Display the information back to the user.
		System.out.println("Hello, " + name + ". Your age is " +
				age + " and your income is $" +
				income);

	}
}
