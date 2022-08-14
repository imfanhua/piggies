package me.fanhua.piggies.items

import me.fanhua.piggies.tools.items.damage
import me.fanhua.piggies.tools.items.used
import org.bukkit.block.Block
import org.bukkit.block.BlockState
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

open class CustomItemEvent(val player: Player, val item: ItemStack) {
	var used = false
}

open class CustomItemClickedEvent(player: Player, item: ItemStack, val isLeftClick: Boolean): CustomItemEvent(player, item)

open class CustomItemClickedBlockEvent(
	player: Player, item: ItemStack, isLeftClick: Boolean,
	val block: Block
) : CustomItemClickedEvent(player, item, isLeftClick)

open class CustomItemPlacedEvent(
	player: Player, item: ItemStack, block: Block,
	val old: BlockState, val new: BlockState
) : CustomItemClickedBlockEvent(player, item, false, block)

open class CustomItemClickedEntityEvent(
	player: Player, item: ItemStack, isLeftClick: Boolean,
	val entity: Entity
) : CustomItemClickedEvent(player, item, isLeftClick)

open class CustomItemClickedEntityWithBucketEvent(
	player: Player, item: ItemStack, entity: Entity,
	val final: ItemStack
) : CustomItemClickedEntityEvent(player, item, false, entity)

open class CustomItemUsedEntityEvent(
	player: Player, item: ItemStack,
	val isDrop: Boolean
) : CustomItemEvent(player, item)

open class CustomItemConsumedEvent(player: Player, item: ItemStack) : CustomItemEvent(player, item)
open class CustomItemBrokeEvent(player: Player, item: ItemStack) : CustomItemEvent(player, item)

fun <T: CustomItemEvent> T.use() = apply { used = true }

fun <T: CustomItemEvent> T.damage(cost: Int = 1) = apply { item.damage(cost) }
fun <T: CustomItemEvent> T.used(cost: Int = 1) = apply { item.used(cost) }
