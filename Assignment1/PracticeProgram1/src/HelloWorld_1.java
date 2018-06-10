/***********************************************
 * CISC 190 02/01/2017
 * Student: David
 * Assignment 1 - Part 1_1
 * This is a practice program to print the original output
*************************************************/

public class HelloWorld_1 {

/** @param args **/
public static void main(String[] args) {
	int i;
	int count;
	double value1 = 0.00;
	String name;
	String firstName, lastName;
	name = "Snoopy";
	firstName = "Dr. Evil";
	lastName = "Mini Me";
	System.out.println("Hello class");
	System.out.println("My name is "+ name);
	//System.out.println("This is a Java Programming course.")	
	
	for(i=0; i<=3; i++) {
		System.out.print("Pass "+ i);		
		System.out.println(" = " + value1);
			++value1;
		}
	}
}