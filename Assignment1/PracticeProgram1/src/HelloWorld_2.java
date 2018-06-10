/***********************************************
 * CISC 190 02/01/2017
 * Student: David
 * Assignment 1 - Part 1_2
 * This is a practice program to change the original output (HelloWorld_1) to the proposed output 2
*************************************************/

public class HelloWorld_2 {

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
	System.out.println("My name is "+ name + "!");
	//System.out.println("This is a Java Programming course.")	
	
	for(i=0; i<=3; i++) {
		System.out.printf("Pass %d = %.2f%n", i, value1);
			++value1;
		}
	}
}