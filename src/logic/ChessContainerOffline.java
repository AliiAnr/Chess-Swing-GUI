package logic;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import board.ChessBoard;

import java.awt.event.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ChessContainerOffline extends JFrame {

   public ChessContainerOffline() {
      Font poppinsFont = null;
      Font poppinsFontBold = null;
      try {
         poppinsFont = Font.createFont(Font.TRUETYPE_FONT,
               new File("font\\Poppins-Regular.ttf"));
         poppinsFontBold = Font.createFont(Font.TRUETYPE_FONT,
               new File("font\\Poppins-Bold.ttf"));
      } catch (FontFormatException | IOException e) {
         e.printStackTrace();
      }
      JPanel panel = new JPanel();
      JPanel rightPanel = new JPanel();
      JPanel leftPanel = new JPanel();
      ChessBoard chessBoard = new ChessBoard();

      JPanel topPanel = new JPanel();
      String imagePathTop = "image/black_king.png";
      ImageIcon iconTop = new ImageIcon(
            new ImageIcon(imagePathTop).getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH));
      JLabel imageLabelTop = new JLabel(iconTop);
      JPanel imagePanel = new JPanel();
      imagePanel.add(imageLabelTop);
      imagePanel.setPreferredSize(new Dimension(68, 68));
      imagePanel.setBackground(Color.decode("#262522"));
      imagePanel.setLayout(new GridBagLayout());
      topPanel.setPreferredSize(new Dimension(808, 70));
      topPanel.setBackground(Color.decode("#302e2b"));
      topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

      JPanel namePanel = new JPanel();
      namePanel.setPreferredSize(new Dimension(620, 68));
      namePanel.setBackground(Color.decode("#302e2b"));
      namePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

      JLabel nameLabel = new JLabel("Ali Ganteng");
      nameLabel.setFont(poppinsFontBold.deriveFont(20f));
      nameLabel.setForeground(Color.decode("#f8f8f8"));
      nameLabel.setPreferredSize(new Dimension(600, 25));

      nameLabel.setBackground(Color.decode("#302e2b"));
      nameLabel.setOpaque(true);
      namePanel.add(nameLabel);

      JPanel timePanel = new JPanel();
      timePanel.setPreferredSize(new Dimension(119, 68));
      timePanel.setBackground(Color.decode("#262522"));
      timePanel.setLayout(new GridBagLayout());
      timePanel.add(chessBoard.blackTimer);

      topPanel.add(imagePanel);
      topPanel.add(namePanel);
      topPanel.add(timePanel);

      JPanel bottomPanel = new JPanel();
      String imagePathBottom = "image/white_king.png";
      ImageIcon iconBottom = new ImageIcon(
            new ImageIcon(imagePathBottom).getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH));
      JLabel imageLabelBottom = new JLabel(iconBottom);
      JPanel imagePanel2 = new JPanel();
      imagePanel2.add(imageLabelBottom);
      imagePanel2.setPreferredSize(new Dimension(68, 68));
      imagePanel2.setBackground(Color.decode("#262522"));
      imagePanel2.setLayout(new GridBagLayout());
      bottomPanel.setPreferredSize(new Dimension(808, 70));
      bottomPanel.setBackground(Color.decode("#302e2b"));
      bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

      JPanel namePanel2 = new JPanel();
      namePanel2.setPreferredSize(new Dimension(620, 68));
      namePanel2.setBackground(Color.decode("#302e2b"));
      namePanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

      JLabel nameLabel2 = new JLabel("Ali Ganteng hehe");
      nameLabel2.setFont(poppinsFontBold.deriveFont(20f));
      nameLabel2.setForeground(Color.decode("#f8f8f8"));
      nameLabel2.setPreferredSize(new Dimension(600, 25));

      nameLabel2.setBackground(Color.decode("#302e2b"));
      nameLabel2.setOpaque(true);
      namePanel2.add(nameLabel2);

      JPanel timePanel2 = new JPanel();
      timePanel2.setPreferredSize(new Dimension(119, 68));
      timePanel2.setBackground(Color.decode("#262522"));
      timePanel2.setLayout(new GridBagLayout());
      timePanel2.add(chessBoard.whiteTimer);

      bottomPanel.add(imagePanel2);
      bottomPanel.add(namePanel2);
      bottomPanel.add(timePanel2);

      rightPanel.setPreferredSize(new Dimension(500, 970));
      rightPanel.setBackground(Color.decode("#262522"));
      rightPanel.setLayout(new GridBagLayout());
      JPanel resignPanel = new JPanel();
      resignPanel.setPreferredSize(new Dimension(450, 70));
      resignPanel.setBackground(Color.decode("#262522"));
      resignPanel.setLayout(new GridBagLayout());
      JPanel drawPanel = new JPanel();
      drawPanel.setPreferredSize(new Dimension(450, 70));
      drawPanel.setBackground(Color.decode("#262522"));
      drawPanel.setLayout(new GridBagLayout());

      RoundedButton resignButton = createButton("Resign", 15, 1);
      resignButton.setFont(poppinsFontBold.deriveFont(20f));
      resignButton.setPreferredSize(new Dimension(420, 55));
      resignButton.setBackground(Color.decode("#42413f"));
      resignButton.setForeground(Color.WHITE);
      resignPanel.add(resignButton);
      
      resignButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            chessBoard.askResign(chessBoard.isWhitesTurn);
            
         }
      });

      RoundedButton drawButton = createButton("Draw", 15, 2);
      drawButton.setFont(poppinsFontBold.deriveFont(20f));
      drawButton.setPreferredSize(new Dimension(420, 55));
      drawButton.setBackground(Color.decode("#7ec139"));
      drawButton.setForeground(Color.WHITE);
      drawPanel.add(drawButton);
      
      drawButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            chessBoard.askDraw(chessBoard.isWhitesTurn);
            
         }
      });
      
      JPanel innerPanel = new JPanel();
      innerPanel.setLayout(new FlowLayout());
      innerPanel.setPreferredSize(new Dimension(430, 215));
      innerPanel.setBackground(Color.decode("#262522"));
      
      innerPanel.add(resignPanel);
      innerPanel.add(drawPanel);
      
      rightPanel.add(innerPanel);

      leftPanel.setPreferredSize(new Dimension(820, 970));
      leftPanel.setBackground(Color.decode("#302e2b"));

      panel.setPreferredSize(new Dimension(1400, 970));
      panel.setBackground(Color.decode("#302e2b"));

      this.setTitle("Chess");
      this.setLayout(new GridBagLayout());
      panel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 0));
      leftPanel.add(topPanel);
      leftPanel.add(chessBoard);
      leftPanel.add(bottomPanel);
      panel.add(leftPanel);
      panel.add(rightPanel);
      this.add(panel);
      this.getContentPane().setBackground(Color.decode("#302e2b"));
      this.setSize(new Dimension(1450, 1030));
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setLocationRelativeTo(null);
      this.setVisible(true);
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
               
            }else if (number == 2) {
               
               button.setBackground(Color.decode("#90c05e")); 
            }
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            if (number == 1) {
               button.setBackground(Color.decode("#42413f"));
               
            }else if (number == 2) {
               
               button.setBackground(Color.decode("#7ec139"));
            }
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }
      });

      return button;
   }
   public void reFrame() {
      this.dispose();
      new ChessContainerOffline();
   }

}
