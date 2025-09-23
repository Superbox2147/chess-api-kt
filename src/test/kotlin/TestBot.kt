import chess.api.ChessApi

fun main() {
    for (i in 0..<500) {
        val board = ChessApi.getBoard()
        val moves = ChessApi.getLegalMoves(board)
        ChessApi.chessPush(moves.random())
        ChessApi.chessDone()
    }
}