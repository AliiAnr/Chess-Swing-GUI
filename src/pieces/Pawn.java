package pieces;
import board.ChessBoard;

public class Pawn extends Pieces {

   public Pawn(ChessBoard board, int col, int row, boolean isWhite) {
      super(board, isWhite ? "image\\white_pawn.png" : "image\\black_pawn.png");
      this.col = col;
      this.row = row;
      this.isWhite = isWhite;
      this.name = "Pawn";
      this.value = 1;
      this.xPos = col * board.titleSize;
      this.yPos = row * board.titleSize;
   }
   
   public boolean isValidMovement(int col, int row) {
      
      int colorIndex = isWhite ? 1 : -1;
      if (this.col == col && row == this.row - colorIndex && board.getPiece(col, row) == null) {
         return true;
      }
      
      if(isFirstMove && this.col == col && row == this.row - 2 * colorIndex && board.getPiece(col, row) == null && board.getPiece(col, row + colorIndex) == null) {
         return true;
      }
      
      //capture left
      if (this.col - 1 == col && row == this.row - colorIndex && board.getPiece(col, row) != null && board.getPiece(col, row).isWhite != this.isWhite) {
         return true;
      }
      
      //capture right
      if (this.col + 1 == col && row == this.row - colorIndex && board.getPiece(col, row) != null && board.getPiece(col, row).isWhite != this.isWhite) {
         return true;
      }
      
      //en passant left
      if (board.getTileNum(col, row) == board.enPassentTile && col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col, row + colorIndex) != null) {
         return true;
      } 
      if (board.getTileNum(col, row) == board.enPassentTile && col == this.col + 1 && row == this.row - colorIndex && board.getPiece(col, row + colorIndex) != null) {
         return true;
      }
      
      return false;
      
   }
   
   
}
