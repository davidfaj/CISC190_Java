/*******************************************************************************
 *																			   *
 * CISC 190 - 04/15/2017													   *
 * David - April 15, 2017
 * 																			   *
 * Brief Description:														   *
 * This is project Programming Assignment 5. It creates the SecurityCipher class
 * based on the UML given by the teacher. It's a class used for encryption and
 * decryption. It has 6 private fields, 3 constructors and many methods.
 * The methods are getters, setters, encryption (4 types), decryption (4 types),
 * and some auxiliary methods (like to print, write to file, compare, etc.).  
 * 																			   *
 * Special Classes:															   *
 * There are no Private or Protected Classes
 * 																			   *
 * Detailed Notes on Code:									
 * 1) I created method coPrimes() to check if 2 numbers are coprimes (used in AFFINE encryption)
 * 2) I created a method messageToFile() to write any string to a file. So, all encryption
 * 		methods prints the encrypted message to the file "encryptedMessage.txt", while
 * 		all decryption methods prints the decrypted message to the file "decryptedMessage.txt".
 * 3) As confirmed with teacher in class, I didn't create the methods setAlphaLenght and
 * 		getAlphaLength (defined in UML); the field "key" (defined in UML) is not used.  
 * ****************************************************************************/

// Import libraries needed to:
import java.io.*;					// :work with files
import java.util.Scanner;			// :read input


// Declare class SecurityCipher
public class SecurityCipher {
	
	/**********
	 * FIELDS *
	 **********/

	private String key;					// not used (see note 3)
	private int diameter;				// diameter for Scytale encryption
	private String origMessage;			// original message to be encrypted
	private String cipherMessage;		// encrypted message 
	private String decryptedMessage;	// decrypted message
	private int alphabet = 26;			// number of characters in alphabet (A to Z)
	
	
	/****************
	 * CONSTRUCTORS *
	 ****************/

	/**
	 * Constructor SecurityCipher()
	 * 		No parameters set by user. Initialize all fields. 
	 */
	public SecurityCipher() {
		key = "";
		diameter = 0;
		origMessage = "My name is David";
		cipherMessage = "";
		decryptedMessage = "";
	}


	/**
	 * Constructor SecurityCipher(String message, String k, int diam)
	 * 	User set 3 parameters. Initialize the other ones.
	 * @param message	String, the original message to be encrypted
	 * @param k			String, the key value 
	 * @param diam		int, the diameter in Scytale encryption
	 */
	public SecurityCipher(String message, String k, int diam) {
		origMessage = message;
		key = k;
		diameter = diam;
		origMessage = message;
		cipherMessage = "";
		decryptedMessage = "";
	}
	
	
	/**
	 * Constructor SecurityCipher(File fullFileName, String k, int diam)
	 * 	User set 3 parameters. Initialize the other ones.
	 * @param fullFileName	File, the text file with the original message to be encrypted
	 * @param k				String, the key value 
	 * @param diam			int, the diameter in Scytale encryption
	 * @exception			IOException on IO error
	 */
	public SecurityCipher(File fullFileName, String k, int diam) throws IOException {
		key = k;
		diameter = diam;
		cipherMessage = "";
		decryptedMessage = "";
		// Read origMessage from the file
			// Check if the file exists. If not, exit constructor.
			File userFile = fullFileName;
			if (!userFile.exists()) {
				System.out.println("(System ERROR message: The file doesn't exist)");
				System.exit(0);
			}
			// Read the message inside the file
			Scanner fRead = new Scanner(userFile);
			// Set origMessage as "" initially to concatenate more data to it
			origMessage = "";
			// While there is more data, read it and concatenate to origMessage
			while (fRead.hasNext()) {
				origMessage += fRead.nextLine();
			}
			// Close the file
			fRead.close();
	}
	

	/***********
	 * METHODS *
	 ***********/

