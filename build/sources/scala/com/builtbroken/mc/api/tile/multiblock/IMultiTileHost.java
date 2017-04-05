package com.builtbroken.mc.api.tile.multiblock;

import com.builtbroken.jlib.data.vector.IPos3D;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

/**
 * Implemented by blocks that act as the host or logical brain for a multiblock structure
 * Created by Dark on 8/9/2015.
 */
public interface IMultiTileHost
{
    /**
     * Called when a peace of the structure has been added to the world
     *
     * @param tileMulti - tile that was added
     */
    void onMultiTileAdded(IMultiTile tileMulti);

    /**
     * Called when a peace of the structure has been removed from the world
     *
     * @param tileMulti - tile that was destroyed
     * @param source    - what broke the block (Player or Explosion), can be null
     * @param harvest   - should the block be harvested, only used when a player breaks the tile
     */
    boolean onMultiTileBroken(IMultiTile tileMulti, Object source, boolean harvest);

    /**
     * Called when a tile has been invalidated. Most likely this is
     * called when a chunk is unloaded from the world.
     *
     * @param tileMulti - tile that was invalidated
     */
    void onTileInvalidate(IMultiTile tileMulti);

    /**
     * Called when a player right clicks a tile
     *
     * @param tile   - tile clicked
     * @param player - person who clicked the tile
     * @param side   - side clicked
     * @param hit    - hit data of where the tile was clicked
     */
    boolean onMultiTileActivated(IMultiTile tile, EntityPlayer player, int side, IPos3D hit);

    /**
     * Called when the player left clicks a tile. No additional data is provided beyond
     * tile and player due to MC's methods.
     *
     * @param tile   - tile that was clicked
     * @param player - player
     */
    void onMultiTileClicked(IMultiTile tile, EntityPlayer player);

    /**
     * Grabs that raw data of the layout of the structure. This data will be used to generate
     * the structure
     *
     * @return map of positions to multiTile types see {@link com.builtbroken.mc.prefab.tile.multiblock.EnumMultiblock}
     */
    HashMap<IPos3D, String> getLayoutOfMultiBlock();
}
