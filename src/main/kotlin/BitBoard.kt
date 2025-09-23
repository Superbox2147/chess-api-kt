package chess.api

import com.sun.jna.IntegerType

class BitBoard @JvmOverloads constructor(val value: Long = 0) : IntegerType(8, value, true) {
    override fun toByte(): Byte = value.toByte()
    override fun toShort(): Short = value.toShort()
}