package com.skitscape.spleefultimate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class Messages
{
  private static HashMap<String, String> messages = new HashMap<String, String>();

  private static HashMap<String, String> defaultMessages = createDefaultMessages();

  private static HashMap<String, String> createDefaultMessages()
  {
    HashMap<String, String> defaultMessages = new HashMap<String, String>();

    defaultMessages.put("announce-game_wasstopped", ChatColor.RED + "The game was stopped.");
    defaultMessages.put("error-game_allowsnospectators", ChatColor.RED + "The game does not allow spectators!");
    defaultMessages.put("error-game_atleastonespleefandoneloseregion", ChatColor.RED + "At least one Spleef region and one lose region have to be defined before starting a game!");
    defaultMessages.put("error-game_atleasttwoplayers", ChatColor.RED + "At least two players are required for a Spleef game!");
    defaultMessages.put("error-game_doesnothavethattp", ChatColor.RED + "The game has no teleportation point with that ID!");
    defaultMessages.put("error-game_hasnojoinpoint", ChatColor.RED + "The game has no join point, so you can't join!");
    defaultMessages.put("error-game_isdisabled", ChatColor.RED + "The game is disabled!");
    defaultMessages.put("error-game_isfull", ChatColor.RED + "The game is full.");
    defaultMessages.put("error-game_teleportationforbidden", ChatColor.RED + "Teleportation is forbidden in a Spleef game!");
    defaultMessages.put("error-onlyplayerscanusecommands", "Only players are able to use commands!");
    defaultMessages.put("error-player_alreadyplaying", ChatColor.RED + "You are already playing!");
    defaultMessages.put("error-player_cannotjoinrunninggame", ChatColor.RED + "You can't join a running Spleef game!");
    defaultMessages.put("error-player_nopermission", ChatColor.RED + "You have no permission!");
    defaultMessages.put("error-player_noreturnlocation", ChatColor.RED + "You have no location to return to!");
    defaultMessages.put("error-player_notplaying", ChatColor.RED + "You aren't playing Spleef!");
    defaultMessages.put("error-player_placeblocksingame", ChatColor.RED + "You can't place blocks in a Spleef game!");
    defaultMessages.put("error-regions_havetousewand", ChatColor.LIGHT_PURPLE + "You have to use the wand!");
    defaultMessages.put("error-regions_inoneworldonly", ChatColor.RED + "The region selection blocks can be in one world only!");
    defaultMessages.put("error-regions_notmaderegionselection", ChatColor.LIGHT_PURPLE + "You have not made a region selection yet!");
    defaultMessages.put("error-teleportationfailed", ChatColor.RED + "Teleportation failed.");
    defaultMessages.put("message-block1set", ChatColor.LIGHT_PURPLE + "Block #1 set.");
    defaultMessages.put("message-block2set", ChatColor.LIGHT_PURPLE + "Block #2 set.");
    defaultMessages.put("message-game_tpremoved", ChatColor.BLUE + "Teleportation point removed.");
    defaultMessages.put("message-game_tpset", ChatColor.BLUE + "Teleportation point set.");
    defaultMessages.put("message-money_accountneededtogetmoney", ChatColor.RED + "You need an account to win money!");
    defaultMessages.put("message-money_accountneededtojoin", ChatColor.RED + "You need an account to join this game!");
    defaultMessages.put("message-player_died", ChatColor.BLUE + "You died and were removed from your game.");
    defaultMessages.put("message-player_disconnected", ChatColor.BLUE + "You got disconnected and were removed from your game.");
    defaultMessages.put("message-player_joined", ChatColor.BLUE + "You joined the game.");
    defaultMessages.put("message-player_kicked", ChatColor.RED + "You were kicked from the game!");
    defaultMessages.put("message-player_left", ChatColor.BLUE + "You left the game.");
    defaultMessages.put("message-player_lost", ChatColor.BLUE + "You are out!");
    defaultMessages.put("message-player_resetgame", ChatColor.BLUE + "You reset the arena.");
    defaultMessages.put("message-player_startedgame", ChatColor.BLUE + "You started the game.");
    defaultMessages.put("message-player_stoppedgame", ChatColor.BLUE + "You stopped the game.");
    defaultMessages.put("message-player_wongame", ChatColor.GOLD + "You won the game. Congratulations!");
    defaultMessages.put("message-regions_wandactivated", ChatColor.LIGHT_PURPLE + "Wand activated (use a wooden pickaxe).");
    defaultMessages.put("message-regions_wanddeactivated", ChatColor.LIGHT_PURPLE + "Wand deactivated.");
    defaultMessages.put("message-savedgames", ChatColor.GREEN + "Saved all Spleef games.");

    defaultMessages.put("announce-player_died", ChatColor.GOLD + "{PLAYER} died.");
    defaultMessages.put("announce-player_disconnected", ChatColor.GOLD + "{PLAYER} got disconnected.");
    defaultMessages.put("announce-player_joined", ChatColor.BLUE + "{PLAYER} joined the game.");
    defaultMessages.put("announce-player_kicked", ChatColor.GOLD + "{PLAYER} was kicked from the game.");
    defaultMessages.put("announce-player_left", ChatColor.BLUE + "{PLAYER} left the game.");
    defaultMessages.put("announce-player_lost", ChatColor.GOLD + "{PLAYER} is out!");
    defaultMessages.put("announce-player_startedgame", ChatColor.BLUE + "{PLAYER} started the game. Good Luck!");
    defaultMessages.put("announce-player_won", ChatColor.GOLD + "{PLAYER} won the game!");
    defaultMessages.put("error-game_alreadydisabled", ChatColor.RED + "The Spleef game with the ID \"{ID}\" was already disabled!");
    defaultMessages.put("error-game_alreadyenabled", ChatColor.RED + "The Spleef game with the ID \"{ID}\" was already enabled!");
    defaultMessages.put("error-game_alreadyexists", ChatColor.RED + "A game with the ID \"{ID}\" already exists!");
    defaultMessages.put("error-game_alreadyrunning", ChatColor.RED + "The Spleef game with the ID \"{ID}\" is already running!");
    defaultMessages.put("error-game_doesnotexist", ChatColor.RED + "A game with the ID \"{ID}\" does not exist!");
    defaultMessages.put("error-game_doesnothavedefault", ChatColor.RED + "The server has no default game.");
    defaultMessages.put("error-game_notrunning", ChatColor.RED + "The Spleef game with the ID \"{ID}\" is not running!");
    defaultMessages.put("error-game_stillrunning", ChatColor.RED + "The Spleef game with the ID \"{ID}\" is still running, stop it first!");
    defaultMessages.put("error-ignoredregion_alreadyexists", ChatColor.RED + "An ignored region with the ID \"{ID}\" already exists in this game!");
    defaultMessages.put("error-ignoredregion_doesnotexist", ChatColor.RED + "An ignored region with the ID \"{ID}\" does not exist in this game!");
    defaultMessages.put("error-loseregion_alreadyexists", ChatColor.RED + "A lose region with the ID \"{ID}\" already exists in this game!");
    defaultMessages.put("error-loseregion_doesnotexist", ChatColor.RED + "A lose region with the ID \"{ID}\" does not exist in this game!");
    defaultMessages.put("error-novalidnumber", ChatColor.RED + "{NUMBER} is not a valid number.");
    defaultMessages.put("error-player_doesnotexist", ChatColor.RED + "A player with the name \"{PLAYER}\" does not exist on this server!");
    defaultMessages.put("error-player_doesnotplay", ChatColor.RED + "\"{PLAYER}\" does not play Spleef right now!");
    defaultMessages.put("error-regions_wandtypenotsupported", ChatColor.RED + "The wand type \"{TYPE}\" is not supported.");
    defaultMessages.put("error-spleefregion_alreadyexists", ChatColor.RED + "A Spleef region with the ID \"{ID}\" already exists in this game!");
    defaultMessages.put("error-spleefregion_doesnotexist", ChatColor.RED + "A Spleef region with the ID \"{ID}\" does not exist in this game!");
    defaultMessages.put("error-sign_nodefaultset", ChatColor.RED + "There is no default arena on this server.");
    defaultMessages.put("message-game_created", ChatColor.BLUE + "A game with the ID \"{ID}\" was created.");
    defaultMessages.put("message-game_defaultremoved", ChatColor.BLUE + "The default game \"{ID}\" was removed as the default.");
    defaultMessages.put("message-game_removed", ChatColor.BLUE + "The game with the ID \"{ID}\" was removed.");
    defaultMessages.put("message-game_renamed", ChatColor.BLUE + "The game was renamed to \"{NEWID}\".");
    defaultMessages.put("message-game_setjoincost", ChatColor.BLUE + "Join cost set to {MONEYAMOUNT}.");
    defaultMessages.put("message-game_setmaxplayers", ChatColor.BLUE + "Number of maximum players set to {AMOUNT}.");
    defaultMessages.put("message-game_setreward", ChatColor.BLUE + "Reward set to {MONEYAMOUNT}.");
    defaultMessages.put("message-game_setasdefault", ChatColor.BLUE + "The Spleef region with the ID \"{ID}\" was set as the default.");
    defaultMessages.put("message-game_successfullydisabled", ChatColor.BLUE + "The default game \"{ID}\" was disabled.");
    defaultMessages.put("message-game_successfullyenabled", ChatColor.BLUE + "The default game \"{ID}\" was enabled.");
    defaultMessages.put("message-ignoredregion_added", ChatColor.BLUE + "An ignored region with the ID \"{ID}\" was added to the game.");
    defaultMessages.put("message-ignoredregion_removed", ChatColor.BLUE + "The ignored region with the ID \"{ID}\" was removed from the game.");
    defaultMessages.put("message-loseregion_added", ChatColor.BLUE + "A lose region with the ID \"{ID}\" was added to the game.");
    defaultMessages.put("message-loseregion_removed", ChatColor.BLUE + "The lose region with the ID \"{ID}\" was removed from the game.");
    defaultMessages.put("message-money_joincostsubtracted", ChatColor.BLUE + "{MONEYAMOUNT} was removed from your account for joining this game.");
    defaultMessages.put("message-money_needmoremoneytojoin", ChatColor.RED + "You need {MONEYAMOUNT} to join this game!");
    defaultMessages.put("message-money_wonmoney", ChatColor.GOLD + "You won {MONEYAMOUNT}!");
    defaultMessages.put("message-player_kickedplayer", ChatColor.BLUE + "You kicked \"{PLAYER}\" from the game.");
    defaultMessages.put("message-regions_wandtypechanged", ChatColor.LIGHT_PURPLE + "The wand type was changed to \"{TYPE}\".");
    defaultMessages.put("message-spleefregion_added", ChatColor.BLUE + "A Spleef region with the ID \"{ID}\" was added to the game.");
    defaultMessages.put("message-spleefregion_removed", ChatColor.BLUE + "The Spleef region with the ID \"{ID}\" was removed from the game.");

    return defaultMessages;
  }

  public static String getMessage(String id)
  {
    if (messages.containsKey(id)) {
      return (String)messages.get(id);
    }

    if (defaultMessages.containsKey(id)) {
      return (String)defaultMessages.get(id);
    }

    return "";
  }

  public static void loadMessages(ConfigurationSection parentSection)
  {
    if (parentSection == null) {
      return;
    }

    Set<String> keys = parentSection.getKeys(false);
    if ((keys == null) || (keys.size() == 0)) {
      return;
    }
    for (String id : keys)
    {
      if (defaultMessages.containsKey(id))
        messages.put(id, parentSection.getString(id));
    }
  }

  public static void saveMessages(ConfigurationSection parentSection)
  {
    if (parentSection == null) {
      return;
    }

    Vector<String> messageIds = new Vector<String>();
    messageIds.addAll(defaultMessages.keySet());
    Collections.sort(messageIds);

    for (int i = 0; i < messageIds.size(); i++)
    {
      String id = (String)messageIds.get(i);

      if (messages.containsKey(id))
      {
        parentSection.set(id, messages.get(id));
      }
      else
      {
        parentSection.set(id, defaultMessages.get(id));
      }
    }
  }
}