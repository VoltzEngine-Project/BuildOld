package com.builtbroken.mc.api.event;

import com.builtbroken.mc.api.IWorldPosition;
import com.builtbroken.mc.api.edit.IWorldChangeAction;
import com.builtbroken.mc.api.edit.IWorldEdit;
import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

import java.util.Collection;

/**
 * Prefab for all events triggered by IWorldChangeAction
 * Created by robert on 12/2/2014.
 */
public abstract class WorldChangeActionEvent extends WorldEvent
{
    public final TriggerCause triggerCause;
    public final IWorldChangeAction worldChangeAction;

    public WorldChangeActionEvent(World world, IWorldChangeAction worldChangeAction, TriggerCause triggerCause)
    {
        super(world);
        this.triggerCause = triggerCause;
        this.worldChangeAction = worldChangeAction;
    }

    /**
     * Called when the action is created and has yet to be triggered. This is the time
     * to change the params of the action, or cancel the action.
     */
    @Cancelable
    public static class ActionCreated extends WorldChangeActionEvent
    {
        public final IWorldPosition startingPoint;
        public ActionCreated(IWorldPosition v, IWorldChangeAction worldChangeAction, TriggerCause triggerCause)
        {
            super(v.world(), worldChangeAction, triggerCause);
            startingPoint = v;
        }
    }

    /**
     * Called after all changes have been made to the world and the action is done
     */
    public static class PostWorldChangeEvent extends WorldChangeActionEvent
    {
        public final IWorldPosition startingPoint;
        public PostWorldChangeEvent(IWorldPosition v, IWorldChangeAction worldChangeAction, TriggerCause triggerCause)
        {
            super(v.world(), worldChangeAction, triggerCause);
            startingPoint = v;
        }
    }

    /**
     * Event to allow mods to override or remove changed blocks from the list of
     * blocks that will be effected by IWorldChangeAction instance
     */
    public static class FinishedCalculatingEffectEvent extends WorldChangeActionEvent
    {
        public final IWorldPosition startingPoint;
        public final Collection<IWorldEdit> blocks;
        public FinishedCalculatingEffectEvent(IWorldPosition v, Collection<IWorldEdit> blocks, IWorldChangeAction worldChangeAction, TriggerCause triggerCause)
        {
            super(v.world(), worldChangeAction, triggerCause);
            this.startingPoint = v;
            this.blocks = blocks;
        }
    }
}
