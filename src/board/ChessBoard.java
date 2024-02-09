package board;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import logic.CheckScanner;
import logic.Input;
import logic.Move;
import logic.PlayerTimer;
import logic.RoundedButton;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Pieces;
import pieces.Queen;
import pieces.Rook;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ChessBoard extends JPanel {
   public int titleSize = 101;

   public int cols = 8;
   public int rows = 8;

   ArrayList<Pieces> pieces = new ArrayList<Pieces>();

   public Pieces selectedPiece;

   Input input = new Input(this);

   public int enPassentTile = -1;

   public CheckScanner checkScanner = new CheckScanner(this);

   public boolean isWhitesTurn = true;

   public PlayerTimer whiteTimer;
   public PlayerTimer blackTimer;

   private int whiteScore = 0;
   private int blackScore = 0;

   private boolean promotionPending = false;
   private boolean gameOver = false;
   Font poppinsFont = null;
   Font poppinsFontBold = null;

   public ChessBoard() {
      try {
         poppinsFont = Font.createFont(Font.TRUETYPE_FONT,
               new File("font\\Poppins-Regular.ttf"));
         poppinsFontBold = Font.createFont(Font.TRUETYPE_FONT,
               new File("font\\Poppins-Bold.ttf"));
      } catch (FontFormatException | IOException e) {
         e.printStackTrace();
      }
      this.setPreferredSize(new Dimension(titleSize * cols, titleSize * rows));
      this.addMouseListener(input);
      this.addMouseMotionListener(input);
      addPieces();
      this.whiteTimer = new PlayerTimer(this, 1);
      this.blackTimer = new PlayerTimer(this, 1);

      this.whiteTimer.setOpponentTimer(blackTimer);
      this.blackTimer.setOpponentTimer(whiteTimer);
      blackTimer.pauseTimer();
   }

   public void switchTurn() {
      if (!isWhitesTurn) {
         this.whiteTimer.pauseTimer();
         this.blackTimer.resumeTimer();
      } else {
         this.blackTimer.pauseTimer();
         this.whiteTimer.resumeTimer();
      }
   }

   public void askResign(boolean isWhitesTurn) {
      String whoAsk = isWhitesTurn ? "White" : "Black";

      JFrame frame = new JFrame();
      frame.setSize(500, 500);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setLayout(new GridBagLayout());
      frame.getContentPane().setBackground(Color.decode("#302e2b"));
      frame.setResizable(false);

      JPanel panel = new JPanel();
      panel.setBackground(Color.decode("#262522"));
      panel.setLayout(new GridBagLayout());
      panel.setPreferredSize(new Dimension(420, 420));

      JPanel innerPanel = new JPanel();
      innerPanel.setBackground(Color.decode("#262522"));
      innerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
      innerPanel.setPreferredSize(new Dimension(400, 200));

      JLabel label = new JLabel("Are You Sure " + whoAsk + " Want To Resign?", SwingConstants.CENTER);

      label.setFont(poppinsFontBold.deriveFont(20f));
      label.setForeground(Color.WHITE);

      RoundedButton button = createButton("OK", 15, 2);
      button.setFont(poppinsFontBold.deriveFont(20f));
      button.setPreferredSize(new Dimension(400, 55));
      button.setBackground(Color.decode("#7ec139"));
      button.setForeground(Color.WHITE);

      button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            frame.dispose();
            handleGameEnd(!isWhitesTurn);
         }
      });

      innerPanel.add(label);
      innerPanel.add(button);
      panel.add(innerPanel);
      frame.add(panel);

      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }
   public void askDraw(boolean isWhitesTurn) {
      String whoAsk = isWhitesTurn ? "White" : "Black";

      JFrame frame = new JFrame();
      frame.setSize(500, 500);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setLayout(new GridBagLayout());
      frame.getContentPane().setBackground(Color.decode("#302e2b"));
      frame.setResizable(false);

      JPanel panel = new JPanel();
      panel.setBackground(Color.decode("#262522"));
      panel.setLayout(new GridBagLayout());
      panel.setPreferredSize(new Dimension(420, 420));

      JPanel innerPanel = new JPanel();
      innerPanel.setBackground(Color.decode("#262522"));
      innerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
      innerPanel.setPreferredSize(new Dimension(400, 200));

      JLabel label = new JLabel( whoAsk + " Want to Draw", SwingConstants.CENTER);

      label.setFont(poppinsFontBold.deriveFont(20f));
      label.setForeground(Color.WHITE);

      RoundedButton button = createButton("Aggree", 15, 2);
      button.setFont(poppinsFontBold.deriveFont(20f));
      button.setPreferredSize(new Dimension(400, 55));
      button.setBackground(Color.decode("#7ec139"));
      button.setForeground(Color.WHITE);

      button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            whiteTimer.pauseTimer();
            blackTimer.pauseTimer();
            frame.dispose();
            handleGameDraw();
         }
      });

      innerPanel.add(label);
      innerPanel.add(button);
      panel.add(innerPanel);
      frame.add(panel);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public Pieces getPiece(int col, int row) {
      for (Pieces piece : pieces) {
         if (piece.col == col && piece.row == row) {
            return piece;
         }
      }
      return null;
   }

   public boolean getPromotionPending() {
      return promotionPending;
   }

   public boolean getGameOver() {
      return gameOver;
   }

   public void makeMove(Move move) {
      if (move.getPiece().isWhite == isWhitesTurn && !promotionPending && !gameOver) {
         if (move.getPiece().name.equals("Pawn")) {
            movePawn(move);
         } else if (move.getPiece().name.equals("King")) {
            moveKing(move);
         }
         move.getPiece().col = move.getNewCol();
         move.getPiece().row = move.getNewRow();
         move.getPiece().xPos = move.getNewCol() * titleSize;
         move.getPiece().yPos = move.getNewRow() * titleSize;

         move.getPiece().isFirstMove = false;

         capture(move);

         if (isMoveCheckingKing(move)) {
            if (isWhitesTurn) {
               System.out.println("White check!");
            } else {
               handleTimeCheckMate();
               System.out.println("Black check!");
            }
            
            if (isCheckmate(!isWhitesTurn)) {
               handleTimeCheckMate();
               if (isWhitesTurn) {
                  System.out.println("White checkmate!");
               } else {
                  System.out.println("Black checkmate!");
               }
            }
         }
         isWhitesTurn = !isWhitesTurn;
         switchTurn();
      }
   }

   public boolean isCheckmate(boolean isWhite) {
      Pieces king = findKing(isWhite);
      for (int col = 0; col < cols; col++) {
         for (int row = 0; row < rows; row++) {
            Move move = new Move(this, king, col, row);
            if (isValidMove(move) && !wouldBeInCheck(move)) {
               return false;
            }
         }
      }
      
      List<Pieces> piecesCopy = new ArrayList<>(pieces);

      for (Pieces piece : piecesCopy) {
         if (piece.isWhite == isWhite) {
            for (int col = 0; col < cols; col++) {
               for (int row = 0; row < rows; row++) {
                  Move move = new Move(this, piece, col, row);
                  if (isValidMove(move) && !wouldBeInCheck(move)) {
                     return false;
                  }
               }
            }
         }
      }

      if (!isInCheck(king)) {
         for (Pieces piece : piecesCopy) {
            if (piece.isWhite != isWhite) {
               for (int col = 0; col < cols; col++) {
                  for (int row = 0; row < rows; row++) {
                     Move move = new Move(this, piece, col, row);
                     if (isValidMove(move) && !wouldBeInCheck(move)) {
                        return false;
                     }
                  }
               }
            }
         }
      }
      
      return true;
   }

   public boolean wouldBeInCheck(Move move) {
      Pieces capturedPiece = null;
      int oldCol = move.getPiece().col;
      int oldRow = move.getPiece().row;

      Pieces destinationPiece = getPiece(move.getNewCol(), move.getNewRow());
      if (destinationPiece != null) {
         pieces.remove(destinationPiece);
         capturedPiece = destinationPiece;
      }

      move.getPiece().col = move.getNewCol();
      move.getPiece().row = move.getNewRow();

      Pieces king = findKing(move.getPiece().isWhite);

      boolean check = isInCheck(king);

      move.getPiece().col = oldCol;
      move.getPiece().row = oldRow;

      if (capturedPiece != null) {
         pieces.add(capturedPiece);
      }

      return check;
   }

   private void moveKing(Move move) {
      if (Math.abs(move.getPiece().col - move.getNewCol()) == 2) {
         Pieces rook;
         if (move.getPiece().col < move.getNewCol()) {
            rook = getPiece(7, move.getPiece().row);
            rook.col = 5;
         } else {
            rook = getPiece(0, move.getPiece().row);
            rook.col = 3;
         }
         rook.xPos = rook.col * titleSize;
      }
   }

   private void movePawn(Move move) {

      int colorIndex = move.getPiece().isWhite ? 1 : -1;

      if (getTileNum(move.getNewCol(), move.getNewRow()) == enPassentTile) {
         move.setCapture(getPiece(move.getNewCol(), move.getNewRow() + colorIndex));
      }

      if (Math.abs(move.getPiece().row - move.getNewRow()) == 2) {
         enPassentTile = getTileNum(move.getNewCol(), move.getNewRow() + colorIndex);
      } else {
         enPassentTile = -1;

      }

      colorIndex = move.getPiece().isWhite ? 0 : 7;

      if (move.getNewRow() == colorIndex) {
         promotionPawn(move);
      }

   }

   public boolean isMoveCheckingKing(Move move) {
      boolean isWhite = move.getPiece().isWhite;
      for (Pieces piece : pieces) {
         if (piece.name.equals("King") && piece.isWhite != isWhite) {
            if (isInCheck(piece)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean isInCheck(Pieces king) {
      for (Pieces piece : pieces) {
         if (piece.isWhite != king.isWhite) {
            Move move = new Move(this, piece, king.col, king.row);
            if (isValidMove(move)) {
               return true;
            }
         }
      }

      return false;
   }

   public void handleTimeOut() {
      if (isWhitesTurn) {
         handleGameEnd(false);
      } else {
         handleGameEnd(true);
      }
   }

   public void handleTimeCheckMate() {
      if (!isWhitesTurn) {
         handleGameEnd(false);
      } else {
         handleGameEnd(true);
      }
   }
   
   private void handleGameDraw() {


      JFrame frame = new JFrame();
      frame.setSize(500, 500);
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      frame.setLayout(new GridBagLayout());
      frame.getContentPane().setBackground(Color.decode("#302e2b"));
      frame.setResizable(false);

      JPanel panel = new JPanel();
      panel.setBackground(Color.decode("#262522"));
      panel.setLayout(new GridBagLayout());
      panel.setPreferredSize(new Dimension(420, 420));

      JPanel innerPanel = new JPanel();
      innerPanel.setBackground(Color.decode("#262522"));
      innerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
      innerPanel.setPreferredSize(new Dimension(400, 200));

      JLabel label = new JLabel("Game Draw!", SwingConstants.CENTER);
      label.setFont(poppinsFontBold.deriveFont(30f));
      label.setForeground(Color.WHITE);

      RoundedButton button = createButton("OK", 15, 2);
      button.setFont(poppinsFontBold.deriveFont(20f));
      button.setPreferredSize(new Dimension(400, 55));
      button.setBackground(Color.decode("#7ec139"));
      button.setForeground(Color.WHITE);

      button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            frame.dispose();
            resetGame();
         }
      });

      innerPanel.add(label);
      innerPanel.add(button);
      panel.add(innerPanel);
      frame.add(panel);

      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   private void handleGameEnd(boolean whiteWins) {

      String winner = whiteWins ? "White" : "Black";

      System.out.println("Game Over! " + winner + " wins!");
      JFrame frame = new JFrame();
      frame.setSize(500, 500);
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      frame.setLayout(new GridBagLayout());
      frame.getContentPane().setBackground(Color.decode("#302e2b"));
      frame.setResizable(false);

      JPanel panel = new JPanel();
      panel.setBackground(Color.decode("#262522"));
      panel.setLayout(new GridBagLayout());
      panel.setPreferredSize(new Dimension(420, 420));

      JPanel innerPanel = new JPanel();
      innerPanel.setBackground(Color.decode("#262522"));
      innerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
      innerPanel.setPreferredSize(new Dimension(400, 200));

      JLabel label = new JLabel("Game Over! " + winner + " wins!", SwingConstants.CENTER);
      label.setFont(poppinsFontBold.deriveFont(30f));
      label.setForeground(Color.WHITE);

      RoundedButton button = createButton("OK", 15, 2);
      button.setFont(poppinsFontBold.deriveFont(20f));
      button.setPreferredSize(new Dimension(400, 55));
      button.setBackground(Color.decode("#7ec139"));
      button.setForeground(Color.WHITE);

      button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            frame.dispose();
            resetGame();
         }
      });

      innerPanel.add(label);
      innerPanel.add(button);
      panel.add(innerPanel);
      frame.add(panel);

      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public void resetGame() {
      JPanel chessBoardParentPanel = (JPanel) getParent();
      JPanel chessBoardParentParentPanel = (JPanel) chessBoardParentPanel.getParent();
      JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(chessBoardParentParentPanel);
      
      whiteTimer.pauseTimer();
      blackTimer.pauseTimer();
      Class<? extends JFrame> classTurunan = parentFrame.getClass();
      parentFrame.dispose();
      try{
         JFrame frame = classTurunan.getDeclaredConstructor().newInstance();
      }catch(Exception e){
         e.printStackTrace();
      }
      
   }

   private void promotionPawn(Move move) {
      promotionPending = true;
      JFrame frame = new JFrame();
      frame.setUndecorated(true);
      BufferedImage transparentImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
      frame.setIconImage(transparentImage);
      frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      JPanel panel = new JPanel();

      String color = move.getPiece().isWhite ? "white" : "black";
      Color backgroundColor = move.getPiece().isWhite ? Color.decode("#565352") : Color.decode("#f8f8f8");
      panel.setBackground(backgroundColor);

      JButton queenButton = createButton("image/" + color + "_queen.png", move);
      queenButton.addActionListener(e -> {
         pieces.remove(move.getPiece());
         pieces.add(new Queen(this, move.getNewCol(), move.getNewRow(), move.getPiece().isWhite));
         frame.dispose();
         promotionPending = false;
         switchTurn();
         repaint();
      });
      panel.add(queenButton);

      JButton rookButton = createButton("image/" + color + "_rook.png", move);
      rookButton.addActionListener(e -> {
         pieces.remove(move.getPiece());
         pieces.add(new Rook(this, move.getNewCol(), move.getNewRow(), move.getPiece().isWhite));
         frame.dispose();
         promotionPending = false;
         switchTurn();
         repaint();
      });
      panel.add(rookButton);

      JButton bishopButton = createButton("image/" + color + "_bishop.png", move);
      bishopButton.addActionListener(e -> {
         pieces.remove(move.getPiece());
         pieces.add(new Bishop(this, move.getNewCol(), move.getNewRow(), move.getPiece().isWhite));
         frame.dispose();
         promotionPending = false;
         switchTurn();
         repaint();
      });
      panel.add(bishopButton);

      JButton knightButton = createButton("image/" + color + "_knight.png", move);
      knightButton.addActionListener(e -> {
         pieces.remove(move.getPiece());
         pieces.add(new Knight(this, move.getNewCol(), move.getNewRow(), move.getPiece().isWhite));
         frame.dispose();
         promotionPending = false;
         switchTurn();
         repaint();
      });
      panel.add(knightButton);

      frame.add(panel);

      frame.pack();
      frame.setLocation(387, 470);
      frame.setResizable(false);
      frame.setVisible(true);
   }

   private static JButton createButton(String imagePath, Move move) {
      JButton button = new JButton(new ImageIcon(imagePath));
      button.setOpaque(false);
      button.setContentAreaFilled(false);
      button.setBorder(null);

      button.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            button.setContentAreaFilled(true);
            if (!move.getPiece().isWhite) {
               button.setBackground(Color.decode("#565352"));
            } else {
               button.setBackground(Color.decode("#f8f8f8"));

            }

            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            button.setContentAreaFilled(false);
            button.setForeground(null);
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }
      });

      return button;
   }

   private static RoundedButton createButton(String name, int cornerRadius, int number) {
      RoundedButton button = new RoundedButton(name, cornerRadius);
      button.setOpaque(false);
      button.setFocusable(false);
      button.setBorder(null);

      button.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            if (number == 1) {
               button.setBackground(Color.decode("#494846"));

            } else if (number == 2) {

               button.setBackground(Color.decode("#90c05e"));
            }
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            if (number == 1) {
               button.setBackground(Color.decode("#42413f"));

            } else if (number == 2) {

               button.setBackground(Color.decode("#7ec139"));
            }
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }
      });

      return button;
   }

   public void capture(Move move) {
      Pieces capturedPiece = move.getCapture();
      if (capturedPiece != null) {
         if (capturedPiece.isWhite) {
            blackScore += capturedPiece.value;
            System.out.println(blackScore);
         } else {
            whiteScore += capturedPiece.value;
            System.out.println(whiteScore);
         }
         pieces.remove(capturedPiece);
      }
   }

   public boolean isValidMove(Move move) {

      int newRow = move.getNewRow();
      int newCol = move.getNewCol();

      if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
         return false;
      }

      if (sameColor(move.getPiece(), move.getCapture())) {
         return false;
      }

      if (!move.getPiece().isValidMovement(move.getNewCol(), move.getNewRow())) {
         return false;
      }

      if (move.getPiece().moveCollidesWithPiece(move.getNewCol(), move.getNewRow())) {
         return false;
      }

      if (checkScanner.isKingChecked(move)) {
         return false;
      }

      return true;
   }

   public boolean sameColor(Pieces piece1, Pieces piece2) {
      if (piece1 == null || piece2 == null) {
         return false;
      }
      return piece1.isWhite == piece2.isWhite;
   }

   public int getTileNum(int col, int row) {
      return row * rows + col;
   }

   public Pieces findKing(boolean isWhite) {
      for (Pieces piece : pieces) {
         if (piece.name.equals("King") && piece.isWhite == isWhite) {
            return piece;
         }
      }
      return null;
   }

   public void addPieces() {
      pieces.add(new Knight(this, 1, 0, false));
      pieces.add(new Knight(this, 6, 0, false));
      pieces.add(new Knight(this, 1, 7, true));
      pieces.add(new Knight(this, 6, 7, true));
      pieces.add(new Bishop(this, 2, 0, false));
      pieces.add(new Bishop(this, 5, 0, false));
      pieces.add(new Bishop(this, 2, 7, true));
      pieces.add(new Bishop(this, 5, 7, true));
      pieces.add(new Rook(this, 0, 0, false));
      pieces.add(new Rook(this, 7, 0, false));
      pieces.add(new Rook(this, 0, 7, true));
      pieces.add(new Rook(this, 7, 7, true));
      pieces.add(new Queen(this, 3, 0, false));
      pieces.add(new Queen(this, 3, 7, true));
      pieces.add(new King(this, 4, 0, false));
      pieces.add(new King(this, 4, 7, true));
      for (int i = 0; i < 8; i++) {
         pieces.add(new Pawn(this, i, 1, false));
         pieces.add(new Pawn(this, i, 6, true));
      }
   }

   public void paintComponent(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      for (int row = 0; row < rows; row++) {
         for (int col = 0; col < cols; col++) {
            if ((row + col) % 2 == 0) {
               g2d.setColor(Color.decode("#e9edcc"));
            } else {
               g2d.setColor(Color.decode("#779954"));
            }

            g2d.fillRect(col * titleSize, row * titleSize, titleSize, titleSize);
         }
      }
      
      if (selectedPiece != null && selectedPiece.isWhite == isWhitesTurn && !promotionPending && !gameOver) {
         for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
               if (isValidMove(new Move(this, selectedPiece, col, row))) {
                  g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                  int x = col * titleSize + titleSize / 2;
                  int y = row * titleSize + titleSize / 2;
                  
                  Pieces pieceAtPosition = getPieceAtPosition(col, row);
                  if (pieceAtPosition != null && pieceAtPosition.isWhite != selectedPiece.isWhite) {
                     int circleSize = 94;
                     g2d.setColor(new Color(119, 116, 116, 90));
                     float strokeWidth = 7.0f; 
                     g2d.setStroke(new BasicStroke(strokeWidth));
                     g2d.drawOval(x - circleSize / 2, y - circleSize / 2, circleSize, circleSize);
                  } else {
                     int dotSize = 134 / 4; 
                     g2d.setColor(new Color(119, 116, 116, 90));
                     g2d.fillOval(x - dotSize / 2, y - dotSize / 2, dotSize, dotSize);
                  }
               }
            }
         }
      }

      for (Pieces piece : pieces) {
         if (piece != selectedPiece) {
            piece.draw(g2d);
         }
      }

      if (selectedPiece != null) {
         selectedPiece.draw(g2d);
      }

   }

   private Pieces getPieceAtPosition(int col, int row) {
      for (Pieces piece : pieces) {
         if (piece.col == col && piece.row == row) {
            return piece;
         }
      }
      return null;
   }
}
