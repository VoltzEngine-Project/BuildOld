package com.builtbroken.test.prefab.energy;

import com.builtbroken.mc.mods.rf.RFEnergyHandler;
import com.builtbroken.mc.testing.junit.AbstractTest;
import com.builtbroken.mc.testing.junit.VoltzTestRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * Created by Dark on 8/15/2015.
 */
@RunWith(VoltzTestRunner.class)
public class TestRFEnergyHandler extends AbstractTest
{
    public void testInit()
    {
        RFEnergyHandler handler = RFEnergyHandler.INSTANCE;
        //TODO If the ratio changes UPDATE THE WIKI with the new values
        Assert.assertTrue("Handler ratio should be 0.5", handler.toForgienEnergy == (1.0 / 2.0));
        Assert.assertTrue("Handler ratio should be 2", handler.toUEEnergy == 2);
    }
}
