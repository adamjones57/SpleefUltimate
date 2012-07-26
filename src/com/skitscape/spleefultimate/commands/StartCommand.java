package com.skitscape.spleefultimate.commands;



import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.StartSpleefTask;
import com.skitscape.spleefultimate.util.MessageFormatter;



public class StartCommand extends SubCommand
{

  public boolean onCommand(Player player, String[] args)
  {
	  String id=null;
	  if (args.length != 1) {
	     	if(args.length==0) {
	     		id = GameManager.getDefaultGameName();
	     	} else {
	     		return false;
	     	}
	     } else {
	     	id = args[0];
	     }
	     
	 	if(id==null) return false;
    if (!player.hasPermission("spleefultimate.mod.start"))
    {
      player.sendMessage(Messages.getMessage("error-player_nopermission"));

      return true;
    }

    SpleefGame game = getGame(id);
    if (game == null)
    {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_doesnotexist"), "{ID}", 
        id));

      return true;
    }
    if(!game.getEnabled() && !player.hasPermission("spleefultimate.mod.startdisabled")) {
        player.sendMessage(Messages.getMessage("error-game_isdisabled"));

        return true;    	
    }
    if (!game.isActive())
    {
      if ((game.getPlayerNumber() == 0) || (
        (game.getPlayerNumber() == 1) && (!player.hasPermission("spleefextreme.start.single"))))
      {
        player.sendMessage(Messages.getMessage("error-game_atleasttwoplayers"));

        return true;
      }

      if ((game.getArena().getSpleefRegionNumber() == 0) || (game.getArena().getLoseRegionNumber() == 0))
      {
        player.sendMessage(Messages.getMessage("error-game_atleastonespleefandoneloseregion"));

        return true;
      }
  	StartSpleefTask start = new StartSpleefTask(5, game);
      start.run();      
      //game.start();

      game.tellActivePlayers(MessageFormatter.format(Messages.getMessage("announce-player_startedgame"), "{PLAYER}", player.getDisplayName()), player);

      player.sendMessage(Messages.getMessage("message-player_startedgame"));
    }
    else
    {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_alreadyrunning"), "{ID}", id));
    }
    return true;
  }

  public String getUsage()
  {
    return "/spleef start <gameId>";
  }
}