package me.fanhua.piggies.gui.ui

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

abstract class IBaseUI : IUI {

	protected var redraw = true

	override fun redraw(): Boolean = redraw
	override fun updated() { redraw = true }
	override fun draw(canvas: IUICanvas) { redraw = false }

	protected open fun whenObservableUpdated(property: KProperty<*>, oldValue: Any?, newValue: Any?) {
		if (oldValue != newValue) updated()
	}

	protected fun <T> observable(initialValue: T)
		= Delegates.observable(initialValue, ::whenObservableUpdated)
	protected inline fun <T> observable(initialValue: T, crossinline updated: (T) -> Unit)
		= Delegates.observable(initialValue) { property, oldValue, newValue ->
			if (oldValue != newValue) updated(newValue)
			whenObservableUpdated(property, oldValue, newValue)
		}

}
