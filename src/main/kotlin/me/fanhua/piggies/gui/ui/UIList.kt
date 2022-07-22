package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.gui.GUISize
import me.fanhua.piggies.tools.items.*
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import kotlin.properties.Delegates

abstract class UIList<T>(
	list: List<T>? = null,
	extra: Int = 0,
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
) : IUI {

	var list by Delegates.observable(list) { _, _, _ -> redraw = true }
	var extra by Delegates.observable(extra) { _, _, _ -> redraw = true }
	var x by Delegates.observable(x) { _, _, _ -> redraw = true }
	var y by Delegates.observable(y) { _, _, _ -> redraw = true }
	var width by Delegates.observable(width) { _, _, _ -> redraw = true }
	var height by Delegates.observable(height) { _, _, _ -> redraw = true }

	var page by Delegates.observable(0) { _, _, _ -> redraw = true }

	private var lastSize: GUISize? = null
	private var lastStart = 0
	private var redraw = true

	val totalPage: Int
		get() {
			val lastSize = lastSize
			val list = list
			if (lastSize == null || list == null) return 0
			val total = lastSize.size - extra - 1
			if (total < 1) return 0
			val size = list.size
			val page = size / total
			return if (page * total < size) page + 1 else page
		}

	fun prevPage() { if (page >= 0) page-- }
	fun nextPage() { page++ }

	fun firstPage() { page = 0 }
	fun lastPage() { page = Int.MAX_VALUE }
	fun updated() { redraw = true }

	override fun redraw() = redraw

	override fun update() {}

	override fun draw(canvas: IUICanvas) {
		redraw = false
		val diff = canvas.diffOf(x, y, width, height)
		val lastSize = diff.size
		this.lastSize = lastSize
		val list = list
		if (list == null || list.isEmpty()) {
			draw(canvas, lastSize.width - 1, lastSize.lines - 1, 0, 1)
			return
		}
		val pageSize = lastSize.size - extra - 1
		val total = totalPage
		if (page < 0) page = 0 else if (page >= total) page = total - 1
		redraw = false
		if (page >= total) return
		val start = page * pageSize
		val size = list.size
		val width = lastSize.width
		lastStart = start
		for (i in 0 until pageSize) {
			val index = start + i
			if (index >= size) break
			val y = i / width
			draw(canvas, i - y * width, y, list[index], index)
		}
		draw(canvas, lastSize.width - 1, lastSize.lines - 1, page, total)
	}

	override fun use(clicker: Player, type: ClickType, x: Int, y: Int): Boolean {
		val lastSize = lastSize
		val list = list
		if (lastSize == null || list == null || list.isEmpty()) return false
		val calcX = x - this.x
		val calcY = y - this.y
		if (calcX < 0 || calcY < 0 || calcX > lastSize.width || calcY > lastSize.lines) return false
		val pos = calcY * lastSize.width + calcX
		val i = lastStart + pos
		if (pos >= lastSize.size - extra - 1) {
			if (pos == lastSize.size - 1) return use(clicker, type)
		} else if (i < list.size) return use(clicker, type, calcX, calcY, list[i], i)
		return false
	}

	protected fun draw(canvas: IUICanvas, x: Int, y: Int, page: Int, maxPage: Int) {
		canvas.draw(x, y, Material.BOOK.item(if (page < 63) page + 1 else 1) {
			name("§e${(page + 1)}§7 / §6$maxPage")
			lores(
				" §7 > §a§l左键§6 下一页",
				" §7 > §e§l右键§6 上一页",
			)
		})
	}

	protected abstract fun draw(canvas: IUICanvas, x: Int, y: Int, value: T, index: Int)
	protected abstract fun use(clicker: Player, type: ClickType, x: Int, y: Int, value: T, index: Int): Boolean
	protected open fun use(clicker: Player, type: ClickType): Boolean {
		if (type.isLeftClick) nextPage() else if (type.isRightClick) prevPage()
		return true
	}

}
