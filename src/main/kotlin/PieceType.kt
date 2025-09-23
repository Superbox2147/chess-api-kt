package chess.api

import kotlin.jvm.Throws

enum class PieceType {
    PAWN,
    BISHOP,
    KNIGHT,
    ROOK,
    QUEEN,
    KING;

    fun toCInt(): Int = when(this) {
        PAWN -> 1
        BISHOP -> 2
        KNIGHT -> 3
        ROOK -> 4
        QUEEN -> 5
        KING -> 6
    }

    companion object {
        @Throws(IllegalArgumentException::class)
        fun fromCInt(int: Int): PieceType = when(int) {
            1 -> PAWN
            2 -> BISHOP
            3 -> KNIGHT
            4 -> ROOK
            5 -> QUEEN
            6 -> KING
            else -> throw IllegalArgumentException("$int is out of bounds for range [1, 6]")
        }

        fun fromCIntNullable(int: Int): PieceType? = when(int) {
            1 -> PAWN
            2 -> BISHOP
            3 -> KNIGHT
            4 -> ROOK
            5 -> QUEEN
            6 -> KING
            else -> null
        }
    }
}