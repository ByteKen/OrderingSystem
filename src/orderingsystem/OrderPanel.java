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
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 *
 * @author escob
 */
public class OrderPanel extends JPanel{
    private MenuPanel menuPanel;
    private JLabel tableNumberLabel, totalLabel;
    public JTextArea orderArea;
    public int tableNumber;
    public double total = 0.0;
    
    public void assignTableNumber() {
         Random random = new Random();
         tableNumber = random.nextInt(20) + 1;
    }
    
    private JPanel createOrderPanel() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Order");
        titledBorder.setTitleColor(Color.WHITE);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(titledBorder);
        panel.setPreferredSize(new Dimension(435, 0));
        panel.setBackground(new Color(0, 153, 0));
        
        tableNumberLabel = new JLabel("Table #" + tableNumber, SwingConstants.CENTER);
        totalLabel = new JLabel("Total: ₱0.00", SwingConstants.CENTER);
        
        orderArea = new JTextArea(10,20);
        orderArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(orderArea);
        
        tableNumberLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        tableNumberLabel.setForeground(Color.WHITE);
        totalLabel.setForeground(Color.WHITE);
        
        panel.add(tableNumberLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(totalLabel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    public void appendToOrderArea(String text) {
        orderArea.append(text);
    }
    
    public void removeOneItemFromOrderArea(String itemName) {
        String currentText = orderArea.getText();
        String[] lines = currentText.split("\n");
        boolean updated = false;
        StringBuilder newText = new StringBuilder();

    // Loop through each line of the order area text
        for (String line : lines) {
        // Look for the line that starts with the given item name and hasn't been updated yet.
        if (!updated && line.trim().startsWith(itemName)) {
            // Expect the line format: "ItemName x<quantity> - ₱<totalPrice>"
            // Find the position of "x" and " -"
            int xIndex = line.indexOf("x");
            int dashIndex = line.indexOf(" -", xIndex);
            if (xIndex != -1 && dashIndex != -1) {
                String qtyString = line.substring(xIndex + 1, dashIndex).trim();
                try {
                    int qty = Integer.parseInt(qtyString);
                    int newQty = qty - 1;  // decrement the quantity by 1
                    // Only update if newQty is >= 0. If newQty is 0, we remove the line.
                    if (newQty > 0) {
                        // Get the unit price from menuPanel for this item.
                        double price = 0.0;
                        if (menuPanel.getMenuItemPrices().containsKey(itemName)) {
                            price = menuPanel.getMenuItemPrices().get(itemName);
                        }
                        // Create an updated line with the new quantity and new total for that item.
                        String updatedLine = itemName + " x" + newQty + " - ₱" + String.format("%.2f", price * newQty);
                        newText.append(updatedLine).append("\n");
                    }
                    // Mark as updated so we don't process any further lines matching this item.
                    updated = true;
                    continue;
                } catch (NumberFormatException ex) {
                    // If parsing fails, we simply append the original line.
                }
            }
        }
        // For all other lines or if this line is not updated, add it unchanged.
        newText.append(line).append("\n");
    }
    // Set the modified text back to the orderArea.
    orderArea.setText(newText.toString());
}
    
     public void updateTotal() {
        totalLabel.setText("Total: ₱" + String.format("%.2f", total));
    }
     
     public void resetOrder(boolean newTable) {
        orderArea.setText("");
        total = 0.0;
        if (newTable) {
            menuPanel.resetQuantityItem();
            assignTableNumber();
            tableNumberLabel.setText("Table #" + tableNumber);
        }
        updateTotal();
    }

    
    public OrderPanel(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
        setLayout(new BorderLayout());
        assignTableNumber();
        
        add(createOrderPanel(), BorderLayout.CENTER);

    }
}
