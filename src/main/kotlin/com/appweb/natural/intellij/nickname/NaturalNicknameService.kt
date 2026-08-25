package com.appweb.natural.intellij.nickname

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.vfs.VirtualFile
import java.util.concurrent.ConcurrentHashMap

object NaturalNicknameService {
    const val NICKNAME_FILE = ".natural-nicknames.json"
    private val gson = GsonBuilder().setPrettyPrinting().create()

    // Cache: directory path -> (VFile modification stamp, nicknames map)
    private val cache = ConcurrentHashMap<String, Pair<Long, Map<String, String>>>()

    fun getNickname(file: VirtualFile): String? {
        return loadNicknames(file.parent ?: return null)[file.name.uppercase()]
    }

    fun setNickname(file: VirtualFile, nickname: String?) {
        val dir = file.parent ?: return
        val nicknames = loadNicknames(dir).toMutableMap()
        val key = file.name.uppercase()
        if (nickname.isNullOrBlank()) {
            nicknames.remove(key)
        } else {
            nicknames[key] = nickname
        }
        saveNicknames(dir, nicknames)
        cache.remove(dir.path)
    }

    private fun loadNicknames(dir: VirtualFile): Map<String, String> {
        return ReadAction.compute<Map<String, String>, Exception> {
            val nicknameVFile = dir.findChild(NICKNAME_FILE) ?: return@compute emptyMap()

            val stamp = nicknameVFile.modificationStamp
            val cached = cache[dir.path]
            if (cached != null && cached.first == stamp) {
                return@compute cached.second
            }

            try {
                val content = String(nicknameVFile.contentsToByteArray(), Charsets.UTF_8)
                val type = object : TypeToken<Map<String, String>>() {}.type
                val nicknames: Map<String, String> = gson.fromJson(content, type) ?: emptyMap()
                cache[dir.path] = Pair(stamp, nicknames)
                nicknames
            } catch (_: Exception) {
                emptyMap()
            }
        }
    }

    private fun saveNicknames(dir: VirtualFile, nicknames: Map<String, String>) {
        val json = gson.toJson(nicknames.toSortedMap())
        // Use VFS write so the modification stamp updates synchronously,
        // ensuring the cache invalidation in setNickname sees fresh content immediately.
        ApplicationManager.getApplication().runWriteAction {
            val nicknameFile = dir.findChild(NICKNAME_FILE)
                ?: dir.createChildData(NaturalNicknameService, NICKNAME_FILE)
            nicknameFile.setBinaryContent(json.toByteArray(Charsets.UTF_8))
        }
    }
}
