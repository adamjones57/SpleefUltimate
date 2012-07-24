package com.skitscape.spleefultimate.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.PlayerManager;


public class PlayerTeleportListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGH)
  public void onPlayerTeleport(PlayerTeleportEvent event)
  {
    Player player = event.getPlayer();

    if (!PlayerManager.isInGame(player))
    {
      return;
    }
    if(!(event.getTo().distance(GameManager.getGame(player).getTp("join")) == 0)) {
	    event.setCancelled(true);
	
	    player.sendMessage(Messages.getMessage("error-game_teleportationforbidden"));
    }
  }
}