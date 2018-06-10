import java.util.Scanner;

/***********************************************
 * CISC 190 02/01/2017
 * Student: David
 * Assignment 1 - Part 2
 * This program ask the user name and prints it inside a box of asterisks as an output
*************************************************/

public class ProgramAssignment1 {
	public static void main(String[] args) {
		String userName; 
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter your name:");
		userName = keyboard.nextLine();
		int length = userName.length();
		int i;
		System.out.println("");
		//for(i=0; i)
		System.out.println(new String(new char[length+4]).replace("\0", "*"));
		System.out.println("*" + new String(new char[length+2]).replace("\0", " ") + "*");
		System.out.println("* " + userName + " *");
		System.out.println("*" + new String(new char[length+2]).replace("\0", " ") + "*");
		System.out.println(new String(new char[length+4]).replace("\0", "*"));
	}
}
