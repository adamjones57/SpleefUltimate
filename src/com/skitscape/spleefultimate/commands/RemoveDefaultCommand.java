package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.util.MessageFormatter;


public class RemoveDefaultCommand extends SubCommand {

	@Override
	public boolean onCommand(Player pl, String[] args) {
		if(args.length!=0) return false;
		if(!pl.hasPermission("spleefultimate.admin.removedefault")) {
			pl.sendMessage(Messages.getMessage("error-player_nopermission"));
			return true;
		}
		
		if(GameManager.getDefaultGameName() != null) {
			String oldDefaultName = GameManager.getDefaultGameName(); 
			GameManager.removeDefaultGame(null);
			pl.sendMessage(MessageFormatter.format(Messages.getMessage("message-game_defaultremoved"), "{ID}", 
					oldDefaultName));
		} else {
			pl.sendMessage(Messages.getMessage("error-game_doesnothavedefault"));
		}
		
		return true;
	}

	@Override
	public String getUsage() {
		return "/spleef removedefault";
	}
	
}
