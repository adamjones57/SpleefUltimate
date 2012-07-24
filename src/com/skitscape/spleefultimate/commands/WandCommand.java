package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.RegionSelections;

public class WandCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 0) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.admin.wand"))
    {
      player.sendMessage(Messages.getMessage("error-player_nopermission"));

      return true;
    }

    if (RegionSelections.hasSelection(player))
    {
      RegionSelections.setHasSelection(player, false);

      player.sendMessage(Messages.getMessage("message-regions_wanddeactivated"));
    }
    else
    {
      RegionSelections.setHasSelection(player, true);

      player.sendMessage(Messages.getMessage("message-regions_wandactivated"));
    }

    return true;
  }

  public String getUsage()
  {
    return "/spleef wand";
  }
}