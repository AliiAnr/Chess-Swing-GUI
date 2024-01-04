package pieces;
import board.ChessBoard;

public class Knight extends Pieces {
   public Knight(ChessBoard board, int col, int row, boolean isWhite) {
      super(board, isWhite ? "image\\white_knight.png" : "image\\black_knight.png");
      this.col = col;
      this.row = row;
      this.isWhite = isWhite;
      this.name = "Knight";
      this.value = 3;
      this.xPos = col * board.titleSize;
      this.yPos = row * board.titleSize;
   }
   
   public boolean isValidMovement(int col, int row) {
      return Math.abs(col-this.col) * Math.abs(row-this.row) == 2;
   }
}