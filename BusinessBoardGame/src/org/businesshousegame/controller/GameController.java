package org.businesshousegame.controller;

import java.util.Scanner;

import org.businesshousegame.models.Configuration;
import org.businesshousegame.models.Game;
import org.businesshousegame.utils.Constants;
import org.businesshousegame.utils.Utilities;

/**
 * Starting point of the application
 * 
 * @author rajasri janakiraman
 */

public class GameController {

	public static void main(String[] args) {
		GameController controller = new GameController();
		try {
			if (controller.loadConfiguration()) {
				Game game = new Game();
				game.play();
			}
		} catch (Exception e) {
			Utilities.displayMessageNewLine("Game stopped unexpectedly!");
		}

	}

	/**
	 * Reads the configuration values from the config.properties file. If any
	 * incorrect values is defined, user shall opt to continue with default
	 * configurations
	 * 
	 * @return whether the configurations were loaded successfully or not
	 */
	public boolean loadConfiguration() {
		Configuration gameConfig = Configuration.getInstance();
		Utilities.displayMessageNewLine("Reading your configurations.");
		if (gameConfig.loadConfigurations()) {
			Utilities.displayMessageNewLine("Configurations accepted!");
			return true;
		} else {
			Utilities.displayMessageNewLine("Incorrect configurations!");
			Utilities.displayMessageNewLine("Do you want to play the game with the below default configurations ? "
					+ Constants.YES + " or " + Constants.NO);
			Configuration.getInstance().displayDefaultConfigurations();
			Scanner sc = new Scanner(System.in);
			String userOption = sc.nextLine();
			sc.close();
			if (null != userOption && (Constants.YES.equalsIgnoreCase(userOption))) {
				gameConfig.loadDefaultConfigurations();
				return true;
			}
		}
		return false;
	}

}
