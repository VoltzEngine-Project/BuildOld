package com.builtbroken.mc.lib.render.fx;

import com.builtbroken.mc.core.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author Zmaster
 */
public class FxRocketFire extends EntityFX
{
    public static final ResourceLocation icon = new ResourceLocation(References.DOMAIN, "textures/particle/soft.png");

    public FxRocketFire(World world, Color color, double x, double y, double z, double motx, double moty, double motz)
    {
        this(world, x, y, z, motx, moty, motz);
        this.particleRed = (color.getRed() / 255f); // + this.rand.nextFloat() / 10f;
        this.particleGreen = (color.getGreen() / 255f); // + this.rand.nextFloat() / 5f;
        this.particleBlue = (color.getBlue() / 255f);
    }

    public FxRocketFire(World world, double x, double y, double z, double motx, double moty, double motz)
    {
        super(world, x, y, z, motx, moty, motz);

        this.prevPosX = this.posX = this.lastTickPosX = x;
        this.prevPosY = this.posY = this.lastTickPosY = y;
        this.prevPosZ = this.posZ = this.lastTickPosZ = z;

        this.particleRed = 0.9F + this.rand.nextFloat() / 10f;
        this.particleGreen = 0.6F + this.rand.nextFloat() / 5f;
        this.particleBlue = 0.0F;
        this.setSize(0.12F, 0.12F);
        this.particleScale *= this.rand.nextFloat() * 0.6F + 6F;
        this.motionX = motx;
        this.motionY = moty;
        this.motionZ = motz;
        this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.6D));
    }

    @Override
    public void renderParticle(Tessellator tess, float x1, float y1, float z1, float x2, float y2, float z2)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(icon);

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);


        float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) x1 - interpPosX);
        float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) x1 - interpPosY);
        float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) x1 - interpPosZ);
        float f10 = 0.1F * this.particleScale;


        tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, 1f);

        tess.addVertexWithUV((double) (f11 - y1 * f10 - y2 * f10), (double) (f12 - z1 * f10), (double) (f13 - x2 * f10 - z2 * f10), 1, 1);
        tess.addVertexWithUV((double) (f11 - y1 * f10 + y2 * f10), (double) (f12 + z1 * f10), (double) (f13 - x2 * f10 + z2 * f10), 1, 0);
        tess.addVertexWithUV((double) (f11 + y1 * f10 + y2 * f10), (double) (f12 + z1 * f10), (double) (f13 + x2 * f10 + z2 * f10), 0, 0);
        tess.addVertexWithUV((double) (f11 + y1 * f10 - y2 * f10), (double) (f12 - z1 * f10), (double) (f13 + x2 * f10 - z2 * f10), 0, 1);
        GL11.glEnable(GL11.GL_BLEND);

        GL11.glPopMatrix();
    }

    @Override
    public int getFXLayer()
    {
        return 2;
    }

    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        //Change color and alpha over lifespan
        this.particleAlpha = 1 - this.particleAge / (float) this.particleMaxAge;
        this.particleGreen -= this.particleGreen * this.particleAge / ((float) this.particleMaxAge * 2);

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionX);
    }
}