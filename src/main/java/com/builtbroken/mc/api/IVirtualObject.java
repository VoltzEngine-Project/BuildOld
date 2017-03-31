package com.builtbroken.mc.api;

import net.minecraft.world.World;

import java.io.File;

/**
 * Used in combination with the save manager and other managers to say this object needs to be save
 * since its not connected with the world
 *
 * @author DarkGuardsman
 */
public interface IVirtualObject extends ISave
{
	/**
	 * File this is saved as, don't create anything here as the manager will do that for you
	 */
	File getSaveFile();

	/**
	 * Will only be called after an object has been loaded. Allows the object to know were its been
	 * loaded from and decide if it wants to use the location as its getSaveFile return
	 */
	void setSaveFile(File file);

    /**
     * Save events are triggered when the world saves. Use this to
     * decide if you need to save to file when the world does.
     * @param world
     * @return
     */
	boolean shouldSaveForWorld(World world);

}
