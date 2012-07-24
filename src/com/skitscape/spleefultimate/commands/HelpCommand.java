package com.skitscape.spleefultimate.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.CommandHandler;
import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class HelpCommand extends SubCommand
{
  HashMap<String, SubCommand> subCommands_;
  Vector<String> subCommandKeys;
  int maxPageNumber;
  final int commandsPerPage = 8;
  int pageNumber;

  public HelpCommand(CommandHandler handler)
  {
    this.subCommands_ = handler.getSubCommands();

    this.subCommandKeys = new Vector(this.subCommands_.keySet());
    Collections.sort(this.subCommandKeys);

    this.maxPageNumber = (int)Math.ceil(this.subCommandKeys.size() / 8.0D);
  }

  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 1) {
      return false;
    }

    try
    {
      pageNumber = Integer.valueOf(args[0]).intValue();
    }
    catch (NumberFormatException ex)
    {
      player.sendMessage(MessageFormatter.format(Messages.getMessage("error-novalidnumber"), "{NUMBER}", 
        args[0]));

      return true;
    }
    if (pageNumber < 1)
      pageNumber = 1;
    if (pageNumber > this.maxPageNumber) {
      pageNumber = this.maxPageNumber;
    }

    player.sendMessage(ChatColor.LIGHT_PURPLE + "SpleefExtreme commands - page " + pageNumber + " of " + 
      String.valueOf(this.maxPageNumber));
    String defGameName = GameManager.getDefaultGameName() != null ? GameManager.getDefaultGameName() : "none";
    player.sendMessage(ChatColor.LIGHT_PURPLE + "Current Default game: " + defGameName);
    player.sendMessage(ChatColor.LIGHT_PURPLE + "---------------------------------");
    for (int i = (pageNumber - 1) * 8; i < pageNumber * 8; i++)
    {
      if (i < this.subCommandKeys.size()) {
        player.sendMessage("* " + ((SubCommand)this.subCommands_.get(this.subCommandKeys.elementAt(i))).getUsage());
      }
    }
    return true;
  }

  public String getUsage()
  {
    return "/spleef help <pageNumber>";
  }
}