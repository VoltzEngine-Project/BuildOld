package com.builtbroken.mc.core.commands.permissions;

import com.builtbroken.mc.api.IVirtualObject;
import com.builtbroken.mc.core.References;
import com.builtbroken.mc.core.handler.SaveManager;
import com.builtbroken.mc.lib.access.AccessGroup;
import com.builtbroken.mc.lib.access.AccessProfile;
import com.builtbroken.mc.lib.access.AccessUser;
import com.builtbroken.mc.lib.access.IProfileContainer;
import com.builtbroken.mc.lib.helper.NBTUtility;
import com.builtbroken.mc.lib.mod.loadable.AbstractLoadable;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

/**
 * Created by robert on 2/17/2015.
 */
public class GroupProfileHandler extends AbstractLoadable implements IVirtualObject, IProfileContainer
{
    public static final GroupProfileHandler GLOBAL = new GroupProfileHandler();

    public static boolean enablePermissions = true;

    public AccessProfile profile;

    static
    {
        SaveManager.registerClass("permissionHandler", GroupProfileHandler.class);
    }

    @Override
    public void init()
    {
        if (enablePermissions)
        {
            MinecraftForge.EVENT_BUS.register(GroupProfileHandler.GLOBAL);
        }
    }

    @Override
    public void postInit()
    {
        //TODO add database support
        //TODO add flat file support
        //TODO add XML file support
    }

    @Override
    public File getSaveFile()
    {
        return new File(NBTUtility.getSaveDirectory(), "ve/permissions/global.dat");
    }

    @Override
    public void setSaveFile(File file)
    {

    }

    @Override
    public boolean shouldSaveForWorld(World world)
    {
        return world != null && world.provider.dimensionId == 0;
    }

    @Override
    public void load(NBTTagCompound nbt)
    {
        //String version = nbt.getString("ve_version"); - for later
        if (nbt.hasKey("profile"))
            profile = new AccessProfile(nbt.getCompoundTag("profile"));
        else
            generateNew();
    }

    @Override
    public NBTTagCompound save(NBTTagCompound nbt)
    {
        //Save Version and Build number in case we change how save/loading works between versions
        nbt.setString("ve_version", References.VERSION + "b" + References.BUILD_VERSION);

        //Save Profile
        if (profile != null)
        {
            nbt.setTag("profile", profile.save(new NBTTagCompound()));
        }
        return nbt;
    }

    public void generateNew()
    {
        profile = new AccessProfile();

        //Create owner group - Power to do everything, AKA /OP
        AccessGroup owner_group = new AccessGroup("Owner");
        owner_group.addNode(PermissionsRegistry.ALL);

        //Create admin group - Power to Ban, Make Regions, and adjust settings
        AccessGroup admin_group = new AccessGroup("Admin");
        admin_group.addNode("root.minecraft.difficulty");
        admin_group.addNode("root.minecraft.setworldspawn");
        admin_group.addNode("root.minecraft.give");
        admin_group.addNode("root.minecraft.gamemode");
        admin_group.addNode("root.minecraft.summon");
        admin_group.addNode("root.minecraft.spawnpoint");

        //Create mod group - Power to kick, temp-ban, and silence users
        AccessGroup mod_group = new AccessGroup("Moderator");
        mod_group.addNode("root.minecraft.whitelist");
        mod_group.addNode("root.minecraft.ban-ip");
        mod_group.addNode("root.minecraft.pardon-ip");
        mod_group.addNode("root.minecraft.time");
        mod_group.addNode("root.minecraft.save-all");
        mod_group.addNode("root.minecraft.pardon");
        mod_group.addNode("root.minecraft.ban");
        mod_group.addNode("root.minecraft.weather");
        mod_group.addNode("root.minecraft.toggledownfall");
        mod_group.addNode("root.minecraft.tp");

        //Create dev group - sub version of user just for show
        AccessGroup dev_group = new AccessGroup("Dev");
        dev_group.addNode("root.minecraft.debug");

        //Create media group - sub version of user just for show
        AccessGroup media_group = new AccessGroup("Media");
        media_group.addNode("root.minecraft.seed");

        //Create user group - default group
        AccessGroup user_group = new AccessGroup("User");
        user_group.addNode("root.minecraft.help");
        user_group.addNode("root.minecraft.me");
        user_group.addNode("root.minecraft.tell");

        //Extend groups
        owner_group.setToExtend(admin_group);
        admin_group.setToExtend(mod_group);
        mod_group.setToExtend(user_group);
        dev_group.setToExtend(user_group);
        media_group.setToExtend(user_group);

        //Add groups to profile
        profile.addGroup(owner_group);
        profile.addGroup(admin_group);
        profile.addGroup(mod_group);
        profile.addGroup(dev_group);
        profile.addGroup(user_group);
    }

    @Override
    public boolean shouldLoad()
    {
        return FMLCommonHandler.instance().getEffectiveSide().isServer();
    }

    public boolean canExecuteCommand(ICommandSender sender, ICommand command, String[] args)
    {
        if (sender instanceof EntityPlayer)
        {
            AccessUser user = getAccessProfile().getUserAccess((EntityPlayer) sender);
            String node = PermissionsRegistry.getNodeFor(command, args);
            return user.hasNode(node);
        }
        return command.canCommandSenderUseCommand(sender);
    }

    @Override
    public AccessProfile getAccessProfile()
    {
        if (profile == null)
        {
            load(NBTUtility.loadData(getSaveFile()));
            SaveManager.register(this);
        }
        return profile;
    }

    @Override
    public void setAccessProfile(AccessProfile profile)
    {

    }

    @Override
    public boolean canAccess(String username)
    {
        return true;
    }

    @Override
    public boolean hasNode(EntityPlayer player, String node)
    {
        return profile != null && getAccessProfile().hasNode(player, node);
    }

    @Override
    public boolean hasNode(String username, String node)
    {
        return profile != null && getAccessProfile().hasNode(username, node);
    }

    @Override
    public void onProfileChange()
    {

    }
}
