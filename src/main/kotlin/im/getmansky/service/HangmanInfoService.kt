package im.getmansky.service

import im.getmansky.model.GameStatus
import im.getmansky.model.HangmanPicture
import org.springframework.stereotype.Service

@Service
class HangmanInfoService {

    private val maxErrors = HangmanPicture.PICTURES.size - 1

    fun showResult(gameStatus: GameStatus): String =
        listOf(
            getPicture(gameStatus),
            getTextInfo(gameStatus)
        ).joinToString("\n")

    private fun getTextInfo(gameStatus: GameStatus): String {
        val wordBuffer = StringBuffer()
        gameStatus.word.forEach { letter ->
            if (gameStatus.goodLetters.contains(letter)) {
                wordBuffer.append(letter.uppercase())
            } else {
                wordBuffer.append("_")
            }
        }
        return listOf(
            "Word: $wordBuffer",
            "Errors (${gameStatus.badLetters.size}): ${gameStatus.badLetters.joinToString(", ")}",
        ).joinToString("\n")
    }

    private fun getPicture(gameStatus: GameStatus): String =
        if (gameStatus.badLetters.size >= maxErrors) {
            HangmanPicture.PICTURES.last()
        } else {
            val errors = gameStatus.badLetters.size
            HangmanPicture.PICTURES[errors]
        }
}