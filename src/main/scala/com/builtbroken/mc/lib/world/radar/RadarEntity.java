package com.builtbroken.mc.lib.world.radar;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/5/2016.
 */
public class RadarEntity extends RadarObject<Entity>
{
    protected Entity entity;

    public RadarEntity(Entity referent)
    {
        this.entity = referent;
    }

    @Override
    public boolean isValid()
    {
        return entity != null && entity.isEntityAlive() && entity.worldObj != null;
    }

    @Override
    public World world()
    {
        return entity != null ? entity.worldObj : null;
    }

    @Override
    public double x()
    {
        return entity != null ? entity.posX : 0;
    }

    @Override
    public double y()
    {
        return entity != null ? entity.posY : 0;
    }

    @Override
    public double z()
    {
        return entity != null ? entity.posZ : 0;
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof RadarEntity && ((RadarEntity) object).isValid())
        {
            return ((RadarEntity) object).entity == entity || ((RadarEntity) object).entity != null && entity != null && ((RadarEntity) object).entity.getEntityId() == entity.getEntityId();
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        if (entity != null)
        {
            return entity.hashCode();
        }
        return super.hashCode();
    }
}
