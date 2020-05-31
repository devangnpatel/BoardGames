package boardgames;

import game.InformationPanel;
import checkers.CheckersGame;
import checkers.classic.CheckersClassicGame;
import checkers.warped.CheckersWarpedGame;
import chess.ChessGame;
import chess.chess2D.Chess2DGame;
import chess.real.ChessRealGame;
import game.network.Client;
import game.network.Server;
import game.utility.Properties.PlayerColor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import othello.OthelloGame;
import othello.gui.debugmode.OthelloDebugGame;
import othello.original.OthelloOriginalGame;

/**
 *
 * @author devang
 */
public class GamesWindow implements ActionListener {
    
    public Client client;
    
    private JFrame gameFrame;
    private JMenuBar menuBar;
    
    private JMenu gameMenu;
    private JMenu aiGameMenu;
    private JMenu extraGameMenu;
    private JMenu basicGameMenu;
    
    private JMenuItem chessMenuItem, checkersMenuItem, othelloMenuItem;
    private JMenuItem chessAIMenuItem, checkersAIMenuItem, othelloAIMenuItem;
    private JMenuItem warpedCheckersMenuItem, warpedCheckers1PMenuItem;
    private JMenuItem realChessMenuItem, realChess1PMenuItem;
    private JMenuItem windowsReversiMenuItem, windowsReversi1PMenuItem;
    private JMenuItem chess2DMenuItem,checkersClassicMenuItem,othelloOriginalMenuItem;
    
    private JButton startClientButton;
    private JButton launchServerButton;
    
    private JTextField serverTextField;
    private JTextField portTextField;
    
    private JLabel serverLabel;
    private JLabel portLabel;
            
    private JButton localButton;
    private JButton networkButton;
    
    private JLabel titleLabel;
    private String gameName = "Board Games";
    
    private InformationPanel statusPanel;
    private JPanel titlePanel;
    private JPanel typePanel;
    private JPanel spacerPanel;
    private JPanel networkInfoPanel;
    private JPanel networkButtonsPanel;
    
    public GamesWindow() {
        init();
    }
    
