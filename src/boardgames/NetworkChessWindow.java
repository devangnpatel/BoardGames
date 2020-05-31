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
public class NetworkChessWindow extends GamesWindow implements ActionListener {
    
    // public Client client;
    
    private JFrame gameFrame;
    private JTextField serverTextField;
    private JTextField portTextField;
    
    private JButton startClientButton;
    private JButton launchServerButton;
    
    private JLabel serverLabel;
    private JLabel portLabel;
                
    private JLabel titleLabel;
    private String gameName = "Chess over the Internet";
    
    private InformationPanel statusPanel;
    /*private JPanel titlePanel;
    private JPanel typePanel;
    private JPanel spacerPanel;*/
    private JPanel networkInfoPanel;
    private JPanel networkButtonsPanel;
    
    public NetworkChessWindow() {
        super();
    }
    
    @Override
    public void init()
    {
        gameFrame = new JFrame("Network Chess");
        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                gameFrame.dispose();
                //System.exit(0);
            }
        });

        gameFrame.add(gamePanel());
        int xLocation = (int)Math.floor(Math.random()*500);
        int yLocation = (int)Math.floor(Math.random()*400);
        gameFrame.setLocation(xLocation,yLocation);
        gameFrame.setSize(320, 180); 
        gameFrame.setResizable(false);
        //gameFrame.pack();
        gameFrame.setVisible(true);
    }
    
    @Override
    public JPanel gamePanel()
    {
        JPanel gamePanel = new JPanel();
        GridLayout gamePanelLayout = new GridLayout(3,1);
        gamePanel.setLayout(gamePanelLayout);

        networkInfoPanel = networkInfoPanel();
        networkButtonsPanel = networkButtonsPanel();
        statusPanel = new InformationPanel();
        
        /*gamePanel.add(titlePanel);
        gamePanel.add(typePanel);
        gamePanel.add(spacerPanel);*/
        gamePanel.add(networkInfoPanel);
        gamePanel.add(networkButtonsPanel);
        gamePanel.add(statusPanel);

        startClientButton.setEnabled(true);
        launchServerButton.setEnabled(true);
        serverTextField.setEnabled(true);
        portTextField.setEnabled(true);
        serverLabel.setEnabled(true);
        portLabel.setEnabled(true);
        
        return gamePanel;
    }
    
    /*public JPanel spacerPanel()
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
        buttonPanel.setVisible(true);
        
        return buttonPanel;
    }*/
    
    @Override
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
    
    @Override
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

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
                
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
            
            gameFrame.repaint();

        }

    }
    
    @Override
    public void clientConnectedMessage()
    {
        statusPanel.setInformationText("Connected to Server . . . waiting for opponent");
    }
    
    @Override
    public void gameStartedMessage(PlayerColor color)
    {
        statusPanel.setInformationText("game started . . . good luck!");
        Chess2DGame.new2DChessNetworkGame(color, client);
    }
}
