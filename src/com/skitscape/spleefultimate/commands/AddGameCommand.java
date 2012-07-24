package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class AddGameCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 1) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.admin.addgame"))
    {
      player.sendMessage(Messages.getMessage("error-player_nopermission"));

      return true;
    }

    if (!GameManager.hasGame(args[0]))
    {
      GameManager.addGame(args[0]);

      player.sendMessage(MessageFormatter.format(Messages.getMessage("message-game_created"), "{ID}", args[0]));
    }
    else {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_alreadyexists"), "{ID}", args[0]));
    }
    return true;
  }

  public String getUsage()
  {
    return "/spleef addgame <gameId>";
  }
}