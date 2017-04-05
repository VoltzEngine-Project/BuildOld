package com.builtbroken.mc.core.network.packet;


import com.builtbroken.jlib.data.vector.IPos3D;
import com.builtbroken.mc.lib.transform.vector.Pos;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 5/6/2016.
 */
public class PacketSpawnParticleStream extends PacketType
{
    public int dim;
    public Pos start;
    public Pos end;

    public PacketSpawnParticleStream()
    {
        //Needed for forge to construct the packet
    }

    public PacketSpawnParticleStream(int dim, IPos3D pos, IPos3D pos2)
    {
        this.dim = dim;
        this.start = new Pos(pos);
        this.end = new Pos(pos2);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(dim);
        start.writeByteBuf(buffer);
        end.writeByteBuf(buffer);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        dim = buffer.readInt();
        start = new Pos(buffer);
        end = new Pos(buffer);
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        if (player.worldObj.provider.dimensionId == dim)
        {
            double distance = start.distance(end);
            Pos direction = start.sub(end).divide(distance);
            int particles = (int) (distance / 0.3);

            for (int l = 0; l < particles; ++l)
            {
                Pos next = start.sub(direction.multiply(l));
                player.worldObj.spawnParticle("portal", next.x(), next.y(), next.z(), 0, 0, 0);
            }
        }
    }
}
