package chess.api

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.ptr.IntByReference

interface NativeBindings : Library {
    fun chess_get_board(): Pointer
    fun chess_free_board(board: Pointer)
    fun chess_clone_board(board: Pointer): Pointer
    fun chess_get_legal_moves(board: Pointer, len: IntByReference): Pointer
    fun chess_free_moves_array(moves: Pointer)
    fun chess_push(move: MoveByPointer.Companion.ByValue)
    fun chess_done()
    fun chess_get_opponent_move(): MoveByPointer.Companion.ByValue
    fun chess_is_white_turn(board: Pointer): Boolean
    fun chess_skip_turn(board: Pointer)
    fun chess_in_check(board: Pointer): Boolean
    fun chess_in_checkmate(board: Pointer): Boolean
    fun chess_in_draw(board: Pointer): Boolean
    fun chess_can_kingside_castle(board: Pointer, color: Int): Boolean
    fun chess_can_queenside_castle(board: Pointer, color: Int): Boolean
    fun chess_is_game_ended(board: Pointer): Int
    fun chess_zobrist_key(board: Pointer): Long
    fun chess_make_move(board: Pointer, move: MoveByPointer.Companion.ByValue)
    fun chess_undo_move(board: Pointer)
    fun chess_get_bitboard(board: Pointer, color: Int, piece_type: Int): Long
    fun chess_get_full_moves(board: Pointer): Int
    fun chess_get_half_moves(board: Pointer): Int
    fun chess_get_time_millis(): Long
    fun chess_get_opponent_time_millis(): Long
    fun chess_get_elapsed_time_millis(): Long
    fun chess_get_piece_from_index(board: Pointer, index: Int): Int
    fun chess_get_piece_from_bitboard(board: Pointer, bitboard: Long): Int
    fun chess_get_color_from_index(board: Pointer, index: Int): Int
    fun chess_get_color_from_bitboard(board: Pointer, bitboard: Long): Int
    fun chess_get_index_from_bitboard(bitboard: Long): Int
    fun chess_get_bitboard_from_index(index: Int): Long

    companion object {
        val INSTANCE: NativeBindings = Native.load("chessapi", NativeBindings::class.java)
    }
}