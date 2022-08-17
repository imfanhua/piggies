package me.fanhua.piggies.gui.impl

import me.fanhua.piggies.gui.IGUI
import me.fanhua.piggies.gui.IInventoryFactory
import me.fanhua.piggies.gui.ui.ActionType
import me.fanhua.piggies.gui.ui.IUI
import me.fanhua.piggies.ticks.Ticks
import me.fanhua.piggies.tools.void
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

internal class GUIImpl(factory: IInventoryFactory, override val title: String) : IGUI {

	override val size = factory.size
	private val inventory = factory.create(this, title)
	override fun getInventory() = inventory

	private var redraw = true
	override val ui: MutableList<IUI> = ArrayList()
	private val canvas = InvUICanvasImpl(size, inventory)

	private var lastUpdatedTick: Int? = null

	private val closeHandlers = arrayListOf<IGUI.(Player) -> Unit>()
	private val updateHandlers = arrayListOf<IGUI.() -> Unit>()

	override fun onUpdate(updateHandler: IGUI.() -> Unit): IGUI = apply {
		updateHandlers.remove(updateHandler)
		updateHandlers.add(updateHandler)
	}
	override fun removeUpdateHandler(updateHandler: IGUI.() -> Unit)
		= updateHandlers.remove(updateHandler).void()
	override fun clearUpdateHandlers() = updateHandlers.clear()

	override fun onClose(closeHandler: IGUI.(Player) -> Unit) = apply {
		closeHandlers.remove(closeHandler)
		closeHandlers.add(closeHandler)
	}
	override fun removeCloseHandler(closeHandler: IGUI.(Player) -> Unit)
		= closeHandlers.remove(closeHandler).void()
	override fun clearCloseHandlers() = closeHandlers.clear()

	override fun clearAllHandlers() {
		updateHandlers.clear()
		closeHandlers.clear()
	}

	override fun redraw() = apply { redraw = true }

	override val isRedrawNeeded: Boolean
		get() = redraw || ui.any(IUI::redraw)

	override fun draw() {
		redraw = false
		inventory.clear()
		for (ui in ui) ui.draw(canvas)
	}

	override fun whenUpdate() {
		val tick = Ticks.tick
		if (lastUpdatedTick == tick) return
		lastUpdatedTick = tick
		doUpdate()
	}

	override fun doUpdate() {
		for (handler in updateHandlers) handler(this)
		for (ui in ui) ui.update()
		if (isRedrawNeeded) draw()
	}

	override fun whenUse(event: InventoryClickEvent) {
		val player = event.whoClicked as? Player ?: return
		val slot = event.slot
		val y = slot / size.width
		val x = slot - y * size.width
		val type = ActionType[event.click, event.hotbarButton]
		for (i in ui.indices.reversed())
			if (ui[i].use(player, type, x, y)) break
	}

	override fun whenClose(event: InventoryCloseEvent) {
		val player = event.player as? Player ?: return
		for (handler in closeHandlers) handler(this, player)
	}

	override fun <T : IUI> add(ui: T) = ui.apply {
		if (!this@GUIImpl.ui.contains(ui)) {
			redraw = true
			this@GUIImpl.ui.add(ui)
		}
	}

	override fun <T : IUI> remove(ui: T) = ui.apply {
		if (this@GUIImpl.ui.remove(ui)) redraw = true
	}

	override fun clear() {
		if (this.ui.isEmpty()) return
		this.ui.clear()
		redraw = true
	}

}
