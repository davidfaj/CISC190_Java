/************************************************************************************
 * 																					*
 * CISC 190 - 02/28/2017															*
 * David - February 28, 2017
 * 																					*
 * Brief Description:																*
 * This is project Programming Assignment 3. It creates a coffee shop, which prompts the user the name, how many cups of coffee and payment method. Then it calculates the total value to be paid including taxes, and dependind on the user's answers, send different messages.
 * 																					*
 * Special Classes:																	*
 * There are no Private or Protected Classes
 * 																					*
 * Detailed Notes on Code:									
 * 1) System.exit(0): used to exit fully the program (not like break, for exiting loops)
 * 2) Switch: used to create conditional code, according to specific cases (each "case" inside the switch) and a "default" case if the previous specific cases weren't met
 * 3) charAt(): used to get the first character of the payment method typed by the client. As I'm getting the user input through nextLine(), this method returns a String, so I used charAt(0) to get the char at position 0 of that string, which is the first position / first typed letter   
 * 4) && || operators: used to check more than one combined conditions 
 * 5) Used and explained in previous exercises: Scanner, nextInt(), DecimalFormat, printf
 * ************************************************************************************/
import java.util.Scanner;		// Needed for the scanner class
import java.text.DecimalFormat;	// Needed for the DecimalFormat

public class CupsOfCoffee {
	public static void main (String[] args) {
		
		// Set the variables to be used and their data types
		int numCups;
		char payMethod;
		String firstName;
		double subTotal;
		double tax;
		double total;
		double cupPrice = 2.00;
		final double TAX_RATE = 0.08;
		
		// Create a Scanner object to read input.
		Scanner keyboard = new Scanner(System.in);
		
		// Print a welcome message to the user
		System.out.println("Welcome to Cups of Coffee!");
		
		// Print the cup of coffee price
		String pattern = "$.00";	// used to format the price
		DecimalFormat decimalFormat = new DecimalFormat(pattern);	// used to format the price
		System.out.println("Cup of Coffee: " + decimalFormat.format(cupPrice));
		
		// Ask the user the first name
		System.out.print("What is your first name? ");
		
		// Get the user name
		firstName = keyboard.nextLine();
		
		// Ask the user how many cups of coffee will be ordered
		System.out.print("Hey " + firstName + ", how many cups of coffee would you like to order? ");
		
		// Get number of cups
		numCups = keyboard.nextInt();
		
		// If the user asks for less than 1 cup, send him/her out of the store and exit the program
		if(numCups < 1) {
			System.out.println("Get out of my store, you bum!");
			System.exit(0);
		}
		
		// Calculate the detailed amount to be paid
		subTotal = numCups * cupPrice;
		tax = subTotal * TAX_RATE; 
		total =  subTotal + tax;
		
		// Display the detailed amount to be paid
		System.out.printf("\nSubtotal: $%.2f", subTotal);
		System.out.printf("\nTax: $%.2f", tax);
		System.out.printf("\nTotal: $%.2f\n", total);
		
		// Ask the payment method and show the methods available
		System.out.print("How will you be paying, " + firstName + "? (Enter 'm' for money, 'c' for credit card, 's' for store credit or 'g' for gold): ");
		
		// Consume the remaining newline
		keyboard.nextLine();
		
		// Get the payment method
		payMethod = keyboard.nextLine().charAt(0);
		
		// Switch statement to display the payment type 
		switch (payMethod) {
		case ('m'):
			System.out.printf("** You are paying $%.2f using money.\n", total);
		break;
		case ('M'):
			System.out.printf("** You are paying $%.2f using money.\n", total);
			break;
		case ('c'):
			System.out.printf("** You are paying $%.2f by Credit Card.\n", total);
			break;
		case ('C'):
			System.out.printf("** You are paying $%.2f by Credit Card.\n", total);
			break;
		case ('s'):
			System.out.printf("** You are paying $%.2f with Store Credit.\n", total);
			break;
		case ('S'):
			System.out.printf("** You are paying $%.2f with Store Credit.\n", total);
			break;
		case ('g'):
			System.out.printf("** You are paying $%.2f with Gold.\n", total);
			break;
		case ('G'):
			System.out.printf("** You are paying $%.2f with Gold.\n", total);
			break;
		default:
			System.out.println("** Sorry, this type of payment is not accepted here.");
			System.exit(0);
		}
		
		// Display some final messages according to the assignment specific cases
		if( (total<10) && (payMethod == 'c') || (payMethod == 'C') ) {
			System.out.println("** Sorry, there is a $10.00 minimum purchase for credit card");
		}
		else if ( (total>=10) && (total<=100) ) {
			System.out.println("** Thank you, you earned a free glass of Lemonade!");
		}
		else if ( (numCups>50) || (total>100) ) {
			System.out.println("** I'm going to Disneyland!");
		}
	
	// Display a good bye message 
	System.out.println("Thanks " + firstName + ", and have a nice day!");

	// Exit the program
	System.exit(0);
	}

}