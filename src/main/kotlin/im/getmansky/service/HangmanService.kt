package im.getmansky.service

import im.getmansky.model.GameStatus
import im.getmansky.model.HangmanPicture
import org.springframework.stereotype.Service
import java.util.*

@Service
class HangmanService(
    private val hangmanInfoService: HangmanInfoService,
    private val hangmanDictionaryService: HangmanDictionaryService,
) {
    private val scanner: Scanner = Scanner(System.`in`)

    private val maxErrors = HangmanPicture.PICTURES.size - 1

    fun run() {
        while (true) {
            if (!continueGameQuestion()) {
                return
            }
            println("*** Я загадываю слово, попробуй его угадать")
            var gameStatus = GameStatus(
                hangmanDictionaryService.getNewWord(),
                badLetters = mutableListOf(),
                goodLetters = mutableListOf()
            )

            while (true) {
                if (isGameOver(gameStatus)) {
                    if (isPlayerFail(gameStatus)) {
                        println("*** Вы проиграли. Задуманное слово \"${gameStatus.word}\"")
                    } else {
                        println("*** Поздравления! Вы выиграли")
                    }
                    break
                }

                val letter = askNextLetter(gameStatus)
                gameStatus =
                    if (isCorrectLetter(letter, gameStatus)) {
                        gameStatus.copy(goodLetters = gameStatus.goodLetters + letter)
                    } else {
                        gameStatus.copy(badLetters = gameStatus.badLetters + letter)
                    }
                println(hangmanInfoService.showResult(gameStatus))
            }
        }
    }

    private fun continueGameQuestion(): Boolean {
        print("Хотите продолжить игру (Y/N или Д/Н): ")
        val answer = scanner.next().lowercase()
        return answer == "y" || answer == "д"
    }

    private fun askNextLetter(gameStatus: GameStatus): Char {
        while (true) {
            print("Ваша буква: ")
            val answer = scanner.next()
            if (!LETTERS_REGEX.matches(answer)) {
                println("Ошибка! Вы ввели неверную букву. Допускаются только буквы русского алфавита")
                continue
            }
            val letter = answer.lowercase().toCharArray().first()
            if (gameStatus.badLetters.contains(letter) || gameStatus.goodLetters.contains(letter)) {
                println("Ошибка! Вы уже использовали эту букву")
                continue
            }
            return letter
        }
    }

    private fun isCorrectLetter(letter: Char, gameStatus: GameStatus): Boolean =
        gameStatus.word.contains(letter)

    private fun isGameOver(gameStatus: GameStatus): Boolean =
        gameStatus.badLetters.size >= maxErrors || gameStatus.goodLetters.size == gameStatus.word.length

    private fun isPlayerFail(gameStatus: GameStatus): Boolean =
        gameStatus.badLetters.size >= maxErrors

    private companion object {
        val LETTERS_REGEX = Regex("[А-ЯЁа-яё]")
    }
}