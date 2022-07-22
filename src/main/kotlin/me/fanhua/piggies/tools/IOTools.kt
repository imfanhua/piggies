package me.fanhua.piggies.tools

import java.io.File
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.StandardCopyOption

fun File.copyFileTo(outFile: File): Boolean = copyFileTo(outFile, StandardCopyOption.REPLACE_EXISTING)
fun File.copyFileTo(outFile: File, vararg options: CopyOption): Boolean
	= exists() && ok { Files.copy(toPath(), outFile.toPath(), *options) }

fun File.copyDirectoryTo(outDir: File): Boolean = copyDirectoryTo(outDir, StandardCopyOption.REPLACE_EXISTING)
fun File.copyDirectoryTo(outDir: File, vararg options: CopyOption): Boolean {
	if (!exists()) return false
	if (!outDir.exists())
		try {
			Files.copy(toPath(), outDir.toPath(), *options)
		} catch (_: Throwable) {
			return false
		}
	val list = list() ?: return false
	for (name in list) if (!File(this, name).copyTo(File(outDir, name), *options)) return false
	return true
}

fun File.copyTo(outFile: File): Boolean = copyTo(outFile, StandardCopyOption.REPLACE_EXISTING)
fun File.copyTo(outFile: File, vararg options: CopyOption): Boolean
	= if (isDirectory) copyDirectoryTo(outFile, *options) else copyFileTo(outFile, *options)