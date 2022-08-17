package me.fanhua.piggies.gui.ui

import org.bukkit.event.inventory.ClickType

enum class ActionType(
	val type: ClickType,
	val hotbar: Int = -1,
) {

	LEFT(ClickType.LEFT),
	SHIFT_LEFT(ClickType.SHIFT_LEFT),
	RIGHT(ClickType.RIGHT),
	SHIFT_RIGHT(ClickType.SHIFT_RIGHT),
	WINDOW_BORDER_LEFT(ClickType.WINDOW_BORDER_LEFT),
	WINDOW_BORDER_RIGHT(ClickType.WINDOW_BORDER_RIGHT),
	MIDDLE(ClickType.MIDDLE),
	NUMBER_KEY_1(ClickType.NUMBER_KEY, 0),
	NUMBER_KEY_2(ClickType.NUMBER_KEY, 1),
	NUMBER_KEY_3(ClickType.NUMBER_KEY, 2),
	NUMBER_KEY_4(ClickType.NUMBER_KEY, 3),
	NUMBER_KEY_5(ClickType.NUMBER_KEY, 4),
	NUMBER_KEY_6(ClickType.NUMBER_KEY, 5),
	NUMBER_KEY_7(ClickType.NUMBER_KEY, 6),
	NUMBER_KEY_8(ClickType.NUMBER_KEY, 7),
	NUMBER_KEY_9(ClickType.NUMBER_KEY, 8),
	DOUBLE_CLICK(ClickType.DOUBLE_CLICK),
	DROP(ClickType.DROP),
	CONTROL_DROP(ClickType.CONTROL_DROP),
	CREATIVE(ClickType.CREATIVE),
	SWAP_OFFHAND(ClickType.SWAP_OFFHAND),
	UNKNOWN(ClickType.UNKNOWN),
	;

	val isKeyboard: Boolean = type.isKeyboardClick
	val isCreative: Boolean = type.isCreativeAction
	val isLeftClick: Boolean = type.isLeftClick
	val isRightClick: Boolean = type.isRightClick
	val isWithAlt: Boolean = type.isShiftClick
	val key = if (hotbar < 0) -1 else hotbar + 1

	companion object {

		private val TYPE_MAP = values().associateBy { it.type }
		private val HOTBAR_MAP = values().filter { it.hotbar != -1 }.sortedBy { it.hotbar }.toTypedArray()
		private val KEY_MAP = values().filter { it.key != -1 }.sortedBy { it.key }.toTypedArray()

		operator fun get(type: ClickType) = TYPE_MAP[type] ?: UNKNOWN
		operator fun get(type: ClickType, hotbar: Int)
			= (if (type == ClickType.NUMBER_KEY) HOTBAR_MAP.getOrNull(hotbar) else TYPE_MAP[type]) ?: UNKNOWN

		fun fromHotbar(hotbar: Int) = HOTBAR_MAP.getOrNull(hotbar) ?: UNKNOWN
		fun fromKey(key: Int) = KEY_MAP.getOrNull(key) ?: UNKNOWN

	}

}
