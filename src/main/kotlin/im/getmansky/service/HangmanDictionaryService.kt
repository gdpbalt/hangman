package im.getmansky.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class HangmanDictionaryService(
    private val resourceLoader: ResourceLoader,

    @Value("\${game.russian.wordlist}")
    private val filename: String
) {

    fun getNewWord(): String =
        resourceLoader.getResource(filename).inputStream.bufferedReader().useLines { lines ->
            return lines.toList().random()
        }
}