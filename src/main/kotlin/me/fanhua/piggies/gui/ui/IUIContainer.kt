package me.fanhua.piggies.gui.ui

interface IUIContainer {

	fun <T : IUI> add(ui: T): T
	fun <T : IUI> remove(ui: T): T
	val ui: List<IUI>
	fun clear()

}
