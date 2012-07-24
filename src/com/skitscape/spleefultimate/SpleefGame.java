package com.skitscape.spleefultimate;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Logger;



import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.skitscape.spleefultimate.util.ConfigHelper;
import com.skitscape.spleefultimate.util.MessageFormatter;


public class SpleefGame
{
  private Boolean active;
  private Boolean counting;
  
  private Boolean enabled;
  private double joinCost_;
  private double reward_;
  private HashMap<String, Location> tps_;
  private int maxPlayers_;
  private SpleefArena arena_;
  private Vector<Player> players_;
  private Vector<Player> formerPlayers_;
  
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
    
    this.joinCost_ = 0.0D;
    this.reward_ = 0.0D;

    this.tps_ = new HashMap();

    this.maxPlayers_ = 0;

    this.arena_ = new SpleefArena();

    this.players_ = new Vector();
    this.setFormerPlayers_(new Vector());
  }

  public void setMaxPlayers(int amount)
  {
    if ((amount >= 2) || (amount == 0))
      this.maxPlayers_ = amount;
  }

  public void setJoinCost(double amount)
  {
    if (amount >= 0.0D)
      this.joinCost_ = amount;
  }

  public void setReward(double amount)
  {
    if (amount >= 0.0D)
      this.reward_ = amount;
  }

  public int getMaxPlayers()
  {
    return this.maxPlayers_;
  }

  public SpleefArena getArena()
  {
    return this.arena_;
  }

  public double getJoinCost()
  {
    return this.joinCost_;
  }

  public double getReward()
  {
    return this.reward_;
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
    return this.players_.size();
  }

  public boolean isFull()
  {
    return (getMaxPlayers() != 0) && (getPlayerNumber() >= this.maxPlayers_);
  }

  public boolean hasPlayer(Player player)
  {
    return this.players_.contains(player);
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
    this.players_.add(player);
  }

  public void removePlayer(Player player, boolean addToFormerPlayers)
  {
    if (!hasPlayer(player)) {
      return;
    }
    if ((addToFormerPlayers) && (isActive())) {
      this.getFormerPlayers_().add(player);
    }
    this.players_.remove(player);

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
      for (Player player : this.players_)
      {
        removePlayer(player, true);
      }

      tellAllPlayers(Messages.getMessage("announce-game_wasstopped"));
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

      if (this.players_.size() == 0) {
        return;
      }
      Player winner = (Player)this.players_.get(0);
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

    tellFormerPlayers(MessageFormatter.format(Messages.getMessage("announce-player_won"), "{PLAYER}", winnerName), winner);
    winner.sendMessage(Messages.getMessage("message-player_wongame"));
  }
  
  public Location getTp(String id)
  {
    if ((id == null) || (!hasTp(id))) {
      return null;
    }
    return (Location)this.tps_.get(id);
  }

  public void setTp(Object tpName, Location point)
  {
    if ((tpName == null) || (point == null)) {
      return;
    }
    this.tps_.put((String) tpName, point);
  }

  public void removeTp(String id)
  {
    if (id == null) {
      return;
    }
    if (hasTp(id))
      this.tps_.remove(id);
  }

  public boolean hasTp(String id)
  {
    if (!this.tps_.containsKey(id)) {
      return false;
    }
    return this.tps_.get(id) != null;
  }

  public void tellAllPlayers(String message)
  {
    tellAllPlayers(message, null);
  }

  public void tellAllPlayers(String message, Player exception)
  {
    for (Player player : this.players_)
    {
      if ((player != null) && 
        (player == exception)) {
        break;
      }
      player.sendMessage(message);
    }

    for (Player formerPlayer : this.getFormerPlayers_())
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
    for (Player player : this.players_)
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
    for (Player formerPlayer : this.getFormerPlayers_())
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

    this.joinCost_ = parentSection.getDouble("joincost", 0.0D);
    this.reward_ = parentSection.getDouble("reward", 0.0D);
    this.maxPlayers_ = parentSection.getInt("maxPlayers", 0);
    this.countdown = parentSection.getInt("countdown", 0);
    this.enabled = parentSection.getBoolean("enabled", true);

    this.arena_.load(parentSection);

    ConfigurationSection tpsSection = parentSection.getConfigurationSection("tps");
    if (tpsSection != null)
    {
      Set tpNames = tpsSection.getKeys(false);
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

    parentSection.set("joincost", Double.valueOf(this.joinCost_));
    parentSection.set("reward", Double.valueOf(this.reward_));
    parentSection.set("maxplayers", Integer.valueOf(this.maxPlayers_));
    parentSection.set("countdown", Integer.valueOf(this.countdown));
    parentSection.set("enabled", Boolean.valueOf(this.enabled));

    this.arena_.save(parentSection);

    ConfigurationSection tpsSection = parentSection.createSection("tps");
    for (String tpName : this.tps_.keySet())
    {
      ConfigurationSection tpSection = tpsSection.createSection(tpName);

      Location tpLocation = (Location)this.tps_.get(tpName);

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

public Vector<Player> getFormerPlayers_() {
	return formerPlayers_;
}

public void setFormerPlayers_(Vector<Player> formerPlayers_) {
	this.formerPlayers_ = formerPlayers_;
}

public Boolean getCounting() {
	return counting;
}

public void setCounting(Boolean counting) {
	this.counting = counting;
}
}