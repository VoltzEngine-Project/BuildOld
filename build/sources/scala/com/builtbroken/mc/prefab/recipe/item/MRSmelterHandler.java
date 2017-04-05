package com.builtbroken.mc.prefab.recipe.item;

import com.builtbroken.mc.api.recipe.MachineRecipeType;
import com.builtbroken.mc.prefab.recipe.item.MRHandlerItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

/**
 * Created by robert on 1/9/2015.
 */
public class MRSmelterHandler extends MRHandlerItemStack
{
    public MRSmelterHandler()
    {
        super(MachineRecipeType.ITEM_SMELTER.INTERNAL_NAME);
    }

    @Override
    public ItemStack getRecipe(Object[] items, float extraChance, float failureChance)
    {
        if(items != null)
        {
            ItemStack result = super.getRecipe(items, extraChance, failureChance);
            if (result == null && items.length == 1 && items[0] instanceof ItemStack)
            {
                return FurnaceRecipes.smelting().getSmeltingResult((ItemStack)items[0]);
            }
            return result;
        }
        return null;
    }
}
