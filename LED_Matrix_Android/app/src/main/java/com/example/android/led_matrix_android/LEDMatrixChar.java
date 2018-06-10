/********************************************************************************
 *																			    *
 * CISC 190 - 05/21/2017													    *
 * David - May 21, 2017 											    *
 * 																			    *
 * Brief Description:														    *
 * The LEDMatrixChar class is used to save the LEDMatrix_Table, which is like an
 *  extension of the ASCII table. It containts the LED Matrix code for each
 *  character. It contains methods to get each character and convert to string.
 * 																			    * 																			   *
 * Detailed Notes on Code:													    *
 * 1) This class was created by my, it didn't exist in default Android app.
 * 2) I couldn't finish creating all the ASCII characters tables. Now, it has
 *  only capitalized letters, numbers, dot and null.
 ********************************************************************************/

package com.example.android.led_matrix_android;

// Import libraries
import android.util.Log;

// Declare class LEDMatrixChar
public class LEDMatrixChar {
    // FIELDS

    // LED Matrix dimensions: 8 (width) x 8 (height) LEDs
    // LED Matrix character format: element 0 is the ASCII decimal; elements 1 to 8 corresponds to each row in the LED matrix; each digit in elements 1 to 8 corresponds to each LED being turned ON (1) or OFF (0))
    // LED Matrix character format: Each 8 digits after the "0b" (binary literal) are the 8 column values of each row / Left to Right / Up to Down
    private final static int[][] LEDMatrix_Table = {
            {0,0b00000000,0b00000000,0b00000000,0b00000000,0b00000000,0b00000000,0b00000000,0b00000000},    // (null)
            {46,0b00000000,0b00000000,0b00000000,0b00000000,0b00000000,0b00000000,0b00010000,0b00000000},   // '.'
            {48,0b01111110,0b10000011,0b10000101,0b10001001,0b10010001,0b10100001,0b11000001,0b01111110},   // '0'
            {49,0b00110000,0b01010000,0b10010000,0b00010000,0b00010000,0b00010000,0b00010000,0b11111110},   // '1'
            {50,0b00110000,0b01001000,0b10000100,0b00001000,0b00010000,0b00100000,0b01000000,0b11111100},   // '2'
            {51,0b00000000,0b00111100,0b01000010,0b00000010,0b00011100,0b00000010,0b01000010,0b00111100},   // '3'
            {52,0b10000100,0b10000100,0b10000100,0b11111100,0b00000100,0b00000100,0b00000100,0b00000100},   // '4'
            {53,0b11111110,0b10000000,0b10000000,0b11111000,0b00000100,0b00000010,0b00000100,0b11111000},   // '5'
            {54,0b01111100,0b10000010,0b10000000,0b11111100,0b10000010,0b10000010,0b10000010,0b01111100},   // '6'
            {55,0b11111110,0b00000010,0b00000100,0b00001000,0b00010000,0b00100000,0b01000000,0b10000000},   // '7'
            {56,0b01111100,0b10000010,0b10000010,0b01111100,0b10000010,0b10000010,0b01111100,0b00000000},   // '8'
            {57,0b01111100,0b10000010,0b10000010,0b10000010,0b01111110,0b00000010,0b10000010,0b01111100},   // '9'
            {65,0b00000000,0b00111100,0b01000010,0b01000010,0b01111110,0b01000010,0b01000010,0b00000000},   // 'A'
            {66,0b00000000,0b01111100,0b01000010,0b01111100,0b01000010,0b01000010,0b01111100,0b00000000},   // 'B'
            {67,0b00000000,0b00111100,0b01000010,0b01000000,0b01000000,0b01000010,0b00111100,0b00000000},   // 'C'
            {68,0b00000000,0b01111000,0b01000100,0b01000010,0b01000010,0b01000100,0b01111000,0b00000000},   // 'D'
            {69,0b00000000,0b01111110,0b01000000,0b01111110,0b01000000,0b01000000,0b01111110,0b00000000},   // 'E'
            {70,0b00000000,0b01111110,0b01000000,0b01111100,0b01000000,0b01000000,0b01000000,0b00000000},   // 'F'
            {71,0b00000000,0b00111100,0b01000000,0b01001100,0b01000010,0b01000010,0b00111100,0b00000000},   // 'G'
            {72,0b00000000,0b01000010,0b01000010,0b01111110,0b01000010,0b01000010,0b01000010,0b00000000},   // 'H'
            {73,0b00000000,0b00010000,0b00010000,0b00010000,0b00010000,0b00010000,0b00010000,0b00000000},   // 'I'
            {74,0b00000000,0b01111110,0b00001000,0b00001000,0b00001000,0b01001000,0b00111000,0b00000000},   // 'J'
            {75,0b00000000,0b01000100,0b01001000,0b01110000,0b01010000,0b01001000,0b01000100,0b00000000},   // 'K'
            {76,0b00000000,0b00100000,0b00100000,0b00100000,0b00100000,0b00100000,0b00111110,0b00000000},   // 'L'
            {77,0b00000000,0b01000010,0b01100110,0b01011010,0b01000010,0b01000010,0b01000010,0b00000000},   // 'M'
            {78,0b00000000,0b01000010,0b01100010,0b01010010,0b01001010,0b01000110,0b01000010,0b00000000},   // 'N'
            {79,0b00000000,0b00111100,0b01000010,0b01000010,0b01000010,0b01000010,0b00111100,0b00000000},   // 'O'
            {80,0b00000000,0b01111100,0b01000010,0b01000010,0b01111100,0b01000000,0b01000000,0b00000000},   // 'P'
            {81,0b00000000,0b00111100,0b01000010,0b01000010,0b01010010,0b00111100,0b00001000,0b00000000},   // 'Q'
            {82,0b00000000,0b01111100,0b01000010,0b01000010,0b01111100,0b01001000,0b01000100,0b00000000},   // 'R'
            {83,0b00000000,0b00111110,0b01000000,0b00111100,0b00000010,0b00000010,0b01111100,0b00000000},   // 'S'
            {84,0b00000000,0b01111110,0b00010000,0b00010000,0b00010000,0b00010000,0b00010000,0b00000000},   // 'T'
            {85,0b00000000,0b01000010,0b01000010,0b01000010,0b01000010,0b01000010,0b00111100,0b00000000},   // 'U'
            {86,0b00000000,0b00100010,0b00100010,0b00100010,0b00100010,0b00010100,0b00001000,0b00000000},   // 'V'
            {87,0b00000000,0b00100010,0b00101010,0b00101010,0b00101010,0b00101010,0b00010100,0b00000000},   // 'W'
            {88,0b00000000,0b00000000,0b01000100,0b00101000,0b00010000,0b00101000,0b01000100,0b00000000},   // 'X'
            {89,0b00000000,0b01000100,0b01000100,0b00101000,0b00010000,0b00010000,0b00010000,0b00000000},   // 'Y'
            {90,0b00000000,0b01111110,0b00000100,0b00001000,0b00010000,0b00100000,0b01111110,0b00000000}    // 'Z'
    };

