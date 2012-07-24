package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.PlayerManager;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class LeaveCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 0) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.player.leave"))
    {
      player.sendMessage(Messages.getMessage("error-player_nopermission"));

      return true;
    }

    if (PlayerManager.isInGame(player))
    {
      SpleefGame game = GameManager.getGame(player);

      PlayerManager.removePlayerFromGame(player, true, false);

      game.tellAllPlayers(MessageFormatter.format(Messages.getMessage("announce-player_left"), "{PLAYER}", player.getDisplayName()), player);
      player.sendMessage(Messages.getMessage("message-player_left"));
    }
    else {
      player.sendMessage(Messages.getMessage("error-player_notplaying"));
    }
    return true;
  }

  public String getUsage()
  {
    return "/spleef leave";
  }
}