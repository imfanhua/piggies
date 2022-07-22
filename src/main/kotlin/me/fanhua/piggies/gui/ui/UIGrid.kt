package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.gui.GUISize
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import kotlin.properties.Delegates

class UIGrid constructor(
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
) : IContainerUI {

	var x by Delegates.observable(x) { _, _, _ -> redraw = true }
	var y by Delegates.observable(y) { _, _, _ -> redraw = true }
	var width by Delegates.observable(width) { _, _, _ -> redraw = true }
	var height by Delegates.observable(height) { _, _, _ -> redraw = true }

	private var lastSize: GUISize? = null
	private var redraw = true

	override val ui: MutableList<IUI> = arrayListOf()

	override fun redraw() = redraw || ui.any(IUI::redraw)

	override fun update() {
		for (ui in ui) ui.update()
	}

	override fun draw(canvas: IUICanvas) {
		redraw = false
		val diff = canvas.diffOf(x, y, width, height)
		lastSize = diff.size
		for (ui in ui) ui.draw(diff)
	}

	override fun use(clicker: Player, type: ClickType, x: Int, y: Int): Boolean {
		var calcX = x
		var calcY = y
		calcX -= this.x
		calcY -= this.y
		if (calcX < 0 || calcY < 0 || calcX > lastSize!!.width || calcY > lastSize!!.lines) return false
		for (ui in ui) if (ui.use(clicker, type, calcX, calcY)) return true
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
