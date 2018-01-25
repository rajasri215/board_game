package org.businesshousegame.utils;

import java.util.Random;

public class Utilities {

	/**
	 * To convert a given string to double. Includes exception handling Returns
	 * 0 if incorrect value
	 * 
	 * @param value
	 *            : String value
	 * @return : double value
	 */
	public static double convertToDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException ne) {
			return 0.0;
		}
	}

	/**
	 * To convert a given string to int. Includes exception handling Returns 0
	 * if incorrect value
	 * 
	 * @param value
	 *            : String value
	 * @return : int value
	 */
	public static int convertToInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ne) {
			return 0;
		}
	}

	public static void displayMessage(String message) {
		System.out.print(message);
	}

	public static void displayMessageNewLine(String message) {
		System.out.println(message);
	}

	/**
	 * Generates a random number between the given min and max values
	 * 
	 * @param min
	 *            : Minimum number
	 * @param max:
	 *            Maximum number
	 * @return a random number
	 */
	public static int generateRandomNumber(int min, int max) {
		Random randomNoGenerator = new Random();
		return randomNoGenerator.nextInt(max - min + 1) + min;
	}

	/**
	 * To avoid creation of object for this class
	 */
	private Utilities() {

	}

}
