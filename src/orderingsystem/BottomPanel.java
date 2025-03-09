/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderingsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import orderingsystem.actionListener.addBtnListener;
import orderingsystem.actionListener.checkOutBtnListener;
import orderingsystem.actionListener.clearBtnListener;
import orderingsystem.actionListener.removeBtnListener;

/**
 *
 * @author escob
 */
public class BottomPanel extends JPanel{
    private MenuPanel menuPanel;
    private OrderPanel orderPanel;
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        
        JButton addBtn = new JButton("Add");
        JButton removeBtn = new JButton("Remove");
        JButton checkOutBtn = new JButton("Check Out");
        JButton clearBtn = new JButton("Clear");
        
        Dimension buttonSize = new Dimension(120, 40);
        
        addBtn.setPreferredSize(buttonSize);
        removeBtn.setPreferredSize(buttonSize);
        checkOutBtn.setPreferredSize(buttonSize);
        clearBtn.setPreferredSize(buttonSize);
        
        addBtn.setForeground(Color.WHITE);
        removeBtn.setForeground(Color.WHITE);
        checkOutBtn.setForeground(Color.WHITE);
        clearBtn.setForeground(Color.WHITE);
        
        addBtn.setBackground(new Color(0, 153, 0));
        removeBtn.setBackground(new Color(0, 153, 0));
        checkOutBtn.setBackground(new Color(0, 153, 0));
        clearBtn.setBackground(new Color(0, 153, 0));
        
        addBtn.setFont(new Font("Arial", Font.BOLD, 16));
        removeBtn.setFont(new Font("Arial", Font.BOLD, 16));
        checkOutBtn.setFont(new Font("Arial", Font.BOLD, 16));
        clearBtn.setFont(new Font("Arial", Font.BOLD, 16));
        
        addBtn.addActionListener(new addBtnListener(menuPanel, orderPanel));
        removeBtn.addActionListener(new removeBtnListener(menuPanel, orderPanel));
        checkOutBtn.addActionListener(new checkOutBtnListener(menuPanel, orderPanel));
        clearBtn.addActionListener(new clearBtnListener(orderPanel));
        
        panel.add(addBtn);
        panel.add(removeBtn);
        panel.add(checkOutBtn);
        panel.add(clearBtn);
        
        return panel;
    }
    
    public BottomPanel(MenuPanel menuPanel, OrderPanel orderPanel) {
        this.menuPanel = menuPanel;
        this.orderPanel = orderPanel;
        setLayout(new BorderLayout());
        add(createButtonPanel(), BorderLayout.CENTER);
    }
}
