/***********************************************
 * CISC 190 02/01/2017
 * Student: David
 * Assignment 1 - Part 1_3
 * This is a practice program to change the original output (HelloWorld_1) to the proposed output 3
 * I found 2 solutions: one below (formatting decimals to 0) and the second one could be changing the value1 type to integer, like this:
 * int value1 = 0;
 * System.out.println("Pass " + i + " = " + value1);
*************************************************/

public class HelloWorld_3 {

/** @param args **/
public static void main(String[] args) {
	int i;
	
	int count;
	double value1 = 0.00;
	String name;
	String firstName, lastName;
	name = "David";
	firstName = "David";
	lastName = "F";
	System.out.println("Hello World");
	System.out.println("My name is "+ name + " " + lastName + "!");
	//System.out.println("This is a Java Programming course.")	
	
	for(i=0; i<=4; i++) {
		System.out.printf("Pass %d = %.0f%n", i, value1);
			++value1;
		}
	}
}