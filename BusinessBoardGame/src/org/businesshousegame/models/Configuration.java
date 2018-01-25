package org.businesshousegame.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.businesshousegame.dbaccesslayer.ConfigurationAccessLayer;
import org.businesshousegame.utils.Constants;
import org.businesshousegame.utils.Utilities;

/**
 * Single ton class to hold the game configurations at a given point of time
 * Object cannot be created Values for this class is loaded only at the start
 * time
 * 
 * @author rajasri janakiraman
 */
public class Configuration {

	private static Configuration configuration;

	public static Configuration getConfiguration() {
		return configuration;
	}
	public static Configuration getInstance() {
		if (null == configuration) {
			configuration = new Configuration();
		}
		return configuration;
	}
	List<CellType> cellDefinition;
	int diceMaximumValue = 12;
	int diceMinimumValue = 2;
	double emptyValue;
	double hotelPurchaseValue;
	double hotelRentValue;
	double initialAmount;
	double jailValue;
	int noOfCells;
	int noOfPass;
	int noOfPlayers;

	int passDuration;

	double treasureValue;

	private Configuration() {
		// Singleton class
	}

	public void displayDefaultConfigurations() {
		Utilities.displayMessageNewLine("no_of_players=2");
		Utilities.displayMessageNewLine("no_of_cells=20");
		Utilities.displayMessageNewLine("treasure_cell_credit=200");
		Utilities.displayMessageNewLine("hotel_cell_credit=150");
		Utilities.displayMessageNewLine("hotel_cell_debit=50");
		Utilities.displayMessageNewLine("jail_cell_debit=200");
		Utilities.displayMessageNewLine("initial_amount=2000");
		Utilities.displayMessageNewLine("no_of_chances=10");
		Utilities.displayMessageNewLine("pass_duration=1");
		Utilities.displayMessageNewLine("cell_definition=E,E,J,H,E,T,J,T,E,E,H,J,T,H,E,E,J,H,E,T");
	}

	public List<CellType> getCellDefinitions() {
		return cellDefinition;
	}

	/**
	 * Reads the requested key's value from the provided property file
	 * 
	 * @param key:
	 *            property name
	 * @param property
	 *            : property file
	 * @return property value
	 */
	private String getConfigurationValue(String key, Properties property) {
		return (String) property.get(key);
	}

	public int getDiceMaximumValue() {
		return diceMaximumValue;
	}

	public int getDiceMinimumValue() {
		return diceMinimumValue;
	}

	public double getEmptyValue() {
		return emptyValue;
	}

	public double getHotelPurchaseValue() {
		return hotelPurchaseValue;
	}

	public double getHotelRentValue() {
		return hotelRentValue;
	}

	public double getInitialAmount() {
		return initialAmount;
	}

	public double getJailValue() {
		return jailValue;
	}

	public int getNoOfCells() {
		return noOfCells;
	}

	public int getNoOfPass() {
		return noOfPass;
	}

	public int getNoOfPlayers() {
		return noOfPlayers;
	}

	public int getPassDuration() {
		return passDuration;
	}

	public double getTreasureValue() {
		return treasureValue;
	}

	/**
	 * Loads the cell definitions for the game board
	 * 
	 * @param cellDef
	 *            : cell definitions separated by comma
	 * @return List of CellType which is the interpretation of cellDef
	 */
	private List<CellType> loadCellDefinitions(String cellDef) {

		if (this.cellDefinition == null) {
			this.cellDefinition = new ArrayList<>();
		}
		if (null != cellDef) {
			String[] definitions = cellDef.split(",");
			if (null != definitions && definitions.length == noOfCells && definitions[0].equals(Constants.EMPTY)) {

				for (String string : definitions) {
					CellType cellType = CellType.EMPTY;
					if (Constants.JAIL.equals(string)) {
						cellType = CellType.JAIL;
					} else if (Constants.TREASURE.equals(string)) {
						cellType = CellType.TREASURE;
					} else if (Constants.HOTEL.equals(string)) {
						cellType = CellType.HOTEL;
					}

					this.cellDefinition.add(cellType);
				}
			}
		}
		return cellDefinition;
	}

	/**
	 * Reads the config.properties file and loads the configuration and triggers
	 * validation
	 * 
	 * @return true if valid configurations. false if otherwise
	 */
	public boolean loadConfigurations() {
		ConfigurationAccessLayer accessLayer = new ConfigurationAccessLayer();
		Properties properties = accessLayer.accessProperty(".\\config.properties");

		noOfCells = Utilities.convertToInt(getConfigurationValue("no_of_cells", properties));
		noOfPlayers = Utilities.convertToInt(getConfigurationValue("no_of_players", properties));
		treasureValue = Utilities.convertToInt(getConfigurationValue("treasure_cell_credit", properties));
		hotelPurchaseValue = Utilities.convertToInt(getConfigurationValue("hotel_cell_credit", properties));
		hotelRentValue = Utilities.convertToInt(getConfigurationValue("hotel_cell_debit", properties));
		jailValue = Utilities.convertToInt(getConfigurationValue("jail_cell_debit", properties));
		initialAmount = Utilities.convertToInt(getConfigurationValue("initial_amount", properties));
		noOfPass = Utilities.convertToInt(getConfigurationValue("no_of_chances", properties));
		passDuration = Utilities.convertToInt(getConfigurationValue("pass_duration", properties));
		String cellDef = getConfigurationValue("cell_definition", properties);
		cellDefinition = loadCellDefinitions(cellDef);

		return validateConfigurations();

	}

	/**
	 * Sets default configuration properties
	 */
	public void loadDefaultConfigurations() {
		noOfCells = 20;
		noOfPlayers = 2;
		treasureValue = 200.00;
		hotelPurchaseValue = 150.00;
		hotelRentValue = 50.00;
		jailValue = 200.00;
		initialAmount = 2000.00;
		noOfPass = 10;
		passDuration = 1;
		cellDefinition = loadCellDefinitions("E,E,J,H,E,T,J,T,E,E,H,J,T,H,E,E,J,H,E,T");

	}

	/**
	 * Validated the user configurations
	 * 
	 * @return true if all validations are successful, false if unsuccessful
	 */
	private boolean validateConfigurations() {
		if (!(noOfCells >= 4 && noOfCells <= 30)) {
			Utilities.displayMessageNewLine("Incorrect no of cells.");
			return false;
		}
		if (noOfPlayers < 2 || noOfPlayers > 10) {
			Utilities.displayMessageNewLine("Incorrect no of players.");
			return false;
		}
		if (hotelPurchaseValue >= initialAmount || hotelRentValue >= initialAmount || jailValue >= initialAmount) {
			Utilities.displayMessageNewLine("Incorrect amount values.");
			return false;
		}
		if (noOfPass > 10 || noOfPass < 1) {
			Utilities.displayMessageNewLine("Incorrect no of pass.");
			return false;
		}
		if (passDuration < 1 || passDuration > 10) {
			Utilities.displayMessageNewLine("Incorrect pass duration.");
			return false;
		}
		if ((null != cellDefinition && cellDefinition.isEmpty())) {
			Utilities.displayMessageNewLine("Incorrect cell definitions.");
			return false;
		}
		return true;
	}

}
