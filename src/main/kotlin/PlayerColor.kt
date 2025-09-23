package chess.api

enum class PlayerColor {
    WHITE,
    BLACK;

    fun toCInt(): Int = when(this) {
        WHITE -> 0
        BLACK -> 1
    }

    companion object {
        fun fromCInt(int: Int): PlayerColor = if (int == 0) WHITE else BLACK
    }
}