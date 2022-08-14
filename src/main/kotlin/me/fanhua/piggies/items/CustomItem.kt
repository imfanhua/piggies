package me.fanhua.piggies.items

import me.fanhua.piggies.PiggyPlugin
import me.fanhua.piggies.plugins.last
import me.fanhua.piggies.tools.ok
import me.fanhua.piggies.tools.plugins.key

abstract class CustomItem(plugin: PiggyPlugin, val id: String) {

	val key = plugin.key(id)
	val stringKey = "$key"

	init {
		@Suppress("LeakingThis")
		CustomItems.add(this)
		plugin.last {
			ok { unload() }
			CustomItems.remove(this)
		}
	}

	open fun whenUsed(event: CustomItemUsedEntityEvent) {}
	open fun whenClicked(event: CustomItemClickedEvent) {}
	open fun whenBroke(event: CustomItemBrokeEvent) {}
	open fun whenConsumed(event: CustomItemConsumedEvent) { event.use() }
	open fun whenPlaced(event: CustomItemPlacedEvent) { event.use() }
	open fun whenClickedBlock(event: CustomItemClickedBlockEvent) {}
	open fun whenClickedEntity(event: CustomItemClickedEntityEvent) { if (!event.isLeftClick) event.use() }
	open fun whenClickedEntityWithBucket(event: CustomItemClickedEntityEvent) { event.use() }

	protected open fun unload() {}

}
