package game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * not really used yet
 * - would display a window holding a label with text that can be easily updated
 *   and a button that would close the window
 * @author devang
 */
public class InformationWindow extends JFrame implements ActionListener {
    private String labelText;
    private String buttonText;
    private JButton windowButton;
    private JLabel windowLabel;
    
    public InformationWindow(String text)
    {
        super(text);
        
        labelText = text;
        buttonText = "    close    ";
        
        JPanel informationPanel = new JPanel();
        GridLayout informationPanelLayout = new GridLayout(2,1);
        informationPanel.setLayout(informationPanelLayout);
        
        windowLabel = new JLabel(labelText,SwingConstants.CENTER);
        windowButton = new JButton(buttonText);
        windowLabel.setVisible(true);
        windowButton.setVisible(true);
        informationPanel.add(windowLabel);
        informationPanel.add(windowButton);
        informationPanel.setVisible(true);
        
        windowButton.addActionListener(this);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                dispose();
            }
        });
        setLocation(300,300);
        add(informationPanel);
        pack();
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        
        if (s.equals("    close    "))
        {
            dispose();
        }
    }
}
