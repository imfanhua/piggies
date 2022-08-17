package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.gui.GUISize
import me.fanhua.piggies.tools.items.item
import me.fanhua.piggies.tools.items.lores
import me.fanhua.piggies.tools.items.name
import me.fanhua.piggies.tools.vars.Cache
import org.bukkit.Material
import org.bukkit.entity.Player

abstract class UIList<T>(
	list: List<T>? = null,
	extra: Int = 0,
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
) : IBaseSizedUI(x, y, width, height) {

	private var cache: Cache<*>? = list as? Cache<*>
	var list by observable(list) { cache = it as? Cache<*> }

	var extra by observable(extra)
	var page by observable(0)

	private var lastStart = 0

	val totalPage: Int
		get() {
			val size = size
			val list = list
			if (size == null || list == null) return 0
			val total = size.size - extra - 1
			if (total < 1) return 0
			val listSize = list.size
			val page = listSize / total
			return if (page * total < listSize) page + 1 else page
		}

	fun prevPage() { if (page >= 0) page-- }
	fun nextPage() { page++ }

	fun firstPage() { page = 0 }
	fun lastPage() { page = Int.MAX_VALUE }

	override fun update() {
		cache?.let { if (it.use()) redraw = true }
	}

	override fun whenDraw(canvas: IUICanvas) {
		val size = canvas.size
		val list = list
		if (list.isNullOrEmpty()) {
			draw(canvas, size.width - 1, size.lines - 1, 0, 1)
			return
		}
		val pageSize = size.size - extra - 1
		val total = totalPage
		if (page < 0) page = 0 else if (page >= total) page = total - 1
		redraw = false
		if (page >= total) return
		val start = page * pageSize
		val listSize = list.size
		val width = size.width
		lastStart = start
		for (i in 0 until pageSize) {
			val index = start + i
			if (index >= listSize) break
			val y = i / width
			draw(canvas, i - y * width, y, list[index], index)
		}
		draw(canvas, size.width - 1, size.lines - 1, page, total)
	}

	override fun whenUse(clicker: Player, type: ActionType, x: Int, y: Int, size: GUISize): Boolean {
		val list = list
		if (list.isNullOrEmpty()) return false
		val pos = size[x, y]
		val i = lastStart + pos
		if (pos >= size.size - extra - 1) {
			if (pos == size.size - 1) return use(clicker, type)
		} else if (i < list.size) return use(clicker, type, x, y, list[i], i)
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
	protected abstract fun use(clicker: Player, type: ActionType, x: Int, y: Int, value: T, index: Int): Boolean
	protected open fun use(clicker: Player, type: ActionType): Boolean {
		if (type.isLeftClick) nextPage() else if (type.isRightClick) prevPage()
		return true
	}

}
