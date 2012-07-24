package com.skitscape.spleefultimate.commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.SpleefGame;

public class RstartCommand extends SubCommand {
	  public boolean onCommand(Player player, String[] args)
	  {
		  SpleefGame game = getGame(args[0]);
	    if (args.length != 1) {
	      return false;
	    }

	    if (!player.hasPermission("spleefultimate.player.rstart"))
	    {
	      player.sendMessage(Messages.getMessage("error-player_nopermission"));

	      return true;
	    }
	    if(game.isActive()) {
	    	player.sendMessage(ChatColor.RED + "The spleef game for this arena is currently running.");
	    	return true;
	    } else {
	    	Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[SpleefUltimate]" + ChatColor.BLUE + player.getDisplayName() + " would like the Spleef Game to start");
	    	return true;
	    }
	    }
	    public String getUsage()
	    {
	      return "/spleef rstart <gameId>";
}
}
