package com.skitscape.spleefultimate.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.PlayerManager;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class PlayerDisconnectListener implements Listener
{
	@EventHandler
  public void onPlayerQuit(PlayerQuitEvent event)
  {
    Player player = event.getPlayer();

    if (!PlayerManager.isInGame(player))
    {
      return;
    }

    SpleefGame game = GameManager.getGame(player);

    PlayerManager.removePlayerFromGame(player, true, false);

    game.tellAllPlayers(MessageFormatter.format(Messages.getMessage("announce-player_disconnected"), "{PLAYER}", player.getDisplayName()), player);
    player.sendMessage(Messages.getMessage("message-player_disconnected"));
  }
}