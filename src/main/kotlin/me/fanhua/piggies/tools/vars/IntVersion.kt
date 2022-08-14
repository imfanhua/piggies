package me.fanhua.piggies.tools.vars

interface WithVersion {
	val version: Int
}

data class IntVersion(override var version: Int = 0): WithVersion
