package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class RemoveTpCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 2) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.admin.removetp"))
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

    String id = args[1].toLowerCase();
    if (!game.hasTp(id))
    {
      player.sendMessage(Messages.getMessage("error-game_doesnothavethattp"));

      return true;
    }

    game.removeTp(id);

    player.sendMessage(Messages.getMessage("message-game_tpremoved"));

    return true;
  }

  public String getUsage()
  {
    return "/spleef removetp <gameId> <tpId>";
  }
}