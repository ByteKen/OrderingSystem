/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderingsystem.actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import orderingsystem.MenuPanel;
import orderingsystem.OrderPanel;

/**
 *
 * @author escob
 */
public class clearBtnListener implements ActionListener{
    private MenuPanel menuPanel;
    private OrderPanel orderPanel;
    
    public clearBtnListener(MenuPanel menuPanel, OrderPanel orderPanel) {
        this.menuPanel = menuPanel;
        this.orderPanel = orderPanel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(
             menuPanel,
            "Do you want to clear the entire transaction?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION
        );
        if (response == JOptionPane.YES_OPTION) {
            orderPanel.resetOrder(true);
        }
    }
    
}
