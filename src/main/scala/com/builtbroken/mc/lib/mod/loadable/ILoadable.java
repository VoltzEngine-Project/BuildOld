package com.builtbroken.mc.lib.mod.loadable;

/**
 * Applied to loadable objects.
 *
 * @author Calclavia
 */
public interface ILoadable
{
	void preInit();

	void init();

	void postInit();

	void loadComplete();
}
