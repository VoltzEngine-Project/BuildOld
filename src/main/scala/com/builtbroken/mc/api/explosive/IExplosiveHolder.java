package com.builtbroken.mc.api.explosive;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Anything that can contain an explosive in a way that the explosive can be inserted/set
 * Created by robert on 2/1/2015.
 */
public interface IExplosiveHolder extends IExplosive
{
    /**
     * Sets what explosive the container uses
     *
     * @param ex   - registered explosive handler
     * @param size - size, or load of the explosive
     * @param nbt  - data used to trigger the explosive
     * @return true if it was set, false if it was rejected
     */
    boolean setExplosive(IExplosiveHandler ex, double size, NBTTagCompound nbt);

}
