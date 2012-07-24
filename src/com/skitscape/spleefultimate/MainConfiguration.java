package com.skitscape.spleefultimate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class MainConfiguration {
	
	public static boolean leaderboards_enabled;
	public static HashMap<String, String> sqlDetails;
	private static YamlConfiguration configFile;
	
	public static void instantiate() {
	    configFile = ConfigManager.getConfiguration("config.yml");
	    if (configFile == null) {
	      configFile = new YamlConfiguration();
	    }
	    MainConfiguration.loadLeaderboards(configFile.getConfigurationSection("leaderboard"));
	    
	}

	public static void loadLeaderboards(ConfigurationSection configSect) {
		if(configSect == null) return;
		Set<String> allLines = configSect.getKeys(false); //false means don't go into nested children
		if (allLines == null || allLines.size() == 0) return;
		Iterator<String> it = allLines.iterator();
		
		boolean enabled = false;
		String host = null;
		String user = null;
		String pass = null;
		String db = null;
		
		while(it.hasNext()) {
			String thisStr = it.next();
			if(thisStr.equalsIgnoreCase("sqlhost")) {
				host = configSect.getString("sqlhost");
				break;
			} else if(thisStr.equalsIgnoreCase("sqluser")) {
				user = configSect.getString("sqluser");
				break;
			} else if(thisStr.equalsIgnoreCase("sqlpass")) {
				pass = configSect.getString("sqlpass");
				break;
			} else if(thisStr.equalsIgnoreCase("sqldb")) {
				db = configSect.getString("sqldb");
				break;
			} else if(thisStr.equalsIgnoreCase("enabled")) {
				enabled = configSect.getString("enabled").equalsIgnoreCase("true") ? true : false;
			}
		}
		
		if(enabled) {
			if(host != null && user != null && pass != null && db != null) {
				leaderboards_enabled = true;
				sqlDetails.put("host", host);
				sqlDetails.put("user", user);
				sqlDetails.put("pass", pass);
				sqlDetails.put("db", db);
			}
		}
		
	}
	public static void userWin(String userName) {
		ThreadUserWin thisThread = new ThreadUserWin(userName);
		thisThread.run();
	}
	
}
