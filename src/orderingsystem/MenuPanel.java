/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderingsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import orderingsystem.actionListener.MenuItemListener;

/**
 *
 * @author escob
 */
public class MenuPanel extends JPanel{
    private HashMap<String, Double> menuItemPrices;
    private List<JButton> menuButtons;
    private JButton selectedButton;
    
    private ImageIcon createImageIcon(String path, int width, int height) {
        ImageIcon orignalIcon = new ImageIcon(path);
        Image scaledImage = orignalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
    
    private JPanel createMenu() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Menu");
        titledBorder.setTitleColor(Color.WHITE);
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(titledBorder);
        panel.setBackground(new Color(0, 153, 0));
        
        panel.setPreferredSize(new Dimension(450, 600));
        
         String[] items = {
             "Hawaiian Overload Pizzawrap", 
             "Hawaiian Overload Pizzawrap Value Meal", 
             "Hawaiian Overload Pizzawrap Pack of Three", 
             "Assorted Pizzawrap Pack of Three A", 
             "Roast Beef & Cream Cheese Pizzawrap", 
             "Roast Beef & Cream Cheese Pizzawrap Value Meal", 
             "Roast Beef & Cream Cheese Pizzawrap Pack of Three", 
             "Assorted Pizzawrap Pack of Three B", 
             "Garlic & Cheese Classic",
             "Choco Tiramisu Frozen Cake", 
             "Creamy Caramel Frozen Cake",
             "Classic Choco Frozen Cake"
         };
        
        for (String item : items) {
            double price = menuItemPrices.get(item);
            String buttonText = "<html><div style='width:200px;; text-align:left;'>"
                    + item + "<br/>"
                    + "<span style='color:green; font-weight:bold;'>₱"
                    + String.format("%.2f", price) + " only</span>"
                    + "</div></html>";
            
            JButton btn = new JButton(buttonText);
            btn.setIcon(createImageIcon("src/Assets/images/" + item.toLowerCase() + ".png", 270, 170));
            btn.addActionListener(new MenuItemListener(this));
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn.setMargin(new Insets(5, 5, 5, 5));
            btn.setActionCommand(item);
            btn.setFocusPainted(false);
            
            Dimension fixedSize = new Dimension(280, 240);
            btn.setPreferredSize(fixedSize);
            btn.setMinimumSize(fixedSize);
            btn.setMaximumSize(fixedSize);
            
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            
            btn.putClientProperty("quantity", 0);
            
            menuButtons.add(btn);
            panel.add(btn);
        }
        return panel;
    }
    
    public void setSelectedButton(JButton button) {
        // Optionally, unhighlight the previously selected button.
        if (selectedButton != null) {
            // Remove the highlight (or reset the border).
            selectedButton.setBorder(BorderFactory.createEmptyBorder());
        }
        
        selectedButton = button;
        
        Integer quantity = (Integer) selectedButton.getClientProperty("quantity");
        // Highlight the new selection (for example, with a blue border).
        if (quantity > 0) {
            selectedButton.setBorder(BorderFactory.createTitledBorder("Quantity: " + quantity));
        } else {
            selectedButton.setBorder(BorderFactory.createTitledBorder("Selected"));
        }
    }
    
    // Getter for the selected button.
    public JButton getSelectedButton() {
        return selectedButton;
    }
    
    public void resetQuantityItem() {
        for (JButton btn : menuButtons) {
            btn.putClientProperty("quantity", 0);
            btn.setBorder(BorderFactory.createEmptyBorder());
        }
    }
    
    public Map<String, Double> getMenuItemPrices() {
        return Collections.unmodifiableMap(menuItemPrices);
    }
    
    public MenuPanel() {
        menuItemPrices = new HashMap<>();
        menuButtons = new ArrayList<>();
        
        menuItemPrices.put("Hawaiian Overload Pizzawrap", 77.00);
        menuItemPrices.put("Hawaiian Overload Pizzawrap Value Meal", 122.00);
        menuItemPrices.put("Hawaiian Overload Pizzawrap Pack of Three", 223.00);
        menuItemPrices.put("Assorted Pizzawrap Pack of Three A", 223.00);
        menuItemPrices.put("Roast Beef & Cream Cheese Pizzawrap", 111.00);
        menuItemPrices.put("Roast Beef & Cream Cheese Pizzawrap Value Meal", 156.00);
        menuItemPrices.put("Roast Beef & Cream Cheese Pizzawrap Pack of Three", 312.00);
        menuItemPrices.put("Assorted Pizzawrap Pack of Three B", 312.00);
        menuItemPrices.put("Garlic & Cheese Classic", 140.00);
        menuItemPrices.put("Choco Tiramisu Frozen Cake", 62.00);
        menuItemPrices.put("Creamy Caramel Frozen Cake", 62.00);
         menuItemPrices.put("Classic Choco Frozen Cake", 62.00);
        
        setLayout(new BorderLayout());
        add(createMenu(), BorderLayout.CENTER);
    }

    
}
