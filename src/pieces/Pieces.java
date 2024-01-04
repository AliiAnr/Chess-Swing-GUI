package pieces;
import javax.imageio.*;

import board.ChessBoard;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Pieces {

   public int col, row;
   public int xPos, yPos;

   public boolean isWhite;
   public String name;
   public int value;

   ChessBoard board;
   
   public boolean isFirstMove = true;

   Image image;

   public Pieces(ChessBoard board, String imagePath) {
      this.board = board;
      try {
         Image originalImage = ImageIO.read(new File(imagePath));
         this.image = getScaledImage(originalImage, board.titleSize, board.titleSize);
      } catch (IOException e) {
         System.out.println("Image not found");
      }
   }

   public boolean isValidMovement(int col, int row) {
      return true;
   }

   public boolean moveCollidesWithPiece(int col, int row) {
      return false;
   }

   public void draw(Graphics2D g2d) {
      g2d.drawImage(image, xPos, yPos, null);
   }

   private Image getScaledImage(Image srcImg, int w, int h){
      BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = resizedImg.createGraphics();

      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      g2.drawImage(srcImg, 0, 0, w, h, null);
      g2.dispose();

      return resizedImg;
   }

}