    // METHODS

    /**
     * Method get_char(int charDecimal)
     * 	Get the correspondent LEDMatrix code for the given char (ASCII decimal)
     * @param charDecimal		int, decimal representation of a char in the ASCII table
     * @return lm_array         int[], array with the LEDs states (1-on / 0-off), 8 rows with 8 LEDs each
     */
    public static int[] get_char(int charDecimal) {
        int[] lm_array = new int[8];
        // Loop through the LEDMatrix to find letter ASCII decimal
        for (int i = 0; i < LEDMatrix_Table.length; i++) {
            // If find it, populate the array to be returned with the 8 LED rows
            if(LEDMatrix_Table[i][0] == charDecimal) {
                for(int j = 1; j <= 8; j++) {
                    lm_array[j-1] = LEDMatrix_Table[i][j];
                }
                break;	// go out of the loop
            }
            // If don't find it, populate the array to be returned with the 8 LED rows of a null char
            else {
                for(int j = 1; j <= 8; j++) {
                    lm_array[j-1] = LEDMatrix_Table[0][j];
                }
            }
        }
        return lm_array;	// return an array with the 8 LED rows
    }


    /**
     * Method log_char(int[] lm_array)
     * Print the correspondent LEDMatrix code for the char in the Android Studio debugger
     * @param lm_array		int[], array with the LEDs states (1-on / 0-off), 8 rows with 8 LEDs each
     */
    public static void log_char(int[] lm_array) {
        for (int i = 0; i < lm_array.length; i++) {
            Log.d("LEDMatrixChar class", String.format("%8s", Integer.toBinaryString(lm_array[i])).replace(' ', '0'));
        }
    }


    /**
     * Method binary8(int binary)
     * 	Convert a binary integer to a binary literal String with 8 digits
     * @param binary	int, number in Java binary literal format (0b...)
     * @return          String, the string representation of the binary literal with 8 characters ("00000000")
     */
    public static String binary8(int binary) {
        return String.format("%8s", Integer.toBinaryString(binary)).replace(' ', '0');
    }


}