package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class StopCommand extends SubCommand
{
	public boolean onCommand(Player player, String[] args)
	{
	    if (args.length != 1) {
	    	return false;
	    }
	
	    if (!player.hasPermission("spleefultimate.mod.stop"))
	    {
		      player.sendMessage(Messages.getMessage("error-player_nopermission"));
		
		      return true;
	    }
	
	    SpleefGame game = getGame(args[0]);
	    if (game == null)
	    {
		      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_doesnotexist"), "{ID}", 
		        args[0]));
		
		      return true;
	    }
	    
	    if(!game.getEnabled() && !player.hasPermission("spleefultimate.mod.stopdisabled")) {
	        player.sendMessage(Messages.getMessage("error-game_isdisabled"));

	        return true;    	
	    }
	
	    if (game.isActive())
	    {
	    	game.stop(true);
		
		    player.sendMessage(Messages.getMessage("message-player_stoppedgame"));
	    }
	    else
	    {
	    	player.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_notrunning"), "{ID}", args[0]));
	    }
	
	    return true;
	  }

  public String getUsage()
  {
	  return "/spleef stop <gameId>";
  	}
}