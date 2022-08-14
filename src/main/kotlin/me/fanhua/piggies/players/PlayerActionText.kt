package me.fanhua.piggies.players

import me.fanhua.piggies.Piggies
import me.fanhua.piggies.parts.Parts
import me.fanhua.piggies.tools.plugins.logger
import me.fanhua.piggies.tools.plugins.task
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player

private data class ActionText(
	var text: BaseComponent? = null,
	var temp: BaseComponent? = null,
	var ticks: Int = 0,
)
private val ActionTextPart = Parts.temp(::ActionText)

object PlayerActionText {

	private val EMPTY = TextComponent(" ")

	val defaults = arrayListOf<(Player) -> BaseComponent?>()

	init {
		Piggies.task(::tick)

		Piggies.logger.info("+ #[PlayerActionText]")
	}

	fun default(block: (Player) -> BaseComponent?) = apply { defaults.add(block) }

	fun default(block: () -> BaseComponent?) = apply { defaults.add { block() } }

	private fun tick() = Bukkit.getOnlinePlayers().forEach(::displayFor)

	fun text(player: Player, text: TextComponent)
		= ActionTextPart[player].let {
			it.text = text
			it.temp = null
		}

	fun text(player: Player, text: String) = text(player, TextComponent(text))

	fun show(player: Player, ticks: Int = 60, text: TextComponent)
		= ActionTextPart[player].let {
			it.temp = text
			it.ticks = ticks
		}

	fun show(player: Player, text: TextComponent) = show(player, 60, text)

	fun show(player: Player, text: String) = show(player, 60, TextComponent(text))

	fun show(player: Player, ticks: Int = 60, text: String) = show(player, ticks, TextComponent(text))

	fun clear(player: Player) {
		val text = ActionTextPart[player]
		text.text = null
		text.temp = null
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, EMPTY)
	}

	private fun displayFor(player: Player) {
		val text = ActionTextPart[player]
		(text.temp?.apply {
			if (--text.ticks <= 0) text.temp = null
		} ?: text.text ?: defaults.firstNotNullOfOrNull { it(player) })?.let {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, it)
		}
	}

}
