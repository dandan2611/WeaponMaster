package com.dandan2611.weaponmaster;

import org.bukkit.Color;
import org.bukkit.entity.EntityType;

public class Constants {

    /**
     * Maximum number of tries to determine a random location height
     * If maximum tries are reached, the location is not suitable to summon mobs
     */
    public static final Integer RANDOM_LOCATION_MAX_Y_TRIES = 3;

    /*

    ██████╗░░█████╗░██╗██╗░░░░░░██████╗░██╗░░░██╗███╗░░██╗
    ██╔══██╗██╔══██╗██║██║░░░░░██╔════╝░██║░░░██║████╗░██║
    ██████╔╝███████║██║██║░░░░░██║░░██╗░██║░░░██║██╔██╗██║
    ██╔══██╗██╔══██║██║██║░░░░░██║░░╚██╗██║░░░██║██║╚████║
    ██║░░██║██║░░██║██║███████╗╚██████╔╝╚██████╔╝██║░╚███║
    ╚═╝░░╚═╝╚═╝░░╚═╝╚═╝╚══════╝░╚═════╝░░╚═════╝░╚═╝░░╚══╝

     */

    /**
     * Max railgun reach distance
     */
    public static final Integer RAILGUN_MAX_DISTANCE = 12;

    /**
     * Railgun fire task ticks
     * Smaller the ticks will be, faster the railgun will fire
     */
    public static final Long RAILGUN_TASK_TICKS = 1L;

    /**
     * Maximum number of times the railgun will fire in one time
     */
    public static final Integer RAILGUN_MAX_ITERATIONS = 2;

    /*

    ██╗░░░░░░█████╗░░██████╗███████╗██████╗░
    ██║░░░░░██╔══██╗██╔════╝██╔════╝██╔══██╗
    ██║░░░░░███████║╚█████╗░█████╗░░██████╔╝
    ██║░░░░░██╔══██║░╚═══██╗██╔══╝░░██╔══██╗
    ███████╗██║░░██║██████╔╝███████╗██║░░██║

     */

    /**
     * Maximum laser distance (with bounces)
     */
    public static final Double LASER_MAX_DISTANCE = 15d;

    /**
     * Laser steps
     * Steps are used to draw particles and detect collisions
     */
    public static final Double LASER_STEPS = 0.1d;

    /**
     * Laser color
     */
    public static final Color LASER_COLOR = Color.fromRGB(52, 148, 172);

    /**
     * Laser raytrace raysize
     */
    public static final Float LASER_RAYSIZE = 0.25f;

    /**
     * Laser fire ticks
     */
    public static final Integer LASER_FIRE_TICKS = 40;

    /*

    ███████╗██╗░██████╗██╗░░██╗        ██╗░░░░░░█████╗░██╗░░░██╗███╗░░██╗░█████╗░██╗░░██╗███████╗██████╗░
    ██╔════╝██║██╔════╝██║░░██║        ██║░░░░░██╔══██╗██║░░░██║████╗░██║██╔══██╗██║░░██║██╔════╝██╔══██╗
    █████╗░░██║╚█████╗░███████║        ██║░░░░░███████║██║░░░██║██╔██╗██║██║░░╚═╝███████║█████╗░░██████╔╝
    ██╔══╝░░██║░╚═══██╗██╔══██║        ██║░░░░░██╔══██║██║░░░██║██║╚████║██║░░██╗██╔══██║██╔══╝░░██╔══██╗
    ██║░░░░░██║██████╔╝██║░░██║        ███████╗██║░░██║╚██████╔╝██║░╚███║╚█████╔╝██║░░██║███████╗██║░░██║
    ╚═╝░░░░░╚═╝╚═════╝░╚═╝░░╚═╝        ╚══════╝╚═╝░░╚═╝░╚═════╝░╚═╝░░╚══╝░╚════╝░╚═╝░░╚═╝╚══════╝╚═╝░░╚═╝

     */

    /**
     * Fish launcher projectile initial velocity
     */
    public static final Double FISH_LAUNCHER_BOMB_VELOCITY = 2.25d;

    /**
     * Fish launcher detection & cleanup task period ticks
     */
    public static final Long FISH_LAUNCHER_TASK_TICKS = 2L;

    /**
     * Fish launcher countdown task period ticks
     */
    public static final Long FISH_LAUNCHER_COUNTDOWN_TICKS = 2L;

    /**
     * Fish launcher timer (in seconds) until the bomb explodes after hitting the ground
     */
    public static final Integer FISH_LAUNCHER_TIMER = 6;

    /**
     * Fish launcher time when the fish will be first filled
     */
    public static final Integer FISH_LAUNCHER_FIRST_FILL = 4;

    /**
     * Fish launcher time when the fish will be second filled
     */
    public static final Integer FISH_LAUNCHER_SECOND_FILL = 2;

    /**
     * Fish launcher explosion radius
     * /!\ Too high values can cause server lag spikes /!\
     */
    public static final Float FISH_LAUNCHER_EXPLOSION_RADIUS = 16f;

    /**
     * Fish launcher poison effect radius
     */
    public static final Float FISH_LAUNCHER_POISON_RADIUS = 2 * FISH_LAUNCHER_EXPLOSION_RADIUS;

