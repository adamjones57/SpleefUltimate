package com.skitscape.spleefultimate.commands;

import java.util.Arrays;
import java.util.Vector;


import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class SetTpCommand extends SubCommand
{
  final String[] validIds = { "join", "lose", "spectate", "win" };
  final Vector<String> validIdsList = new Vector(Arrays.asList(this.validIds));

  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 2) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.admin.settp"))
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

    String id = args[1].toLowerCase();
    if (!this.validIdsList.contains(id))
    {
      return false;
    }

    Location playerLocation = player.getLocation();
    game.setTp(id, playerLocation);

    player.sendMessage(Messages.getMessage("message-game_tpset"));

    return true;
  }

  public String getUsage()
  {
    return "/spleef settp <gameId> <join|lose|spectate|win>";
  }
}