package com.skitscape.spleefultimate.listeners;


import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.PlayerManager;
import com.skitscape.spleefultimate.SpleefGame;
import com.skitscape.spleefultimate.util.MessageFormatter;

public class PlayerLoseListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGH)
  public void onPlayerMove(PlayerMoveEvent event)
  {
    Player player = event.getPlayer();

    if (!PlayerManager.isInGame(player))
    {
      return;
    }

    SpleefGame game = GameManager.getGame(player);
    /*if (!game.isActive())
    {
      return;
    }*/

    Location location = player.getLocation();
    
    double ycord = location.getY();
    ycord--;
    location.setY(ycord);

    Block block = location.getBlock();

    if (game.getArena().containsLoseBlock(block))
    {
    	if(!game.isActive()) {
    		player.teleport(game.getTp("join"));
    		return;
    	}
      PlayerManager.removePlayerFromGame(player, true, true);

      game.tellAllPlayers(MessageFormatter.format(Messages.getMessage("announce-player_lost"), "{PLAYER}", player.getDisplayName()), player);
      player.sendMessage(Messages.getMessage("message-player_lost"));
    }
  }
}