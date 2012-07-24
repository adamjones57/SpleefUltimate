package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.RegionSelections;
import com.skitscape.spleefultimate.Selection;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class AddIgnoredRegionCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 2) {
      return false;
    }

    if (!player.hasPermission("spleefextreme.admin.addignoredregion"))
    {
      player.sendMessage(Messages.getMessage("error-player_nopermission"));

      return true;
    }

    Selection selection = RegionSelections.getSelection(player);
    if (selection == null)
    {
      player.sendMessage(Messages.getMessage("error-regions_havetousewand"));

      return true;
    }

    if (!selection.isValid())
    {
      player.sendMessage(Messages.getMessage("error-regions_notmaderegionselection"));

      return true;
    }

    SpleefGame game = getGame(args[0]);
    if (game == null)
    {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_doesnotexist"), "{ID}", 
        args[0]));

      return true;
    }

    if (game.getArena().hasLoseRegion(args[1]))
    {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-ignoredregion_alreadyexists"), "{ID}", args[1]));

      return true;
    }

    game.getArena().addIgnoredRegion(args[1], selection);

    player.sendMessage(MessageFormatter.format(Messages.getMessage("message-ignoredregion_added"), "{ID}", args[1]));

    return true;
  }

  public String getUsage()
  {
    return "/spleef addignoredregion <gameId> <regionId>";
  }
}