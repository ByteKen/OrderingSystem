/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderingsystem.actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import orderingsystem.MenuPanel;

/**
 *
 * @author escob
 */
public class MenuItemListener implements ActionListener {
    private MenuPanel menuPanel;
    
    public MenuItemListener(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       JButton source = (JButton) e.getSource();
       menuPanel.setSelectedButton(source);
    }
}
