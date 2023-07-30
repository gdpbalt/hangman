package im.getmansky.model

data class GameStatus(
    val word: String,
    val badLetters: List<Char>,
    val goodLetters: List<Char>,
)