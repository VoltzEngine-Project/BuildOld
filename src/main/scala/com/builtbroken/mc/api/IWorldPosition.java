package com.builtbroken.mc.api;

import net.minecraft.world.World;

/**
 * Useful interface to define that an object has a 3D location, and a defined world.
 *
 * @author DarkGuardsman
 */
public interface IWorldPosition
{
    World world();

    double x();

    double y();

    double z();

    default int xi()
    {
        return (int) Math.floor(x());
    }

    default int yi()
    {
        return (int) Math.floor(y());
    }

    default int zi()
    {
        return (int) Math.floor(z());
    }

    default float xf()
    {
        return (float) x();
    }

    default float yf()
    {
        return (float) y();
    }

    default float zf()
    {
        return (float) z();
    }
}
