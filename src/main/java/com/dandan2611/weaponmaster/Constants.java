package com.dandan2611.weaponmaster;

import org.bukkit.entity.EntityType;

public class Constants {

    public static final Integer RANDOM_LOCATION_MAX_Y_TRIES = 3;

    public static final Integer MAX_RAILGUN_DISTANCE = 12;
    public static final Long RAILGUN_TASK_TICKS = 1L;
    public static final Integer MAX_RAILGUN_TASK_ITERATIONS = 2;

    public static final Double MAX_LASER_DISTANCE = 15d;
    public static final Double LASER_STEPS = 0.1d;

    public static final Double FISH_LAUNCHER_BOMB_VELOCITY = 2.25d;
    public static final Long FISH_LAUNCHER_TASK_TICKS = 5L;
    public static final Long FISH_LAUNCHER_COUNTDOWN_TICKS = 2L;
    public static final Integer FISH_LAUNCHER_TIMER = 6;
    public static final Integer FISH_LAUNCHER_FIRST_FILL = 4;
    public static final Integer FISH_LAUNCHER_SECOND_FILL = 2;
    public static final Float FISH_LAUNCHER_EXPLOSION_RADIUS = 16f;

    public static final Long INVOKER_STICK_TASK_TICKS = 5L;
    public static final Double INVOKER_STICK_MAX_TARGET_DISTANCE = 24d;
    public static final Integer INVOKER_STICK_MOBS_CLOSE_UP_DISTANCE = 4;
    public static final Integer INVOKER_STICK_MAX_RANDOM_LOCATION_TRIES = 6;
    public static final Integer INVOKER_STICK_MIN_MOBS_SPAWN = 3;
    public static final Integer INVOKER_STICK_MAX_MOBS_SPAWN = 5;
    public static final EntityType INVOKER_STICK_ENTITY_TYPE = EntityType.ZOMBIE;

    public static final Long POSEIDON_SCEPTER_TASK_TICKS = 2L;
    public static final Double POSEIDON_SCEPTER_CYLINDER_START_RADIUS = 0.5d;
    public static final Double POSEIDON_SCEPTER_CYLINDER_MAX_RADIUS = 2.5d;
    public static final Integer POSEIDON_SCEPTER_CYLINDER_STEPS = 64;
    public static final Integer POSEIDON_SCEPTER_NUM_FLOORS = 5;
    public static final Double POSEIDON_SCEPTER_FLOORS_SEPARATION = 0.25;

}
