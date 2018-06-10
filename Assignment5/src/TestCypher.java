import java.io.*;

// Used to test the SecurityCipher class
public class TestCypher {
	
	public static void main (String[] args) throws IOException{
		String original, encrypted, decrypted;
		original = "abCDEFGHIJKLMNOPQRSTUVWXYZ";
		//original = "I am hurt very badly HELP";
		// Instantiate a new SecurityCipher object 
		SecurityCipher sc1 = new SecurityCipher();
		SecurityCipher sc2 = new SecurityCipher(original, "1", 1);
		SecurityCipher sc3 = new SecurityCipher(original, "1", 1);
		SecurityCipher sc4 = new SecurityCipher(original, "1", 1);
		String fullFileName = "originalMessage.txt";
		File userFile = new File(fullFileName);
		SecurityCipher sc5 = new SecurityCipher(userFile, "1", 1);
		
		// Test getters and setters
		sc1.Print("TESTING GETTERS/SETTERS");
		
		sc1.Print("Diameter is: " + sc1.getDiameter());
		sc1.Print("Key is: " + sc1.getKey());
		sc1.setDiameter(18);
		sc1.setKey("new key");
		sc1.Print("New diameter is: " + sc1.getDiameter());
		sc1.Print("New key is: " + sc1.getKey());
		sc1.Print("\n");
		
		// test encryption 1
		sc1.Print("TESTING ENCRYPTION 1");
		encrypted = "";
		decrypted = "";
		encrypted = sc1.EncryptionCipher();
		sc1.Print("Encrypted message: " + encrypted);
		decrypted = sc1.DecryptionCipher();
		sc1.Print("Decrypted message: " + decrypted);
		sc1.Print( "Comparison of original and decrypted messages in upper case is " + (sc1.Compare("My name is David", decrypted) ? "TRUE" : "FALSE") );
		sc1.Print("\n");
		
		// test encryption 2 (ATBASH)
		sc2.Print("TESTING ENCRYPTION 2 - ATBASH");
		encrypted = "";
		decrypted = "";
		sc2.Print("Original message: " + original);
		encrypted = sc2.EncryptionCipher(1);
		sc2.Print("Encrypted message: " + encrypted);
		decrypted = sc2.DecryptionCipher(1);
		sc2.Print("Decrypted message: " + decrypted);
		sc2.Print( "Comparison of original and decrypted messages in upper case is " + (sc2.Compare(original, decrypted) ? "TRUE" : "FALSE") );
		sc2.Print("\n");
		
		// test encryption 3 (SCYTALE)
		sc3.Print("TESTING ENCRYPTION 3 - SCYTALE");
		encrypted = "";
		decrypted = "";
		int diam = 4;
		sc3.Print("Diameter of scytale: " + diam);
		sc3.Print("Original message: " + original);
		encrypted = sc3.EncryptionCipher(diam, "123");
		sc3.Print("Encrypted message: " + encrypted);
		decrypted = sc3.DecryptionCipher(diam, "123");
		sc3.Print("Decrypted message: " + decrypted);
		sc3.Print( "Comparison of original and decrypted messages in upper case is " + (sc3.Compare(original, decrypted) ? "TRUE" : "FALSE") );
		sc3.Print("\n");
		
		// test encryption 4 (AFFINE)
		sc4.Print("TESTING ENCRYPTION 4 - AFFINE");
		encrypted = "";
		decrypted = "";
		int num1 = 5, num2 = 8;
		sc4.Print("Original message: " + original);
		encrypted = sc4.EncryptionCipher(num1, num2);
		sc4.Print("Encrypted message: " + encrypted);
		decrypted = sc4.DecryptionCipher(num1, num2);
		sc4.Print("Decrypted message: " + decrypted);
		sc4.Print( "Comparison of original and decrypted messages in upper case is " + (sc4.Compare(original, decrypted) ? "TRUE" : "FALSE") );
		sc4.Print("\n");
		
		
		// test encryption 4 (AFFINE) with message inside text file
		sc5.Print("TESTING ENCRYPTION 4 - AFFINE - with message inside file");
		encrypted = "";
		decrypted = "";
		num1 = 5;
		num2 = 8;
		sc5.Print("Original message: " + original);
		encrypted = sc5.EncryptionCipher(num1, num2);
		sc5.Print("Encrypted message: " + encrypted);
		decrypted = sc5.DecryptionCipher(num1, num2);
		sc5.Print("Decrypted message: " + decrypted);
		fullFileName = "originalMessage.txt";
		File userFile1 = new File(fullFileName);
		fullFileName = "decryptedMessage.txt";
		File userFile2 = new File(fullFileName);
		sc5.Print( "Comparison of inside files original and decrypted messages in upper case is " + (sc5.Compare(userFile1, userFile2) ? "TRUE" : "FALSE") );
		sc5.Print("\n");
		
	}
	
}
