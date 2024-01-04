package logic;
import board.ChessBoard;
import pieces.Pieces;

public class CheckScanner {

   ChessBoard board;

   public CheckScanner(ChessBoard board) {
      this.board = board;
   }

   public boolean isKingChecked(Move move) {

      Pieces king = board.findKing(move.getPiece().isWhite);
      assert king != null;

      int kingCol = king.col;
      int kingRow = king.row;

      if (board.selectedPiece != null && board.selectedPiece.name.equals("King")) {
         kingCol = move.getNewCol();
         kingRow = move.getNewRow();
      }

      return isCheckByRook(move.getNewCol(), move.getNewRow(), king, kingCol, kingRow, 0, 1) ||
            isCheckByRook(move.getNewCol(), move.getNewRow(), king, kingCol, kingRow, 1, 0) ||
            isCheckByRook(move.getNewCol(), move.getNewRow(), king, kingCol, kingRow, 0, -1) ||
            isCheckByRook(move.getNewCol(), move.getNewRow(), king, kingCol, kingRow, -1, 0) ||

            isCheckByBishop(move.getNewCol(), move.getNewRow(), king, kingCol, kingRow, -1, -1) ||
            isCheckByBishop(move.getNewCol(), move.getNewRow(), king, kingCol, kingRow, 1, -1) ||
            isCheckByBishop(move.getNewCol(), move.getNewRow(), king, kingCol, kingRow, 1, 1) ||
            isCheckByBishop(move.getNewCol(), move.getNewRow(), king, kingCol, kingRow, -1, 1) ||

            isCheckByKnight(move.getNewCol(), move.getNewRow(), king, kingCol, kingRow) ||
            isCheckByPawn(move.getNewCol(), move.getNewRow(), king, kingCol, kingRow) ||
            isCheckByKing(king, kingCol, kingRow);
   }


   private boolean isCheckByRook(int col, int row, Pieces king, int kingCol, int kingRow, int colVal, int rowVal) {

      for (int i = 1; i < 8; i++) {
         if (kingCol + (i * colVal) == col && kingRow + (i * rowVal) == row) {
            break;
         }

         Pieces piece = board.getPiece(kingCol + (i * colVal), kingRow + (i * rowVal));
         if (piece != null && piece != board.selectedPiece) {
            if (!board.sameColor(piece, king) && (piece.name.equals("Rook") || piece.name.equals("Queen"))) {
               return true;
            }
            break;
         }
      }
      return false;
   }

   private boolean isCheckByBishop(int col, int row, Pieces king, int kingCol, int kingRow, int colVal, int rowVal) {

      for (int i = 1; i < 8; i++) {
         if (kingCol - (i * colVal) == col && kingRow - (i * rowVal) == row) {
            break;
         }

         Pieces piece = board.getPiece(kingCol - (i * colVal), kingRow - (i * rowVal));
         if (piece != null && piece != board.selectedPiece) {
            if (!board.sameColor(piece, king) && (piece.name.equals("Bishop") || piece.name.equals("Queen"))) {
               return true;
            }
            break;
         }
      }
      return false;
   }

   private boolean isCheckByKnight(int col, int row, Pieces king, int kingCol, int kingRow) {
      return knightCheck(board.getPiece(kingCol - 1, kingRow - 2), king, col, row) ||
            knightCheck(board.getPiece(kingCol + 1, kingRow - 2), king, col, row) ||
            knightCheck(board.getPiece(kingCol + 2, kingRow - 1), king, col, row) ||
            knightCheck(board.getPiece(kingCol + 2, kingRow + 1), king, col, row) ||
            knightCheck(board.getPiece(kingCol + 1, kingRow + 2), king, col, row) ||
            knightCheck(board.getPiece(kingCol - 1, kingRow + 2), king, col, row) ||
            knightCheck(board.getPiece(kingCol - 2, kingRow + 1), king, col, row) ||
            knightCheck(board.getPiece(kingCol - 2, kingRow - 1), king, col, row);

   }

   private boolean knightCheck(Pieces piece_1, Pieces pieces_2, int col, int row) {
      return piece_1 != null && !board.sameColor(piece_1, pieces_2) && piece_1.name.equals("Knight")
            && !(piece_1.col == col && piece_1.row == row);
   }

   private boolean isCheckByKing(Pieces king, int kingCol, int kingRow) {
      return kingCheck(board.getPiece(kingCol - 1, kingRow - 1), king) ||
            kingCheck(board.getPiece(kingCol + 1, kingRow - 1), king) ||
            kingCheck(board.getPiece(kingCol, kingRow - 1), king) ||
            kingCheck(board.getPiece(kingCol - 1, kingRow), king) ||
            kingCheck(board.getPiece(kingCol + 1, kingRow), king) ||
            kingCheck(board.getPiece(kingCol - 1, kingRow + 1), king) ||
            kingCheck(board.getPiece(kingCol + 1, kingRow + 1), king) ||
            kingCheck(board.getPiece(kingCol, kingRow + 1), king);

   }

   private boolean kingCheck(Pieces piece_1, Pieces piece_2) {
      return piece_1 != null && !board.sameColor(piece_1, piece_2) && piece_1.name.equals("King");
   }

   private boolean isCheckByPawn(int col, int row, Pieces king, int kingCol, int kingRow) {
      int colorVal = king.isWhite ? -1 : 1;
      return pawnCheck(board.getPiece(kingCol - 1, kingRow + colorVal), king, col, row) ||
            pawnCheck(board.getPiece(kingCol + 1, kingRow + colorVal), king, col, row);

   }

   private boolean pawnCheck(Pieces piece_1, Pieces piece_2, int col, int row) {
      return piece_1 != null && !board.sameColor(piece_1, piece_2) && piece_1.name.equals("Pawn");
   }

}
