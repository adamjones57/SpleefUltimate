package com.skitscape.spleefultimate;


	import java.util.UUID;
	import java.util.Vector;
	import org.bukkit.Bukkit;
	import org.bukkit.Location;
	import org.bukkit.World;
	import org.bukkit.block.Block;
	import org.bukkit.configuration.ConfigurationSection;

	public class Selection {
	  private Block pos1;
	  private Block pos2;
	  private int minBlockX;
	  private int minBlockY;
	  private int minBlockZ;
	  private int maxBlockX;
	  private int maxBlockY;
	  private int maxBlockZ;
	  private World world;
	  private SelectionType type;

	  public Selection(World world)
	  {
	    this.world = world;

	    this.type = SelectionType.CUBOID;
	  }

	  public Selection(Selection existingCuboid)
	  {
	    this.world = existingCuboid.getWorld();

	    this.type = existingCuboid.getType();

	    setPos1(existingCuboid.getPos1());
	    setPos2(existingCuboid.getPos2());
	  }

	  public void setPos1(Block block)
	  {
	    if (block == null) {
	      return;
	    }
	    this.pos1 = block;

	    calculateCoordinates();
	  }

	  public void setPos2(Block block)
	  {
	    if (block == null) {
	      return;
	    }
	    this.pos2 = block;

	    calculateCoordinates();
	  }

	  protected Block getPos1()
	  {
	    return this.pos1;
	  }

	  protected Block getPos2()
	  {
	    return this.pos2;
	  }

	  public World getWorld()
	  {
	    return this.world;
	  }

	  public SelectionType getType()
	  {
	    return this.type;
	  }

	  public void setType(SelectionType type)
	  {
	    this.type = type;
	  }

	  public Vector<Block> getSelectedBlocks()
	  {
	    Vector<Block> selectedBlocks = new Vector<Block>();

	    for (int x = this.minBlockX; x < this.maxBlockX + 1; x++)
	    {
	      for (int y = this.minBlockY; y < this.maxBlockY + 1; y++)
	      {
	        for (int z = this.minBlockZ; z < this.maxBlockZ + 1; z++)
	        {
	          Block block = this.world.getBlockAt(x, y, z);

	          if ((block == null) || 
	            (!containsBlock(block))) continue;
	          selectedBlocks.add(block);
	        }
	      }
	    }

	    return selectedBlocks;
	  }

	  public boolean isValid()
	  {
	    return (this.world != null) && (this.pos1 != null) && (this.pos2 != null);
	  }

	  public boolean containsBlock(Block block)
	  {
	    if (block == null) {
	      return false;
	    }
	    if (!isValid()) {
	      return false;
	    }

	    Location blockLocation = block.getLocation();
	    int blockX = blockLocation.getBlockX();
	    int blockY = blockLocation.getBlockY();
	    int blockZ = blockLocation.getBlockZ();

	    if ((block.getWorld() != this.world) || 
	      (blockX < this.minBlockX) || (blockX > this.maxBlockX) || 
	      (blockY < this.minBlockY) || (blockY > this.maxBlockY) || 
	      (blockZ < this.minBlockZ) || (blockZ > this.maxBlockZ)) {
	      return false;
	    }
	    if (this.type != SelectionType.CUBOID)
	    {
	      if (!checkTypeConditions(blockX, blockY, blockZ)) {
	        return false;
	      }
	    }
	    return true;
	  }

	  private boolean checkTypeConditions(int blockX, int blockY, int blockZ)
	  {
		switch (type)
	    {
	    case OUTLINE:
	      if (((blockX == this.minBlockX) || (blockX == this.maxBlockX)) && 
	        (blockZ >= this.minBlockZ) && (blockZ <= this.maxBlockZ)) {
	        break;
	      }
	      if (((blockZ == this.minBlockZ) || (blockZ == this.maxBlockZ)) && 
	        (blockX >= this.minBlockX) && (blockX <= this.maxBlockX)) {
	        break;
	      }
	      if (((blockY == this.minBlockY) || (blockY == this.maxBlockY)) && 
	        (blockX >= this.minBlockX) && (blockX <= this.maxBlockX) && 
	        (blockZ >= this.minBlockZ) && (blockZ <= this.maxBlockZ))
	        break;
	      return false;
	    case WALLS:
	      if (((blockX == this.minBlockX) || (blockX == this.maxBlockX)) && 
	        (blockZ >= this.minBlockZ) && (blockZ <= this.maxBlockZ)) {
	        break;
	      }
	      if (((blockZ == this.minBlockZ) || (blockZ == this.maxBlockZ)) && 
	        (blockX >= this.minBlockX) && (blockX <= this.maxBlockX))
	        break;
	      return false;
	    }

	    return true;
	  }

	  private void calculateCoordinates()
	  {
	    Location pos1Location = this.pos1 != null ? this.pos1.getLocation() : null;
	    Location pos2Location = this.pos2 != null ? this.pos2.getLocation() : null;

	    if ((pos1Location == null) && (pos2Location == null)) {
	      return;
	    }

	    if (pos1Location == null)
	      pos1Location = pos2Location;
	    if (pos2Location == null) {
	      pos2Location = pos1Location;
	    }

	    this.minBlockX = Math.min(pos1Location.getBlockX(), pos2Location.getBlockX());
	    this.minBlockY = Math.min(pos1Location.getBlockY(), pos2Location.getBlockY());
	    this.minBlockZ = Math.min(pos1Location.getBlockZ(), pos2Location.getBlockZ());
	    this.maxBlockX = Math.max(pos1Location.getBlockX(), pos2Location.getBlockX());
	    this.maxBlockY = Math.max(pos1Location.getBlockY(), pos2Location.getBlockY());
	    this.maxBlockZ = Math.max(pos1Location.getBlockZ(), pos2Location.getBlockZ());
	  }

	  public boolean load(ConfigurationSection parentSection)
	  {
	    if (parentSection == null) {
	      return false;
	    }

	    String worldUID = parentSection.getString("worlduid", "");

	    if (worldUID.isEmpty()) {
	      return false;
	    }

	    World selectionWorld = Bukkit.getServer().getWorld(UUID.fromString(worldUID));
	    if (selectionWorld == null) {
	      return false;
	    }

	    this.world = selectionWorld;

	    String typeName = parentSection.getString("type", "");
	    if (!typeName.isEmpty())
	    {
	      try
	      {
	        SelectionType type = SelectionType.valueOf(typeName.toUpperCase());
	        setType(type);
	      }
	      catch (IllegalArgumentException localIllegalArgumentException)
	      {
	      }
	    }
	    if ((!parentSection.isInt("x1")) || 
	      (!parentSection.isInt("y1")) || 
	      (!parentSection.isInt("z1")) || 
	      (!parentSection.isInt("x2")) || 
	      (!parentSection.isInt("y2")) || 
	      (!parentSection.isInt("z2"))) {
	      return false;
	    }

	    int x1 = parentSection.getInt("x1", 0);
	    int y1 = parentSection.getInt("y1", 0);
	    int z1 = parentSection.getInt("z1", 0);
	    int x2 = parentSection.getInt("x2", 0);
	    int y2 = parentSection.getInt("y2", 0);
	    int z2 = parentSection.getInt("z2", 0);

	    Block pos1Block = selectionWorld.getBlockAt(x1, y1, z1);
	    setPos1(pos1Block);

	    Block pos2Block = selectionWorld.getBlockAt(x2, y2, z2);
	    setPos2(pos2Block);

	    return true;
	  }

	  public void save(ConfigurationSection parentSection)
	  {
	    if (parentSection == null) {
	      return;
	    }

	    parentSection.set("worlduid", getWorld().getUID().toString());

	    parentSection.set("type", this.type.toString());

	    parentSection.set("x1", Integer.valueOf(this.minBlockX));
	    parentSection.set("y1", Integer.valueOf(this.minBlockY));
	    parentSection.set("z1", Integer.valueOf(this.minBlockZ));
	    parentSection.set("x2", Integer.valueOf(this.maxBlockX));
	    parentSection.set("y2", Integer.valueOf(this.maxBlockY));
	    parentSection.set("z2", Integer.valueOf(this.maxBlockZ));
	  }
	}

