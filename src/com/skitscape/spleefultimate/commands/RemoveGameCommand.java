package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class RemoveGameCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 1) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.admin.removegame"))
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

    if (game.isActive())
    {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_stillrunning"), "{ID}", args[0]));

      return true;
    }

    GameManager.removeGame(args[0]);

    player.sendMessage(MessageFormatter.format(Messages.getMessage("message-game_removed"), "{ID}", args[0]));

    return true;
  }

  public String getUsage()
  {
    return "/spleef removegame <gameId>";
  }
}