package me.fanhua.piggies.tools.items

import org.bukkit.Material

enum class TierItem {

	@Suppress("SpellCheckingInspection")
	NETHERITE(
		null,
		Material.NETHERITE_HELMET,
		Material.NETHERITE_CHESTPLATE,
		Material.NETHERITE_LEGGINGS,
		Material.NETHERITE_BOOTS,
		Material.NETHERITE_SWORD,
		Material.NETHERITE_AXE,
		Material.NETHERITE_PICKAXE,
		Material.NETHERITE_HOE,
		Material.NETHERITE_SHOVEL
	),

	DIAMOND(
		NETHERITE,
		Material.DIAMOND_HELMET,
		Material.DIAMOND_CHESTPLATE,
		Material.DIAMOND_LEGGINGS,
		Material.DIAMOND_BOOTS,
		Material.DIAMOND_SWORD,
		Material.DIAMOND_AXE,
		Material.DIAMOND_PICKAXE,
		Material.DIAMOND_HOE,
		Material.DIAMOND_SHOVEL
	),

	IRON(
		DIAMOND,
		Material.IRON_HELMET,
		Material.IRON_CHESTPLATE,
		Material.IRON_LEGGINGS,
		Material.IRON_BOOTS,
		Material.IRON_SWORD,
		Material.IRON_AXE,
		Material.IRON_PICKAXE,
		Material.IRON_HOE,
		Material.IRON_SHOVEL
	),

	GOLD(
		IRON,
		Material.GOLDEN_HELMET,
		Material.GOLDEN_CHESTPLATE,
		Material.GOLDEN_LEGGINGS,
		Material.GOLDEN_BOOTS,
		Material.GOLDEN_SWORD,
		Material.GOLDEN_AXE,
		Material.GOLDEN_PICKAXE,
		Material.GOLDEN_HOE,
		Material.GOLDEN_SHOVEL
	),

	STONE(
		GOLD,
		Material.CHAINMAIL_HELMET,
		Material.CHAINMAIL_CHESTPLATE,
		Material.CHAINMAIL_LEGGINGS,
		Material.CHAINMAIL_BOOTS,
		Material.STONE_SWORD,
		Material.STONE_AXE,
		Material.STONE_PICKAXE,
		Material.STONE_HOE,
		Material.STONE_SHOVEL
	),

	@Suppress("SpellCheckingInspection")
	CHAINMAIL(STONE),

	WOOD(
		STONE,
		Material.LEATHER_HELMET,
		Material.LEATHER_CHESTPLATE,
		Material.LEATHER_LEGGINGS,
		Material.LEATHER_BOOTS,
		Material.WOODEN_SWORD,
		Material.WOODEN_AXE,
		Material.WOODEN_PICKAXE,
		Material.WOODEN_HOE,
		Material.WOODEN_SHOVEL
	),

	LEATHER(WOOD),
	;

	var same: TierItem? = null
		private set
	val next: TierItem?

	val helmet: Material
	@Suppress("SpellCheckingInspection")
	val chestplate: Material
	val leggings: Material
	val boots: Material
	val sword: Material
	val axe: Material
	val pickaxe: Material
	val hoe: Material
	val shovel: Material
	val all: List<Material>
	val armors: List<Material>
	val weapons: List<Material>
	val tools: List<Material>
	@Suppress("SpellCheckingInspection")
	val usables: List<Material>

	constructor(
		next: TierItem?,
		helmet: Material,
		@Suppress("SpellCheckingInspection")
		chestplate: Material,
		leggings: Material,
		boots: Material,
		sword: Material,
		axe: Material,
		pickaxe: Material,
		hoe: Material,
		shovel: Material
	) {
		this.next = next
		this.helmet = helmet
		this.chestplate = chestplate
		this.leggings = leggings
		this.boots = boots
		this.sword = sword
		this.axe = axe
		this.pickaxe = pickaxe
		this.hoe = hoe
		this.shovel = shovel

		armors = listOfNotNull(
			helmet,
			chestplate,
			leggings,
			boots,
		)

		weapons = listOfNotNull(
			sword,
			axe,
		)

		tools = listOfNotNull(
			axe,
			pickaxe,
			hoe,
			shovel,
		)

		usables = listOfNotNull(
			sword,
			axe,
			pickaxe,
			hoe,
			shovel,
		)

		all = listOf(armors, usables).flatten()
	}

	constructor(same: TierItem) {
		next = same.next
		this.same = same
		same.same = this
		helmet = same.helmet
		chestplate = same.chestplate
		leggings = same.leggings
		boots = same.boots
		sword = same.sword
		axe = same.axe
		pickaxe = same.pickaxe
		hoe = same.hoe
		shovel = same.shovel
		armors = same.armors
		weapons = same.weapons
		tools = same.tools
		usables = same.usables
		all = same.all
	}

	operator fun contains(type: Material): Boolean = all.contains(type)

	fun isSame(tier: TierItem): Boolean = tier == this
	fun isNext(tier: TierItem): Boolean = tier == next

	fun random(): Material = all.random()
	fun randomArmor(): Material = armors.random()
	fun randomWeapon(): Material = weapons.random()
	fun randomTool(): Material = tools.random()
	fun randomUsable(): Material = usables.random()

}
