package logic;
import board.ChessBoard;
import pieces.Pieces;

public class Move {
   
   private int oldcol;
   private int oldrow;
   private int newcol;
   private int newrow;
   private Pieces piece;
   private Pieces capture;
   
   public Move(ChessBoard board, Pieces piece, int newcol, int newrow) {
      this.oldcol = piece.col;
      this.oldrow = piece.row;
      this.newcol = newcol;
      this.newrow = newrow;
      this.piece = piece;
      this.capture = board.getPiece(newcol, newrow);
   }
   
    
   public void setOldCol(int oldcol) {
      this.oldcol = oldcol;
   }
   
   public void setOldRow(int oldrow) {
      this.oldrow = oldrow;
   }
   
   public void setNewCol(int newcol) {   
      this.newcol = newcol;
   }
   
   public void setNewRow(int newrow) {
      this.newrow = newrow;
   }
   
   public void setPiece(Pieces piece) {
      this.piece = piece;
   }
   
   public void setCapture(Pieces capture) {
      this.capture = capture;
   }
   
   public int getOldCol() {
      return oldcol;
   }
   
   public int getOldRow() {
      return oldrow;
   }
   
   public int getNewCol() {
      return newcol;
   }
   
   public int getNewRow() {
      return newrow;
   }
   
   public Pieces getPiece() {
      return piece;
   }
   
   public Pieces getCapture() {
      return capture;
   }
   
   
   
}
