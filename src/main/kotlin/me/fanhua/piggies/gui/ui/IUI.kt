package me.fanhua.piggies.gui.ui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

interface IUI {

	fun redraw(): Boolean
	fun update()
	fun draw(canvas: IUICanvas)
	fun use(clicker: Player, type: ClickType, x: Int, y: Int): Boolean

}