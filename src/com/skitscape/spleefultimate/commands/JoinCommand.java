package com.skitscape.spleefultimate.commands;



import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.PlayerManager;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;



public class JoinCommand extends SubCommand
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
    

    if (!player.hasPermission("spleefultimate.player.join"))
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
    
    if(!game.getEnabled() && !player.hasPermission("spleefultimate.mod.joindisabled")) {
        player.sendMessage(Messages.getMessage("error-game_isdisabled"));

        return true;    	
    }
    	
    if (game.isActive())
    {
      player.sendMessage(Messages.getMessage("error-player_cannotjoinrunninggame"));

      return true;
    }

    if (PlayerManager.isInGame(player))
    {
      player.sendMessage(Messages.getMessage("error-player_alreadyplaying"));

      return true;
    }

    if (game.isFull())
    {
      player.sendMessage(Messages.getMessage("error-game_isfull"));

      return true;
    }

    if (!game.hasTp("join"))
    {
      player.sendMessage(Messages.getMessage("error-game_hasnojoinpoint"));

      return true;
    }

    Location returnLocation = player.getLocation();
    Location joinLocation = game.getTp("join");
    if (player.teleport(joinLocation)) {
      PlayerManager.setReturnLocation(player, returnLocation);
    }
    else {
      player.sendMessage(Messages.getMessage("error-teleportationfailed"));

      return true;
    }

    game.addPlayer(player);

    game.tellActivePlayers(MessageFormatter.format(Messages.getMessage("announce-player_joined"), "{PLAYER}", player.getDisplayName()), player);

    player.sendMessage(Messages.getMessage("message-player_joined"));

    return true;
  }

  public String getUsage() {
	if(GameManager.getDefaultGameName()!=null) {
		return "/spleef join <gameId> OR (to join the default arena) /spleef join";
	} else {
		return "/spleef join <gameId>";
	}
  }
}