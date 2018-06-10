/********************************************************************************
 *																			    *
 * CISC 190 - 05/21/2017													    *
 * David - May 21, 2017 											    *
 * 																			    *
 * Brief Description:														    *
 * The MainActivity class is the main class used by Android to run the app.
 *  It contains event listeners for GUI events (buttons clicks, menu items
 *  selection). It has also some methods, like connecting to the internet to
 *  get stock data, changing GUI components (LEDs, Console window, color picker
 *  dialog).
 * 																			    * 																			   *
 * Detailed Notes on Code:													    *
 * 1) I used a lot of OOP: polymorphism (@Override for event listeners),
 *  inheritance (extends), and calling methods from the other classes that I
 *  created. This class is default in Android apps, but the other didn't exist.
 * 2) I'm using the Volley library, recommended by Google, to connects to the
 *  internet asynchronously. I have tested the standard Java library for
 *  internet requests (java.net.HttpURLConnection) and it worked, but it doesn't
 *  deal with multithreading, what may block other Android threads.
 * 3) I create the LED Matrix in the GUI dinamically. Instead of creating 64 LEDs
 * one by one in the XML file, I did a nested for loop to create, populate and
 * identify each of the 64 LEDs. The identification made programmatically is
 * very important to call each LED later, when I want to turn them on or off.
 * I also stored each row in an array of objects LinearLayout (containers), and
 * stored all leds in an array of TextViews objects (the squares representing
 * each LED).
 * 4) I used the Android classes Handler and Runnable, that deals with
 *  multithreading, in order to delay the time that the LEDs keeps turned on in
 *  the LED Matrix.
 ********************************************************************************/

package com.example.android.led_matrix_android;

// Import libraries
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.TextView;
import android.os.Handler;
import android.widget.TextView.OnEditorActionListener;
// Import Volley library
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


// Declare class MainActivity
public class MainActivity extends AppCompatActivity {

    // Fields
    public static String string_input;                  // string input by user to be print in LED Matrix
    public static int timeOnSeconds;                    // time that LEDs will be on, in seconds
    public static int colorLedOn;                       // color when the LED is on
    public static int colorLedOff;                      // color when the LED is off
    public static int ledSize;                          // LED size in GUI
    public static int ledMargin;                        // LED margin in GUI
    public static final int ROWS = 8;                   // number of LED rows
    public static final int COLUMNS = 8;                // number of LEDs per row
    public static int numberOfLeds = ROWS * COLUMNS;    // total number of LEDs
    public static String[] colors_array;                // possible LED on colors


    // Methods

    /**
     * Method printLEDMatrix(int ledArray[])
     * 	Print a char in the LED Matrix (GUI), by setting the background color of each LED if it's 0 or 1
     * @param ledArray		String[], array with the 8 rows of 8 LEDs states each (0-off / 1-on)
     */
    public void printLEDMatrix(int ledArray[]) {
        String rowBinary;
        LinearLayout div_led_matrix = (LinearLayout) findViewById(R.id.div_led_matrix);
        for(int i = 0; i < 8; i++) {
            rowBinary = LEDMatrixChar.binary8(ledArray[i]);
            for (int j = 0; j < 8; j++) {
                // Change the color background of each LED if it's 0 or 1
                div_led_matrix.findViewWithTag("led"+i+j).setBackgroundColor(rowBinary.charAt(j)=='0' ? colorLedOff : colorLedOn);
            }
        }
    }


