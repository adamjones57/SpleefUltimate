package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class EnableCommand extends SubCommand {
	@Override
	public boolean onCommand(Player pl, String[] args) {
		if(args.length != 1) return false;
		if(!pl.hasPermission("spleefultimate.mod.enable")) {
			pl.sendMessage(Messages.getMessage("error-player_nopermission"));
			return true;
		}
		SpleefGame tG = getGame(args[0]);
		if(tG == null) {
			pl.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_doesnotexist"), "{ID}", args[0]));
			return true;
		}
		if(tG.getEnabled() == Boolean.valueOf(true)) {
			pl.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_alreadyenabled"), "{ID}", args[0]));
		} else {
			tG.setEnabled(Boolean.valueOf(true));
			pl.sendMessage(MessageFormatter.format(Messages.getMessage("message-game_successfullyenabled"), "{ID}", args[0]));
		}
		return true;
	}

	@Override
	public String getUsage() {
		return "/spleef enable <gameId>";
	}
}
