package me.fanhua.piggies.gui

import me.fanhua.piggies.gui.impl.GUIImpl

fun IInventoryFactory.new(title: String): IGUI = GUIImpl(this, title)
inline fun IInventoryFactory.new(title: String, call: IGUI.() -> Unit): IGUI = new(title).apply(call)