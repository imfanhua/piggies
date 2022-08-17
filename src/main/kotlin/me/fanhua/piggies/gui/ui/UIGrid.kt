package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.gui.GUISize
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

open class UIGrid constructor(
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
) : IBaseSizedUI(x, y, width, height), IContainerUI {

	override val ui: MutableList<IUI> = arrayListOf()

	override fun redraw() = redraw || ui.any(IUI::redraw)

	override fun update() {
		for (ui in ui) ui.update()
	}

	override fun whenDraw(canvas: IUICanvas) {
		for (ui in ui) ui.draw(canvas)
	}

	override fun whenUse(clicker: Player, type: ActionType, x: Int, y: Int, size: GUISize): Boolean {
		for (i in ui.indices.reversed())
			if (ui[i].use(clicker, type, x, y)) return true
		return false
	}

	override fun <T : IUI> add(ui: T) = ui.apply {
		if (!this@UIGrid.ui.contains(ui)) {
			redraw = true
			this@UIGrid.ui.add(ui)
		}
	}

	override fun <T : IUI> remove(ui: T) = ui.apply {
		if (this@UIGrid.ui.remove(ui)) redraw = true
	}

	override fun clear() {
		if (ui.isEmpty()) return
		ui.clear()
		redraw = true
	}

}

fun IUIContainer.grid(
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
) = add(UIGrid(x, y, width, height))

inline fun IUIContainer.grid(
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	call: UIGrid.() -> Unit
) = grid(x, y, width, height).apply(call)
