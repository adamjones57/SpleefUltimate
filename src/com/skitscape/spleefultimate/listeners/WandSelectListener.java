package com.skitscape.spleefultimate.listeners;


import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.PlayerManager;
import com.skitscape.spleefultimate.RegionSelections;
import com.skitscape.spleefultimate.Selection;

public class WandSelectListener implements Listener
{
	@EventHandler
  public void onPlayerInteract(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    if (player == null) {
      return;
    }

    if (PlayerManager.isInGame(player)) {
      return;
    }
    if (!RegionSelections.hasSelection(player)) {
      return;
    }
    ItemStack itemStack = player.getItemInHand();
    if (itemStack == null) {
      return;
    }
    if (itemStack.getTypeId() != 270) {
      return;
    }
    Block clickedBlock = event.getClickedBlock();
    if (clickedBlock == null) {
      return;
    }
    if ((clickedBlock.isEmpty()) || (clickedBlock.isLiquid())) {
      return;
    }
    Action action = event.getAction();

    if (action.equals(Action.LEFT_CLICK_BLOCK))
    {
      setPos1(player, clickedBlock);

      event.setCancelled(true);
    }
    else if (action.equals(Action.RIGHT_CLICK_BLOCK))
    {
      setPos2(player, clickedBlock);

      event.setCancelled(true);
    }
  }

  private void setPos1(Player player, Block block)
  {
    if (!player.hasPermission("spleefultimate.admin.wand"))
    {
      player.sendMessage(Messages.getMessage("error-player_nopermission"));

      RegionSelections.setHasSelection(player, false);

      return;
    }

    if (!RegionSelections.hasSelection(player)) {
      return;
    }
    Selection playerSelection = RegionSelections.getSelection(player);

    if (playerSelection.getWorld() != player.getWorld())
    {
      player.sendMessage(Messages.getMessage("error-regions_inoneworldonly"));

      return;
    }

    playerSelection.setPos1(block);

    player.sendMessage(Messages.getMessage("message-block1set"));
  }

  private void setPos2(Player player, Block block)
  {
    if (!player.hasPermission("spleefultimate.admin.wand"))
    {
      player.sendMessage(Messages.getMessage("error-player_nopermission"));

      RegionSelections.setHasSelection(player, false);

      return;
    }

    if (!RegionSelections.hasSelection(player)) {
      return;
    }
    Selection playerSelection = RegionSelections.getSelection(player);

    if (playerSelection.getWorld() != player.getWorld())
    {
      player.sendMessage(Messages.getMessage("error-regions_inoneworldonly"));

      return;
    }

    playerSelection.setPos2(block);

    player.sendMessage(Messages.getMessage("message-block2set"));
  }
}