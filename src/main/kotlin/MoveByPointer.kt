package chess.api

import com.sun.jna.Pointer
import com.sun.jna.Structure

@Structure.FieldOrder("from", "to", "promotion", "capture", "castle")
open class MoveByPointer() : Structure() {
    @JvmField
    var from: Long = 0
    @JvmField
    var to: Long = 0
    @JvmField
    var promotion: Byte = 0
    @JvmField
    var capture: Byte = 0
    @JvmField
    var castle: Byte = 0

    fun toMove(): Move = Move(this)

    override fun toString() = "Move(from=${from}, to=${to}, promotion=${promotion}, capture=$capture, castle=$castle)"

    constructor(p: Pointer) : this() {
        useMemory(p)
        read()
    }

    companion object {
        class ByValue : MoveByPointer(), Structure.ByValue
    }
}