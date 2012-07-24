package com.skitscape.spleefultimate.commands;


import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class RenameGameCommand extends SubCommand
{
  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 2) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.admin.renamegame"))
    {
      player.sendMessage(Messages.getMessage("error-player_nopermission"));

      return true;
    }

    if (!GameManager.hasGame(args[0]))
    {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_doesnotexist"), "{ID}", 
        args[0]));

      return true;
    }

    if (GameManager.hasGame(args[1]))
    {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-game_alreadyexists"), "{ID}", 
        args[1]));

      return true;
    }

    GameManager.renameGame(args[0], args[1]);

    player.sendMessage(MessageFormatter.format(Messages.getMessage("message-game_renamed"), "{NEWID}", 
      args[1]));

    return true;
  }

  public String getUsage()
  {
    return "/spleef renamegame <oldGameId> <newGameId>";
  }
}