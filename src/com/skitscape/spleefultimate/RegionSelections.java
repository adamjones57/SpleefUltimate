package com.skitscape.spleefultimate;

import java.util.HashMap;
import org.bukkit.entity.Player;

public class RegionSelections
{
  private static HashMap<Player, Selection> selections_ = new HashMap();

  public static boolean hasSelection(Player player)
  {
    return selections_.containsKey(player);
  }

  public static void setHasSelection(Player player, boolean selectionEnabled)
  {
    if (selectionEnabled)
    {
      if (!hasSelection(player)) {
        selections_.put(player, new Selection(player.getWorld()));
      }

    }
    else if (hasSelection(player))
      selections_.remove(player);
  }

  public static Selection getSelection(Player player)
  {
    if (hasSelection(player)) {
      return (Selection)selections_.get(player);
    }
    return null;
  }

  public static void setSelectionType(Player player, SelectionType type)
  {
    if (selections_.containsKey(player))
      ((Selection)selections_.get(player)).setType(type);
  }
}