package pieces;
import board.ChessBoard;

public class Queen extends Pieces{
   public Queen(ChessBoard board, int col, int row, boolean isWhite) {
      super(board, isWhite ? "image\\white_queen.png" : "image\\black_queen.png");
      this.col = col;
      this.row = row;
      this.isWhite = isWhite;
      this.name = "Queen";
      this.value = 9;
      this.xPos = col * board.titleSize;
      this.yPos = row * board.titleSize;
   }
   
   public boolean isValidMovement(int col, int row) {
      return (Math.abs(this.col - col) == Math.abs(this.row - row)) || ((this.col == col && this.row != row) || (this.col != col && this.row == row));
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
      } else if (this.row == row) {
         int min = Math.min(this.col, col);
         int max = Math.max(this.col, col);
         for (int i = min + 1; i < max; i++) {
            if (board.getPiece(i, row) != null) {
               return true;
            }
         }
      } else {
         int colDirection = col - this.col > 0 ? 1 : -1;
         int rowDirection = row - this.row > 0 ? 1 : -1;
         int colDistance = Math.abs(col - this.col);
         int rowDistance = Math.abs(row - this.row);
         for (int i = 1; i < colDistance; i++) {
            if (board.getPiece(this.col + i * colDirection, this.row + i * rowDirection) != null) {
               return true;
            }
         }
      }
      return false;
   }
   
}
