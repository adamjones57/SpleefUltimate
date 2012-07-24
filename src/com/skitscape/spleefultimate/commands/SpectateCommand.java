package com.skitscape.spleefultimate.commands;


import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.PlayerManager;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class SpectateCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 1) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.player.spectate"))
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

    if (!game.hasTp("spectate"))
    {
      player.sendMessage(Messages.getMessage("error-game_allowsnospectators"));

      return true;
    }

    Location returnLocation = player.getLocation();
    Location spectateLocation = game.getTp("spectate");
    if (player.teleport(spectateLocation))
      PlayerManager.setReturnLocation(player, returnLocation);
    else {
      player.sendMessage(Messages.getMessage("error-teleportationfailed"));
    }
    return true;
  }

  public String getUsage()
  {
    return "/spleef spectate <gameId>";
  }
}