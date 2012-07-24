package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class RemoveLoseRegionCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 2) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.admin.removeloseregion"))
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

    if (!game.getArena().hasLoseRegion(args[1]))
    {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-loseregion_doesnotexist"), "{ID}", args[1]));

      return true;
    }

    game.getArena().removeLoseRegion(args[1]);

    player.sendMessage(MessageFormatter.format(Messages.getMessage("message-loseregion_removed"), "{ID}", args[1]));

    return true;
  }

  public String getUsage()
  {
    return "/spleef removeloseregion <gameId> <regionId>";
  }
}