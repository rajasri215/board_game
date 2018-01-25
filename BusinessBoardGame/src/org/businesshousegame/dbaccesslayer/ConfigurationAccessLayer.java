package org.businesshousegame.dbaccesslayer;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Access layer to properties file If file has to be changed to DB, only this
 * layer needs to be changed
 * 
 * @author rajasri janakiraman
 * 
 */
public class ConfigurationAccessLayer {

	public Properties accessProperty(String fileName) {
		Properties propertiesFile = new Properties();
		try {
			propertiesFile.load(new FileInputStream(".\\Config.properties"));
		} catch (Exception e) {
			// Error handling
		}
		return propertiesFile;
	}
}
