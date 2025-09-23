, # Chess API (Kotlin)

## `class Board`
A pointer to the internal `Board` state object to pass into functions.\
The `Board` is automatically freed from memory when collected but can be deleted manually.

| Field            | Description                                          |
|------------------|------------------------------------------------------|
| `clone(): Board` | Create a copy of this `Board`.                       |
| `close()`        | Explicitly delete this `Board` and free its pointer. |

## `object ChessApi`
The main API can be found here, has bindings for all functions of the C API.

| Field                                                                           | Description                                                                                                                                                                                                                                             |
|---------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `getBoard(): Board`                                                             | Returns the current `Board`.                                                                                                                                                                                                                            |
| `freeBoard(boardPtr: Pointer)`                                                  | Direct mapping to the C API's `void chess_free_board(Board *board)`.<br/>Explicitly deletes the provided board from memory.                                                                                                                             |
| `cloneBoard(board: Board): Board`                                               | Create a copy of the given `Board`.                                                                                                                                                                                                                     |
| `chessPush(move: Move)`                                                         | Push a move to be played when running `chessDone()`.                                                                                                                                                                                                    |
| `chessDone()`                                                                   | End the turn and play the latest move pushed with `chessPush(move: Move)`                                                                                                                                                                               |
| `getOpponentMove(): Move`                                                       | Returns the opponent's latest move, a `Move` with all 0 fields will be returned if none have been made yet                                                                                                                                              |
| `isWhiteTurn(board: Board): Boolean`                                            | Returns `true` if it is currently white's turn and `false` on black's turn.                                                                                                                                                                             |
| `skipTurn(board: Board)`                                                        | Skips the current player's turn. You can't skip moves in the actual game, but this can be helpful for some search strategies.                                                                                                                           |
| `inCheck(board: Board): Boolean`                                                | Returns `true` if the board position places the player that will play in check.                                                                                                                                                                         |
| `inCheckmate(board: Board): Boolean`                                            | Returns `true` if the board position places the player that will play in checkmate.                                                                                                                                                                     |
| `inDraw(board: Board): Boolean`                                                 | Returns `true` if the board state is a draw, the 50 move limit has been reached or threefold repetition has occurred.                                                                                                                                   |
| `canKingsideCastle(board: Board, playerColor: PlayerColor): Boolean`            | Returns `true` if the player with the given color can castle kingside.                                                                                                                                                                                  |
| `canQueensideCastle(board: Board, playerColor: PlayerColor): Boolean`           | Return `true if the player with the given color can queenside castle.                                                                                                                                                                                   |
| `getGameState(board: Board): GameState`                                         | Returns the `GameState` enum corresponding to the state of the game on the given board.                                                                                                                                                                 |
| `zobristKey(board: Board): Long`                                                | Returns a hash of the given board.                                                                                                                                                                                                                      |
| `makeMove(board: Board, move: Move)`                                            | Makes a move and updates the state of the given `Board`.<br/>Note that this does not push moves to the Chess bot output, for that use `chessPush(move: Move)`                                                                                           |
| `undoMove(board: Board)`                                                        | Undoes a move on the given `Board`. Calling on a `Board` where a move has not been made is an error.                                                                                                                                                    |
| `getBitboard(board: Board, color: PlayerColor, pieceType: PieceType): BitBoard` | Returns the `BitBoard` corresponding to the given `PieceType` of the given player color on the given `Board`.                                                                                                                                           |
| `getFullMoves(board: Board): Int`                                               | Returns the number of full moves elapsed on the board. This value begins at 1, and increments each time black makes a move.                                                                                                                             |
| `getHalfMoves(board: Board): Int`                                               | Returns the number of half moves elapsed on the board. This value begins at 0, increments each time either player makes a move, and resets to 0 upon pawn moves or captures.                                                                            |
| `getTimeMillis(): Long`                                                         | Returns the remaining time for this player in milliseconds.                                                                                                                                                                                             |
| `getOpponentTimeMillis(): Long`                                                 | Returns the remaining time for the opponent in milliseconds.                                                                                                                                                                                            |
| `getElapsedTimeMillis(): Long`                                                  | Returns the time that has passed since the start of the turn, in milliseconds.                                                                                                                                                                          |
| `getPieceFromIndex(board: Board, index: Int): PieceType`                        | Returns the type of the piece in the given index. Indeces correspond to board tiles beginning at a1, increasing first at the letter and secondarily at the number, each wraparound of the letter increases the number. Indeces should be between 0-63.  |
| `getPieceFromBitboard(board: Board, bitBoard: BitBoard): PieceType`             | Returns the type of piece in the set bit of the given `BitBoard`, the `BitBoard` is expected to have only one bit set.                                                                                                                                  |
| `getColorFromIndex(board: Board, index: Int): PlayerColor`                      | Returns the color of the piece in the given index. Indeces correspond to board tiles beginning at a1, increasing first at the letter and secondarily at the number, each wraparound of the letter increases the number. Indeces should be between 0-63. |
| `getColorFromBitboard(board: Board, bitBoard: BitBoard): PlayerColor`           | Returns the color of the pieve in the set bit of the given `BitBoard`, the `BitBoard` is expected to have only one bit set.                                                                                                                             |
| `getIndexFromBitboard(bitBoard: BitBoard): Int`                                 | Converts a `BitBoard` with a single bit set into an `Int` index from 0-63, corresponding to the same square.                                                                                                                                            |
| `getBitboardFromIndex(index: Int): BitBoard`                                    | Converts an `Int` index into a `BitBoard` with a single bit set at the square defined by the index.                                                                                                                                                     |
| `getLegalMoves(board: Board): Array<Move>`                                      | Returns an array containing all legal moves on the given `Board`.                                                                                                                                                                                       |

## `class Move`
An object representing a Chess move.

| Field                   | Description                                                                                             |
|-------------------------|---------------------------------------------------------------------------------------------------------|
| `from: BitBoard`        | A `BitBoard` with the bit corresponding to the starting square of the piece set.                        |
| `to: BitBoard`          | A `BitBoard` with the bit corresponding to the ending square of the piece set.                          |
| `promotion: PieceType?` | If this move results in a promotion, will be the type of the piece being promoted to, otherwise `null`. |
| `capture: Boolean`      | `true` if the move captures a piece.                                                                    |
| `castle: Boolean`       | `true` if the move results in castling.                                                                 |

## `class BitBoard`
A `Long` where every bit represents a square of the board.

| Field         | Description                         |
|---------------|-------------------------------------|
| `value: Long` | The value given to this `BitBoard`. |

## `enum class PieceType`
An enum representing the type of a piece.

| Value    | Description     |
|----------|-----------------|
| `PAWN`   | A pawn piece.   |
| `BISHOP` | A bishop piece. |
| `KNIGHT` | A knight piece. |
| `ROOK`   | A rook piece.   |
| `QUEEN`  | A queen piece.  |
| `KING`   | A king piece.   |

## `enum class GameState`
An enum representing the state of the game.

| Value       | Description                       |
|-------------|-----------------------------------|
| `CHECKMATE` | The game has reached checkmate.   |
| `NORMAL`    | The game is in a normal state.    |
| `STALEMATE` | The game has reached a stalemate. |

## `enum class PlayerColor`
An enum representing the color of a player.

| Value   | Description   |
|---------|---------------|
| `WHITE` | White player. |
| `BLACK` | Black player. |