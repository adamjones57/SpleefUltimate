package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.util.MessageFormatter;


public class SetDefaultCommand extends SubCommand {

	@Override
	public boolean onCommand(Player pl, String[] args) {
		if(args.length!=1) return false;
		if(!pl.hasPermission("spleefultimate.admin.setdefault")) {
			pl.sendMessage(Messages.getMessage("error-player_nopermission"));
			return true;
		}
		
		if(GameManager.hasGame(args[0])) {
			if(GameManager.getDefaultGame()==null || !GameManager.getDefaultGameName().equalsIgnoreCase(args[0])) {
				GameManager.setDefaultGame(args[0]);
			}
			pl.sendMessage(MessageFormatter.format(Messages.getMessage("message-game_setasdefault"), "{ID}", 
					args[0]));
		} else {
			pl.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_doesnotexist"), "{ID}", 
					args[0]));
		}
		
		return true;
	}

	@Override
	public String getUsage() {
		return "/spleef setdefault <gameId>";
	}

}
