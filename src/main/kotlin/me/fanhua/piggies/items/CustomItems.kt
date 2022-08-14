package me.fanhua.piggies.items

import me.fanhua.piggies.Piggies
import me.fanhua.piggies.tools.data.persistents.get
import me.fanhua.piggies.tools.data.persistents.set
import me.fanhua.piggies.tools.data.persistents.with
import me.fanhua.piggies.tools.items.isEmpty
import me.fanhua.piggies.tools.plugins.key
import me.fanhua.piggies.tools.plugins.logger
import me.fanhua.piggies.tools.plugins.on
import me.fanhua.piggies.tools.void
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action.*
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.hanging.HangingBreakByEntityEvent
import org.bukkit.event.hanging.HangingBreakEvent
import org.bukkit.event.player.*
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object CustomItems {

	private val KEY_ITEM_ID = Piggies.key("item_id").with(PersistentDataType.STRING)
	private val ITEMS: MutableMap<String, CustomItem> = mutableMapOf()

	val keys: Collection<String> get() = ITEMS.keys
	val items: Collection<CustomItem> get() = ITEMS.values

	init {
		Piggies.on(object : Listener {

			@EventHandler(priority = EventPriority.LOW)
			fun onBlockPlaceEvent(event: BlockPlaceEvent) {
				val item = event.itemInHand
				val custom = CustomItems[item] ?: return
				if (CustomItemPlacedEvent(event.player, item, event.block, event.block.state, event.blockReplacedState)
						.apply(custom::whenPlaced)
						.apply(custom::whenClickedBlock)
						.apply(custom::whenClicked)
						.used)
					event.isCancelled = true
			}

			@EventHandler(priority = EventPriority.LOW)
			fun onPlayerInteractEvent(event: PlayerInteractEvent) {
				val item = event.item ?: return
				val custom = CustomItems[item] ?: return
				val isLeftClick = when (event.action) {
					LEFT_CLICK_BLOCK -> true
					RIGHT_CLICK_BLOCK -> false
					LEFT_CLICK_AIR -> true
					RIGHT_CLICK_AIR -> false
					else -> return
				}

				if ((event.clickedBlock?.let {
						CustomItemClickedBlockEvent(event.player, item, isLeftClick, it)
						.apply(custom::whenClickedBlock)
					} ?: CustomItemClickedEvent(event.player, item, isLeftClick))
						.apply(custom::whenClicked)
						.used)
					event.isCancelled = true
			}

			@EventHandler(priority = EventPriority.LOW)
			fun onPlayerBucketEmptyEvent(event: PlayerBucketEmptyEvent) = useBucket(event)

			@EventHandler(priority = EventPriority.LOW)
			fun onPlayerBucketFillEvent(event: PlayerBucketFillEvent) = useBucket(event)

			private fun useBucket(event: PlayerBucketEvent) {
				val item = event.itemStack ?: return
				val custom = CustomItems[item] ?: return
				if (CustomItemClickedBlockEvent(event.player, item, false, event.block)
						.apply(custom::whenClickedBlock)
						.apply(custom::whenClicked)
						.used)
					event.isCancelled = true
			}

			@EventHandler(priority = EventPriority.LOW)
			fun onPlayerItemConsumeEvent(event: PlayerItemConsumeEvent) {
				val item = event.item
				val custom = CustomItems[item] ?: return
				if (CustomItemConsumedEvent(event.player, item)
						.apply(custom::whenConsumed)
						.used)
					event.isCancelled = true
				event.setItem(item)
			}

			@EventHandler(priority = EventPriority.LOW)
			fun onPlayerItemBreakEvent(event: PlayerItemBreakEvent) {
				val item = event.brokenItem
				val custom = CustomItems[item] ?: return
				if (CustomItemBrokeEvent(event.player, item)
						.apply(custom::whenBroke)
						.used)
					item.amount++
			}

			@EventHandler(priority = EventPriority.LOW)
			fun onPlayerInteractEntityEvent(event: PlayerInteractEntityEvent) {
				if (rightClickEntity(event.player, event.hand, event.rightClicked))
					event.isCancelled = true
			}

			@EventHandler(priority = EventPriority.LOW)
			fun onPlayerArmorStandManipulateEvent(event: PlayerArmorStandManipulateEvent) {
				if (rightClickEntity(event.player, event.hand, event.rightClicked))
					event.isCancelled = true
			}

			@EventHandler(priority = EventPriority.LOW)
			fun onPlayerBucketEntityEvent(event: PlayerBucketEntityEvent) {
				val item = event.originalBucket
				val custom = CustomItems[item] ?: return
				if (CustomItemClickedEntityWithBucketEvent(event.player, item, event.entity, event.entityBucket)
						.apply(custom::whenClickedEntityWithBucket)
						.apply(custom::whenClickedEntity)
						.apply(custom::whenClicked)
						.used)
					event.isCancelled = true
			}

			private fun rightClickEntity(player: Player, slot: EquipmentSlot, entity: Entity): Boolean {
				val item = player.inventory.getItem(slot)
				val custom = CustomItems[item] ?: return false
				return CustomItemClickedEntityEvent(player, item, false, entity)
					.apply(custom::whenClickedEntity)
					.apply(custom::whenClicked)
					.used
			}

			@EventHandler(priority = EventPriority.LOW)
			fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {
				if (event.cause != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return
				if (leftClickEntity(event.damager, event.entity))
					event.isCancelled = true
			}

			@EventHandler(priority = EventPriority.LOW)
			fun onHangingBreakByEntityEvent(event: HangingBreakByEntityEvent) {
				if (event.cause != HangingBreakEvent.RemoveCause.ENTITY) return
				if (leftClickEntity(event.remover, event.entity))
					event.isCancelled = true
			}

			private fun leftClickEntity(who: Entity?, entity: Entity): Boolean {
				val player = who as? Player ?: return false
				val item = player.inventory.itemInMainHand
				val custom = CustomItems[item] ?: return false
				return CustomItemClickedEntityEvent(player, item, true, entity)
						.apply(custom::whenClickedEntity)
						.apply(custom::whenClicked)
						.used
			}

			@EventHandler(priority = EventPriority.LOW)
			fun onPlayerDropItemEvent(event: PlayerDropItemEvent) {
				val entity = event.itemDrop
				val item = entity.itemStack
				val custom = CustomItems[item] ?: return
				val result = CustomItemUsedEntityEvent(event.player, item, true).apply(custom::whenUsed)
				if (result.used)
					event.isCancelled = true
				else if (item.isEmpty) entity.remove()
				else entity.itemStack = item
			}

			@EventHandler(priority = EventPriority.LOW)
			fun onPlayerDropItemEvent(event: PlayerSwapHandItemsEvent) {
				val player = event.player
				val main = whenSwap(player, event.mainHandItem)
				val off = whenSwap(player, event.offHandItem)
				if (main || off) event.isCancelled = true
			}

			private fun whenSwap(player: Player, item: ItemStack?): Boolean {
				if (item == null) return false
				val custom = CustomItems[item] ?: return false
				return CustomItemUsedEntityEvent(player, item, false).apply(custom::whenUsed).used
			}

		})

		Piggies.logger.info("+ #[CustomItems]")
	}

	operator fun get(key: NamespacedKey) = ITEMS["$key"]
	operator fun get(stringKey: String) = ITEMS[stringKey]

	operator fun get(item: ItemStack?) = item?.let {
		it.itemMeta?.persistentDataContainer?.get(KEY_ITEM_ID)?.let(ITEMS::get)
	}

	operator fun set(item: ItemStack, key: NamespacedKey) = set(item, "$key")
	operator fun set(item: ItemStack, stringKey: String) {
		item.itemMeta = item.itemMeta?.apply { persistentDataContainer[KEY_ITEM_ID] = stringKey }
	}
	operator fun set(item: ItemStack, custom: CustomItem) = set(item, custom.stringKey)

	internal fun add(item: CustomItem) { ITEMS[item.stringKey] = item }
	internal fun remove(item: CustomItem) = ITEMS.remove(item.stringKey).void()

}

fun ItemStack.with(custom: CustomItem) = apply { CustomItems[this] = custom }
