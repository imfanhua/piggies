package me.fanhua.piggies.gui.ui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class UILambdaFlatList<T>(
	list: List<T>? = null,
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	renderer: (IUICanvas.(Int, Int, T, Int) -> Unit),
	var handler: ((UILambdaFlatList<T>, Player, ClickType, Int, Int, T, Int) -> Unit)? = null,
) : UIFlatList<T>(list, x, y, width, height) {

	var renderer by observable(renderer)

	override fun draw(canvas: IUICanvas, x: Int, y: Int, value: T, index: Int) = renderer(canvas, x, y, value, index)

	override fun use(clicker: Player, type: ClickType, x: Int, y: Int, value: T, index: Int): Boolean
		= true.apply { handler?.invoke(this@UILambdaFlatList, clicker, type, x, y, value, index) }

	fun onUse(handler: (ui: UILambdaFlatList<T>, clicker: Player, type: ClickType, x: Int, y: Int, value: T, index: Int) -> Unit) = apply {
		this.handler = handler
	}

}

fun <T> IUIContainer.flat(
	list: List<T>? = null,
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	renderer: (IUICanvas.(x: Int, y: Int, value: T, index: Int) -> Unit),
) = add(UILambdaFlatList(list, x, y, width, height, renderer))
