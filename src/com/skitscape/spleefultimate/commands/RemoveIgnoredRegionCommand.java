package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class RemoveIgnoredRegionCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 2) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.admin.removeignoredregion"))
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

    if (!game.getArena().hasIgnoredRegion(args[1]))
    {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-ignoredregion_doesnotexist"), "{ID}", args[1]));

      return true;
    }

    game.getArena().removeIgnoredRegion(args[1]);

    player.sendMessage(MessageFormatter.format(Messages.getMessage("message-ignoredregion_removed"), "{ID}", args[1]));

    return true;
  }

  public String getUsage()
  {
    return "/spleef removeignoredregion <gameId> <regionId>";
  }
}