package com.builtbroken.mc.api.modules.weapon;

import com.builtbroken.mc.api.data.weapon.*;

import java.util.Stack;

/**
 * An instant of a clip that contains ammo and other data
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/20/2016.
 */
public interface IClip
{
    /**
     * Data that drives most of the properties of the clip
     *
     * @return data
     */
    IClipData getClipData();

    /**
     * How much ammo is contained in the clip
     *
     * @return ammo
     */
    int getAmmoCount();

    /**
     * Current stack of ammo in the clip
     *
     * @return stack (Last in, First Out... same way a real clip works)
     */
    Stack<IAmmoData> getAmmo();

    /**
     * Called to consume ammo from the clip.
     * <p>
     * Make sure to call this instead of calling
     * {@link #getAmmo()) then {@link Stack#pop()}
     * as the changes may not be saved correctly
     *
     * @param count - amount of ammo to consumed, normally
     *              this should be 1 but might be more for faster firing weapons
     */
    void consumeAmmo(int count);

    /**
     * Called to load a single round of ammo
     *
     * @param data
     * @return
     */
    default boolean loadAmmo(IAmmoData data)
    {
        return loadAmmo(data, 1) == 1;
    }

    /**
     * Called to load ammo into the clip
     *
     * @param data
     * @param count
     * @return
     */
    int loadAmmo(IAmmoData data, int count);

    /**
     * Gets how full the weapon is as a percent
     *
     * @return
     */
    default float getPercentFull()
    {
        return (float) getAmmoCount() / (float) getClipData().getMaxAmmo();
    }

    /**
     * Gets how full the weapon is as a percent
     *
     * @return
     */
    default String getAmmoRatio()
    {
        return getAmmoCount() + "/" + getClipData().getMaxAmmo();
    }

    /**
     * Gets how much space is in the clip
     *
     * @return
     */
    default int getEmptyAmmoSpace()
    {
        return getClipData().getMaxAmmo() - getAmmoCount();
    }

    /**
     * Wrapper method call for {@link IClipData#getMaxAmmo()}
     *
     * @return
     */
    default int getMaxAmmo()
    {
        return getClipData().getMaxAmmo();
    }

    /**
     * Wrapper method call for {@link IClipData#getReloadType()}
     *
     * @return
     */
    default ReloadType getReloadType()
    {
        return getClipData().getReloadType();
    }

    /**
     * Wrapper method call for {@link IClipData#getAmmoType()} )}
     *
     * @return
     */
    default IAmmoType getAmmoType()
    {
        return getClipData().getAmmoType();
    }
}
