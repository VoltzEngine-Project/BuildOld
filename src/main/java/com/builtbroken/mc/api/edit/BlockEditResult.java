package com.builtbroken.mc.api.edit;

/**
 * Enum of results generated when placing blocks
 * using BlockEdit instances
 * <p>
 * Created by robert on 12/4/2014.
 */
public enum BlockEditResult
{
    /** Everything went well and the block was set */
    PLACED,
    /** Something prevent the block from placing */
    BLOCKED,
    /** World object was null */
    NULL_WORLD,
    /** Chunk was not loaded, and to avoid lag we don't load the chunk for you */
    CHUNK_UNLOADED,
    /** Entity was in the way, only checked if requested */
    ENTITY_BLOCKED,
    /** Data already matched at the location, maybe you want to update the tile instead? */
    ALREADY_PLACED,
    /** Block that was at the original location is no longer the same */
    PREV_BLOCK_CHANGED,
    /** Data provides is invalid */
    INVALID,
    /** Block could not be placed at location */
    INVALID_PLACEMENT
}
