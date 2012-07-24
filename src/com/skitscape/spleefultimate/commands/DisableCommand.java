package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class DisableCommand extends SubCommand {

	@Override
	public boolean onCommand(Player pl, String[] args) {
		if(args.length != 1) return false;
		if(!pl.hasPermission("spleefultimate.mod.disable")) {
			pl.sendMessage(Messages.getMessage("error-player_nopermission"));
			return true;
		}
		SpleefGame tG = getGame(args[0]);
		if(tG == null) {
			pl.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_doesnotexist"), "{ID}", args[0]));
			return true;
		}
		if(tG.getEnabled() == Boolean.valueOf(false)) {
			pl.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_alreadydisabled"), "{ID}", args[0]));
		} else {
			tG.setEnabled(Boolean.valueOf(false));
			pl.sendMessage(MessageFormatter.format(Messages.getMessage("message-game_successfullydisabled"), "{ID}", args[0]));
		}
		return true;
	}

	@Override
	public String getUsage() {
		return "/spleef disable <gameId>";
	}

}
