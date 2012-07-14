package com.skitscape.spleefultimate;

import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import com.spleefultimate.util.LogHelper;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.ConfigurationSection;

public class SpleefArena
{
  private Hashtable<Object, Selection> spleefRegions;
  private Hashtable<Object, Selection> ignoredRegions;
  private Hashtable<Object, Selection> loseRegions;
  private BlockState[] arenaState;
  private World world;

  public SpleefArena()
  {
    this.spleefRegions = new Hashtable();
    this.loseRegions = new Hashtable();
    this.ignoredRegions = new Hashtable();
  }

  public boolean containsSpleefBlock(Block block)
  {
	 
    for (Selection cuboid : this.spleefRegions.values())
    {
      if ((cuboid.containsBlock(block)) && 
        (!containsIgnoredBlock(block))) {
        return true;
      }
    }
    return false;
  }

  public boolean containsLoseBlock(Block block)
  {
    for (Selection cuboid : this.loseRegions.values())
    {
      if ((cuboid.containsBlock(block)) && 
        (!containsIgnoredBlock(block))) {
        return true;
      }
    }
    return false;
  }

  public boolean containsIgnoredBlock(Block block)
  {
    for (Selection cuboid : this.ignoredRegions.values())
    {
      if (cuboid.containsBlock(block)) {
        return true;
      }
    }
    return false;
  }

  public boolean hasSpleefRegion(Object regionName)
  {
    return this.spleefRegions.containsKey(regionName);
  }

  public boolean hasLoseRegion(Object regionName)
  {
    return this.loseRegions.containsKey(regionName);
  }

  public boolean hasIgnoredRegion(Object regionName)
  {
    return this.ignoredRegions.containsKey(regionName);
  }

  public void addSpleefRegion(Object regionName, Selection region)
  {
    Selection regionClone = new Selection(region);

    if (!hasSpleefRegion(regionName))
      this.spleefRegions.put(regionName, regionClone);
  }

  public void addLoseRegion(Object regionName, Selection region)
  {
    Selection regionClone = new Selection(region);

    if (!hasLoseRegion(regionName))
      this.loseRegions.put(regionName, regionClone);
  }

  public void addIgnoredRegion(Object regionName, Selection region)
  {
    Selection regionClone = new Selection(region);

    if (!hasIgnoredRegion(regionName))
      this.ignoredRegions.put(regionName, regionClone);
  }

  public void removeSpleefRegion(Object regionName)
  {
    if (hasSpleefRegion(regionName))
      this.spleefRegions.remove(regionName);
  }

  public void removeLoseRegion(Object regionName)
  {
    if (hasLoseRegion(regionName))
      this.loseRegions.remove(regionName);
  }

  public void removeIgnoredRegion(Object regionName)
  {
    if (hasIgnoredRegion(regionName))
      this.ignoredRegions.remove(regionName);
  }

  public int getSpleefRegionNumber()
  {
    return this.spleefRegions.size();
  }

  public int getLoseRegionNumber()
  {
    return this.loseRegions.size();
  }

  public void saveState()
  {
    Vector<Block> arenaStateList = new Vector<Block>();
    for (Selection spleefRegion : this.spleefRegions.values())
    {
      for (Block spleefBlock : spleefRegion.getSelectedBlocks())
      {
        if (!containsIgnoredBlock(spleefBlock)) {
          arenaStateList.add((Block) spleefBlock.getState());
        }
      }
    }

    this.arenaState = ((BlockState[])arenaStateList.toArray(new BlockState[0]));
  }

  public void restoreState()
  {
    if (this.arenaState != null)
    {
      for (BlockState blockState : this.arenaState)
      {
        blockState.update(true);
      }
    }
  }

  public void load(ConfigurationSection parentSection)
  {
    if (parentSection == null) {
      return;
    }

    ConfigurationSection childSection = parentSection.getConfigurationSection("spleefregions");

    if (childSection != null)
    {
      Set<String> spleefRegionNames = childSection.getKeys(false);
      if (spleefRegionNames != null)
      {
        for (Object name : spleefRegionNames)
        {
          Selection cuboid = new Selection(world);

          if (cuboid.load(childSection.getConfigurationSection((String) name)))
            addSpleefRegion(name, cuboid);
          else {
            LogHelper.log("Loading of Spleef region \"" + name + "\" failed");
          }
        }
      }

    }

    childSection = parentSection.getConfigurationSection("loseregions");

    if (childSection != null)
    {
      Set<String> loseRegionNames = childSection.getKeys(false);
      if (loseRegionNames != null)
      {
        for (Object name : loseRegionNames)
        {
          Selection cuboid = new Selection(world);

          if (cuboid.load(childSection.getConfigurationSection((String) name)))
          {
            addLoseRegion(name, cuboid);
          }
          else LogHelper.log("Loading of lose region \"" + name + "\" failed");
        }

      }

    }

    childSection = parentSection.getConfigurationSection("ignoredregions");

    if (childSection != null)
    {
      Set<String> ignoredRegionNames = childSection.getKeys(false);
      if (ignoredRegionNames != null)
      {
        for (Object name : ignoredRegionNames)
        {
          Selection cuboid = new Selection(world);

          if (cuboid.load(childSection.getConfigurationSection((String) name)))
          {
            addIgnoredRegion(name, cuboid);
          }
          else LogHelper.log("Loading of ignored region \"" + name + "\" failed");
        }
      }
    }
  }

  public void save(ConfigurationSection parentSection)
  {
    if (parentSection == null) {
      return;
    }

    if (getSpleefRegionNumber() != 0)
    {
      ConfigurationSection childSection = parentSection.createSection("spleefregions");

      for (Object regionName : this.spleefRegions.keySet())
      {
        ConfigurationSection regionSection = childSection.createSection((String) regionName);
        Selection cuboid = (Selection)this.spleefRegions.get(regionName);

        cuboid.save(regionSection);
      }

    }

    if (getLoseRegionNumber() != 0)
    {
      ConfigurationSection childSection = parentSection.createSection("loseregions");

      for (Object regionName : this.loseRegions.keySet())
      {
        ConfigurationSection regionSection = childSection.createSection((String) regionName);
        Selection cuboid = (Selection)this.loseRegions.get(regionName);

        cuboid.save(regionSection);
      }

    }

    if (this.ignoredRegions.size() != 0)
    {
      ConfigurationSection childSection = parentSection.createSection("ignoredregions");

      for (Object regionName : this.ignoredRegions.keySet())
      {
        ConfigurationSection regionSection = childSection.createSection((String) regionName);

        Selection cuboid = (Selection)this.ignoredRegions.get(regionName);

        cuboid.save(regionSection);
      }
    }
  }
}