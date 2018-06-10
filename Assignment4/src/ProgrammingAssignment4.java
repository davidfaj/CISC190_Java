/*******************************************************************************
 *																			   *
 * CISC 190 - 03/22/2017													   *
 * David - March 22, 2017
 * 																			   *
 * Brief Description:														   *
 * This is project Programming Assignment 4. It asks the user for a file name and his name.
 *  Then it uses one method to create a file with the user defined file name, write the name inside 
 *  the file and count the number of characters of the user name. Then it uses a 2nd method to read the file, 
 *  get the data from it, count the data number of characters and append date/time to the file. 
 *  Then a 3rd method opens the file, read the data from it, compares the two previous counts of characters 
 *  and return if the comparison passed or failed. Then it prints the results in the console.
 * 																			   *
 * Special Classes:															   *
 * There are no Private or Protected Classes
 * 																			   *
 * Detailed Notes on Code:									
 * 1) I created a method to set the COMPLETE file name, and it also checks the operational system (Mac or Win)
 * 	to define the folder name
 * 2) I didn't use the constants PASS and FAIL like in the example, talked to the teacher in the class, 
 * 	who told they are not mandatory. I used strings inside the 3rd method, instead of it, and it works.
 * 3) IOException: as the program is dealing with files, it needs to deal with exceptions, in this case IO ones.
 * 4) I used Date and SimpleDateFormatlibraries to get and format date and time more visually friendly.
 * 5) File, FileWriter, PrintWriter, Scanner classes to deal with file reading and writing.
 * 6) File.exists() and .isDirectory(), to check if file and directory exist.
 * ****************************************************************************/

// Import libraries needed to:
import java.io.*;					// :work with files
import java.util.Scanner;			// :read input
import java.util.Date;				// :get date and time
import java.text.SimpleDateFormat;	// :format date and time

// Declare class ProgrammingAssignment4
public class ProgrammingAssignment4 {

