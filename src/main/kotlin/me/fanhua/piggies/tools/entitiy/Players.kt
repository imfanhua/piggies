package me.fanhua.piggies.tools.entitiy

import org.bukkit.GameMode
import org.bukkit.entity.Player

val Player.isInGame get()
	= when (gameMode) {
		GameMode.SURVIVAL, GameMode.ADVENTURE -> true
		else -> false
	}