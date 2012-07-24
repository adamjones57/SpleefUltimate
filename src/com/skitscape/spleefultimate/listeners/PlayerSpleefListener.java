package com.skitscape.spleefultimate.listeners;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.PlayerManager;
import com.skitscape.spleefultimate.SpleefGame;

public class PlayerSpleefListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGH)
  public void onPlayerInteract(PlayerInteractEvent event)
  {
    Action action = event.getAction();

    if (!action.equals(Action.LEFT_CLICK_BLOCK)) {
      return;
    }

    Player player = event.getPlayer();
    if (player == null)
      return;
    if (!PlayerManager.isInGame(player)) {
      return;
    }

    Block block = event.getClickedBlock();
    if (block == null)
      return;
    if ((block.isEmpty()) || (block.isLiquid()))
      return;
    if (block.getType() == Material.BEDROCK) {
      return;
    }

    SpleefGame game = GameManager.getGame(player);
    if (!game.isActive()) {
      return;
    }
    Logger.getLogger("Minecraft").info("Interacted: active");

    if (game.getArena().containsSpleefBlock(block))
      block.setType(Material.AIR);
  }
}