    /**
     * Fish launcher poison effect duration
     */
    public static final Integer FISH_LAUNCHER_POISON_DURATION = 15;

    /*


    ██╗███╗░░██╗██╗░░░██╗░█████╗░██╗░░██╗███████╗██████╗░        ░██████╗████████╗██╗░█████╗░██╗░░██╗
    ██║████╗░██║██║░░░██║██╔══██╗██║░██╔╝██╔════╝██╔══██╗        ██╔════╝╚══██╔══╝██║██╔══██╗██║░██╔╝
    ██║██╔██╗██║╚██╗░██╔╝██║░░██║█████═╝░█████╗░░██████╔╝        ╚█████╗░░░░██║░░░██║██║░░╚═╝█████═╝░
    ██║██║╚████║░╚████╔╝░██║░░██║██╔═██╗░██╔══╝░░██╔══██╗        ░╚═══██╗░░░██║░░░██║██║░░██╗██╔═██╗░
    ██║██║░╚███║░░╚██╔╝░░╚█████╔╝██║░╚██╗███████╗██║░░██║        ██████╔╝░░░██║░░░██║╚█████╔╝██║░╚██╗
    ╚═╝╚═╝░░╚══╝░░░╚═╝░░░░╚════╝░╚═╝░░╚═╝╚══════╝╚═╝░░╚═╝        ╚═════╝░░░░╚═╝░░░╚═╝░╚════╝░╚═╝░░╚═╝

     */

    /**
     * Invoker Stick task period
     */
    public static final Long INVOKER_STICK_TASK_TICKS = 5L;

    /**
     * Max entities target distances until the target is cancelled
     */
    public static final Double INVOKER_STICK_MAX_TARGET_DISTANCE = 24d;

    /**
     * Mobs spawn & regroup radius
     */
    public static final Integer INVOKER_STICK_MOBS_CLOSE_UP_DISTANCE = 4;

    /**
     * Max location tries until
     */
    public static final Integer INVOKER_STICK_MAX_RANDOM_LOCATION_TRIES = 6;

    /**
     * Minimum number of mobs to spawn
     */
    public static final Integer INVOKER_STICK_MIN_MOBS_SPAWN = 3;

    /**
     * Maximum number of mobs to spawn
     */
    public static final Integer INVOKER_STICK_MAX_MOBS_SPAWN = 5;

    /**
     * Types of entities that can be spawned
     * Entities must be childrens of {@link org.bukkit.entity.Mob} to correctly have a target
     */
    public static final EntityType[] INVOKER_STICK_ENTITIES_TYPES =
            new EntityType[] {EntityType.ZOMBIE, EntityType.SKELETON, EntityType.ENDERMAN};

    /*


    ██████╗░░█████╗░░██████╗███████╗██╗██████╗░░█████╗░███╗░░██╗
    ██╔══██╗██╔══██╗██╔════╝██╔════╝██║██╔══██╗██╔══██╗████╗░██║
    ██████╔╝██║░░██║╚█████╗░█████╗░░██║██║░░██║██║░░██║██╔██╗██║
    ██╔═══╝░██║░░██║░╚═══██╗██╔══╝░░██║██║░░██║██║░░██║██║╚████║
    ██║░░░░░╚█████╔╝██████╔╝███████╗██║██████╔╝╚█████╔╝██║░╚███║
    ╚═╝░░░░░░╚════╝░╚═════╝░╚══════╝╚═╝╚═════╝░░╚════╝░╚═╝░░╚══╝

    ░██████╗░█████╗░███████╗██████╗░████████╗███████╗██████╗░
    ██╔════╝██╔══██╗██╔════╝██╔══██╗╚══██╔══╝██╔════╝██╔══██╗
    ╚█████╗░██║░░╚═╝█████╗░░██████╔╝░░░██║░░░█████╗░░██████╔╝
    ░╚═══██╗██║░░██╗██╔══╝░░██╔═══╝░░░░██║░░░██╔══╝░░██╔══██╗
    ██████╔╝╚█████╔╝███████╗██║░░░░░░░░██║░░░███████╗██║░░██║
    ╚═════╝░░╚════╝░╚══════╝╚═╝░░░░░░░░╚═╝░░░╚══════╝╚═╝░░╚═╝

     */

    /**
     * Poseidon Scepter task period
     */
    public static final Long POSEIDON_SCEPTER_TASK_TICKS = 2L;

    /**
     * Starting cylinder radius
     */
    public static final Double POSEIDON_SCEPTER_CYLINDER_START_RADIUS = 0.5d;

    /**
     * Max radius of the cylinder
     */
    public static final Double POSEIDON_SCEPTER_CYLINDER_MAX_RADIUS = 2.5d;

    /**
     * Number of steps to draw the cylinder
     * /!\ A lot of steps can cause fps drops and server lags/crashes /!\
     */
    public static final Integer POSEIDON_SCEPTER_CYLINDER_STEPS = 64;

    /**
     * Number of cylinders to draw
     */
    public static final Integer POSEIDON_SCEPTER_NUM_FLOORS = 5;

    /**
     * Height difference between each cylinder
     */
    public static final Double POSEIDON_SCEPTER_FLOORS_SEPARATION = 0.25;

}
