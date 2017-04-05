package com.builtbroken.mc.core.content.resources.items;

import com.builtbroken.mc.core.References;
import com.builtbroken.mc.core.content.resources.DefinedGenItems;
import com.builtbroken.mc.core.content.resources.GenMaterial;
import com.builtbroken.mc.lib.helper.LanguageUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Prefab for auto generating ore material items without doing complex meta shifting
 * Created by Robert on 8/28/2015.
 */
public class ItemGenMaterial extends Item
{
    public final DefinedGenItems itemType;

    /**
     * @param itemType - type of item this is, should not be duplicated and should never be null
     * @throws RuntimeException if ItemType is null
     */
    public ItemGenMaterial(DefinedGenItems itemType)
    {
        if (itemType == null)
            throw new RuntimeException("Item type can not be null for ItemGenMaterial");

        this.itemType = itemType;
        this.setUnlocalizedName(References.PREFIX + itemType.name().toLowerCase());
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
        this.itemIcon = reg.registerIcon(References.PREFIX + itemType.iconName);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return "item." + References.PREFIX + itemType.name + LanguageUtility.capitalizeFirst(getMaterial(stack).name().toLowerCase());
    }

    public GenMaterial getMaterial(ItemStack stack)
    {
        if (stack.getItemDamage() >= 0 && stack.getItemDamage() < GenMaterial.values().length)
        {
            return GenMaterial.values()[stack.getItemDamage()];
        }
        return GenMaterial.UNKNOWN;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (GenMaterial mat : GenMaterial.values())
        {
            if (mat != GenMaterial.UNKNOWN && !itemType.ignoreMaterials.contains(mat))
            {
                list.add(new ItemStack(item, 1, mat.ordinal()));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass)
    {
        GenMaterial material = getMaterial(stack);
        if (material.color != null)
        {
            return material.color.getRGB();
        }
        return 16777215;
    }
}
