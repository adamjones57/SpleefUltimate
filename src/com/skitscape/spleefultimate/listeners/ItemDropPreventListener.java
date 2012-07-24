package com.skitscape.spleefultimate.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.skitscape.spleefultimate.PlayerManager;

public class ItemDropPreventListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGH)
  public void onPlayerDropItem(PlayerDropItemEvent event)
  {
    Player player = event.getPlayer();
    if (player == null) {
      return;
    }
    if (!PlayerManager.isInGame(player)) {
      return;
    }

    event.setCancelled(true);
  }
}