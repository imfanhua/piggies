package me.fanhua.piggies.gui.ui

import me.fanhua.piggies.Piggies
import me.fanhua.piggies.gui.GUI
import me.fanhua.piggies.gui.IGUI
import me.fanhua.piggies.gui.contains
import me.fanhua.piggies.tools.data.holders.PlayerHold
import me.fanhua.piggies.tools.data.holders.hold
import me.fanhua.piggies.tools.items.*
import me.fanhua.piggies.tools.plugins.tick
import me.fanhua.piggies.tools.void
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.ClickType.*
import org.bukkit.inventory.ItemStack

class UISlot(
	x: Int,
	y: Int,
	icon: ItemStack? = null,
	placed: ItemStack? = null,
	var place: ((UISlot, Player, ClickType) -> Unit)? = null,
	var changed: ((UISlot, ItemStack?) -> Unit)? = null,
) : IBasePosUI(x, y) {

	object Slot {

		val SWAP: (UISlot, Player, ClickType) -> Unit = ::swap

		fun swap(ui: UISlot, clicker: Player, type: ClickType) = swap(ui, clicker.hold).void()

		private fun swap(ui: UISlot, clicker: PlayerHold) = Piggies.tick { ->
			clicker.orNull?.let {
				if (GUI[it].contains(ui)) {
					val placed = ui.placed?.clone()
					ui.placed = it.itemOnCursor.clone().nullEmpty
					it.setItemOnCursor(placed)
				}
			}
		}

	}

	object Tag {

		val TAG = new(::amount)
		val ONLY_ONE = new(::one)
		val ONLY_TYPE = new(::amount, ::type)
		val ONLY_ONE_TYPE = new(::type, ::one)

		fun new(vararg filters: (placed: ItemStack?, item: ItemStack?, clicker: Player, type: ClickType) -> ItemStack?): (ui: UISlot, clicker: Player, type: ClickType) -> Unit = { ui, clicker, type ->
			var item = clicker.itemOnCursor.clone().nullEmpty
			val placed = ui.placed
			filters.forEach { item = it(placed, item, clicker, type) }
			ui.placed = item
		}

		fun one(placed: ItemStack?, item: ItemStack?, clicker: Player, type: ClickType): ItemStack?
			= item?.clone(1)

		fun type(placed: ItemStack?, item: ItemStack?, clicker: Player, type: ClickType): ItemStack?
			= item?.let { ItemStack(it.type, it.amount) }

		fun amount(placed: ItemStack?, item: ItemStack?, clicker: Player, click: ClickType): ItemStack?
			= when (click) {
				LEFT -> item ?: placed?.clone()?.apply { if (amount < type.maxStackSize) amount++ }
				SHIFT_LEFT ->
					item?.clone()?.apply { amount = type.maxStackSize }
					?: placed?.clone()?.apply { amount = type.maxStackSize }
				RIGHT -> placed?.clone()?.apply { amount-- }?.nullEmpty
				SHIFT_RIGHT -> placed?.clone()?.apply { amount = 1 }?.nullEmpty
				DROP -> placed?.clone()?.apply { amount-- }?.nullEmpty
				CONTROL_DROP -> null
				else -> placed
			}

	}

	var icon by observable(icon)
	var placed by observable(placed) { changed?.invoke(this, it) }

	override fun draw(canvas: IUICanvas) {
		redraw = false
		(placed ?: icon)?.let { canvas.draw(x, y, it) }
	}

	override fun use(clicker: Player, type: ClickType, x: Int, y: Int): Boolean
		= if (this.x != x || this.y != y) false
		else true.apply { place?.invoke(this@UISlot, clicker, type) }

	fun drop(gui: IGUI) = gui.onClose(::dropTo)

	fun dropTo(player: Player) {
		placed?.let {
			player.give(it.clone())
		}
		placed = null
	}

	private fun dropTo(gui: IGUI, player: Player) = dropTo(player)

}


fun IUIContainer.slot(
	x: Int,
	y: Int,
	icon: ItemStack? = null,
	placed: ItemStack? = null,
	place: ((UISlot, Player, ClickType) -> Unit)? = UISlot.Slot.SWAP,
	changed: ((UISlot, ItemStack?) -> Unit)? = null,
) = add(UISlot(x, y, icon, placed, place, changed))

fun IUIContainer.tag(
	x: Int,
	y: Int,
	icon: ItemStack? = null,
	placed: ItemStack? = null,
	place: ((UISlot, Player, ClickType) -> Unit)? = UISlot.Tag.TAG,
	changed: ((UISlot, ItemStack?) -> Unit)? = null,
) = add(UISlot(x, y, icon, placed, place, changed))

fun IUIContainer.tag(
	x: Int,
	y: Int,
	icon: ItemStack? = null,
	place: ((UISlot, Player, ClickType) -> Unit)? = UISlot.Tag.TAG,
	changed: ((UISlot, ItemStack?) -> Unit)? = null,
) = add(UISlot(x, y, icon, null, place, changed))
