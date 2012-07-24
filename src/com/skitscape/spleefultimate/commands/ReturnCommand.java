package com.skitscape.spleefultimate.commands;


import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.PlayerManager;

public class ReturnCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 0) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.player.return"))
    {
      player.sendMessage(Messages.getMessage("error-player_nopermission"));

      return true;
    }

    Location returnLocation = PlayerManager.getReturnLocation(player);
    if (returnLocation == null)
    {
      player.sendMessage(Messages.getMessage("error-player_noreturnlocation"));

      return true;
    }

    if (player.teleport(returnLocation))
      PlayerManager.setReturnLocation(player, null);
    else {
      player.sendMessage(Messages.getMessage("error-teleportationfailed"));
    }
    return true;
  }

  public String getUsage()
  {
    return "/spleef return";
  }
}