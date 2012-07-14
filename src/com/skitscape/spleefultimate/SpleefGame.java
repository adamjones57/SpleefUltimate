package com.skitscape.spleefultimate;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Logger;

import com.spleefultimate.util.ConfigHelper;
import com.spleefultimate.util.MessageFormatter;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SpleefGame
{
  private Boolean active;
  private Boolean counting;
  
  private Boolean enabled;
  private double joinCost;
  private double reward;
  private HashMap<String, Location> tps;
  private int maxPlayers;
  private SpleefArena arena;
  private Vector<Player> players;
  private Vector<Player> formerPlayers;
  
  private int countdown;

public int getCountdown() {
	return countdown;
}

public void setCountdown(int countdown) {
	this.countdown = countdown;
}

public SpleefGame()
  {
    this.active = Boolean.valueOf(false);
    this.setCounting(Boolean.valueOf(false));
    this.enabled = Boolean.valueOf(true);
    
    this.joinCost = 0.0D;
    this.reward = 0.0D;

    this.tps = new HashMap<String, Location>();

    this.maxPlayers = 0;

    this.arena = new SpleefArena();

    this.players = new Vector<Player>();
    this.setFormerPlayers(new Vector<Player>());
  }

  public void setMaxPlayers(int amount)
  {
    if ((amount >= 2) || (amount == 0))
      this.maxPlayers = amount;
  }

  public void setJoinCost(double amount)
  {
    if (amount >= 0.0D)
      this.joinCost = amount;
  }

  public void setReward(double amount)
  {
    if (amount >= 0.0D)
      this.reward = amount;
  }

  public int getMaxPlayers()
  {
    return this.maxPlayers;
  }

  public SpleefArena getArena()
  {
    return this.arena;
  }

  public double getJoinCost()
  {
    return this.joinCost;
  }

  public double getReward()
  {
    return this.reward;
  }

  public Boolean getEnabled() {
	return enabled;
  }

  public void setEnabled(Boolean enabled) {
	this.enabled = enabled;
  }

public boolean isActive()
  {
    return this.active.booleanValue();
  }

  public int getPlayerNumber()
  {
    return this.players.size();
  }

  public boolean isFull()
  {
    return (getMaxPlayers() != 0) && (getPlayerNumber() >= this.maxPlayers);
  }

  public boolean hasPlayer(Player player)
  {
    return this.players.contains(player);
  }

  public void addPlayer(Player player)
  {
    if (player == null)
      return;
    if (isFull())
      return;
    if (hasPlayer(player)) {
      return;
    }
    this.players.add(player);
  }

  public void removePlayer(Player player, boolean addToFormerPlayers)
  {
    if (!hasPlayer(player)) {
      return;
    }
    if ((addToFormerPlayers) && (isActive())) {
      this.getFormerPlayers().add(player);
    }
    this.players.remove(player);

    if (hasTp("lose"))
      player.teleport(getTp("lose"));
  }

  public void start()
  {
	  StartSpleefTask startTask = new StartSpleefTask(countdown, this);
	  startTask.run();
  }

  public void stop(boolean manualStop)
  {
    if (!isActive()) {
      return;
    }
    if (manualStop)
    {
      for (Player player : this.players)
      {
        removePlayer(player, true);
      }

      tellAllPlayers(Messages.getMessage("announce-gamewasstopped"));
    }

    this.active = Boolean.valueOf(false);

    reset();
  }

  public void reset()
  {
    getArena().restoreState();
  }

  public void checkWin()
  {
    if ((isActive()) && (getPlayerNumber() <= 1))
    {
      stop(false);

      if (this.players.size() == 0) {
        return;
      }
      Player winner = (Player)this.players.get(0);
      if (winner == null) {
        return;
      }
      handleWin(winner);

      removePlayer(winner, false);

      if (hasTp("win"))
        winner.teleport(getTp("win"));
    }
  }

  private void handleWin(Player winner)
  {
    String winnerName = winner.getDisplayName();

    tellFormerPlayers(MessageFormatter.format(Messages.getMessage("announce-playerwon"), "{PLAYER}", winnerName), winner);
    winner.sendMessage(Messages.getMessage("message-playerwongame"));

   
      }
    
  
  
  public Location getTp(String id)
  {
    if ((id == null) || (!hasTp(id))) {
      return null;
    }
    return (Location)this.tps.get(id);
  }

  public void setTp(Object tpName, Location point)
  {
    if ((tpName == null) || (point == null)) {
      return;
    }
    this.tps.put((String) tpName, point);
  }

  public void removeTp(String id)
  {
    if (id == null) {
      return;
    }
    if (hasTp(id))
      this.tps.remove(id);
  }

  public boolean hasTp(String id)
  {
    if (!this.tps.containsKey(id)) {
      return false;
    }
    return this.tps.get(id) != null;
  }

  public void tellAllPlayers(String message)
  {
    tellAllPlayers(message, null);
  }

  public void tellAllPlayers(String message, Player exception)
  {
    for (Player player : this.players)
    {
      if ((player != null) && 
        (player == exception)) {
        break;
      }
      player.sendMessage(message);
    }

    for (Player formerPlayer : this.getFormerPlayers())
    {
      if ((formerPlayer != null) && 
        (formerPlayer == exception)) {
        break;
      }
      formerPlayer.sendMessage(message);
    }
  }

  public void tellActivePlayers(String message)
  {
    tellActivePlayers(message, null);
  }

  public void tellActivePlayers(String message, Player exception)
  {
    for (Player player : this.players)
    {
      if ((player != null) && 
        (player == exception)) {
        break;
      }
      player.sendMessage(message);
    }
  }

  public void tellFormerPlayers(String message)
  {
    tellAllPlayers(message, null);
  }

  public void tellFormerPlayers(String message, Player exception)
  {
    for (Player formerPlayer : this.getFormerPlayers())
    {
      if ((formerPlayer != null) && 
        (formerPlayer == exception)) {
        break;
      }
      formerPlayer.sendMessage(message);
    }
  }

  public void load(ConfigurationSection parentSection)
  {
    if (parentSection == null) {
      return;
    }

    this.joinCost = parentSection.getDouble("joincost", 0.0D);
    this.reward = parentSection.getDouble("reward", 0.0D);
    this.maxPlayers = parentSection.getInt("maxPlayers", 0);
    this.countdown = parentSection.getInt("countdown", 0);
    this.enabled = parentSection.getBoolean("enabled", true);

    this.arena.load(parentSection);

    ConfigurationSection tpsSection = parentSection.getConfigurationSection("tps");
    if (tpsSection != null)
    {
      Set<String> tpNames = tpsSection.getKeys(false);
      if ((tpNames != null) && (tpNames.size() > 0))
      {
        for (Object tpName : tpNames)
        {
          ConfigurationSection tpSection = tpsSection.getConfigurationSection((String) tpName);

          Location tpLocation = ConfigHelper.readLocation(tpSection);

          if (tpLocation != null)
            setTp(tpName, tpLocation);
        }
      }
    }
  }

  public void save(ConfigurationSection parentSection)
  {
    if (parentSection == null) {
      return;
    }

    parentSection.set("joincost", Double.valueOf(this.joinCost));
    parentSection.set("reward", Double.valueOf(this.reward));
    parentSection.set("maxplayers", Integer.valueOf(this.maxPlayers));
    parentSection.set("countdown", Integer.valueOf(this.countdown));
    parentSection.set("enabled", Boolean.valueOf(this.enabled));

    this.arena.save(parentSection);

    ConfigurationSection tpsSection = parentSection.createSection("tps");
    for (String tpName : this.tps.keySet())
    {
      ConfigurationSection tpSection = tpsSection.createSection(tpName);

      Location tpLocation = (Location)this.tps.get(tpName);

      ConfigHelper.writeLocation(tpSection, tpLocation);
    }
  }
  public Boolean getActive() {
	return active;
}

public void setActive(Boolean active) {
	Logger.getLogger("Minecraft").info("Setting active");
	this.active = active;
}

public Vector<Player> getFormerPlayers() {
	return formerPlayers;
}

public void setFormerPlayers(Vector<Player> formerPlayers) {
	this.formerPlayers = formerPlayers;
}

public Boolean getCounting() {
	return counting;
}

public void setCounting(Boolean counting) {
	this.counting = counting;
}
}