package com.skitscape.spleefultimate;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerManager
{
  private static HashMap<Player, Location> returnLocations_ = new HashMap();

  public static boolean isInGame(Player player)
  {
    return GameManager.getGame(player) != null;
  }

  public static void removePlayerFromGame(Player player, boolean checkWin, boolean addToFormerPlayers)
  {
    if (!isInGame(player)) {
      return;
    }
    SpleefGame game = GameManager.getGame(player);

    game.removePlayer(player, addToFormerPlayers);

    if (checkWin)
      game.checkWin();
  }

  public static Location getReturnLocation(Player player)
  {
    if (returnLocations_.containsKey(player)) {
      return (Location)returnLocations_.get(player);
    }
    return null;
  }

  public static void setReturnLocation(Player player, Location location)
  {
    if (player == null) {
      return;
    }
    if (location != null)
      returnLocations_.put(player, location);
    else if (returnLocations_.containsKey(player))
      returnLocations_.remove(player);
  }
}