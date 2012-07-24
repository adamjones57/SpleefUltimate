package com.skitscape.spleefultimate.listeners;


import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.PlayerManager;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class PlayerDeathListener implements Listener
{
	@EventHandler
  public void onEntityDeath(EntityDeathEvent event)
  {
    Entity causer = event.getEntity();
    if (!(causer instanceof Player)) {
      return;
    }

    Player player = (Player)causer;

    if (!PlayerManager.isInGame(player))
    {
      return;
    }

    SpleefGame game = GameManager.getGame(player);
    if (!game.isActive())
    {
      return;
    }

    PlayerManager.removePlayerFromGame(player, true, true);

    game.tellAllPlayers(MessageFormatter.format(Messages.getMessage("announce-player_died"), "{PLAYER}", player.getDisplayName()), player);
    player.sendMessage(Messages.getMessage("message-player_died"));
  }
}