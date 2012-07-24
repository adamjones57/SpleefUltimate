package com.skitscape.spleefultimate;

import java.io.File;



import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.skitscape.spleefultimate.listeners.BlockDamagePreventListener;
import com.skitscape.spleefultimate.listeners.BlockPlacePreventListener;
import com.skitscape.spleefultimate.listeners.ClickSignListener;
import com.skitscape.spleefultimate.listeners.ItemDropPreventListener;
import com.skitscape.spleefultimate.listeners.PlayerDeathListener;
import com.skitscape.spleefultimate.listeners.PlayerDisconnectListener;
import com.skitscape.spleefultimate.listeners.PlayerLoseListener;
import com.skitscape.spleefultimate.listeners.PlayerSpleefListener;
import com.skitscape.spleefultimate.listeners.PlayerTeleportListener;
import com.skitscape.spleefultimate.listeners.WandSelectListener;
import com.skitscape.spleefultimate.util.LogHelper;


public class SpleefUltimate extends JavaPlugin
{
  private PluginDescriptionFile pdfFile;
  private PluginManager pm;
  private BlockDamagePreventListener blockDamagePreventListener;
  private ItemDropPreventListener itemDropPreventListener;
  private PlayerDeathListener playerDeathListener;
  private BlockPlacePreventListener blockPlacePreventListener;
  private PlayerDisconnectListener playerDisconnectListener;
  private PlayerLoseListener playerLoseListener;
  private PlayerSpleefListener playerSpleefListener;
  private PlayerTeleportListener playerTeleportListener;
  private WandSelectListener wandSelectListener;
  private ClickSignListener clickSignListener;
  private CommandHandler commandHandler;
  public static File dataFolder;
  public YamlConfiguration messagesConfig;

  public void onEnable()
  {
    this.pdfFile = getDescription();

    this.pm = getServer().getPluginManager();

    dataFolder = getDataFolder();

    this.blockDamagePreventListener = new BlockDamagePreventListener();
    this.blockPlacePreventListener = new BlockPlacePreventListener();
    this.itemDropPreventListener = new ItemDropPreventListener();
    this.playerDeathListener = new PlayerDeathListener();
    this.playerDisconnectListener = new PlayerDisconnectListener();
    this.playerLoseListener = new PlayerLoseListener();
    this.playerSpleefListener = new PlayerSpleefListener();
    this.playerTeleportListener = new PlayerTeleportListener();
    this.wandSelectListener = new WandSelectListener();
    this.clickSignListener = new ClickSignListener();
    
    this.pm.registerEvents(blockDamagePreventListener, this);
    this.pm.registerEvents(blockPlacePreventListener, this);
    this.pm.registerEvents(itemDropPreventListener, this);
    this.pm.registerEvents(playerDeathListener, this);
    this.pm.registerEvents(playerDisconnectListener, this);
    this.pm.registerEvents(playerLoseListener, this);
    this.pm.registerEvents(playerSpleefListener, this);
    this.pm.registerEvents(playerTeleportListener, this);
    this.pm.registerEvents(wandSelectListener, this);
    this.pm.registerEvents(clickSignListener, this);
    this.commandHandler = new CommandHandler(this);

    getCommand("spleef").setExecutor(this.commandHandler);

    this.messagesConfig = ConfigManager.getConfiguration("messages.yml");
    if (this.messagesConfig != null)
    {
      ConfigurationSection messagesSection = this.messagesConfig.getConfigurationSection("messages");
      if (messagesSection != null) {
        Messages.loadMessages(messagesSection);
      }
    }
    else
    {
      this.messagesConfig = new YamlConfiguration();
      this.messagesConfig.options().header(
        "See http://dev.bukkit.org/server-mods/spleefultimate for more information");

      Messages.saveMessages(this.messagesConfig.createSection("messages"));

      ConfigManager.saveConfiguration("messages.yml", this.messagesConfig);
    }


    LogHelper.log(this.pdfFile.getName() + " version " + this.pdfFile.getVersion() + " enabled.");
  }

  public void onDisable()
  {
    LogHelper.log(this.pdfFile.getName() + " version " + this.pdfFile.getVersion() + " disabled.");
  }

  public PluginDescriptionFile getPDF()
  {
    return this.pdfFile;
  }
}