/********************************************************************************
 *																			    *
 * CISC 190 - 05/21/2017													    *
 * David - May 21, 2017 											    *
 * 																			    *
 * Brief Description:														    *
 * The Stock class is used to save and supply stock data to other classes. The
 *  data is obtained through an HTML string using method setStock(String res).
 * 																			    * 																			   *
 * Detailed Notes on Code:													    *
 * 1) This class was created by my, it didn't exist in default Android app.
 * 2) Most code was to deal with parsing HTML, so I used many String methods    *
 ********************************************************************************/

package com.example.android.led_matrix_android;

// Import libraries
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

// Declare class Stock
public class Stock {
    // Fields
    // All private and static, as they will be used only once at a time by the LEDMatrix app
    private static String stockTicker = "";
    private static String stockName = "";
    private static String stockPrice = "";
    private static String stockDateTime = "";
    private static String errorMessage = "";

    // Getter methods
    public static String getStockTicker() {return stockTicker;}
    public static String getStockName() {return stockName;}
    public static String getStockPrice() {return stockPrice;}
    public static String getStockDateTime() {return stockDateTime;}
    public static String getErrorMessage() {return errorMessage;}

    // Setter methods
    /**
     * Method setStock(String res)
     * 	Set all stock data based on res
     * @param res		String, HTML response got from https://www.google.com/finance
     */
    public static void setStock(String res) {
        // Reset stock data to empty
        stockTicker = "";
        stockName = "";
        stockPrice = "";
        stockDateTime = "";
        errorMessage = "";

        // If stock ticker doesn't exist (HTML response will contain "no matches")
        int check;
        check = res.indexOf("no matches");
        if(check >= 0) {
            errorMessage = "Stock ticker not found";
            return;
        }

        // If ticker exists (HTML response will contain "<div id=\"sharebox-data")
        check = res.indexOf("<div id=\"sharebox-data");
        if (check >= 0) {
            // Reduce res only to the content of "sharebox-data" <div>, to increase performance (calling String methods with a smaller String)
            int posStart = check;
            int posEnd = res.substring(posStart).indexOf("</div>");	// next </div>, that closes the "sharebox-data" <div>
            res = res.substring(posStart, posStart + posEnd);
            // Clean multiple spaces in HTML response
            res = res.trim().replaceAll(" +", " ");
            // Clean new lines in HTML response
            res = res.replaceAll("\n", "");
            res = res.replaceAll("\r", "");
            String strSearch;
            int strStart, strEnd;
            // Find stock name
            strSearch = "\"name\" content=\"";
            strStart = res.indexOf(strSearch) + strSearch.length();
            strEnd = res.substring(strStart).indexOf("\" />");
            stockName = res.substring(strStart, strStart + strEnd);
            // Find stock ticker
            strSearch = "\"tickerSymbol\" content=\"";
            strStart = res.indexOf(strSearch) + strSearch.length();
            strEnd = res.substring(strStart).indexOf("\" />");
            stockTicker = res.substring(strStart, strStart + strEnd);
            // Find stock price
            strSearch = "\"price\" content=\"";
            strStart = res.indexOf(strSearch) + strSearch.length();
            strEnd = res.substring(strStart).indexOf("\" />");
            stockPrice = res.substring(strStart, strStart + strEnd);
            // Find stock price date and time
            strSearch = "\"quoteTime\" content=\"";
            strStart = res.indexOf(strSearch) + strSearch.length();
            strEnd = res.substring(strStart).indexOf("\" />");
            stockDateTime = res.substring(strStart, strStart + strEnd);
            // Try to parse date from HTML file to date (Date object)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date;
            try {
                date = sdf.parse(stockDateTime);
            }
            catch (ParseException e) {
                // If parse wasn't possible, Google changed its date format string, so use current date time
                date = new Date();
            }
            // Convert date to a simpler format
            sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            stockDateTime = sdf.format(date);
            return;
        }

        // Else, probably an internet connection error
        else {
            errorMessage = "Check your internet connection";
            return;
        }
    }

}
