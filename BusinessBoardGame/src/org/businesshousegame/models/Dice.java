package org.businesshousegame.models;

import org.businesshousegame.utils.Utilities;

/**
 * Defines the properties of Cell
 * 
 * @author raman-4
 *
 */
public class Dice {

	/**
	 * To imitate dicing the dice
	 * 
	 * @return a random dice value between the minimum and maximum allowed
	 *         values
	 */
	public int dice() {
		int minVal = Configuration.getInstance().getDiceMinimumValue();
		int maxVal = Configuration.getInstance().getDiceMaximumValue();

		return Utilities.generateRandomNumber(minVal, maxVal);
	}
}
