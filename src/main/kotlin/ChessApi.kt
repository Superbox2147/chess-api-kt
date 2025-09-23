package chess.api

import com.sun.jna.Pointer
import com.sun.jna.ptr.IntByReference
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.file.Paths


/**
 * Bindings for the C Chess API
 */
object ChessApi {
    init {
        val libraryName = "libchessapi.so"
        val jniFolder = Paths.get(System.getProperty("java.io.tmpdir"), "chessapi").toFile()
        jniFolder.mkdirs()
        // Extract the library from the JAR
        val `in`: InputStream = ChessApi::class.java.getResourceAsStream("/$libraryName")!!
        val tempFile = File(jniFolder,libraryName)
        tempFile.deleteOnExit()

        FileOutputStream(tempFile).use { out ->
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while ((`in`.read(buffer).also { bytesRead = it }) != -1) {
                out.write(buffer, 0, bytesRead)
            }
        }
        System.setProperty("jna.library.path", jniFolder.absolutePath.toString())
    }

    private val nativeBindings = NativeBindings.INSTANCE

    fun getBoard(): Board = Board(nativeBindings.chess_get_board())
    fun freeBoard(boardPtr: Pointer) = nativeBindings.chess_free_board(boardPtr)
    fun cloneBoard(board: Board): Board = Board(nativeBindings.chess_clone_board(board.value))
    fun chessPush(move: Move) = nativeBindings.chess_push(move.toMoveByPointer())
    fun chessDone() = nativeBindings.chess_done()
    fun getOpponentMove(): Move = nativeBindings.chess_get_opponent_move().toMove()
    fun isWhiteTurn(board: Board): Boolean = nativeBindings.chess_is_white_turn(board.value)
    fun skipTurn(board: Board) = nativeBindings.chess_skip_turn(board.value)
    fun inCheck(board: Board): Boolean = nativeBindings.chess_in_check(board.value)
    fun inCheckmate(board: Board): Boolean = nativeBindings.chess_in_checkmate(board.value)
    fun inDraw(board: Board): Boolean = nativeBindings.chess_in_draw(board.value)
    fun canKingsideCastle(board: Board, playerColor: PlayerColor): Boolean = nativeBindings.chess_can_kingside_castle(board.value, playerColor.toCInt())
    fun canQueensideCastle(board: Board, playerColor: PlayerColor): Boolean = nativeBindings.chess_can_queenside_castle(board.value, playerColor.toCInt())
    fun getGameState(board: Board): GameState = GameState.fromCInt(nativeBindings.chess_is_game_ended(board.value))
    fun zobristKey(board: Board): Long = nativeBindings.chess_zobrist_key(board.value)
    fun makeMove(board: Board, move: Move) = nativeBindings.chess_make_move(board.value, move.toMoveByPointer())
    fun undoMove(board: Board) = nativeBindings.chess_undo_move(board.value)
    fun getBitboard(board: Board, color: PlayerColor, pieceType: PieceType): BitBoard = BitBoard(nativeBindings.chess_get_bitboard(board.value, color.toCInt(), pieceType.toCInt()))
    fun getFullMoves(board: Board): Int = nativeBindings.chess_get_full_moves(board.value)
    fun getHalfMoves(board: Board): Int = nativeBindings.chess_get_half_moves(board.value)
    fun getTimeMillis(): Long = nativeBindings.chess_get_time_millis()
    fun getOpponentTimeMillis(): Long = nativeBindings.chess_get_opponent_time_millis()
    fun getElapsedTimeMillis(): Long = nativeBindings.chess_get_elapsed_time_millis()
    fun getPieceFromIndex(board: Board, index: Int): PieceType = PieceType.fromCInt(nativeBindings.chess_get_piece_from_index(board.value, index))
    fun getPieceFromBitboard(board: Board, bitBoard: BitBoard): PieceType = PieceType.fromCInt(nativeBindings.chess_get_piece_from_bitboard(board.value, bitBoard.value))
    fun getColorFromIndex(board: Board, index: Int): PlayerColor = PlayerColor.fromCInt(nativeBindings.chess_get_color_from_index(board.value, index))
    fun getColorFromBitboard(board: Board, bitBoard: BitBoard): PlayerColor = PlayerColor.fromCInt(nativeBindings.chess_get_color_from_bitboard(board.value, bitBoard.value))
    fun getIndexFromBitboard(bitBoard: BitBoard): Int = nativeBindings.chess_get_index_from_bitboard(bitBoard.value)
    fun getBitboardFromIndex(index: Int): BitBoard = BitBoard(nativeBindings.chess_get_bitboard_from_index(index))

    fun getLegalMoves(board: Board): Array<Move> {
        val len = IntByReference()
        val movesPtr = nativeBindings.chess_get_legal_moves(board.value, len)

        val moves = Array(len.value) { i ->
            val offset = i * MoveByPointer().size()
            MoveByPointer(movesPtr.share(offset.toLong())).toMove()
        }

        nativeBindings.chess_free_moves_array(movesPtr)
        return moves
    }
}