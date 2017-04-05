package com.builtbroken.mc.core.commands;

import com.builtbroken.mc.core.Engine;
import com.builtbroken.mc.core.commands.debug.CommandDebugRecipes;
import com.builtbroken.mc.core.commands.ext.GroupSubCommand;
import com.builtbroken.mc.core.commands.ext.ModularCommandRemoveAdd;
import com.builtbroken.mc.core.commands.ext.SubCommandWithName;
import com.builtbroken.mc.core.commands.ext.UserSubCommand;
import com.builtbroken.mc.core.commands.modflags.*;
import com.builtbroken.mc.core.commands.permissions.sub.CommandGroup;
import com.builtbroken.mc.core.commands.permissions.sub.CommandUser;
import com.builtbroken.mc.core.commands.sub.CommandVEButcher;
import com.builtbroken.mc.core.commands.sub.CommandVEClear;
import com.builtbroken.mc.core.commands.sub.CommandVERemove;
import com.builtbroken.mc.core.commands.sub.CommandVEVersion;
import com.builtbroken.mc.prefab.commands.AbstractCommand;
import com.builtbroken.mc.prefab.commands.ModularCommand;

/**
 * Created by robert on 1/23/2015.
 */
public class CommandVE extends ModularCommand
{
    public static boolean disableCommands = false;
    public static boolean disableRemoveCommand = false;
    public static boolean disableButcherCommand = false;
    public static boolean disableClearCommand = false;
    public static boolean disableRegionCommand = false;
    public static boolean disableModflagCommands = false;

    public static final CommandVE INSTANCE = new CommandVE();
    private ModularCommand sub_command_new;
    private ModularCommand sub_command_remove;
    private ModularCommand sub_command_dump;
    private ModularCommand sub_command_add;
    private ModularCommand sub_command_add_user;
    private ModularCommand sub_command_remove_user;
    private ModularCommand sub_command_add_perm;
    private ModularCommand sub_command_remove_perm;
    private ModularCommand sub_command_group;
    private ModularCommand sub_command_user;
    private ModularCommand sub_command_debug;


    private CommandVE()
    {
        super("ve");
        if (!disableButcherCommand)
            addCommand(new CommandVEButcher());
        if (!disableRemoveCommand)
            addCommand(new CommandVERemove());
        addCommand(new CommandVEVersion());
        if (!disableClearCommand)
            addCommand(new CommandVEClear());
        if (!disableModflagCommands)
        {
            ModularCommand region_add = new ModularCommandRemoveAdd("region", "region", false);
            ModularCommand region_remove = new ModularCommandRemoveAdd("region", "region", true);
            ModularCommand region = new CommandRegion();

            addToNewCommand(new CommandNewRegion());
            addToRemoveCommand(new CommandRemoveRegion());

            region_add.addCommand(new CommandAddUserToRegion());
            region_remove.addCommand(new CommandRemoveUserFromRegion());

            region.addCommand(region_add);
            region.addCommand(region_remove);
            addCommand(region);
        }
        if(Engine.runningAsDev)
        {
            addToDebugCommand(new CommandDebugRecipes());
        }
    }

    public void addToDebugCommand(AbstractCommand command)
    {
        if (sub_command_debug == null)
        {
            sub_command_debug = new ModularCommand("debug");
            addCommand(sub_command_debug);
        }
        sub_command_debug.addCommand(command);
    }

    public void addToNewCommand(AbstractCommand command)
    {
        if (sub_command_new == null)
        {
            sub_command_new = new ModularCommand("new");
            addCommand(sub_command_new);
        }
        sub_command_new.addCommand(command);
    }

    public void addToRemoveCommand(AbstractCommand command)
    {
        if (sub_command_remove == null)
        {
            sub_command_remove = new ModularCommand("remove");
            addCommand(sub_command_remove);
        }
        sub_command_remove.addCommand(command);
    }

    public void addToDumpCommand(AbstractCommand command)
    {
        if (sub_command_dump == null)
        {
            sub_command_dump = new ModularCommand("dump");
            addCommand(sub_command_dump);
        }
        sub_command_dump.addCommand(command);
    }

    public void addToAddCommand(AbstractCommand command)
    {
        if (sub_command_add == null)
        {
            sub_command_add = new ModularCommand("add");
            addCommand(sub_command_add);
        }
        sub_command_add.addCommand(command);
    }

    public void addToAddUserCommand(SubCommandWithName command)
    {
        if (sub_command_add_user == null)
        {
            sub_command_add_user = new ModularCommandRemoveAdd("user", "user", false);
            addToAddCommand(sub_command_add_user);
        }
        sub_command_add_user.addCommand(command);
    }

    public void addToRemoveUserCommand(SubCommandWithName command)
    {
        if (sub_command_remove_user == null)
        {
            sub_command_remove_user = new ModularCommandRemoveAdd("user", "user", true);
            addToRemoveCommand(sub_command_remove_user);
        }
        sub_command_remove_user.addCommand(command);
    }

    public void addToAddPermCommand(SubCommandWithName command)
    {
        if (sub_command_add_perm == null)
        {
            sub_command_add_perm = new ModularCommandRemoveAdd("perm", "node", false);
            addToAddCommand(sub_command_add_perm);
        }
        sub_command_add_perm.addCommand(command);
    }

    public void addToRemovePermCommand(SubCommandWithName command)
    {
        if (sub_command_remove_perm == null)
        {
            sub_command_remove_perm = new ModularCommandRemoveAdd("perm", "node", true);
            addToRemoveCommand(sub_command_remove_perm);
        }
        sub_command_remove_perm.addCommand(command);
    }

    public void addToGroupCommand(GroupSubCommand command)
    {
        if (sub_command_group == null)
        {
            sub_command_group = new CommandGroup();
            addCommand(sub_command_group);
        }
        sub_command_group.addCommand(command);
    }

    public void addToUserCommand(UserSubCommand command)
    {
        if (sub_command_user == null)
        {
            sub_command_user = new CommandUser();
            addCommand(sub_command_user);
        }
        sub_command_user.addCommand(command);
    }
}
