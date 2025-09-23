package chess.api

import com.sun.jna.Pointer
import java.lang.ref.Cleaner


/**
 * A pointer to the internal Board state object to pass into functions.
 * Automatically freed on garbage collection.
 */
data class Board(val value: Pointer) {
    companion object {
        private val cleaner = Cleaner.create()
    }

    private val cleanable = cleaner.register(this, Cleanup(value))

    private class Cleanup(private val ptr: Pointer) : Runnable {
        override fun run() {
            ChessApi.freeBoard(ptr)
        }
    }

    /**
     * Creates a clone of this [chess.api.Board].
     */
    fun clone(): Board = ChessApi.cloneBoard(this)

    /**
     * Deletes this board object and frees it from memory.
     */
    fun close() {
        cleanable.clean()
    }
}