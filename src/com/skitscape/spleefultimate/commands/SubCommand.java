package com.skitscape.spleefultimate.commands;

import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.SpleefGame;

public abstract class SubCommand
{
  public abstract boolean onCommand(Player paramPlayer, String[] paramArrayOfString);

  public abstract String getUsage();

  protected SpleefGame getGame(String gameName)
  {
    if (GameManager.hasGame(gameName)) {
      return GameManager.getGame(gameName);
    }
    return null;
  }
}