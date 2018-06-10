import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// TODO: Add components to the Settings panel (buttons, combos, ...)
// TODO: Try working with objects to create the 3 panels first, before all event handlings  
// TODO: Make the code cleaner, try using OOP
// TODO: use Thread.sleep(); for turning off the LEDs after x seconds
// OPTIONAL: read text from a file
// OPTIONAL: read text from a website (parse HTML), Twitter, ...
// OPTIONAL: add more characters to the characters map (decimal ASCII and LED format)
// UNDERSTAND keyboard listener implementation
// OPTIONAL: create output to be sent via USB to the device, when it is connected to the computer. This seems very complicated using desktop Java. But in Android there is an API for that, so I'll probably connect the device only to an Android app, not to the Java desktop app. 


public class GUI {
	/*
	 * FIELDS
	 */

	
	/*
	 * Method: main()
	 */
	public static void main(String[] args) {
		// Create a new object LEDMatrixTable
		LEDMatrixTable lm = new LEDMatrixTable();
		
		// Create the app_window (container for all panels in the app)
		final int app_window_w = 600;
		final int app_window_h = 400;
		final String app_window_title = "LED Matrix app";
		JFrame app_window = new JFrame();
		app_window.setTitle(app_window_title);
		app_window.setSize(app_window_w, app_window_h);
		app_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create the app_panel (container for the smaller panels/sections)
		JPanel app_panel = new JPanel();
		app_panel.setBackground(Color.orange);
		
		// Create the LEDMatrix_panel (LED Matrix section)
		JPanel LEDMatrix_panel = new JPanel();
		int rows = 8, columns = 8;
		GridLayout grid = new GridLayout(rows, columns);
		LEDMatrix_panel.setLayout(grid);
		// create a LED array (matrix)
		Color color_off = new Color(255,255,255);	// white
		Color color_on = new Color(255,255,0);	// yellow
		LEDMatrix_panel.setBackground(color_off);
		JLabel[][] LEDMatrix = new JLabel[rows][columns];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				LEDMatrix[r][c] = new JLabel("" + r + c);
				LEDMatrix[r][c].setBorder(BorderFactory.createLineBorder(Color.black));
				LEDMatrix[r][c].setOpaque(true);
				LEDMatrix[r][c].setBackground(color_off);
				LEDMatrix[r][c].setForeground(color_off);
				LEDMatrix_panel.add(LEDMatrix[r][c]);
			}
		}
		app_panel.add(LEDMatrix_panel);
		
		
		// Create the settings_panel (Settings section)
		JPanel settings_panel = new JPanel();
		settings_panel.setLayout(new BoxLayout(settings_panel, BoxLayout.Y_AXIS));
		settings_panel.setBackground(Color.white);
		// text box with timing of letter appearance
		// TODO: save this to a field "timing" in a class
		settings_panel.add(new JLabel("Timing of each turned on letter (in seconds)"));
		JTextField txtTiming = new JTextField("2");
		settings_panel.add(txtTiming);
		// button to set focus to app_panel in order to receive keyboard input 
		JButton btnKeyboard = new JButton("Click here to activate keyboard input");
		btnKeyboard.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Keyboard activated - LED timing " + txtTiming.getText() + "s");
				app_panel.requestFocusInWindow();	// set the focus to the app_panel
				// TODO: console can't be called because it doesn't exist at this point. Try working with objects to create the 3 panels first, before all event handlings
				//console.setCaretPosition(console.getDocument().getLength());	// scroll the console down automatically when new text is output there
			}
		});
		settings_panel.add(btnKeyboard);
		// button to choose the color of the letter when turned ON
		settings_panel.add(new JLabel("Type any letter in the keyboard"));
		JButton btnColorLetter = new JButton("Color for the turned ON letter");
		btnColorLetter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Color color_on_letter = JColorChooser.showDialog(btnColorLetter, "Choose letter turned ON Color", Color.yellow);
				System.out.println("Letter Color chosen: " + color_on_letter);
				// TODO: assign the new color to the variables color (use class and fields)
				app_panel.requestFocusInWindow();	// set the focus to the app_panel
			}
		});
		settings_panel.add(btnColorLetter);
		// button to choose the color of the background when turned ON
		JButton btnColorBackground = new JButton("Color for the turned ON background");
		btnColorBackground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Color color_on_background = JColorChooser.showDialog(btnColorBackground, "Choose background turned ON Color", Color.white);
				System.out.println("Background Color chosen: " + color_on_background);
				// TODO: assign the new color to the variables color (use class and fields)
				app_panel.requestFocusInWindow();	// set the focus to the app_panel
			}
		});
		settings_panel.add(btnColorBackground);
		// button to write a sentence typed by the user in the LED Matrix
		// TODO: nothing done yet
		settings_panel.add(new JLabel("Write a sentence below"));
		JTextField txtTypedSentence = new JTextField();
		settings_panel.add(txtTypedSentence);
		JButton btnTypedSentence = new JButton("Output sentence in LED Matrix");
		btnTypedSentence.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Sentence typed is " + txtTypedSentence.getText());
				// TODO: write this sentence in the console (declared after, so need to use OOP)
			}
		});
		settings_panel.add(btnTypedSentence);
		// button to write a sentence read from a file in the LED Matrix
		// TODO: nothing done yet
		settings_panel.add(new JLabel("Read data from a text file"));
		// button to write a sentence read from a website in the LED Matrix
		// TODO: nothing done yet
		settings_panel.add(new JLabel("Read data from a website"));
		app_panel.add(settings_panel);
		
		
		// Create the console_panel (Console section)
		JPanel console_panel = new JPanel();
		console_panel.setBackground(Color.black);
		JTextArea console = new JTextArea(10,47);
		console.setEditable(false);
		console.setLineWrap(true);
		console.setBackground(Color.black);
		console.setForeground(Color.green);
		console.append("System: Welcome to the LED Matrix system\n");
		JScrollPane scroll = new JScrollPane(console);
		scroll.setBackground(Color.black);
		console_panel.add(scroll);
		console.setCaretPosition(console.getDocument().getLength());	// scroll the console down automatically when new text is output there
		app_panel.add(console_panel);
		

		// Implements a key listener to the app_panel
		app_panel.addKeyListener(new KeyListener(){
			@Override
            public void keyTyped(KeyEvent e) {
				int led_row;
				char digit;
				int typedKey = e.getKeyChar();
				System.out.println("Typed " + (char)typedKey);
				int[] lm_array = lm.get_char(typedKey);	// get the array with the LED Matrix code of the letter
				lm.print_char(lm_array);	// print the LED Matrix code to the console
				// print the LED Matrix code to the LED Matrix
				for (int r = 0; r < rows; r++) {
					for (int c = 0; c < columns; c++) {
						digit = lm.binary8(lm_array[r]).charAt(c);
						LEDMatrix[r][c].setBackground((digit == '0') ? color_off : color_on);
						LEDMatrix[r][c].setForeground((digit == '0') ? color_off : color_on);
						LEDMatrix[r][c].repaint();
						LEDMatrix_panel.revalidate();
					}
				}
				// print the typed letter to the console
				console.append("System: You typed " + (char)typedKey + "\n");
				console_panel.revalidate();
			}

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}
		});
		app_panel.setFocusable(true);	// let the app_panel receive focus
		app_panel.requestFocusInWindow();	// set the focus to the app_panel
		app_panel.setFocusTraversalKeysEnabled(false);	// turn off focus traversal (more details in Java website, keylistener tutorial)
		
		
		// Add app_panel to app_window and make the latter visible
		app_window.add(app_panel);
		app_window.setVisible(true);
		
	}
	
	
	
}
