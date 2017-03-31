package com.builtbroken.mc.core;

import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * References to common static objects used by Voltz Engine and its sub mods
 */
public final class References
{
    public static final String ID = "VoltzEngine";
    public static final String NAME = "Voltz Engine";

    @Deprecated
    /** Use Engine.instance.logger */
    public static Logger LOGGER;

    public static final String MAJOR_VERSION = "@MAJOR@";
    public static final String MINOR_VERSION = "@MINOR@";
    public static final String REVISION_VERSION = "@REVIS@";
    public static final String BUILD_VERSION = "@BUILD@";
    public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION + "." + BUILD_VERSION;

    public static final String DOMAIN = "voltzengine";
    public static final String PREFIX = DOMAIN + ":";

    public static final String DIRECTORY = "/assets/" + DOMAIN + "/";
    public static final String CHANNEL = "voltzengine";
    public static final String TEXTURE_DIRECTORY = "textures/";
    public static final String GUI_DIRECTORY = TEXTURE_DIRECTORY + "gui/";

    public static final String BLOCK_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "blocks/";
    public static final String ITEM_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "items/";
    public static final String MODEL_PATH = "models/";
    public static final String MODEL_DIRECTORY = DIRECTORY + MODEL_PATH;

    /** Main run folder for everything MC */
    public static File ROOT_FOLDER;
    /** Config folder shared by all mods */
    public static File GLOBAL_CONFIG_FOLDER;
    /** Config folder shared by all mods */
    public static File BBM_CONFIG_FOLDER;
}
