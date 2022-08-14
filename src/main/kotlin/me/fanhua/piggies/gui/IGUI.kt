package me.fanhua.piggies.gui

import me.fanhua.piggies.gui.ui.IUIContainer
import org.bukkit.entity.Player

interface IGUI : IBaseGUI, IUIContainer {

	val title: String

	fun redraw(): IGUI
	fun draw()
	val isRedrawNeeded: Boolean

	fun doUpdate()

	fun onUpdate(updateHandler: IGUI.() -> Unit): IGUI
	fun removeUpdateHandler(updateHandler: IGUI.() -> Unit)
	fun clearUpdateHandlers()

	fun onClose(closeHandler: IGUI.(Player) -> Unit): IGUI
	fun removeCloseHandler(closeHandler: IGUI.(Player) -> Unit)
	fun clearCloseHandlers()

	fun clearAllHandlers()

}
