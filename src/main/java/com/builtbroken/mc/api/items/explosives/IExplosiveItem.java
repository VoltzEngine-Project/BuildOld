package com.builtbroken.mc.api.items.explosives;

import com.builtbroken.mc.api.explosive.IExplosiveHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/** Applied to items that contain an explosive instance.
 *
 * Created by robert on 12/2/2014.
 */
public interface IExplosiveItem
{
    /**
     * Gets the explosive from the item stack. Avoid calling to
     * often as the stack is most likely recreating this each
     * time its called. Due to item stack not being able to cache
     * data without hacky implementations of a cache.
     *
     * @param stack - itemstack that contains an instance of this item
     * @return explosive instance, or null if it doesn't contain one
     */
    IExplosiveHandler getExplosive(ItemStack stack);

    /**
     * Returns the NBTTag for additonal data used
     * when spawning explosive blasts.
     *
     * @return null if no data
     */
    NBTTagCompound getAdditionalExplosiveData(ItemStack stack);

    /**
     * Gets the current size of the explosive
     *
     * @return size greater than 0
     */
    double getExplosiveSize(ItemStack stack);
}
