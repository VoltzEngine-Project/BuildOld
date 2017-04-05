package com.builtbroken.mc.lib.json.processors.recipe.smelting;

import com.builtbroken.mc.core.References;
import com.builtbroken.mc.lib.json.imp.IJsonBlockSubProcessor;
import com.builtbroken.mc.lib.json.processors.extra.JsonOreNameProcessor;
import com.builtbroken.mc.lib.json.processors.recipe.JsonRecipeProcessor;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Loads smelting recipes from a json for {@link net.minecraft.item.crafting.FurnaceRecipes}
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 6/24/2016.
 */
public class JsonFurnaceRecipeProcessor extends JsonRecipeProcessor<JsonFurnaceRecipeData> implements IJsonBlockSubProcessor
{
    public static final String KEY = "furnaceRecipe";

    @Override
    public String getMod()
    {
        return References.DOMAIN;
    }

    @Override
    public String getJsonKey()
    {
        return KEY;
    }

    @Override
    public String getLoadOrder()
    {
        return "after:" + JsonOreNameProcessor.KEY;
    }

    @Override
    public JsonFurnaceRecipeData process(final Object out, final JsonElement element)
    {
        final JsonObject recipeData = element.getAsJsonObject();

        ensureValuesExist(recipeData, "input");

        //Get output if it doesn't already exist
        Object output = out;
        if (output == null)
        {
            ensureValuesExist(recipeData, "output");
            output = recipeData.getAsJsonPrimitive("output").getAsString();
        }

        //Get input
        Object input = recipeData.getAsJsonPrimitive("input").getAsString();

        //Get XP if present
        float xp = 0;
        if (recipeData.has("xp"))
        {
            xp = recipeData.getAsJsonPrimitive("xp").getAsFloat();
        }

        //Make recipe
        return new JsonFurnaceRecipeData(this, input, output, xp);
    }
}
