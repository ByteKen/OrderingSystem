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
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import orderingsystem.MenuPanel;
import orderingsystem.OrderPanel;

/**
 *
 * @author escob
 */
public class addBtnListener implements ActionListener {
    private MenuPanel menuPanel;
    private OrderPanel orderPanel;
    
      public addBtnListener(MenuPanel menuPanel, OrderPanel orderPanel) {
        this.menuPanel = menuPanel;
        this.orderPanel = orderPanel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton selectedButton = menuPanel.getSelectedButton();
        if (selectedButton != null) {
            // Prompt the user to enter a quantity for this item.
            String input = JOptionPane.showInputDialog(
                    menuPanel,
                    "Enter quantity for " + selectedButton.getActionCommand() + ":",
                    "Quantity",
                    JOptionPane.QUESTION_MESSAGE);
            
            // If user cancels or enters nothing, exit.
            if (input == null || input.trim().isEmpty()) {
                return;
            }
            
            int qty;
            try {
                qty = Integer.parseInt(input.trim());
                if (qty <= 0) {
                    JOptionPane.showMessageDialog(menuPanel, 
                        "Quantity must be a positive number.", 
                        "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(menuPanel, 
                        "Please enter a valid integer for quantity.", 
                        "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Retrieve current quantity from the button's client property.
            Integer currentQty = (Integer) selectedButton.getClientProperty("quantity");
            if (currentQty == null) {
                currentQty = 0;
            }
            
            // Compute the new cumulative quantity.
            int cumulativeQty = currentQty + qty;
            // If cumulative quantity would exceed 1000, cap it.
            if (cumulativeQty > 1000) {
                cumulativeQty = 1000;
                // Calculate the actual added quantity
                int addedQty = cumulativeQty - currentQty;
                JOptionPane.showMessageDialog(menuPanel, 
                        "Maximum quantity (1000) reached for this item. Only " + addedQty + " unit(s) were added.\nAdvice: Please start another transaction.",
                        "Maximum quantity has reached!", 
                        JOptionPane.INFORMATION_MESSAGE);
                qty = addedQty; // Adjust qty to the number that could be added.
            }
            
            // Store the updated (cumulative) quantity.
            selectedButton.putClientProperty("quantity", cumulativeQty);
            // Update the button's border to display the cumulative quantity.
            selectedButton.setBorder(BorderFactory.createTitledBorder("Quantity: " + cumulativeQty));
            
            // Get the item name and its unit price.
            String itemName = selectedButton.getActionCommand();
            double price = menuPanel.getMenuItemPrices().get(itemName);
            
            // Build the new text line for the item using the cumulative quantity.
            String newTextLine;
            if (itemName.contains("Buy One Take One")) {
                String size = itemName.split(" - ")[0];
                newTextLine = size + " (" + (2 * cumulativeQty) + " pcs) - ₱" 
                        + String.format("%.2f", price * cumulativeQty);
            } else if (itemName.contains("Buy One Take Two")) {
                String item = itemName.split(" - ")[0];
                newTextLine = item + " (" + (3 * cumulativeQty) + " pcs) - ₱" 
                        + String.format("%.2f", price * cumulativeQty);
            } else {
                newTextLine = itemName + " x" + cumulativeQty + " - ₱" 
                        + String.format("%.2f", price * cumulativeQty);
            }
            
            // Retrieve the current order area text.
            String currentOrder = orderPanel.orderArea.getText();
            String[] lines = currentOrder.split("\n");
            boolean found = false;
            StringBuilder updatedOrder = new StringBuilder();
            
            // Loop over each line to see if the item already exists.
            for (String line : lines) {
                // Assume each order line for this item starts with its name.
                if (!found && line.trim().startsWith(itemName)) {
                    // Replace the existing line with newTextLine.
                    updatedOrder.append(newTextLine).append("\n");
                    found = true;
                } else {
                    if (!line.trim().isEmpty()) {
                        updatedOrder.append(line).append("\n");
                    }
                }
            }
            // If no existing line was found, append the new line.
            if (!found) {
                updatedOrder.append(newTextLine).append("\n");
            }
            
            // Update the order text area with the modified text.
            orderPanel.orderArea.setText(updatedOrder.toString());
            
            // Update the overall order total.
            // IMPORTANT: Only add the price for the actual number of units added.
            orderPanel.total += price * qty;
            orderPanel.updateTotal();
        }
    }
}