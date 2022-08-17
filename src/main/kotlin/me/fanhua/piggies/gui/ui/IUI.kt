package me.fanhua.piggies.gui.ui

import org.bukkit.entity.Player

interface IUI {

	fun redraw(): Boolean
	fun update() {}
	fun updated() {}
	fun draw(canvas: IUICanvas)
	fun use(clicker: Player, type: ActionType, x: Int, y: Int): Boolean = false

}
