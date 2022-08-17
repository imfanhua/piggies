package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.gui.GUISize
import me.fanhua.piggies.tools.vars.Cache
import org.bukkit.entity.Player

abstract class UIFlatList<T>(
	list: List<T>? = null,
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
) : IBaseSizedUI(x, y, width, height) {

	private var cache: Cache<*>? = list as? Cache<*>
	var list by observable(list) { cache = it as? Cache<*> }

	override fun update() {
		cache?.let { if (it.use()) redraw = true }
	}

	override fun whenDraw(canvas: IUICanvas) {
		val size = canvas.size
		val list = list
		if (list.isNullOrEmpty()) return
		val pageSize = size.size
		val listSize = list.size
		val width = size.width
		for (i in 0 until pageSize) {
			if (i >= listSize) break
			val y = i / width
			draw(canvas, i - y * width, y, list[i], i)
		}
	}

	override fun whenUse(clicker: Player, type: ActionType, x: Int, y: Int, size: GUISize): Boolean {
		val list = list
		if (list.isNullOrEmpty()) return false
		val pos = size[x, y]
		return if (pos < list.size) use(clicker, type, x, y, list[pos], pos) else false
	}

	protected abstract fun draw(canvas: IUICanvas, x: Int, y: Int, value: T, index: Int)
	protected abstract fun use(clicker: Player, type: ActionType, x: Int, y: Int, value: T, index: Int): Boolean

}
