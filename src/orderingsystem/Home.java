/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderingsystem;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

/**
 *
 * @author escob
 */
public class Home extends JFrame {
    
    private void createComponents() {
       MenuPanel menuPanel = new MenuPanel();
       OrderPanel orderPanel = new OrderPanel(menuPanel);
       BottomPanel bottomPanel = new BottomPanel(menuPanel, orderPanel);
       
       JScrollPane menuScroll = new JScrollPane(menuPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       
       add(menuScroll, BorderLayout.CENTER);
       add(orderPanel, BorderLayout.EAST);
       add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public Home() {
        setTitle("Restaurant Ordering System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        
        createComponents();
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Fallback to default look and feel if Nimbus is unavailable
        }
        
        new Home();
    }
    
}
