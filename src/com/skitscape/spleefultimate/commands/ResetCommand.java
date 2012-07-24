package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class ResetCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 1) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.mod.reset"))
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

    game.reset();

    player.sendMessage(Messages.getMessage("message-player_resetgame"));

    return true;
  }

  public String getUsage()
  {
    return "/spleef reset <gameId>";
  }
}