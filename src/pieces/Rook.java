package pieces;
import board.ChessBoard;

public class Rook extends Pieces{
   public Rook(ChessBoard board, int col, int row, boolean isWhite) {
      super(board, isWhite ? "image\\white_rook.png" : "image\\black_rook.png");
      this.col = col;
      this.row = row;
      this.isWhite = isWhite;
      this.name = "Rook";
      this.value = 5;
      this.xPos = col * board.titleSize;
      this.yPos = row * board.titleSize;
   }
   
   public boolean isValidMovement(int col, int row) {
      return (this.col == col && this.row != row) || (this.col != col && this.row == row);
   }
   
   public boolean moveCollidesWithPiece(int col, int row) {
      if (this.col == col) {
         int min = Math.min(this.row, row);
         int max = Math.max(this.row, row);
         for (int i = min + 1; i < max; i++) {
            if (board.getPiece(col, i) != null) {
               return true;
            }
         }
      } else {
         int min = Math.min(this.col, col);
         int max = Math.max(this.col, col);
         for (int i = min + 1; i < max; i++) {
            if (board.getPiece(i, row) != null) {
               return true;
            }
         }
      }
      return false;
   }
   
}
