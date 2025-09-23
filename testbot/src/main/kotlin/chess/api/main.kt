package chess.api

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

fun main(args: Array<String>) {
    for (i in 0..<500) {
        val board = ChessApi.getBoard()
        val moves = ChessApi.getLegalMoves(board)
        ChessApi.chessPush(moves.random())
        ChessApi.chessDone()
    }
}
