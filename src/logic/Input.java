package logic;
import java.awt.*;
import java.awt.event.*;
import board.ChessBoard;
import pieces.Pieces;

public class Input extends MouseAdapter {

   ChessBoard board;

   public Input(ChessBoard board) {
      this.board = board;
   }

   public void mouseEntered(MouseEvent e) {
      board.setCursor(new Cursor(Cursor.HAND_CURSOR));
   }

   public void mousePressed(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();
  
      int col = x / board.titleSize;
      int row = y / board.titleSize;
  
      Pieces pieceXY = board.getPiece(col, row);
  
      if (pieceXY != null) {
          if (board.selectedPiece != null && board.selectedPiece.isWhite != pieceXY.isWhite) {
              Move move = new Move(board, board.selectedPiece, col, row);
              if (board.isValidMove(move)) {
                  board.makeMove(move);
                  board.selectedPiece = null;
              } else {
                  board.selectedPiece = pieceXY;
              }
          } else {
              board.selectedPiece = pieceXY;
          }
      } else if (board.selectedPiece != null) {
          Move move = new Move(board, board.selectedPiece, col, row);
          if (board.isValidMove(move)) {
              board.makeMove(move);
              board.selectedPiece = null;
          } else {
              board.selectedPiece = null;
          }
      }
      board.repaint();
  }

   public void mouseDragged(MouseEvent e) {
      board.setCursor(new Cursor(Cursor.HAND_CURSOR));

      if (board.selectedPiece != null) {
         int x = e.getX();
         int y = e.getY();

         board.selectedPiece.xPos = x - board.titleSize / 2;
         board.selectedPiece.yPos = y - board.titleSize / 2;

         board.repaint();
      }
   }

   public void mouseReleased(MouseEvent e) {
      board.setCursor(new Cursor(Cursor.HAND_CURSOR));

      int x = e.getX();
      int y = e.getY();

      int col = x / board.titleSize;
      int row = y / board.titleSize;

      if (board.selectedPiece != null) {
          Move move = new Move(board, board.selectedPiece, col, row);
          if (board.isValidMove(move) && board.selectedPiece.isWhite == board.isWhitesTurn && board.getPromotionPending() == false && board.getGameOver() == false) {
              board.makeMove(move);
              board.selectedPiece = null;
          } else {
              board.selectedPiece.xPos = board.selectedPiece.col * board.titleSize;
              board.selectedPiece.yPos = board.selectedPiece.row * board.titleSize;
          }
      } else {
          board.selectedPiece = null;
      }

      board.repaint();
  }

   public void mouseExited(MouseEvent e) {
      board.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
   }
}