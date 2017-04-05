package com.builtbroken.mc.api.data.weapon;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/20/2016.
 */
public interface IGunData extends IWeaponData
{
    /**
     * How this weapon is reloaded.
     * <p>
     * This is used to limit what types of clips can be feed
     * into the weapon. As well controls animations and ammo
     * controls.
     *
     * @return reload type
     */
    ReloadType getReloadType();

    /**
     * All weapons normally have a built
     * int clip data object to allow firing
     * a single round.
     * <p>
     * However, in some cases this may return
     * null if the weapon can literally not
     * function without a clip inserted. Examples
     * of this are laser weapons and plasma weapons.
     * <p>
     * This is also used for weapons that have
     * built in clips and can not have clips
     * inserted into the weapon.
     *
     * @return built in clip
     */
    IClipData getBuiltInClipData();

    /**
     * Type of ammo this weapon can load
     *
     * @return
     */
    IAmmoType getAmmoType();

    /**
     * Type of gun
     * <p>
     * This is mainly used for display to the user
     * but in some cases can be used to modify how
     * the weapon is animated and held by the player
     * model.
     *
     * @return weapon type (Handgun, Rifle(AutoRifle, AssaultRifle SniperRifle), LMG, HMG, SMG)
     */
    String getGunType();

    /**
     * Does the weapon needs to be sighted in
     * order to be fired.
     * <p>
     * This is used for weapons like RPGs
     * and AT rifles that can not be fired
     * from the hip.
     *
     * @return true if needs to be sighted
     */
    boolean isSightedRequiredToFire();

    /**
     * How long it takes to reload the weapon.
     * <p>
     * This is a base value for an average sized clip.
     * Different clips will modify this on the user's
     * actual reload action.
     *
     * @return time in ticks (20 ticks a second)
     */
    int getReloadTime();

    /**
     * Number of rounds the gun can fire in a min
     * ignoring reload time and other factors.
     * <p>
     * This is used most of the time to calculate
     * firing delay between rounds.
     *
     * @return rounds per min
     */
    int getRateOfFire();

    /**
     * Delay in milliseconds before
     * the next round will fire.
     * <p>
     * Used to control the firing action
     * of the weapon.
     *
     * @return delay in miliseconds
     */
    int getFiringDelay();
}
