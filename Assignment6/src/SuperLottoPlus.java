/*******************************************************************************
 *																			   *
 * CISC 190 - 04/25/2017													   *
 * David - April 25, 2017											   *
 * 																			   *
 * Brief Description:														   *
 * This is project Programming Assignment 6. It creates the SuperLottoPlus
 * 	class, where it ask the user how many lottery tickets he/she wants to buy,
 *  and generate these tickets with 6 random numbers (5, and 1 MEGA).
 * 																			   *
 * Special Classes:															   *
 * There are no Private or Protected Classes								   *
 * 																			   *
 * Detailed Notes on Code:													   *
 * 1) I created the generateSuperLottoNoDupes() method
 * 2) I also created the generateNumber() and duplicate() methods, to make the
 *  code in main() easier to visualize. generateNumber() generates only one
 *  random number. duplicate() checks if the generated number is a duplicate
 *  (true) or not (false).
 * 3) To check if the duplicate() method is working properly, set class field
 *  FIRST_5_MAX = 5. If you set it to a less than 5 number (ex: 4), the program
 *  will loop infinitely, as it needs to generate 5 different numbers in a
 *  range of 1 - 4, so the last number will always be a duplicate of the
 *  previous 4 ones
 *******************************************************************************/

// Import libraries needed to:
import java.util.Scanner;		// read input
import java.util.Random;		// generate random numbers for the draw

// Declare class SuperLottoPlus
public class SuperLottoPlus {
	////////////
	// FIELDS //
	////////////
	private final static int NUM_DRAWS = 6;		// number of randomly generated numbers per ticket
	private final static int FIRST_5_MIN = 1;	// lower limit of randomly generated number for the first 5 ticket numbers
	private final static int FIRST_5_MAX = 47;	// upper limit of randomly generated number for the first 5 ticket numbers
	private final static int LAST_MIN = 1;		// lower limit of randomly generated number for the last ticket number (MEGA)
	private final static int LAST_MAX = 27;		// upper limit of randomly generated number for the last ticket number (MEGA)
	private static int[] ticket_numbers = new int[NUM_DRAWS];	// array to contain the 6 randomly generated ticket numbers
	private static Random randomNumber = new Random();		// Random object to generate random numbers
	

	/////////////
	// METHODS //
	/////////////
	
	/**
	 * Method generateSuperLottoNumbers()
	 * 	Generate 6 random numbers (5 + 1 MEGA), but doesn't check if the first 5 may be duplicate
	 * @return ticket_numbers	int[], array with the 6 ticket numbers
	 */
	public static int[] generateSuperLottoNumbers() {
		// Loop to generate the first 5 ticket numbers
		for (int i = 0; i < (NUM_DRAWS - 1); i++) {
			ticket_numbers[i] =  generateNumber(FIRST_5_MIN, FIRST_5_MAX);
		}
		// Loop to generate the last number (MEGA)
		ticket_numbers[NUM_DRAWS - 1] = generateNumber(LAST_MIN, LAST_MAX);
		// Return an array with the 6 randomly generated ticket numbers
		return ticket_numbers;
	}
	
	
	/**
	 * Method generateSuperLottoNoDupes()
	 * 	Generate 6 random numbers (5 + 1 MEGA), not allowing duplicates in the first 5 numbers
	 * @return ticket_numbers	int[], array with the 6 ticket numbers
	 */
	public static int[] generateSuperLottoNoDupes() {
		int try_number;		// used for saving a temporary try to generate a random number
		// Loop to generate the first 5 ticket numbers
		for (int i = 0; i < (NUM_DRAWS - 1); i++) {
			// Try to generate a random number
			do {try_number = generateNumber(FIRST_5_MIN, FIRST_5_MAX);}
			// While it is a duplicate
			while (duplicate(try_number, ticket_numbers, (NUM_DRAWS - 1)));
			// When it's not a duplicate, assign the try to the ticket_numbers array
			ticket_numbers[i] = try_number;
		}
		// Generate the last number (MEGA)
		ticket_numbers[NUM_DRAWS - 1] = generateNumber(LAST_MIN, LAST_MAX);
		// Return an array with the 6 randomly generated ticket numbers
		return ticket_numbers;
	}
	
	
	/**
	 * Method printTicket(int[] arr)
	 * 	Prints a ticket to the console with the format "A B C D E (MEGA: F)", based on an array
	 * @param arr		int[], array with generated numbers
	 */
	public static void printTicket(int[] arr) {
		// Loop to print the first 5 numbers to the console
		for(int i = 0; i < arr.length - 1; i++) {
			System.out.print(arr[i] + " ");
		}
		// Loop to print the last number (MEGA) to the console
		System.out.println("(MEGA: " + arr[arr.length - 1] + ")");
	}
	
	
	/**
	 * Method generateNumber(int min, int max)
	 * 	Generates a random number between the minimum and maximum interval (inclusive)
	 * @param min		int, minimum number to be generated
	 * @param max		int, maximum number to be generated
	 * @return			int, randomly generated number
	 */
	public static int generateNumber(int min, int max) {
		// Return a randomly generated number between min and max (inclusive)
		return randomNumber.nextInt((max - min) + 1) + min; 
	}
	
	
	/**
	 * Method duplicate(int num, int[] arr, int limit)
	 * 	Check if a number already exists inside an array (from first element until limit)
	 *  OBS.: To check if this method is working properly, set class field FIRST_5_MAX = 5. If
	 *   you set it to a less than 5 number (ex: 4), the program will loop infinitely, as it
	 *   needs to generate 5 different numbers in a range of 1 - 4, so the last number will
	 *   always be a duplicate of the previous 4 ones 
	 * @param num		int, number to be checked
	 * @param arr		int[], array with numbers to be compared with num
	 * @param limit		int, number of elements in array to be compared with
	 * @return			boolean, true (if num already exists in array) or false (doesn't exist)
	 */
	public static boolean duplicate(int num, int[] arr, int limit) {
		// Loop through element 0 until limit
		for (int i = 0; i < limit; i++) {
			// Check if num equals to each element. If so, return true.
			if (num == arr[i]) {return true;}
		}
		// If no duplicate was found in the loop, return false
		return false;
	}
	
	
	/**
	 * Method main(String[] args)
	 *  Ask the user how many lottery tickets he/she wants to buy, generate these tickets
	 *   with random numbers, and print them on the console
	 * @param		args, not used
	 */
	public static void main(String[] args) {
		// Ask the user (through console) how many tickets he/she wants to buy
		System.out.println("How many Super Lotto tickets do you want?");
		Scanner keyboard = new Scanner(System.in);	// create a Scanner object to read user input
		int num_tickets = keyboard.nextInt();	// get the number of tickets that the user will buy
		// Loop through number of tickets bought
		for (int i = 0; i < num_tickets; i++) {
			printTicket(generateSuperLottoNoDupes());	// generates each ticket (no duplicates)
			//printTicket(generateSuperLottoNumbers());	// generates each ticket (duplicates)
		}
		keyboard.close();	// close the Scanner object
	}
	
	
}