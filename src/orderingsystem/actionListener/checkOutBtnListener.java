/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderingsystem.actionListener;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import orderingsystem.MenuPanel;
import orderingsystem.OrderPanel;

/**
 *
 * @author escob
 */
public class checkOutBtnListener implements ActionListener {
    private MenuPanel menuPanel;
    private OrderPanel orderPanel;
    
    public checkOutBtnListener(MenuPanel menuPanel, OrderPanel orderPanel) {
        this.menuPanel = menuPanel;
        this.orderPanel = orderPanel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (orderPanel.orderArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(menuPanel, 
                    "No order has been placed yet.", 
                    "Empty Order", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
         String receipt = "===========================================  RECEIPT ====================================================================================\n" +
                         "Table Number: " + orderPanel.tableNumber + "\n" +
                         "-----------------------------------------------------------------------------------------------------------------------------------------\n" +
                         orderPanel.orderArea.getText() +
                         "-----------------------------------------------------------------------------------------------------------------------------------------\n" +
                         "TOTAL: ₱" + String.format("%.2f", orderPanel.total) + "\n" +
                         "=========================================================================================================================================";
         
         JTextArea receiptArea = new JTextArea(receipt);
         receiptArea.setEditable(false);
         receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
         JScrollPane scrollPane = new JScrollPane(receiptArea);
         scrollPane.setPreferredSize(new Dimension(600, 400));
        Object[] options = {"OK"};
        int messageResponse = JOptionPane.showOptionDialog(menuPanel, scrollPane, "Receipt", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        if (messageResponse == JOptionPane.CLOSED_OPTION) {
            // User closed the receipt dialog. Don't continue.
            return;
        }
        
       Object[] options2 = {"YES", "MODIFY"};
        int response = JOptionPane.showOptionDialog(
                menuPanel, 
                "Print receipt?", 
                "Confirmation", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                options2, 
                options2[0]);
        
        if (response == JOptionPane.YES_OPTION) {
            String filepath = "src/Assets/data/sales.txt";
            
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(formatter);
            
            String orderText = orderPanel.orderArea.getText().replace("\n", " ");
            String logEntry = timestamp + " | Table Number: " + orderPanel.tableNumber +
                              " | Order: " + orderText +
                              " | Total: ₱" + String.format("%.2f", orderPanel.total) + "\n";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true))) {
                writer.write(logEntry);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(menuPanel,
                        "Error writing log entry: " + ex.getMessage(),
                        "File Error", JOptionPane.ERROR_MESSAGE);
            }
            
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName("Print Receipt");
            job.setPrintable(new Printable() {
                @Override
                public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
                    // Convert to Graphics2D and set up font
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.translate(pf.getImageableX(), pf.getImageableY());
                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));

                    // Calculate line height and lines per page
                    int lineHeight = g2d.getFontMetrics().getHeight();
                    double pageHeight = pf.getImageableHeight();
                    int linesPerPage = (int) (pageHeight / lineHeight);

                    // Split receipt into lines
                    String[] lines = receipt.split("\n");

                    // Calculate how many pages we need
                    int numPages = (int) Math.ceil(lines.length / (double) linesPerPage);

                    // If the requested pageIndex is beyond numPages, no more pages
                    if (pageIndex >= numPages) {
                        return NO_SUCH_PAGE;
                    }

                    // Print only the lines that fit on this page
                    int startLine = pageIndex * linesPerPage;
                    int endLine = Math.min(startLine + linesPerPage, lines.length);

                    // Position the first line a little below the top
                    int y = 0; 
                    for (int i = startLine; i < endLine; i++) {
                        y += lineHeight;
                        g2d.drawString(lines[i], 0, y);
                    }

                    return PAGE_EXISTS;  // More pages may follow
                }
            });
            // Show the print dialog; if confirmed, print the receipt.
            boolean doPrint = job.printDialog();
            if (doPrint) {
                try {
                    job.print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(menuPanel,
                        "Error printing receipt: " + ex.getMessage(),
                        "Printing Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            orderPanel.resetOrder(true);
        }
        
    }
    
}