	/**
	 * Method main
	 * 		Ask the user for a file name, use the 4 methods, print the result to the screen
	 * @param		args unused
	 * @return		nothing
	 * @exception	IOException on IO error
	 */
	public static void main (String[] args) throws IOException {
		
		// Define variables
		Scanner keyboard = new Scanner(System.in);	// to read input from the keyboard
		String filename;							// to save the filename
		
		// Ask the user a file name
		System.out.print("(main) Hello, please type a name for the file: ");
		filename = setFolderFile(keyboard.nextLine());	// use method setFolderFile()
														// to define the folder that the file will be saved
		
		// Call the 3 methods
			// OpenWriteCloseFile(): create a file write to it, close it AND assign the return value to a variable
			int userNameNumChars = OpenWriteCloseFile(filename);
			// OpenAppendCloseFile(): read the file, append data to it, close it AND assign the return value to a variable
			int fileDataNumChars = OpenAppendCloseFile(filename);
			// OpenPrintCloseFile(): read the file, append to it, close it AND assign the return value to a variable
			String result = OpenPrintCloseFile(filename, userNameNumChars, fileDataNumChars);
		
		// Print to screen whether the program passed or failed
		System.out.println("(main) The program result is " + (result == "PASS" ? "PASS" : "FAIL") + 
				" because the number of characters typed in the keyboard is " + userNameNumChars + 
				" and the number of characters read inside the file is " + fileDataNumChars + " .");
		
		// Close the keyboard
		keyboard.close();
		
		}
		
	
	/**
	 * Method OpenWriteCloseFile
	 * 		Open a file set by user, overwrite it, ask and get user name, write it to the file, determine number
	 * 		of characters of it and close the file
	 * @param filename				String, the user defined filename
	 * @return userNameNumChars		int, number of characters of the user name read by the keyboard
	 * @exception					IOException on IO error
	 */
	public static int OpenWriteCloseFile(String filename) throws IOException {
		
		// Open a file (filename) and overwrite any other file with same name
		PrintWriter outputFile = new PrintWriter(filename);
		System.out.println("(OpenWriteCloseFile) The file " + filename + 
				" was created (or overwritten, if there was a previous version of it)");	// debugging print to console

		// Ask the user for their name
		Scanner keyboard = new Scanner(System.in);	// to read input from the keyboard
		System.out.print("(OpenWriteCloseFile) What is your name? ");
		String userName = keyboard.nextLine();		// get the user input
		
		// Write this name to the file
		outputFile.println(userName);
		
		// Determine the number of characters and print that value to the screen
		int userNameNumChars = userName.length();
		System.out.println("(OpenWriteCloseFile) " + userName + ", your name has " + 
				userNameNumChars + " characters and was written to the file");
		
		//Close the file and the keyboard
		outputFile.close();
		keyboard.close();
		
		//Return number of characters;
		return userNameNumChars;
	}
	
	
	/**
	 * Method OpenAppendCloseFile
	 * 		Open the file created by user, read the data and count the number of characters, append 
	 * 		current date and time to file, close the file, print the name read and its number of characters
	 * @param filename			String, the user defined filename
	 * @return numCharData		int, number of characters of the user name read inside the file
	 * @exception				IOException on IO error
	 */
	public static int OpenAppendCloseFile(String filename) throws IOException {
		
		// Check if the file exists. If not, exit program
		File userFile = new File(filename);
		if (!userFile.exists()) {
			System.out.println("(OpenAppendCloseFile) The file" + filename + 
					" doesn't exist, the program will end!");
			System.exit(0);
		}
		
		// Open the file and allow for appending data
		FileWriter fWrite = new FileWriter(filename, true);
		PrintWriter outputFile = new PrintWriter(fWrite);
		
		// Read the data and count the number of characters
		Scanner fRead = new Scanner(userFile);
		String data = "";						// set data as "" initially to concatenate more data to it
		while (fRead.hasNext()) {				// while there is more data, read it and concatenate to data
			data = data + fRead.nextLine();
		}
		int numCharData = data.length();		// count the number of characters of data
		
		// Append the current date and time to the file
		Date currentDateTime = new Date();		// get current date and time
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");	// set a format for date and time
		String formattedDate = dateFormat.format(currentDateTime);		// create a string with the formatted date and time
		outputFile.println(formattedDate);		// append the string to the file
		System.out.println("(OpenAppendCloseFile) Current date (" + formattedDate + 
				") was appended to the file");	// debugging print to console
		
		// Close the file
		outputFile.close();
		fWrite.close();
		fRead.close();
		
		// Print the name you read above and the number of characters
		System.out.println("(OpenAppendCloseFile) The name inside the file " + filename + 
				" is " + data + " and its number of characters is " + numCharData);
		
		// Return the number of characters you just determined
		return numCharData;
	}
	
	
	/**
	 * Method OpenPrintCloseFile()
	 * 		Open the file for appending data, compare the 2 lengths (lenWritten to lenAppended), write PASS or FAIL 
	 * 		according to the comparison (true or false), read the file, print the result to the screen and close the file 
	 * @param filename			String, the user defined filename
	 * @param lenWritten		int, user name length read in the keyboard 
	 * @param lenAppended		int, user name length read inside the appended file
	 * @return flag				String, number of characters of the user name read by the keyboard
	 * @exception				IOException on IO error
	 */
	public static String OpenPrintCloseFile(String filename, int lenWritten, int lenAppended) throws IOException {
		
		// Check if the file exists. If not, exit program
		File userFile = new File(filename);
		if (!userFile.exists()) {
			System.out.println("(OpenPrintCloseFile) The file " + filename + 
					" doesn't exist, the program will end!");
			System.exit(0);
		}
		
		// Open the file and allow for appending data
		FileWriter fWrite = new FileWriter(filename, true);
		PrintWriter outputFile = new PrintWriter(fWrite);

		// Compare the two lengths you just passed if equal consider a PASS else it is FAIL
		String flag = (lenWritten == lenAppended ? "PASS" : "FAIL");	// set flag to PASS or FAIL
		
		// Write these results to the existing file
		outputFile.println(flag);
		fWrite.close();			// need to close to save what was written to the file
		outputFile.close();		// need to close to save what was written to the file
				
		// Read the file and print the result to the screen
		Scanner fRead = new Scanner(userFile);
		String data = "";				// set data as "" initially to concatenate more data to it
		while (fRead.hasNext()) {		// while there is more data, read it and concatenate to data
			data = data + fRead.nextLine() + "\n";	// add a NEWLINE to be print more user friendly 
		}
		System.out.println("(OpenPrintCloseFile) The file " + filename + " contains the following data:\n" + data);
		
		// Close the file
		fRead.close();
		
		// Return pass or fail flag
		return flag;
	}

	
	/**
	 * Method setFolderFile
	 * 		Check if folder exists in PC or Mac, and returns the COMPLETE filename with the folder and file name
	 * @param filename			String, the user defined filename
	 * @return fileFolderName	String, the complete file name (folder + file name)
	 * @exception				IOException on IO error
	 */
	public static String setFolderFile(String filename) throws IOException {

		// Check the user operational system
		String opSys = System.getProperty("os.name");
		opSys = opSys.substring(0, 3);		// get just the first 3 characters, to compare with "Mac" or "Win"
		
		// Define directory name according to operational System (Mac or Win)
		String dirName = opSys.equals("Mac") ? "./CISC190/" : "C:\\CISC190\\";
		
		// Check if directory exists. If not, ask user to create it and end program
		File dir = new File(dirName);
		if(!dir.isDirectory()) {
			System.out.println("(setFolderFile) The program will end. Please create a folder called " + 
					dirName + " and run the program again.");
			System.exit(0);
		}
		
		// Create a string with the COMPLETE file name (folder + file)
		String fileFolderName = dirName + filename;
		
		// Return the COMPLETE file name
		return fileFolderName;
	}		
		
		
}