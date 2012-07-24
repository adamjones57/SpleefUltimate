package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.RegionSelections;
import com.skitscape.spleefultimate.SelectionType;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class SetWandTypeCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 1) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.admin.wandtype"))
    {
      player.sendMessage(Messages.getMessage("error-player_nopermission"));

      return true;
    }

    if (RegionSelections.hasSelection(player))
    {
      String typeName = args[0];
      if ((typeName != null) && (!typeName.isEmpty()))
      {
        try
        {
          SelectionType type = SelectionType.valueOf(typeName.toUpperCase());

          RegionSelections.setSelectionType(player, type);

          player.sendMessage(MessageFormatter.format(Messages.getMessage("message-regions_wandtypechanged"), "{TYPE}", 
            typeName));
        }
        catch (IllegalArgumentException ex)
        {
          player.sendMessage(MessageFormatter.format(Messages.getMessage("error-regions_wandtypenotsupported"), "{TYPE}", 
            typeName));
        }
      }
    }
    else
    {
      player.sendMessage(Messages.getMessage("error-regions_havetousewand"));
    }

    return true;
  }

  public String getUsage()
  {
    return "/spleef setwandtype <cuboid|outline|walls>";
  }
}