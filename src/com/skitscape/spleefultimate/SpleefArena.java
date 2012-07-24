package com.skitscape.spleefultimate;

import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;


import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.ConfigurationSection;

import com.skitscape.spleefultimate.util.LogHelper;

public class SpleefArena
{
	//TODO: Decide what type of selection to use, depending on using WorldEdit or not
  private Hashtable<Object, Selection> spleefRegions_;
  private Hashtable<Object, Selection> ignoredRegions_;
  private Hashtable<Object, Selection> loseRegions_;
  private BlockState[] arenaState_;
  private World world;

  public SpleefArena()
  {
    this.spleefRegions_ = new Hashtable();
    this.loseRegions_ = new Hashtable();
    this.ignoredRegions_ = new Hashtable();
  }

  public boolean containsSpleefBlock(Block block)
  {
	//TODO: Decide what type of selection to use, depending on using WorldEdit or not  
	 
    for (Selection cuboid : this.spleefRegions_.values())
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
	  //TODO: Decide what type of selection to use, depending on using WorldEdit or not
    for (Selection cuboid : this.loseRegions_.values())
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
		//TODO: Decide what type of selection to use, depending on using WorldEdit or not
    for (Selection cuboid : this.ignoredRegions_.values())
    {
      if (cuboid.containsBlock(block)) {
        return true;
      }
    }
    return false;
  }

  public boolean hasSpleefRegion(Object regionName)
  {
    return this.spleefRegions_.containsKey(regionName);
  }

  public boolean hasLoseRegion(Object regionName)
  {
    return this.loseRegions_.containsKey(regionName);
  }

  public boolean hasIgnoredRegion(Object regionName)
  {
    return this.ignoredRegions_.containsKey(regionName);
  }

  public void addSpleefRegion(Object regionName, Selection region)
  {
	//TODO: Decide what type of selection to use, depending on using WorldEdit or not
    Selection regionClone = new Selection(region);

    if (!hasSpleefRegion(regionName))
      this.spleefRegions_.put(regionName, regionClone);
  }

  public void addLoseRegion(Object regionName, Selection region)
  {
		//TODO: Decide what type of selection to use, depending on using WorldEdit or not
    Selection regionClone = new Selection(region);

    if (!hasLoseRegion(regionName))
      this.loseRegions_.put(regionName, regionClone);
  }

  public void addIgnoredRegion(Object regionName, Selection region)
  {
    Selection regionClone = new Selection(region);

    if (!hasIgnoredRegion(regionName))
      this.ignoredRegions_.put(regionName, regionClone);
  }

  public void removeSpleefRegion(Object regionName)
  {
    if (hasSpleefRegion(regionName))
      this.spleefRegions_.remove(regionName);
  }

  public void removeLoseRegion(Object regionName)
  {
    if (hasLoseRegion(regionName))
      this.loseRegions_.remove(regionName);
  }

  public void removeIgnoredRegion(Object regionName)
  {
    if (hasIgnoredRegion(regionName))
      this.ignoredRegions_.remove(regionName);
  }

  public int getSpleefRegionNumber()
  {
    return this.spleefRegions_.size();
  }

  public int getLoseRegionNumber()
  {
    return this.loseRegions_.size();
  }

  public void saveState()
  {
    Vector arenaStateList = new Vector();
	//TODO: Decide what type of selection to use, depending on using WorldEdit or not
    for (Selection spleefRegion : this.spleefRegions_.values())
    {
      for (Block spleefBlock : spleefRegion.getSelectedBlocks())
      {
        if (!containsIgnoredBlock(spleefBlock)) {
          arenaStateList.add(spleefBlock.getState());
        }
      }
    }

    this.arenaState_ = ((BlockState[])arenaStateList.toArray(new BlockState[0]));
  }

  public void restoreState()
  {
    if (this.arenaState_ != null)
    {
      for (BlockState blockState : this.arenaState_)
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
        	//TODO: Decide what type of selection to use, depending on using WorldEdit or not
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
      Set loseRegionNames = childSection.getKeys(false);
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
      Set ignoredRegionNames = childSection.getKeys(false);
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

      for (Object regionName : this.spleefRegions_.keySet())
      {
        ConfigurationSection regionSection = childSection.createSection((String) regionName);
    	//TODO: Decide what type of selection to use, depending on using WorldEdit or not
        Selection cuboid = (Selection)this.spleefRegions_.get(regionName);

        cuboid.save(regionSection);
      }

    }

    if (getLoseRegionNumber() != 0)
    {
      ConfigurationSection childSection = parentSection.createSection("loseregions");

      for (Object regionName : this.loseRegions_.keySet())
      {
        ConfigurationSection regionSection = childSection.createSection((String) regionName);
    	//TODO: Decide what type of selection to use, depending on using WorldEdit or not
        Selection cuboid = (Selection)this.loseRegions_.get(regionName);

        cuboid.save(regionSection);
      }

    }

    if (this.ignoredRegions_.size() != 0)
    {
      ConfigurationSection childSection = parentSection.createSection("ignoredregions");

      for (Object regionName : this.ignoredRegions_.keySet())
      {
        ConfigurationSection regionSection = childSection.createSection((String) regionName);

        Selection cuboid = (Selection)this.ignoredRegions_.get(regionName);

        cuboid.save(regionSection);
      }
    }
  }
}