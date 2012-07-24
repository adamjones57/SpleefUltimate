package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class SetMaxPlayersCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 2) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.admin.setmaxplayers"))
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

    int newMaxPlayers = game.getMaxPlayers();
    try
    {
      newMaxPlayers = Integer.valueOf(args[1]).intValue();
    }
    catch (NumberFormatException ex)
    {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-novalidnumber"), "{NUMBER}", 
        args[1]));

      return true;
    }

    game.setMaxPlayers(newMaxPlayers);

    player.sendMessage(MessageFormatter.format(Messages.getMessage("message-game_setmaxplayers"), "{AMOUNT}", 
      String.valueOf(newMaxPlayers)));

    return true;
  }

  public String getUsage()
  {
    return "/spleef setmaxplayers <gameId> <amount>";
  }
}