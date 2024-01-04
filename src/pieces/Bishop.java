package pieces;
import board.ChessBoard;

public class Bishop extends Pieces {
   public Bishop(ChessBoard board, int col, int row, boolean isWhite) {
      super(board, isWhite ? "image\\white_bishop.png" : "image\\black_bishop.png");
      this.col = col;
      this.row = row;
      this.isWhite = isWhite;
      this.name = "Bishop";
      this.value = 3;
      this.xPos = col * board.titleSize;
      this.yPos = row * board.titleSize;
   }
   
   public boolean isValidMovement(int col, int row) {
      return Math.abs(this.col - col) == Math.abs(this.row - row);
   }
   
   public boolean moveCollidesWithPiece(int col, int row) {
      int colDirection = col - this.col > 0 ? 1 : -1;
      int rowDirection = row - this.row > 0 ? 1 : -1;
      int colDistance = Math.abs(col - this.col);
      int rowDistance = Math.abs(row - this.row);
      for (int i = 1; i < colDistance; i++) {
         if (board.getPiece(this.col + i * colDirection, this.row + i * rowDirection) != null) {
            return true;
         }
      }
      return false;
   }
   
}
