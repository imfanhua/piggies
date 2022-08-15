package me.fanhua.piggies.tools.entitiy

import org.bukkit.entity.EntityType

val ENTITIES = EntityType.values().filter { it != EntityType.UNKNOWN }
