package me.fanhua.piggies.gui

import me.fanhua.piggies.gui.impl.GUIImpl
import me.fanhua.piggies.gui.ui.IUI
import me.fanhua.piggies.gui.ui.IUIContainer

fun IInventoryFactory.new(title: String): IGUI = GUIImpl(this, title)
inline fun IInventoryFactory.new(title: String, call: IGUI.() -> Unit): IGUI = new(title).apply(call)

fun IUI?.isOrContains(ui: IUI): Boolean
	= this == ui || (this as? IUIContainer)?.ui?.any { it.isOrContains(ui) } == true

fun IBaseGUI?.contains(ui: IUI): Boolean = (this as? IUIContainer)?.ui?.any { it.isOrContains(ui) } == true
