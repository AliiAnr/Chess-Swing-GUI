package pieces;
import board.ChessBoard;
import logic.Move;

public class King extends Pieces {
   public King(ChessBoard board, int col, int row, boolean isWhite) {
      super(board, isWhite ? "image\\white_king.png" : "image\\black_king.png");
      this.col = col;
      this.row = row;
      this.isWhite = isWhite;
      this.name = "King";
      this.value = 1000;
      this.xPos = col * board.titleSize;
      this.yPos = row * board.titleSize;
   }

   public boolean isValidMovement(int col, int row) {
      return Math.abs((col - this.col) * (row - this.row)) == 1
            || Math.abs(col - this.col) + Math.abs(row - this.row) == 1 || castleAble(col, row);
   }

   public boolean moveCollidesWithPiece(int col, int row) {
      return false;
   }

   private boolean castleAble(int col, int row) {
      if (board.checkScanner.isKingChecked(new Move(board, this, this.col, this.row))) {
          return false;
      }
  
      if (this.row == row) {
          if (col == 6) {
              Pieces rook = board.getPiece(7, row);
              if (rook != null && rook.isFirstMove && isFirstMove) {
                  return board.getPiece(5, row) == null &&
                      board.getPiece(6, row) == null;
              }
          } else if (col == 2) {
              Pieces rook = board.getPiece(0, row);
              if (rook != null && rook.isFirstMove && isFirstMove) {
                  return board.getPiece(3, row) == null &&
                      board.getPiece(2, row) == null &&
                      board.getPiece(1, row) == null;
              }
          }
      }
      return false;
  }

}