    public void init()
    {
        gameFrame = new JFrame("Board Games");
        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                gameFrame.dispose();
                //System.exit(0);
            }
        });

        menuBar = initializeMenuBar();
        gameFrame.setJMenuBar(menuBar); 
        gameFrame.add(gamePanel());
        int xLocation = (int)Math.floor(Math.random()*500);
        int yLocation = (int)Math.floor(Math.random()*400);
        gameFrame.setLocation(xLocation,yLocation);
        gameFrame.setSize(500, 500); 
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);
    }
    
    public JPanel gamePanel()
    {
        JPanel gamePanel = new JPanel();
        GridLayout gamePanelLayout = new GridLayout(6,1);
        gamePanel.setLayout(gamePanelLayout);

        titlePanel = titlePanel();
        typePanel = typePanel();
        spacerPanel = spacerPanel();
        networkInfoPanel = networkInfoPanel();
        networkButtonsPanel = networkButtonsPanel();
        statusPanel = new InformationPanel();
        
        gamePanel.add(titlePanel);
        gamePanel.add(typePanel);
        gamePanel.add(spacerPanel);
        gamePanel.add(networkInfoPanel);
        gamePanel.add(networkButtonsPanel);
        gamePanel.add(statusPanel);

        startClientButton.setEnabled(false);
        launchServerButton.setEnabled(false);
        serverTextField.setEnabled(false);
        portTextField.setEnabled(false);
        serverLabel.setEnabled(false);
        portLabel.setEnabled(false);
        localButton.setEnabled(false);
        networkButton.setEnabled(false);
        
        return gamePanel;
    }
    
    public JPanel spacerPanel()
    {
        JPanel spacerPanel = new JPanel();
        GridLayout spacerPanelLayout = new GridLayout(3,1);
        spacerPanel.setLayout(spacerPanelLayout);
        JLabel spacerLabelA = new JLabel(" ");
        JLabel spacerLabelB = new JLabel(" ");
        JLabel spacerLabelC = new JLabel(" ");
        spacerLabelA.setVisible(true);
        spacerLabelB.setVisible(true);
        spacerLabelC.setVisible(true);
        spacerPanel.add(spacerLabelA);
        spacerPanel.add(spacerLabelB);
        spacerPanel.add(spacerLabelC);
        spacerPanel.setVisible(true);
        
        return spacerPanel;
    }
    
    public JPanel titlePanel()
    {
        JPanel titlePanel = new JPanel();
        GridLayout titlePanelLayout = new GridLayout(1,1);
        titlePanel.setLayout(titlePanelLayout);
        titleLabel = new JLabel(gameName,SwingConstants.CENTER);
        titleLabel.setVisible(true);
        titlePanel.add(titleLabel);
        titlePanel.setVisible(true);
        
        return titlePanel;
    }
    
    public JPanel typePanel()
    {
        JPanel buttonPanel = new JPanel();
        GridLayout buttonPanelLayout = new GridLayout(1,2);
        buttonPanel.setLayout(buttonPanelLayout);
        localButton = new JButton("Local Game");
        networkButton = new JButton("Network Game");
        localButton.setVisible(true);
        networkButton.setVisible(true);
        buttonPanel.add(localButton);
        buttonPanel.add(networkButton);
        buttonPanel.setVisible(true);
        
        localButton.addActionListener(this);
        networkButton.addActionListener(this);
        
        return buttonPanel;
    }
    
    public JPanel networkInfoPanel()
    {
        JPanel networkInfoPanel = new JPanel();
        GridLayout networkInfoPanelLayout = new GridLayout(2,2);
        networkInfoPanel.setLayout(networkInfoPanelLayout);
        serverLabel = new JLabel("  server:",SwingConstants.LEFT);
        portLabel = new JLabel("  port:",SwingConstants.LEFT);
        serverTextField = new JTextField();
        portTextField = new JTextField();
        serverTextField.setText("127.0.0.1");
        portTextField.setText("8080");
        serverLabel.setVisible(true);
        portLabel.setVisible(true);
        serverTextField.setVisible(true);
        portTextField.setVisible(true);
        networkInfoPanel.add(serverLabel);
        networkInfoPanel.add(portLabel);
        networkInfoPanel.add(serverTextField);
        networkInfoPanel.add(portTextField);
        networkInfoPanel.setVisible(true);

        return networkInfoPanel;
    }
    
    public JPanel networkButtonsPanel()
    {
        JPanel networkButtonsPanel = new JPanel();
        GridLayout networkButtonsPanelLayout = new GridLayout(2,1);
        networkButtonsPanel.setLayout(networkButtonsPanelLayout);
        startClientButton = new JButton("Connect Client");
        launchServerButton = new JButton("Launch Server");
        startClientButton.setVisible(true);
        launchServerButton.setVisible(true);
        networkButtonsPanel.add(startClientButton);
        networkButtonsPanel.add(launchServerButton);
        networkButtonsPanel.setVisible(true);
        
        startClientButton.addActionListener(this);
        launchServerButton.addActionListener(this);
        
        return networkButtonsPanel;
    }
    
    public JMenuBar initializeMenuBar()
    {
        JMenuBar tempMenuBar = new JMenuBar();
        gameMenu = new JMenu("Debug-Mode");
        aiGameMenu = new JMenu("AI-Games");
        extraGameMenu = new JMenu("Extra");
        basicGameMenu = new JMenu("2P-Games");
        
        chess2DMenuItem = new JMenuItem("Basic Chess 2D");
        checkersClassicMenuItem = new JMenuItem("Checkers Classic");
        othelloOriginalMenuItem = new JMenuItem("Original Othello");
        
        chessAIMenuItem = new JMenuItem("1-P Chess Game");
        checkersAIMenuItem = new JMenuItem("1-P Checkers Game");
        othelloAIMenuItem = new JMenuItem("1-P Othello Game");
        
        chessMenuItem = new JMenuItem("Chess"); 
        checkersMenuItem = new JMenuItem("Checkers");
        othelloMenuItem = new JMenuItem("Othello");
        
        warpedCheckersMenuItem = new JMenuItem("Warped Checkers");
        warpedCheckers1PMenuItem = new JMenuItem("1-P Warped Checkers");
        
        realChessMenuItem = new JMenuItem("Real Chess");
        realChess1PMenuItem = new JMenuItem("1-P Real Chess");
        
        windowsReversiMenuItem = new JMenuItem("Windows 3.0 Reversi");
        windowsReversi1PMenuItem = new JMenuItem("1-P Windows 3.0 Reversi");
        
        chess2DMenuItem.addActionListener(this);
        checkersClassicMenuItem.addActionListener(this);
        othelloOriginalMenuItem.addActionListener(this);
        
        chessAIMenuItem.addActionListener(this);
        checkersAIMenuItem.addActionListener(this);
        othelloAIMenuItem.addActionListener(this);
        
        warpedCheckersMenuItem.addActionListener(this);
        warpedCheckers1PMenuItem.addActionListener(this);
        
        realChessMenuItem.addActionListener(this);
        realChess1PMenuItem.addActionListener(this);
        
        windowsReversiMenuItem.addActionListener(this);
        windowsReversi1PMenuItem.addActionListener(this);
        
        chessMenuItem.addActionListener(this);
        checkersMenuItem.addActionListener(this);
        othelloMenuItem.addActionListener(this);
  
        basicGameMenu.add(chess2DMenuItem);
        basicGameMenu.add(checkersClassicMenuItem);
        basicGameMenu.add(othelloOriginalMenuItem);
        
        aiGameMenu.add(chessAIMenuItem);
        aiGameMenu.add(checkersAIMenuItem);
        aiGameMenu.add(othelloAIMenuItem);
        
        gameMenu.add(chessMenuItem); 
        gameMenu.add(checkersMenuItem);
        gameMenu.add(othelloMenuItem);
        
        extraGameMenu.add(warpedCheckersMenuItem);
        extraGameMenu.add(warpedCheckers1PMenuItem);
        extraGameMenu.add(realChessMenuItem);
        extraGameMenu.add(realChess1PMenuItem);
        extraGameMenu.add(windowsReversiMenuItem);
        extraGameMenu.add(windowsReversi1PMenuItem);

        tempMenuBar.add(basicGameMenu);
        tempMenuBar.add(gameMenu);
        tempMenuBar.add(aiGameMenu);
        tempMenuBar.add(extraGameMenu);
        
        return tempMenuBar;
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        
        if (s.equals("Basic Chess 2D"))
        {
            gameName = "Basic Chess 2D";
            titleLabel.setText(gameName);
            // titleLabel.repaint();
            
            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            localButton.setEnabled(true);
            networkButton.setEnabled(true);
            
            gameFrame.repaint();
        }
        
        if (s.equals("Checkers Classic"))
        {
            gameName = "Checkers Classic";
            titleLabel.setText(gameName);
            // titleLabel.repaint();
            
            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            localButton.setEnabled(true);
            networkButton.setEnabled(true);
            
            gameFrame.repaint();
        }
        
        
        if (s.equals("Original Othello"))
        {
            gameName = "Original Othello";
            titleLabel.setText(gameName);
            // titleLabel.repaint();
            
            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            localButton.setEnabled(true);
            networkButton.setEnabled(true);
            
            gameFrame.repaint();
        }
        
        if (s.equals("Checkers"))
        {
            gameName = "Checkers";
            titleLabel.setText(gameName);
            // titleLabel.repaint();
            
            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            localButton.setEnabled(true);
            networkButton.setEnabled(true);
            
            gameFrame.repaint();
        }
        
        if (s.equals("Chess"))
        {
            gameName = "Chess";
            titleLabel.setText(gameName);
            // titleLabel.repaint();
            
            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            localButton.setEnabled(true);
            networkButton.setEnabled(true);
            
            gameFrame.repaint();
        }
        
        if (s.equals("Othello"))
        {
            gameName = "Othello";
            titleLabel.setText(gameName);
            // titleLabel.repaint();
            
            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            localButton.setEnabled(true);
            networkButton.setEnabled(true);
            
            gameFrame.repaint();
        }
        
        if (s.equals("Warped Checkers"))
        {
            CheckersWarpedGame.newCheckersWarpedLocalGame(); 
            /*gameName = "Warped Checkers";
            titleLabel.setText(gameName);
            // titleLabel.repaint();
            
            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            localButton.setEnabled(true);
            networkButton.setEnabled(true);
            
            gameFrame.repaint();*/
        }
        
        if (s.equals("Real Chess"))
        {
            ChessRealGame.newRealChessLocalGame();
            /*gameName = "Real Chess";
            titleLabel.setText(gameName);
            // titleLabel.repaint();
            
            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            localButton.setEnabled(true);
            networkButton.setEnabled(true);
            
            gameFrame.repaint();*/
        }
        
        if (s.equals("Windows 3.0 Reversi"))
        {
            OthelloGame.newOthelloLocalGame();
            /* gameName = "Windows 3.0 Reversi";
            titleLabel.setText(gameName);
            // titleLabel.repaint();
            
            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            localButton.setEnabled(true);
            networkButton.setEnabled(true);
            
            gameFrame.repaint();*/
        }
        
        if (s.equals("1-P Chess Game"))
        {
            ChessGame.newChessAIGame();
        }
       
        if (s.equals("1-P Checkers Game"))
        {
            CheckersGame.newCheckersAIGame();
        }
               
        if (s.equals("1-P Othello Game"))
        {
            OthelloDebugGame.newOthelloDebugAIGame();
        }
                    
        if (s.equals("1-P Warped Checkers"))
        {
            CheckersWarpedGame.newCheckersWarpedAIGame();
        }
        
        if (s.equals("1-P Real Chess"))
        {
            ChessRealGame.newRealChessAIGame();
        }
             
        if (s.equals("1-P Windows 3.0 Reversi"))
        {
            OthelloGame.newOthelloAIGame();
        }
        
        if (s.equals("Connect Client"))
        {
            client = new Client(this);
            String server = serverTextField.getText();
            int port = Integer.valueOf(portTextField.getText());
            
            client.startClient(server,port);
            
            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            localButton.setEnabled(false);
            networkButton.setEnabled(false);
            
            gameFrame.repaint();
        }
        
        if (s.equals("Launch Server"))
        {
            new Server(gameName);
            
            startClientButton.setEnabled(true);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(true);
            portTextField.setEnabled(true);
            serverLabel.setEnabled(true);
            portLabel.setEnabled(true);
            localButton.setEnabled(false);
            networkButton.setEnabled(false);
            
            gameFrame.repaint();

        }
        
        if (s.equals("Local Game"))
        {
            if (gameName.equalsIgnoreCase("Checkers"))
                CheckersGame.newCheckersLocalGame(); // new CheckersGame();
            
            else if (gameName.equalsIgnoreCase("Warped Checkers"))
                CheckersWarpedGame.newCheckersWarpedLocalGame(); // new CheckersWarpedGame();
            
            else if (gameName.equalsIgnoreCase("Real Chess"))
                ChessRealGame.newRealChessLocalGame(); // new ChessRealGame();
            
            else if (gameName.equalsIgnoreCase("Windows 3.0 Reversi"))
                OthelloGame.newOthelloLocalGame();
            
            else if (gameName.equalsIgnoreCase("Chess"))
                ChessGame.newChessLocalGame(); //new ChessGame();
            
            else if (gameName.equalsIgnoreCase("Othello"))
                OthelloDebugGame.newOthelloDebugLocalGame(); // new OthelloGame();
            
            else if (gameName.equalsIgnoreCase("Basic Chess 2D"))
                Chess2DGame.new2DChessLocalGame();
            
            else if (gameName.equalsIgnoreCase("Checkers Classic"))
                CheckersClassicGame.newClassicCheckersLocalGame();
            
            else if (gameName.equalsIgnoreCase("Original Othello"))
                OthelloOriginalGame.newOthelloOriginalLocalGame();

            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            localButton.setEnabled(false);
            networkButton.setEnabled(false);

            gameName = "Board Games";
            titleLabel.setText(gameName);
            gameFrame.repaint();            
        }
        
        if (s.equals("Network Game"))
        {
            // new CheckersGame();
            // new ChessGame();
            // new OthelloGame();

            startClientButton.setEnabled(true);
            launchServerButton.setEnabled(true);
            serverTextField.setEnabled(true);
            portTextField.setEnabled(true);
            serverLabel.setEnabled(true);
            portLabel.setEnabled(true);
            localButton.setEnabled(false);
            networkButton.setEnabled(false);

            //gameName = "Board Games";
            //titleLabel.setText(gameName);
            gameFrame.repaint();
        }
    }
    
    public void clientConnectedMessage()
    {
        statusPanel.setInformationText("Connected to Server . . . waiting for opponent");
    }
    
    public void gameStartedMessage(PlayerColor color)
    {
        if (gameName.equalsIgnoreCase("Checkers"))
        {
            CheckersGame.newCheckersNetworkGame(color,client); // new CheckersGame(color,client);
        }

        else if (gameName.equalsIgnoreCase("Chess"))
        {
            ChessGame.newChessNetworkGame(color,client); // new ChessGame(color,client);
        }

        else if (gameName.equalsIgnoreCase("Othello"))
        {
            OthelloDebugGame.newOthelloDebugNetworkGame(color,client); // new OthelloGame(color,client);
        }
        
        else if (gameName.equalsIgnoreCase("Basic Chess 2D"))
        {
            Chess2DGame.new2DChessNetworkGame(color,client);
        }

        else if (gameName.equalsIgnoreCase("Checkers Classic"))
        {
            CheckersClassicGame.newClassicCheckersNetworkGame(color,client);
        }

        else if (gameName.equalsIgnoreCase("Original Othello"))
        {
            OthelloOriginalGame.newOthelloOriginalNetworkGame(color,client);
        }
        
        client = null;

        startClientButton.setEnabled(false);
        launchServerButton.setEnabled(false);
        serverTextField.setEnabled(false);
        portTextField.setEnabled(false);
        serverLabel.setEnabled(false);
        portLabel.setEnabled(false);
        localButton.setEnabled(false);
        networkButton.setEnabled(false);

        statusPanel.setInformationText("");
        gameName = "Board Games";
        titleLabel.setText(gameName);
        gameFrame.repaint();
    }
}
