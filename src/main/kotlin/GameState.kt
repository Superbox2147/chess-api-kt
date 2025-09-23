package chess.api

enum class GameState {
    CHECKMATE,
    NORMAL,
    STALEMATE;

    fun toCInt(): Int = when(this) {
        CHECKMATE -> -1
        NORMAL -> 0
        STALEMATE -> 1
    }
    companion object {
        @Throws(IllegalArgumentException::class)
        fun fromCInt(int: Int): GameState = when(int) {
            -1 -> CHECKMATE
            0 -> NORMAL
            1 -> STALEMATE
            else -> throw IllegalArgumentException("$int is out of bounds for range [-1, 1]")
        }
    }
}