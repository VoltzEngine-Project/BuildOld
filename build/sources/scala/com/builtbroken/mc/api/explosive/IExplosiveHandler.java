package com.builtbroken.mc.api.explosive;

import com.builtbroken.mc.api.edit.IWorldChangeAction;
import com.builtbroken.mc.api.event.TriggerCause;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

/**
 * Applied to any explosive handler that created a blast. Should only create
 * one explosive instance per subtype of blast to avoid creating extra ids.
 * Use trigger to express how the blast will be created rather than creating
 * a new explosive instance per different blast.
 */
public interface IExplosiveHandler
{

    /**
     * Attempt to trigger the explosive at the location for the trigger cause.
     * Most of the time this will be call once so avoid chance logic as it maybe not
     * get called again.
     *
     * @param world        The world in which the explosion takes place.
     * @param x            The X-Coord
     * @param y            The Y-Coord
     * @param z            The Z-Coord
     * @param triggerCause - object that describes what caused the explosion to try
     * @param size         - size of the explosive, will be used to set radius using ex. (size * min_radius)
     * @return instanceof IWorldChangeAction that tells what blocks and entities are to be effected
     */
    IWorldChangeAction createBlastForTrigger(World world, double x, double y, double z, TriggerCause triggerCause, double size, NBTTagCompound tag);

    /**
     * Gets the scale that is applied to
     * the input size value.
     * <p>
     * Mainly used for displaying information
     * to users
     *
     * @param stack used to get specific data about the explosive
     *              item that is requesting yield data
     * @return scale
     */
    default double getYieldModifier(ItemStack stack)
    {
        return getYieldModifier();
    }

    /**
     * Gets the scale that is applied to
     * the input size value.
     * <p>
     * Mainly used for displaying information
     * to users
     *
     * @return scale
     */
    default double getYieldModifier()
    {
        return 1;
    }

    /**
     * Gets estimated range info for the given trigger and size
     *
     * @param stack - item that contains the explosive
     * @param lines - list to add info to display for the item tooltip
     * @return min and max pair
     */
    void addInfoToItem(EntityPlayer player, ItemStack stack, List<String> lines);

    /**
     * Called when the explosive is registered
     *
     * @param id    - name the explosive was registered with
     * @param modID - mod the explosive was registered by
     */
    void onRegistered(String id, String modID);

    /** Gets the key to use for translating the name */
    String getTranslationKey();

    /** Gets the id this was registered with */
    String getID();
}
