package com.skitscape.spleefultimate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;



import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import com.skitscape.spleefultimate.commands.AddGameCommand;
import com.skitscape.spleefultimate.commands.AddIgnoredRegionCommand;
import com.skitscape.spleefultimate.commands.AddLoseRegionCommand;
import com.skitscape.spleefultimate.commands.AddSpleefRegionCommand;
import com.skitscape.spleefultimate.commands.DisableCommand;
import com.skitscape.spleefultimate.commands.EnableCommand;
import com.skitscape.spleefultimate.commands.HelpCommand;
import com.skitscape.spleefultimate.commands.JoinCommand;
import com.skitscape.spleefultimate.commands.KickCommand;
import com.skitscape.spleefultimate.commands.LeaveCommand;
import com.skitscape.spleefultimate.commands.RemoveDefaultCommand;
import com.skitscape.spleefultimate.commands.RemoveGameCommand;
import com.skitscape.spleefultimate.commands.RemoveIgnoredRegionCommand;
import com.skitscape.spleefultimate.commands.RemoveLoseRegionCommand;
import com.skitscape.spleefultimate.commands.RemoveSpleefRegionCommand;
import com.skitscape.spleefultimate.commands.RemoveTpCommand;
import com.skitscape.spleefultimate.commands.RenameGameCommand;
import com.skitscape.spleefultimate.commands.ResetCommand;
import com.skitscape.spleefultimate.commands.ReturnCommand;
import com.skitscape.spleefultimate.commands.SaveCommand;
import com.skitscape.spleefultimate.commands.SetDefaultCommand;
import com.skitscape.spleefultimate.commands.SetMaxPlayersCommand;
import com.skitscape.spleefultimate.commands.SetTpCommand;
import com.skitscape.spleefultimate.commands.SetWandTypeCommand;
import com.skitscape.spleefultimate.commands.SpectateCommand;
import com.skitscape.spleefultimate.commands.StartCommand;
import com.skitscape.spleefultimate.commands.StopCommand;
import com.skitscape.spleefultimate.commands.SubCommand;
import com.skitscape.spleefultimate.commands.WandCommand;


public class CommandHandler
  implements CommandExecutor
{
  private SpleefUltimate plugin_;
  private HashMap<String, SubCommand> subCommands_;

  public CommandHandler(SpleefUltimate plugin)
  {
    this.plugin_ = plugin;

    this.subCommands_ = new HashMap();
    setupCommands();
  }

  private void setupCommands()
  {
    this.subCommands_.put("addgame", new AddGameCommand());
    this.subCommands_.put("addignoredregion", new AddIgnoredRegionCommand());
    this.subCommands_.put("addloseregion", new AddLoseRegionCommand());
    this.subCommands_.put("addspleefregion", new AddSpleefRegionCommand());
    this.subCommands_.put("join", new JoinCommand());
    this.subCommands_.put("kick", new KickCommand());
    this.subCommands_.put("leave", new LeaveCommand());
    this.subCommands_.put("removegame", new RemoveGameCommand());
    this.subCommands_.put("removeignoredregion", new RemoveIgnoredRegionCommand());
    this.subCommands_.put("removeloseregion", new RemoveLoseRegionCommand());
    this.subCommands_.put("removespleefregion", new RemoveSpleefRegionCommand());
    this.subCommands_.put("removetp", new RemoveTpCommand());
    this.subCommands_.put("renamegame", new RenameGameCommand());
    this.subCommands_.put("reset", new ResetCommand());
    this.subCommands_.put("return", new ReturnCommand());
    this.subCommands_.put("save", new SaveCommand());
    this.subCommands_.put("setmaxplayers", new SetMaxPlayersCommand());
    this.subCommands_.put("settp", new SetTpCommand());
    this.subCommands_.put("setwandtype", new SetWandTypeCommand());
    this.subCommands_.put("spectate", new SpectateCommand());
    this.subCommands_.put("start", new StartCommand());
    this.subCommands_.put("stop", new StopCommand());
    this.subCommands_.put("wand", new WandCommand());
    this.subCommands_.put("setdefault", new SetDefaultCommand());
    this.subCommands_.put("removedefault", new RemoveDefaultCommand());
    this.subCommands_.put("enable", new EnableCommand());
    this.subCommands_.put("disable", new DisableCommand());
    
    this.subCommands_.put("help", new HelpCommand(this));
  }

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (!(sender instanceof Player))
    {
      sender.sendMessage("[" + this.plugin_.getPDF().getName() + "] " + Messages.getMessage("error-onlyplayerscanusecommands"));

      return true;
    }

    Player player = (Player)sender;

    if (args.length == 0)
    {
      player.sendMessage(this.plugin_.getPDF().getName() + " v" + this.plugin_.getPDF().getVersion());
      player.sendMessage(this.plugin_.getPDF().getDescription());

      return false;
    }

    if (args.length >= 1)
    {
      String subCommandName = args[0].toLowerCase();

      if (this.subCommands_.containsKey(subCommandName))
      {
        Vector cutArgs = new Vector();
        cutArgs.addAll(Arrays.asList(args));
        cutArgs.remove(0);

        SubCommand subCommand = (SubCommand)this.subCommands_.get(subCommandName);

        if (!subCommand.onCommand(player, (String[])cutArgs.toArray(new String[0])))
        {
          player.sendMessage(ChatColor.RED + subCommand.getUsage());

          return true;
        }

        return true;
      }

    }

    return false;
  }

  public HashMap<String, SubCommand> getSubCommands()
  {
    return this.subCommands_;
  }
}