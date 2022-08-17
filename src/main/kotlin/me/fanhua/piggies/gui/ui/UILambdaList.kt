package me.fanhua.piggies.gui.ui

import org.bukkit.entity.Player

class UILambdaList<T>(
	list: List<T>? = null,
	extra: Int = 0,
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	renderer: (IUICanvas.(Int, Int, T, Int) -> Unit),
	var handler: ((UILambdaList<T>, Player, ActionType, Int, Int, T, Int) -> Unit)? = null,
) : UIList<T>(list, extra, x, y, width, height) {

	var renderer by observable(renderer)

	override fun draw(canvas: IUICanvas, x: Int, y: Int, value: T, index: Int) = renderer(canvas, x, y, value, index)

	override fun use(clicker: Player, type: ActionType, x: Int, y: Int, value: T, index: Int): Boolean
		= true.apply { handler?.invoke(this@UILambdaList, clicker, type, x, y, value, index) }

	fun onUse(handler: (ui: UILambdaList<T>, clicker: Player, type: ActionType, x: Int, y: Int, value: T, index: Int) -> Unit) = apply {
		this.handler = handler
	}

}

fun <T> IUIContainer.list(
	list: List<T>? = null,
	extra: Int = 0,
	x: Int = 0,
	y: Int = 0,
	width: Int = -1,
	height: Int = -1,
	renderer: (IUICanvas.(x: Int, y: Int, value: T, index: Int) -> Unit),
) = add(UILambdaList(list, extra, x, y, width, height, renderer))
