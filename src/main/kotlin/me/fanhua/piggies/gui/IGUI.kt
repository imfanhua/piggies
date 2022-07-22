package me.fanhua.piggies.gui

import me.fanhua.piggies.gui.ui.IUIContainer
import org.bukkit.entity.Player
import java.util.function.BiConsumer

interface IGUI : IBaseGUI, IUIContainer {

	val title: String

	fun redraw(): IGUI
	fun draw()
	val isRedrawNeeded: Boolean

	fun onClose(closeHandler: BiConsumer<IGUI, Player>): IGUI
	fun removeCloseHandler(closeHandler: BiConsumer<IGUI, Player>)
	fun clearCloseHandlers()
	fun clearAllHandlers()

}
