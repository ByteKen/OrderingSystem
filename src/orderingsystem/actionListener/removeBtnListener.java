package orderingsystem.actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import orderingsystem.MenuPanel;
import orderingsystem.OrderPanel;

public class removeBtnListener implements ActionListener {
    private MenuPanel menuPanel;
    private OrderPanel orderPanel;
    
    public removeBtnListener(MenuPanel menuPanel, OrderPanel orderPanel) {
        this.menuPanel = menuPanel;
        this.orderPanel = orderPanel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       JButton selectedButton = menuPanel.getSelectedButton();
     if (selectedButton != null) {
            // Prompt the user to enter the removal quantity
            String input = JOptionPane.showInputDialog(
                    menuPanel,
                    "Enter quantity to remove for " + selectedButton.getActionCommand() + ":",
                    "Remove Quantity",
                    JOptionPane.QUESTION_MESSAGE);
            
            if (input == null || input.trim().isEmpty()) {
                return;
            }
            
            int removalQty;
            try {
                removalQty = Integer.parseInt(input.trim());
                if (removalQty <= 0) {
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
            Integer currentQuantity = (Integer) selectedButton.getClientProperty("quantity");
            if (currentQuantity == null) {
                currentQuantity = 0;
            }
            
            // If removalQty exceeds currentQuantity, set removalQty to currentQuantity.
            if (removalQty > currentQuantity) {
                removalQty = currentQuantity;
            }
            
            int newQuantity = currentQuantity - removalQty;
            selectedButton.putClientProperty("quantity", newQuantity);
            selectedButton.setBorder(BorderFactory.createTitledBorder("Quantity: " + newQuantity));
            
            String itemName = selectedButton.getActionCommand();
            
            // Check for a valid price.
            Double priceObj = menuPanel.getMenuItemPrices().get(itemName);
            if (priceObj == null) {
                JOptionPane.showMessageDialog(menuPanel, 
                        "Price not found for item: " + itemName, 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            double price = priceObj;
            
            orderPanel.total -= price * removalQty;
            orderPanel.updateTotal();
            
            // Remove the item text from the order area for removalQty times.
            for (int i = 0; i < removalQty; i++) {
                orderPanel.removeOneItemFromOrderArea(itemName);
            }
        }
    }
}