    /**
     * Method clearLEDMatrix()
     * 	Set all LEDs to off (by setting their color background)
     * 	 It was created in the beginning, but not used anymore. If I want to clear the LED Matrix,
     * 	 I just "print" a null char on it.
     */
    // method: clear the LED Matrix
    public void clearLEDMatrix() {
        LinearLayout div_led_matrix = (LinearLayout) findViewById(R.id.div_led_matrix);
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                div_led_matrix.findViewWithTag("led"+i+j).setBackgroundColor(colorLedOff);
            }
        }
    }


    /**
     * Method printConsoleLog(String msg)
     * 	Prints a log message in the GUI console
     * @param msg		String, message to show in GUI console log
     */
    public void printConsoleLog(String msg) {
        EditText console_log = (EditText) findViewById(R.id.console_log);
        String full_log = console_log.getText().toString();
        full_log = "*log* " + msg + "\n\n" + full_log;  // prepend msg to full_log
        console_log.setText(full_log);
    }


    /**
     * Android Method onCreate(Bundle savedInstanceState)
     * 	Creates the app GUI
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Adds toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get div_led_matrix and change height to wrap_content
        LinearLayout div_led_matrix = (LinearLayout) findViewById(R.id.div_led_matrix);
        div_led_matrix.getLayoutParams().height = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;

        // Get variables from resources and set new ones
        timeOnSeconds = getResources().getInteger(R.integer.timeOnSeconds);
        colorLedOn = ContextCompat.getColor(this, R.color.colorLedOn);
        colorLedOff = ContextCompat.getColor(this, R.color.colorLedOff);
        ledSize = (int) getResources().getDimension(R.dimen.ledSize);
        ledMargin = (int) getResources().getDimension(R.dimen.ledMargin);
        colors_array = getResources().getStringArray(R.array.color_picker_array);

        // Create, add and configure div_led_rows[i] objects (i is number of row) to div_led_matrix
        LinearLayout[] div_led_rows = new LinearLayout[ROWS];   // instantiate array of LinearLayout objects
        TextView[] leds = new TextView[numberOfLeds];
        numberOfLeds = 0;   // reset, it will be used as a counter during leds creation
        for (int i = 0; i < ROWS; i++) {
            div_led_rows[i] = new LinearLayout(this);   // instantiate each LinearLayout object inside array
            div_led_matrix.addView(div_led_rows[i]);
            div_led_rows[i].getLayoutParams().width = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;
            div_led_rows[i].getLayoutParams().height = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;
            //div_led_rows[i].setBackgroundColor(500000*i-16777216);    // just for preview rows in testing
            div_led_rows[i].setOrientation(LinearLayout.HORIZONTAL);
            // Create, add and configure leds[j] objects (j is number of led) inside each div_led_rows[i]
            for(int j = 0; j < COLUMNS; j++) {
                leds[numberOfLeds] = new TextView(this);
                div_led_rows[i].addView(leds[numberOfLeds]);
                leds[numberOfLeds].setTag("led"+i+j);   // set tag "led"+i+j
                leds[numberOfLeds].getLayoutParams().width = ledSize;
                leds[numberOfLeds].getLayoutParams().height = ledSize;
                leds[numberOfLeds].setBackgroundColor(colorLedOff);
                // Set led margins
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)leds[numberOfLeds].getLayoutParams();
                params.setMargins(ledMargin, ledMargin, ledMargin, ledMargin);  // left, top, right, bottom
                leds[numberOfLeds].setLayoutParams(params);
                numberOfLeds++;
            }
        }


        /**
         * Android Method setOnEditorActionListener(new OnEditorActionListener() {})
         * 	Event listener for keyboard "DONE" click in text_input
         * 	Prints the user input in the LED Matrix for timeOnSeconds seconds
         */
        final EditText text_input = (EditText)findViewById(R.id.text_input);
        text_input.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, android.view.KeyEvent event) {
                string_input = text_input.getText().toString() + "\0";  // add a null char to clean the LEDMatrix in the end
                // Write to the console log
                printConsoleLog("Typed " + string_input);
                // print user input to the LED Matrix for timeOnSeconds seconds
                final Handler handler = new Handler();
                for (int i = 0; i < string_input.length(); i++) {
                    final int z = i;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            printLEDMatrix(LEDMatrixChar.get_char(string_input.charAt(z)));
                        }
                    //}, timeOnSeconds + z * timeOnSeconds * 1000);    // wait timeOnSeconds seconds and then execute first code
                    }, z * timeOnSeconds * 1000);         // execute first code without waiting
                }
                return false;
            }
        });





        /**
         * Android Method setOnEditorActionListener(new OnEditorActionListener() {})
         * 	Event listener for keyboard "DONE" click in stock_input
         * 	Request the stock data in the internet (Google Finance), parse the HTML response,
         * 	save the stock and prints the stock price in the LED Matrix for timeOnSeconds seconds
         */
        final EditText stock_input = (EditText)findViewById(R.id.stock_input);
        stock_input.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, android.view.KeyEvent event) {
                // Instantiate the RequestQueue
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://www.google.com/finance?q=NYSE%3A" + stock_input.getText().toString();
                // Request a string response from the provided URL
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Stock.setStock(response);
                        String log;
                        // If Stock.errorMessage is empty, get stock data and write price to LED Matrix
                        if (Stock.getErrorMessage().compareTo("") == 0) {
                            string_input = Stock.getStockPrice() + "\0";
                            log = Stock.getStockName() + " (" + Stock.getStockTicker() + ") last price: $" + Stock.getStockPrice() + " at " + Stock.getStockDateTime();
                            // print stock price to the LED Matrix for timeOnSeconds seconds
                            final Handler handler = new Handler();
                            for (int i = 0; i < string_input.length(); i++) {
                                final int z = i;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //LEDMatrixChar.log_char(LEDMatrixChar.get_char(string_input.charAt(z)));
                                        printLEDMatrix(LEDMatrixChar.get_char(string_input.charAt(z)));
                                    }
                                    //}, timeOnSeconds + z * timeOnSeconds * 1000);    // wait timeOnSeconds seconds and then execute first code
                                }, z * timeOnSeconds * 1000);         // execute first code without waiting
                            }
                        }
                        // Else, if Stock.errorMessage has a message, just get the error message
                        else {
                            log = Stock.getErrorMessage();
                        }
                        printConsoleLog(log);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printConsoleLog("Check your internet connection");
                    }
                });
                // Add the request to the RequestQueue
                queue.add(stringRequest);
                return false;
            }
        });


        /**
         * Android Method setOnEditorActionListener(new OnEditorActionListener() {})
         * 	Event listener for keyboard "DONE" click in settings_edit_timeOnSeconds
         * 	Save the timeOnSeconds defined by the user
         */
        final EditText settings_edit_timeOnSeconds = (EditText)findViewById(R.id.settings_edit_timeOnSeconds);
        settings_edit_timeOnSeconds.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, android.view.KeyEvent event) {
                timeOnSeconds = Integer.parseInt(settings_edit_timeOnSeconds.getText().toString());
                // Write to the console log
                printConsoleLog("Changed time LED on to " + timeOnSeconds + " seconds");
                return false;
            }
        });

    }


    /**
     * Android Method onCreateOptionsMenu(Menu menu)
     * 	Creates the top right menu in the GUI
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /**
     * Android Method onOptionsItemSelected(MenuItem item)
     * 	Event listener for the top right menu
     * 	When each item is selected, change the GUI bottom section to the user selection
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Switch case according to user selection (item id)
        switch (id) {
            case R.id.action_keyboardInput:
                // Make only user_input_main layout visible
                findViewById(R.id.user_input_main).setVisibility(View.VISIBLE);
                findViewById(R.id.stock_input_main).setVisibility(View.GONE);
                findViewById(R.id.settings_main).setVisibility(View.GONE);
                findViewById(R.id.about_main).setVisibility(View.GONE);
                return true;
            case R.id.action_stockInput:
                // Make only stock_input_main layout visible
                findViewById(R.id.user_input_main).setVisibility(View.GONE);
                findViewById(R.id.stock_input_main).setVisibility(View.VISIBLE);
                findViewById(R.id.settings_main).setVisibility(View.GONE);
                findViewById(R.id.about_main).setVisibility(View.GONE);
                return true;
            case R.id.action_settings:
                // Make only settings_main layout visible
                findViewById(R.id.user_input_main).setVisibility(View.GONE);
                findViewById(R.id.stock_input_main).setVisibility(View.GONE);
                findViewById(R.id.settings_main).setVisibility(View.VISIBLE);
                findViewById(R.id.about_main).setVisibility(View.GONE);
                return true;
            case R.id.action_about:
                // Make only about_main layout visible
                findViewById(R.id.user_input_main).setVisibility(View.GONE);
                findViewById(R.id.stock_input_main).setVisibility(View.GONE);
                findViewById(R.id.settings_main).setVisibility(View.GONE);
                findViewById(R.id.about_main).setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Android Class ColorPickerDialogFragment extends DialogFragment
     * 	Used to create a GUI dialog with a color list to pick from
     * 	When each item is selected, the colorLedOn will be changed to the user selection
     * 	and the button will change to the same new color
     */
    public static class ColorPickerDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.color_picker_title)
                   .setItems(R.array.color_picker_array, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           colorLedOn = Color.parseColor(colors_array[which].substring(colors_array[which].indexOf("#")));
                           Button buttonColorPicker = (Button) ((Activity) getContext()).findViewById(R.id.settings_button_ledColorOn);
                           buttonColorPicker.setBackgroundColor(colorLedOn);
                       }
                   });
            return builder.create();
        }
    }


    /**
     * Android Method settingsLedColorOn(View view)
     * 	Event triggered when user clicks on button color picker
     */
    public void settingsLedColorOn(View view) {
        DialogFragment newFragment = new ColorPickerDialogFragment();
        newFragment.show(getSupportFragmentManager(), "colorpicker");
    }


}
