package chess.api

class Move(data: MoveByPointer) {
    var from: BitBoard = BitBoard(data.from)
    var to: BitBoard = BitBoard(data.to)
    var promotion: PieceType? = PieceType.fromCIntNullable(data.promotion.toInt())
    var capture: Boolean = data.capture == 1.toByte()
    var castle: Boolean = data.castle == 1.toByte()

    fun toMoveByPointer(): MoveByPointer.Companion.ByValue {
        val native = MoveByPointer.Companion.ByValue()
        native.from = this.from.value
        native.to = this.to.value
        native.promotion = this.promotion?.toCInt()?.toByte() ?: 0
        native.capture = if (this.capture) 1 else 0
        native.castle = if (this.castle) 1 else 0
        return native
    }

    override fun toString() = "Move(from=${from.value}, to=${to.value}, promotion=$promotion, capture=$capture, castle=$castle)"
}