/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderingsystem.actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import orderingsystem.MenuPanel;
import orderingsystem.OrderPanel;

/**
 *
 * @author escob
 */
public class clearBtnListener implements ActionListener{
    private OrderPanel orderPanel;
    
    public clearBtnListener(OrderPanel orderPanel) {
        this.orderPanel = orderPanel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        orderPanel.resetOrder(true);
    }
    
}
