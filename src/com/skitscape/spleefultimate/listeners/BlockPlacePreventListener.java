package com.skitscape.spleefultimate.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.PlayerManager;

public class BlockPlacePreventListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGH)
  public void onBlockPlace(BlockPlaceEvent event)
  {
    Player player = event.getPlayer();
    if (player == null) {
      return;
    }
    if (!PlayerManager.isInGame(player)) {
      return;
    }

    event.setCancelled(true);

    player.sendMessage(Messages.getMessage("error-player_placeblocksingame"));
  }
}