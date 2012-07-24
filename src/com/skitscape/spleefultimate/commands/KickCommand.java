package com.skitscape.spleefultimate.commands;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.PlayerManager;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class KickCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 1) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.mod.kick"))
    {
      player.sendMessage(Messages.getMessage("error-player_nopermission"));

      return true;
    }

    Player target = Bukkit.getServer().getPlayer(args[0]);
    if (target == null)
    {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-player_doesnotexist"), "{PLAYER}", args[0]));

      return true;
    }

    if (!PlayerManager.isInGame(target))
    {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-player_doesnotplay"), "{PLAYER}", args[0]));

      return true;
    }

    SpleefGame game = GameManager.getGame(target);

    PlayerManager.removePlayerFromGame(target, true, false);

    player.sendMessage(MessageFormatter.format(Messages.getMessage("message-player_kickedplayer"), "{PLAYER}", target.getDisplayName()));

    game.tellAllPlayers(MessageFormatter.format(Messages.getMessage("announce-player_kicked"), "{PLAYER}", target.getDisplayName()), target);
    target.sendMessage(Messages.getMessage("message-player_kicked"));

    return true;
  }

  public String getUsage()
  {
    return "/spleef kick <player>";
  }
}