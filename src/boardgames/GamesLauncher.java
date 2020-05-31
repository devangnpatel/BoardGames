package boardgames;

import checkers.warped.CheckersWarpedGame;
import chess.chess2D.Chess2DGame;
import chess.real.ChessRealGame;
import com.sun.media.jfxmedia.logging.Logger;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import othello.OthelloGame;

/**
 *
 * @author devang
 */
public class GamesLauncher {
    private static int[] close_top_left = {567,8};
    private static int[] close_bot_right = {591,34};
    private static int[] technical_top_left = {390,315};
    private static int[] technical_bot_right = {594,342};
    private static int[] chess_real_top_left = {236,54};
    private static int[] chess_real_bot_right = {379,84};
    private static int[] reversi_top_left = {251,202};
    private static int[] reversi_bot_right = {385,254};
    private static int[] checkers_warped_top_left = {191,152};
    private static int[] checkers_warped_bot_right = {446,188};
    private static int[] chess_2P_top_left = {333,98};
    private static int[] chess_2P_bot_right = {468,130};
    private static int[] chess_internet_top_left = {450,68};
    private static int[] chess_internet_bot_right = {560,90};
    
    public class GamesLauncherCanvas extends Canvas implements MouseListener {
        public GamesLauncherCanvas()
        {
            init();
        }
        
        BufferedImage splashScreen = null;
        
        private void init()
        {
            // String filePath = "gamesImages/splash_screen.png";
            String filePath = "gamesImages/splash_screen_2.png";
            
            try {
                splashScreen = ImageIO.read(getClass().getClassLoader().getResource(filePath));
            } catch (IOException e) {
                Logger.logMsg(0, "Error loading splash screen: " + filePath);
            }

            setSize(600,350);
            setBackground(Color.BLACK);
            setVisible(true);
            addMouseListener(this);
        }

        @Override
        public void paint(Graphics g)
        {
            g.drawImage(splashScreen,0,0,null);
        }    

        @Override
        public void mouseClicked(MouseEvent e) {
            // unused
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int xCoord = e.getX();
            int yCoord = e.getY();
            
            // close button
            if ((xCoord > close_top_left[0]) && (xCoord < close_bot_right[0]) &&
                (yCoord > close_top_left[1]) && (yCoord < close_bot_right[1]))
            {
                System.exit(0);
            }
           
            // technical mode
            if ((xCoord > technical_top_left[0]) && (xCoord < technical_bot_right[0]) &&
                (yCoord > technical_top_left[1]) && (yCoord < technical_bot_right[1]))
            {
                new GamesWindow();
            }
            
            // reversi
            if ((xCoord > reversi_top_left[0]) && (xCoord < reversi_bot_right[0]) &&
                (yCoord > reversi_top_left[1]) && (yCoord < reversi_bot_right[1]))
            {
                OthelloGame.newOthelloAIGame();
            }
            
            // checkers warped
            if ((xCoord > checkers_warped_top_left[0]) && (xCoord < checkers_warped_bot_right[0]) &&
                (yCoord > checkers_warped_top_left[1]) && (yCoord < checkers_warped_bot_right[1]))
            {
                CheckersWarpedGame.newCheckersWarpedAIGame();
            }
            
            // chess real
            if ((xCoord > chess_real_top_left[0]) && (xCoord < chess_real_bot_right[0]) &&
                (yCoord > chess_real_top_left[1]) && (yCoord < chess_real_bot_right[1]))
            {
                ChessRealGame.newRealChessAIGame();
            }
            
            // chess 2P
            if ((xCoord > chess_2P_top_left[0]) && (xCoord < chess_2P_bot_right[0]) &&
                (yCoord > chess_2P_top_left[1]) && (yCoord < chess_2P_bot_right[1]))
            {
                Chess2DGame.new2DChessLocalGame();
            }
            
            // chess internet
            if ((xCoord > chess_internet_top_left[0]) && (xCoord < chess_internet_bot_right[0]) &&
                (yCoord > chess_internet_top_left[1]) && (yCoord < chess_internet_bot_right[1]))
            {
                new NetworkChessWindow();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // unused
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // unused
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // unused
        }

    }
    
    public Frame frame;
    public GamesLauncherCanvas canvas;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        new GamesLauncher();
    }
    
    public GamesLauncher()
    {        
        canvas = new GamesLauncherCanvas();
        frame = new Frame("devang patel  -  board games  -  2020");
        frame.setIgnoreRepaint(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        int xLocation = 50 + (int)Math.floor(Math.random()*600);
        int yLocation = 100 + (int)Math.floor(Math.random()*500);
        frame.setLocation(xLocation,yLocation);
        frame.setResizable(false);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        
    }
    
    /* old version that shows a list of instructions instead of a splash screen
    public GamesLauncher()
    {
        JFrame frame = new JFrame("            games launcher            ");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        // ImageIcon instructionsImage = new ImageIcon("/Users/devang/Desktop/gamesImages/gamesInstructions.jpg");
        ImageIcon instructionsImage = new ImageIcon(getClass().getClassLoader().getResource("gamesImages/gamesInstructions.jpg"));
        JLabel instructionsLabel = new JLabel(instructionsImage);
        panel.add(instructionsLabel);

        JPanel buttonPanel = new JPanel();
        GridLayout buttonPanelLayout = new GridLayout(1,2);
        buttonPanel.setLayout(buttonPanelLayout);
        JButton launchButton = new JButton("Board Games"); // ("\'Board Games\'")
        JButton closeButton = new JButton("close");
        instructionsLabel.setVisible(true);
        launchButton.setVisible(true);
        closeButton.setVisible(true);
        buttonPanel.add(launchButton);
        buttonPanel.add(closeButton);
        buttonPanel.setVisible(true);
        panel.add(instructionsLabel);
        panel.add(buttonPanel);
        panel.setVisible(true);
        frame.add(panel);
        

        launchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                new GamesWindow();
            }});
        
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }});
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(150,0);
        frame.setSize(615,415);
        frame.setResizable(false);
        frame.setVisible(true); 
    }*/

}
