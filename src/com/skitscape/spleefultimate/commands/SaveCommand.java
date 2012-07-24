package com.skitscape.spleefultimate.commands;

import java.util.logging.Logger;


import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.ConfigManager;
import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;

public class SaveCommand extends SubCommand
{
  private YamlConfiguration gamesConfig_;

  public SaveCommand()
  {
    this.gamesConfig_ = ConfigManager.getConfiguration("games.yml");
    if (this.gamesConfig_ == null) {
      this.gamesConfig_ = new YamlConfiguration();
    }
    GameManager.loadGames(this.gamesConfig_.getConfigurationSection("games"));
    GameManager.loadDefaultGame(this.gamesConfig_.getConfigurationSection("defaultGame"));
    //Logger.getLogger("Minecraft").info("Default game loaded.");
  }

  public boolean onCommand(Player player, String[] args)
  {
    if (args.length != 0) {
      return false;
    }

    if (!player.hasPermission("spleefultimate.admin.save"))
    {
      player.sendMessage(Messages.getMessage("error-player_nopermission"));

      return true;
    }

    this.gamesConfig_ = new YamlConfiguration();
    this.gamesConfig_.options().header("Do NOT edit this file unless you know what you are doing!");
    GameManager.saveGames(this.gamesConfig_.createSection("games"));
    GameManager.saveDefaultGame(this.gamesConfig_.createSection("defaultGame"));

    ConfigManager.saveConfiguration("games.yml", this.gamesConfig_);

    player.sendMessage(Messages.getMessage("message-savedgames"));

    return true;
  }

  public String getUsage()
  {
    return "/spleef save";
  }
}