package com.skitscape.spleefultimate.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

import com.skitscape.spleefultimate.PlayerManager;

public class BlockDamagePreventListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGH)
  public void onBlockDamage(BlockDamageEvent event)
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