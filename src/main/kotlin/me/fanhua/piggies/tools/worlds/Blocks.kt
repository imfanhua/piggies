package me.fanhua.piggies.tools.worlds

import me.fanhua.piggies.tools.math.Pos3I
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import java.util.stream.Stream


object Blocks {
}

fun Block.breakBy() = breakBy(ItemStack(Material.NETHERITE_PICKAXE))
fun Block.breakBy(item: ItemStack) = let {
	playBreakEffect()
	breakNaturally(item)
}

fun Block.breakIfNotEmpty(): Boolean = if (!isEmpty) breakBy() else false
fun Block.breakIfNotEmpty(item: ItemStack): Boolean = if (!isEmpty) breakBy(item) else false

fun Block.playBreakEffect() = world.playEffect(location, Effect.STEP_SOUND, type)
fun Location.playBreakEffect(type: Material) = world?.playEffect(this, Effect.STEP_SOUND, type)

fun Location.blocks(distance: Float): Stream<Block> = world!!.blocks(Pos3I(this).area(distance))
fun World.blocks(pos: Stream<Pos3I>): Stream<Block> = pos.map { it[this] }.filter { !it.isEmpty }