package me.fanhua.piggies.lang

import kotlinx.serialization.json.JsonElement
import me.fanhua.piggies.Piggies
import me.fanhua.piggies.tools.data.asJson
import me.fanhua.piggies.tools.data.asObject
import me.fanhua.piggies.tools.data.asString
import me.fanhua.piggies.tools.plugins.logger
import org.bukkit.Material
import org.bukkit.advancement.Advancement
import org.bukkit.attribute.Attribute
import org.bukkit.block.Biome
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.potion.PotionEffectType

object Languages {

	val Advancements: LanguageSet<Advancement>
	val Items: LanguageSet<Material>
	val Entities: LanguageSet<EntityType>
	val Enchantments: LanguageSet<Enchantment>
	val PotionEffects: LanguageSet<PotionEffectType>
	val Biomes: LanguageSet<Biome>
	val Attributes: LanguageSet<Attribute>

	init {
		val sets = LanguageSets()
		Advancements = sets.set { path.with("advancements.", ".title") }
		Items = sets.set { with("item.minecraft.").with("block.minecraft.") }
		Entities = sets.set { with("entity.minecraft.") }
		Enchantments = sets.set { with("enchantment.minecraft.") }
		PotionEffects = sets.set { with("effect.minecraft.") }
		Biomes = sets.set { with("biome.minecraft.") }
		Attributes = sets.set { with("attribute.name.") }

		val lang = Piggies.getResource("lang/lang.json").asJson().asObject!!.mapValuesTo(mutableMapOf()) { it.value.asString!! }
		Piggies.getResource("lang/lang-override.json").asJson().set(lang) { it }
		Piggies.getResource("lang/lang-redirect.json").asJson().set(lang) { lang[it]!! }
		sets.addAll(lang)

		Piggies.logger.info("+ #[Languages]")
	}

	private fun JsonElement?.set(map: MutableMap<String, String>, transform: (String) -> String) {
		asObject!!.forEach { (key, v) ->
			val value = v.asString!!
			if (key.startsWith("::")) {
				if (value.startsWith("::")) {
					val replace = key.substring(2)
					val regex = Regex(value.substring(2))
					map.keys.filter(regex::matches).forEach { map[regex.replace(it, replace)] = map[it]!! }
				} else {
					map.keys.filter(Regex(key.substring(2))::matches).forEach { map[it] = transform(value) }
				}
			} else map[key] = transform(value)
		}
	}

}
