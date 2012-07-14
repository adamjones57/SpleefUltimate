package com.skitscape.spleefultimate;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SpleefUltimate extends JavaPlugin {
	
	SpleefUltimate p = this;
	Logger logger = Logger.getLogger("Minecraft");
	PluginDescriptionFile pdfFile = p.getDescription();
	PluginManager pm = getServer().getPluginManager();
	
	private CommandHandler commandHandler;
	
	
	public void onDisable() {
		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " has successfully been disabled.");
	}
	
	public void onEnable() {
		getCommand("Spleef").setExecutor(this.commandHandler);
		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " has successfully been enabled.");
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    logger.info("Failed to contact the Metrics servers");
		}
	}
	
	
	
}