	/**
	 * Method EncryptionCipher()
	 * 	Encrypts a hard-coded message using the TENIS POLAR encryption. Returns the encrypted string
	 * 	to the user, after writing it in the file "encryptedMessage.txt".
	 * @return cipherMessage	String, encrypted message
	 * @exception				IOException on IO error
	 */
	public String EncryptionCipher() throws IOException {
		// Encryption: TENIS POLAR
		// This is a very simple encryption code, I remember it from a Brazilian book that I read
		// as a kid ("The obedience drug").
		// Description: Simply change the letter from the word "TENIS" to "POLAR" in the same order they appear, and vice-versa (example: T for P, P for T, and so on)
		// Algorithm: a simple if-then-else (if original is 'T', encrypted is 'P', ...) 
		// I also change all original characters to upper case, to limit the characters from A to Z (65 to 90)
		char enc;	// define encrypted character
		// Loop through each letter of the original message
		for (int i = 0; i < origMessage.length(); i++) {
			// Set enc initially as the same original character in upper case
			enc = origMessage.toUpperCase().charAt(i);
			// Execute an IF-ELSE to change enc to the correspondent encrypted character
			if(enc == 'T') enc = 'P';
			else if(enc == 'E') enc = 'O';
			else if(enc == 'N') enc = 'L';
			else if(enc == 'I') enc = 'A';
			else if(enc == 'S') enc = 'R';
			else if(enc == 'P') enc = 'T';
			else if(enc == 'O') enc = 'E';
			else if(enc == 'L') enc = 'N';
			else if(enc == 'A') enc = 'I';
			else if(enc == 'R') enc = 'S';
			// Concatenate enc to the encrypted message
			cipherMessage += enc;
		}
		// Write the encrypted message inside the "encryptedMessage.txt" file
		File userFile = new File("encryptedMessage.txt");
		messageToFile(userFile, cipherMessage);
		// Return the encrypted message
		return cipherMessage;
	}
	
	
	/**
	 * Method EncryptionCipher(int diam)
	 * 	Encrypts a message using the ATBASH encryption. Returns the encrypted string to the
	 * user, after writing it in the file "encryptedMessage.txt".
	 * @param diam				int, diameter set by user
	 * @return cipherMessage	String, encrypted message
	 * @exception				IOException on IO error
	 */
	public String EncryptionCipher(int diam) throws IOException {
		// Encryption: ATBASH
		// Description: It works by substituting the first letter of an alphabet for the last
		// letter, the second letter for the second to last, and so on, effectively reversing
		// the alphabet.
		// Algorithm: Considering that we have an original character ("original"); and that the
		// lower limit ("floor") of the alphabet we are using is the character A (char 65), and
		// the upper limit ("ceil") is Z (char 90), the encrypted character ("enc") formula is:
		// enc = (ceil - (original - floor))
		// I also change all original characters to upper case, to limit the characters from A to Z (65 to 90)
		char original, enc;				// define original and encrypted characters
		char floor = 65, ceil = 90;		// initialize floor and ceiling of the alphabet
		// Loop through each letter of the original message
		for (int i = 0; i < origMessage.length(); i++) {
			// Set original as the same original character in upper case
			original = origMessage.toUpperCase().charAt(i);
			// If char 32 (space), encrypted is also space (32). Else, formula result.
			enc = (original == 32) ? 32 : (char) (ceil - (original - floor));
			// Concatenate enc to the encrypted message
			cipherMessage += enc;
		}
		// Write the encrypted message inside the "encryptedMessage.txt" file
		File userFile = new File("encryptedMessage.txt");
		messageToFile(userFile, cipherMessage);
		// Return the encrypted message
		return cipherMessage;
	}
	
	
	/**
	 * Method EncryptionCipher(int diam, String k)
	 * 	Encrypts a message using the SCYTALE encryption. Returns the encrypted string to the
	 * user, after writing it in the file "encryptedMessage.txt".
	 * @param diam				int, diameter of the Scytale stick
	 * @param k					String, not used (see note 3) 
	 * @return cipherMessage	String, encrypted message
	 * @exception				IOException on IO error
	 */
	public String EncryptionCipher(int diam, String k) throws IOException {
		// Encryption: SCYTALE
		// Description: To encrypt a message, wrap a strip around a stick with diameter "diam",
		// and write the message in the wrapped strip, from left to right and then up-down each
		// time that you reach the end of the stick. Remove the strip and the message is encrypted.
		// To decrypt, just wrap the strip around a stick with the same diameter "diam" and read it
		// from left to right and up-down each time that you reach the end of the stick.
		/* Algorithm:
		 * 	1) Calculate the number of characters in the original message
		 * 	2) Calculate the number of columns "numCols" in the Scytale, which is the number of 
		 *  wraps of the strip. This must be an integer number, so it's rounded up in case it's
		 *  not a whole number.
		 * 	3) The Scytale can be seen as a matrix of "diam" rows by "numCols" columns. In order
		 *  to work, all cells (row,column) must have a character. So, if needed, I fill the last
		 *  cells with spaces (char 32).
		 * 	4) Encrypt the message just by transposing the original matrix and reading each
		 * character in the same order (left to right and up-down in the end of each line). The
		 * position of each character inside the message can be discovered by using the formula:
		 * charPosition = X + Y*numColumns
		 * NOTE: as this encryption method only scrambles the letter according to a logic, there
		 * is no need to change the letters to upper case.
		 */
		int numChars, numCols;		// define number of characters and columns
		char original, enc;			// define original and encrypted characters
		// Get the original message number of chars
		numChars = origMessage.length();
		// Calculate the number of columns. If not an integer, round it up to the next integer
		numCols = (int)Math.ceil((double)numChars/diam);
		// Calculate the new number of chars in the message, as we need to fill up with spaces
		// (char 32) to complete the full Scytale strip
		int newNumChars = diam * numCols;
		// Calculate the number of spaces needed in the end
		int numSpaces = newNumChars - numChars; 
		// Fill the original message with spaces (char 32)
		String origMessageEndSpaces = origMessage;
		for (int i = 0; i < numSpaces; i++) {
			origMessageEndSpaces += (char)32;
		}
		// Encrypt the message (which already contains trailing spaces)
			// Transpose the matrix (rows = diam ; columns = numCols) by inverting the rows to
			// columns and vice-versa
			for (int col = 0; col < numCols; col++) {
				for (int row = 0; row < diam; row++){
					// Read each specific character by using the formula
					enc = origMessageEndSpaces.charAt(col + row*numCols);
					// Concatenate enc to the encrypted message
					cipherMessage += enc;
				}
			}

		/* Draw the Scytale
		 * I draw the original matrix in order to check if the encryption worked. In this case,
		 * to read the encrypted message, just read the characters from up-down and left to right
		 * when it reaches the end of each column.
		 */
		System.out.println("\nSCYTALE drawing (for checking purposes)");
		// Scytale upper border
		for(int col = 0; col < (numCols*2 + 1); col++) {
			System.out.print("-");
		}
		System.out.print("\n");
		// Scytale body 
		int i = 0;
		for (int row = 0; row < diam; row++) {
			for (int col = 0; col < numCols; col++){
		System.out.print("|" + origMessageEndSpaces.charAt(i++));
			}
			System.out.println("|");
		}
		// Scytale lower border
		for(int col = 0; col < (numCols*2 + 1); col++) {
			System.out.print("-");
		}
		System.out.println("\n");
		
		// Write the encrypted message inside the "encryptedMessage.txt" file
		File userFile = new File("encryptedMessage.txt");
		messageToFile(userFile, cipherMessage);
		// Return the encrypted message
		return cipherMessage;
	}
	
	
	/**
	 * Method EncryptionCipher(int num1, int num2)
	 * 	Encrypts a message using the AFFINE encryption. Returns the encrypted string to the
	 * user, after writing it in the file "encryptedMessage.txt".
	 * @param num1				int, is "a" in the function (aX + b)
	 * @param num2				int, is "b" in the function (aX + b)
	 * @return cipherMessage	String, encrypted message
	 * @exception				IOException on IO error
	 */
	public String EncryptionCipher(int num1, int num2) throws IOException {
		// Encryption: AFFINE
		// Description: Each alphabet character is encrypted by using the function:
		// "y = (a*x + b) mod alphabet". As y is the encrypted letter, to decrypt, just use the
		// same formula and calculate x, which is the original letter.
		// Algorithm: Simply calculate each encrypted/decrypted letter by using the formula above.
		// "alphabet" is the number of characters in the alphabet (26). "a" must be coprime with
		// alphabet. "b" is the magnitude of the shift. num1 will be assigned to "a" and num2 will
		// be assigned to "b". The number 65 is added or subtracted because "A" is 65 in the ASCII
		// table, while in the encryption alphabet, we start as "A" being 0, and so on.
		
		// Check if num1 is coprime with alphabet, more than 0 and less than alphabet. If not,
		// exit the method (the encryption wouldn't work), returning a system error message
		String errorMessage = "(System ERROR message: num1 must be: (1) > 0; (2) < " + alphabet 
				+ ";  coprime with "+alphabet + ")";	// set an error message
		if(!coPrimes(num1, num2)) {			// Check if num1 is coprime with alphabet
			cipherMessage = errorMessage;
			return cipherMessage;
		}
		if(num1 <= 0) {						// Check if num1 is more than 0
			cipherMessage = errorMessage;
			return cipherMessage;
		}
		if(num1 > alphabet) {				// Check if num1 less than alphabet
			cipherMessage = errorMessage;
			return cipherMessage;
		}
		
		char original, enc;		// define original and encrypted characters
		// Encrypt the message
			// Loop through each letter of the original message
			for (int i = 0; i < origMessage.length(); i++) {
				// Set original to upper case and minus 65
				original = (char) (origMessage.toUpperCase().charAt(i) - 65);
				// Set enc using the formula and adding 65
				enc = (char) (((num1 * (int)original + num2) % alphabet) + 65);
				// Concatenate enc to the encrypted message
				cipherMessage += enc;
			}
		// Write the encrypted message inside the "encryptedMessage.txt" file
		File userFile = new File("encryptedMessage.txt");
		messageToFile(userFile, cipherMessage);
		// Return the encrypted message
		return cipherMessage;
	}
	
	
	/**
	 * Method DecryptionCipher()
	 * 	Decrypts a message using the TENIS POLAR decryption. Returns the decrypted string
	 * 	to the user, after writing it in the file "decryptedMessage.txt".
	 * @return decryptedMessage		String, decrypted message
	 * @exception					IOException on IO error
	 */
	public String DecryptionCipher() throws IOException {
		// Encryption: TENIS POLAR
		// This is a very simple encryption code, I remember it from a Brazilian book that I read
		// as a kid ("The obedience drug").
		// Description: Simply change the letter from the word "TENIS" to "POLAR" in the same order they appear, and vice-versa (example: T for P, P for T, and so on)
		// Algorithm: a simple if-then-else (if original is 'T', encrypted is 'P', ...)
		// I also change all original characters to upper case, to limit the characters from A to Z (65 to 90)
		char dec;	// define decrypted character
		// Loop through each letter of the encrypted message
		for (int i = 0; i < cipherMessage.length(); i++) {
			// Set dec initially as the same encrypted character in upper case
			dec = cipherMessage.toUpperCase().charAt(i);
			// Execute an IF-ELSE to change dec to the correspondent decrypted character
			if(dec == 'T') dec = 'P';
			else if(dec == 'E') dec = 'O';
			else if(dec == 'N') dec = 'L';
			else if(dec == 'I') dec = 'A';
			else if(dec == 'S') dec = 'R';
			else if(dec == 'P') dec = 'T';
			else if(dec == 'O') dec = 'E';
			else if(dec == 'L') dec = 'N';
			else if(dec == 'A') dec = 'I';
			else if(dec == 'R') dec = 'S';
			// Concatenate dec to the decrypted message
			decryptedMessage += dec;
		}
		// Write the decrypted message inside the "decryptedMessage.txt" file
		File userFile = new File("decryptedMessage.txt");
		messageToFile(userFile, decryptedMessage);
		// Return the decrypted message
		return decryptedMessage;
	}
	
	
	/**
	 * Method DecryptionCipher(int diam)
	 * 	Decrypts a message using the ATBASH decryption. Returns the decrypted string to the
	 * user, after writing it in the file "decryptedMessage.txt".
	 * @param diam					int, diameter set by user
	 * @return decryptedMessage		String, decrypted message
	 * @exception					IOException on IO error
	 */
	public String DecryptionCipher(int diam) throws IOException {
		// Encryption: ATBASH
		// Description: It works by substituting the first letter of an alphabet for the last
		// letter, the second letter for the second to last, and so on, effectively reversing
		// the alphabet.
		// Algorithm: Considering that we have an original character ("original"); and that the
		// lower limit ("floor") of the alphabet we are using is the character A (char 65), and
		// the upper limit ("ceil") is Z (char 90), the encrypted character ("enc") formula is:
		// enc = (ceil - (original - floor)). Decryption works in the same way, just change
		// "original" by "decrypted".
		// I also change all original characters to upper case, to limit the characters from A to Z (65 to 90)
		char enc, dec;					// define encrypted and decrypted characters
		char floor = 65, ceil = 90;		// initialize floor and ceiling of the alphabet
		// Loop through each letter of the encrypted message
		for (int i = 0; i < cipherMessage.length(); i++) {
			// Set enc as the same encrypted character in upper case
			enc = cipherMessage.toUpperCase().charAt(i);
			// If char 32 (space), encrypted is also space (32). Else, formula result.
			dec = (enc == 32) ? 32 : (char) (ceil - (enc - floor));
			// Concatenate enc to the encrypted message
			decryptedMessage += dec;
		}		
		// Write the decrypted message inside the "decryptedMessage.txt" file
		File userFile = new File("decryptedMessage.txt");
		messageToFile(userFile, decryptedMessage);
		// Return the decrypted message
		return decryptedMessage;
	}
	
	
	/**
	 * Method DecryptionCipher(int diam, String k)
	 * 	Decrypts a message using the SCYTALE decryption. Returns the decrypted string to the
	 * user, after writing it in the file "decryptedMessage.txt".
	 * @param diam				int, diameter of the Scytale stick
	 * @param k					String, not used (see note 3) 
	 * @return decryptedMessage	String, decrypted message
	 * @exception				IOException on IO error
	 */
	public String DecryptionCipher(int diam, String k) throws IOException {
		// Encryption: SCYTALE
		// Description: To encrypt a message, wrap a strip around a stick with diameter "diam",
		// and write the message in the wrapped strip, from left to right and then up-down each
		// time that you reach the end of the stick. Remove the strip and the message is encrypted.
		// To decrypt, just wrap the strip around a stick with the same diameter "diam" and read it
		// from left to right and up-down each time that you reach the end of the stick.
		/* Algorithm:
		 * 	1) Calculate the number of characters in the original message
		 * 	2) Calculate the number of columns "numCols" in the Scytale, which is the number of 
		 *  wraps of the strip. As we are transposing the matrix, now the number number of columns
		 *  is the original diameter.
		 * 	3) Decrypt the message just by transposing the encrypted matrix and reading each
		 * character in the same order (left to right and up-down in the end of each line). The
		 * position of each character inside the message can be discovered by using the formula:
		 * charPosition = X + Y*numColumns
		 * NOTE: as this encryption method only scrambles the letter according to a logic, there
		 * is no need to change the letters to upper case.
		 */
		int numChars, numCols;	// define number of characters and columns
		char dec;			// define decrypted character
		// Get decrypted message number of chars
		numChars = cipherMessage.length();
		// As the matrix is transposed, now the the number of columns is the original diam
		numCols = diam;
		// And the diameter (number of rows) is the number of chars divided by current diameter
		int diamTransposed = (int)((double)numChars/diam);  
		// Decrypt the encrypted message
			// Transpose the matrix (rows = diam ; columns = numCols) by inverting the rows to
			// columns and vice-versa
			for (int col = 0; col < numCols; col++) {
				for (int row = 0; row < diamTransposed; row++){
					// Read each specific character by using the formula
					dec = cipherMessage.charAt(col + row*numCols);
					// Concatenate dec to the encrypted message
					decryptedMessage += dec;
				}
			}

		// Remove the trailing spaces from the decrypted message, to match the original, using
		// the REGEX: remove 1 or more space from the end
		decryptedMessage = decryptedMessage.replaceFirst("\\s++$", "");
		
		// Write the decrypted message inside the "decryptedMessage.txt" file
		File userFile = new File("decryptedMessage.txt");
		messageToFile(userFile, decryptedMessage);
		// Return the decrypted message
		return decryptedMessage;
	}
	
	
	/**
	 * Method DecryptionCipher(int num1, int num2)
	 * 	Decrypts a message using the AFFINE decryption. Returns the decrypted string to the
	 * user, after writing it in the file "decryptedMessage.txt".
	 * @param num1					int, is "a" in the function (aX + b)
	 * @param num2					int, is "b" in the function (aX + b)
	 * @return decryptedMessage		String, decrypted message
	 * @exception					IOException on IO error
	 */
	public String DecryptionCipher(int num1, int num2) throws IOException {
		// Encryption: AFFINE
		// Description: Each alphabet character is encrypted by using the function:
		// "y = (a*x + b) mod alphabet". As y is the encrypted letter, to decrypt, just use the
		// same formula and calculate x, which is the original letter.
		// Algorithm: Simply calculate each encrypted/decrypted letter by using the formula above.
		// "alphabet" is the number of characters in the alphabet (26). "a" must be coprime with
		// alphabet. "b" is the magnitude of the shift. num1 will be assigned to "a" and num2 will
		// be assigned to "b". The number 65 is added or subtracted because "A" is 65 in the ASCII
		// table, while in the encryption alphabet, we start as "A" being 0, and so on.

		// Check if num1 is coprime with alphabet, more than 0 and less than alphabet. If not,
		// exit the method (the encryption wouldn't work), returning a system error message
		String errorMessage = "(System ERROR message: num1 must be: (1) > 0; (2) < " + alphabet 
				+ ";  coprime with "+alphabet + ")";		// set an error message
		if(!coPrimes(num1, num2)) {					// Check if num1 is coprime with alphabet
			decryptedMessage = errorMessage;
			return decryptedMessage;
		}
		if(num1 <= 0) {								// Check if num1 is more than 0
			decryptedMessage = errorMessage;
			return decryptedMessage;
		}
		if(num1 > alphabet) {						// Check if num1 less than alphabet
			decryptedMessage = errorMessage;
			return decryptedMessage;
		}
		
		char dec, enc;		// define original and encrypted characters
		// Decrypt the message
			// Loop through each letter of the encrypted message
			for (int i = 0; i < cipherMessage.length(); i++) {
				// Set enc to upper case and minus 65
				enc = (char) (cipherMessage.toUpperCase().charAt(i) - 65);
				// Set dec using the formula and adding 65
				// NOTE: to eliminate negative results in the modulo formula, I break down the
				// original formula in pieces, using a temp variable and the concept that:
				// n mod y: (n % y + y) % y
					// This is the main formula before doing the mod
					int temp = ((alphabet - num1) * (enc - num2));
					temp = ((temp % alphabet + alphabet) % alphabet);
					dec = (char) (temp + 65);	// add 65 to offset to ASCII table
				// Concatenate enc to the encrypted message
				decryptedMessage += dec;
			}
		// Write the decrypted message inside the "decryptedMessage.txt" file
		File userFile = new File("decryptedMessage.txt");
		messageToFile(userFile, decryptedMessage);
		// Return the decrypted message
		return decryptedMessage;
	}
	
	
	/**
	 * Method Compare(String message1, String message2)
	 * 	Compare message1 to message2. Returns true if the comparison is true, or false if the
	 * 	comparison is false.
	 * @param message1			String, initial message to be compared
	 * @param message2			String, second message to be compared to the initial
	 * @return Boolean			Boolean, true or false
	 */
	public boolean Compare(String message1, String message2) {
		// Comparing both in upper case
		return message1.toUpperCase().equals( message2.toUpperCase() );
	}
	
	
	/**
	 * Method Compare(File file1, File file2)
	 * 	Compare a message inside File1 to a message inside File2. Returns true if the comparison
	 * 	is true, or false if the comparison is false.
	 * @param file1			File, initial file with message to be compared
	 * @param file2			File, second file with message to be compared to the initial
	 * @return Boolean		Boolean, true or false
	 * @exception			IOException on IO error
	 */
	public boolean Compare(File file1, File file2) throws IOException {
		// Read data1 from file1
		// Check if the file exists. If not, exit method
		File userFile = file1;
		if (!userFile.exists()) {
			System.out.println("(System ERROR message: The file doesn't exist)");
			System.exit(0);
		}
		// Read the message inside the file
		Scanner fRead = new Scanner(userFile);
		String data1 = "";		// set data1 as "" initially to concatenate more data to it
		// Loop through the all the content inside the text file
		while (fRead.hasNext()) {
			// Read data and concatenate to data1 in upper case
			data1 += fRead.nextLine().toUpperCase();
		}
		// Close the file
		fRead.close();

		
		// Read data2 from file2
		// Check if the file exists. If not, exit method
		userFile = file2;
		if (!userFile.exists()) {
			System.out.println("(System ERROR message: The file doesn't exist)");
			System.exit(0);
		}
		// Read the message inside the file
		fRead = new Scanner(userFile);
		String data2 = "";		// set data2 as "" initially to concatenate more data to it
		// Loop through the all the content inside the text file
		while (fRead.hasNext()) {
			// Read data and concatenate to data2 in upper case
			data2 += fRead.nextLine().toUpperCase();
		}
		// Close the file
		fRead.close();
		// Return true if comparison of data1 to data2 is 0
		return (data1.compareTo(data2) == 0);
	}
	
	
	/**
	 * Method Print(String message)
	 * 	Prints message to the console
	 * @param message			String, message set by the user
	 * @return					void
	 */
	public void Print(String message) {
		System.out.println(message);	// Print message to the console
	}
	
	
	/**
	 * Method setDiameter(int diam)
	 * 	Setter for the diameter
	 * @param diam			int, diameter set by the user
	 * @return				void
	 */
	public void setDiameter(int diam) {
		diameter = diam;	// set diameter to diam
	}
	
	
	/**
	 * Method getDiameter()
	 * 	Getter for the diameter
	 * @return diameter		int, diameter set in the class
	 */
	public int getDiameter() {
		return diameter;	// return the diameter set in the class
	}
	
	
	/**
	 * Method setKey(String k)
	 * 	Setter for the key
	 * @param k			String, key set by the user
	 * @return			void
	 */
	public void setKey(String k) { 
		key = k;	// set key to k
	}
	
	
	/**
	 * Method getKey()
	 * 	Getter for the key
	 * @return key		String, key set in the class
	 */
	public String getKey() {
		return key;		// return the key set in the class
	}
	
	
	/**
	 * Method coPrimes(int num1, int num2)
	 * 	Check if num1 and num2 are coprimes. If they are, return true. If not, return false.
	 * @param num1			int, a first number
	 * @param num2			int, a second number to check if it's coprime with first number
	 * @return Boolean		Boolean, true or false
	 */
	public boolean coPrimes(int num1, int num2) {
		int t;	// define t
		// Loop to calculate the GCD (Greatest Common Divisor) of num1 and num2
		while(num2 != 0){
			t = num1;
			num1 = num2;
			num2 = t % num2;
		}
		// If the GCD of both numbers is 1, they are coprimes (true)
		return (num1 == 1);
	}
	
	
	/**
	 * Method messageToFile(File fullFileName, String message)
	 * 	Write a message inside a file. If file doesn't exist, is created. If exists, is overwritten.
	 * @param fullFileName		File, a File object that references to the file to be written
	 * @param message			String, the message to be written inside the file 
	 * @exception				IOException on IO error
	 */
	public void messageToFile (File fullFileName, String message) throws IOException {
		// Open a file (filename) and overwrite any other file with same name
		PrintWriter outputFile = new PrintWriter(fullFileName);
		// Write the message to the file
		outputFile.print(message);
		//Close the file
		outputFile.close();
	}
	
}
