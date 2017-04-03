package com.builtbroken.mc.framework.block.tile;

import com.builtbroken.mc.core.registry.ModManager;
import com.builtbroken.mc.framework.block.BlockBase;
import com.builtbroken.mc.framework.block.meta.BlockMeta;
import com.builtbroken.mc.framework.block.meta.MetaData;
import com.builtbroken.mc.lib.json.IJsonGenMod;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/3/2017.
 */
public class TileProviderMeta implements ITileProvider
{
    public ITileProvider backupProvider;

    @Override
    public TileEntity createNewTileEntity(BlockBase block, World world, int meta)
    {
        if (block instanceof BlockMeta && ((BlockMeta) block).meta[meta] != null && ((BlockMeta) block).meta[meta].tileEntityProvider != null)
        {
            return ((BlockMeta) block).meta[meta].tileEntityProvider.createNewTileEntity(block, world, meta);
        }
        return backupProvider != null ? backupProvider.createNewTileEntity(block, world, meta) : null;
    }

    @Override
    public void register(BlockBase block, IJsonGenMod mod, ModManager manager)
    {
        if (block instanceof BlockMeta)
        {
            for (MetaData data : ((BlockMeta) block).meta)
            {
                if (data != null && data.tileEntityProvider != null)
                {
                    data.tileEntityProvider.register(block, mod, manager);
                }
            }
        }
        if (backupProvider != null)
        {
            backupProvider.register(block, mod, manager);
        }
    }
}
