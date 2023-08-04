import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JFrame;

public class DropDownCurrencyChange extends JFrame {
   
	private static JPanel currLayout = new JPanel();
	private static JLabel convertLabel = new JLabel();
	private static JLabel convertedLabel = new JLabel();
	private static JTextField convertText = new JTextField();
	private static JButton currencyConv = new JButton();
	private static JComboBox currSelectOne = new JComboBox();
	private static JComboBox currSelectTwo = new JComboBox();
	private static JLabel currResultLabel = new JLabel();


	
	public DropDownCurrencyChange() {
        setTitle("Currency Converter");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(850, 300);
        setLocationRelativeTo(null);
        this.add(currLayout);
        
        convertLabel = new JLabel();
    	convertLabel.setText("Please Enter Your Balance To Convert:");
    	convertLabel.setBounds(225, 20, 400, 25);
    	convertLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
    	convertLabel.setForeground(Color.black);
    	convertLabel.setVisible(true);
    	
    	String[] currencyList = {"USD" , "EUR" , "JPY" };
    	currSelectOne = new JComboBox(currencyList);
    	currSelectOne.setFont(new Font("Courier New", Font.PLAIN, 13));
    	currSelectOne.setBounds(450, 40, 60, 25);
    	currSelectOne.setVisible(true);
    	currSelectOne.setSelectedIndex(1);
    	
    	currSelectTwo = new JComboBox(currencyList);
    	currSelectTwo.setBounds(250, 40, 60, 25);
    	currSelectTwo.setVisible(true);
    	currSelectTwo.setSelectedIndex(0); 
    	
    	convertText.setCaretColor(Color.black); //cursor color
    	convertText.setText("100.00"); //starting text
    	convertText.setEditable(true);
    	convertText.setBounds(350, 40, 80, 25);
    	convertText.setVisible(true);
    	
    	currencyConv = new JButton("Convert Currency!");
    	currencyConv.setFont(new Font("Courier New", Font.PLAIN, 13));
    	currencyConv.setBounds(300, 100, 200, 25);
    	currencyConv.setFocusable(false);
    	//currencyConv.addActionListener(this);
    	currencyConv.setVisible(true);
    	
    	convertedLabel = new JLabel();
    	convertedLabel.setText("New Currency Amount:");
    	convertedLabel.setBounds(300, 150, 400, 25);
    	convertedLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
    	convertedLabel.setForeground(Color.black);
    	convertedLabel.setVisible(true);
    	
    	currResultLabel = new JLabel();
    	currResultLabel.setText(DifferentCurrency.sym3 + "0.00");
    	currResultLabel.setBounds(350, 200, 200, 25);
    	currResultLabel.setFont(new Font("Courier New", Font.PLAIN, 17));
    	currResultLabel.setForeground(Color.red);
    	currResultLabel.setVisible(true);

        
        currLayout.setBounds(0, 0, 850, 300);
        currLayout.setVisible(true);
        //currLayout.setBackground(Color.black);
        currLayout.setLayout(null);
        
        currLayout.add(convertLabel);
        currLayout.add(convertedLabel);
        currLayout.add(convertText);
        currLayout.add(currSelectOne);
        currLayout.add(currSelectTwo);
        currLayout.add(currencyConv);
        currLayout.add(currResultLabel);
    }
	
	public void actionPerformed(ActionEvent e) {
		    if(e.getSource()==currencyConv) { //converts currency
		    	try {
		    		Object selectedConversionOne = currSelectOne.getSelectedItem(); //getting the selected option
		    		String currOne = selectedConversionOne.toString(); //converting to a string to pass variable
		    		Object selectedConversionTwo = currSelectTwo.getSelectedItem();
		    		String currTwo = selectedConversionTwo.toString();
		    		
		    		String ammountToConvert = convertText.getText();
		    		double convertAmmount = Double.parseDouble(ammountToConvert);
		    		//double converResults = Expenser.convertForeignCurrency(currOne, currTwo, convertAmmount);
		    		//currResultLabel.setText(currTwo + ": " + converResults);
		    		currResultLabel.setVisible(true);
		    		}
		    		catch (Exception exc) {
		    			JOptionPane.showMessageDialog(null, "Please enter a valid submission.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
	}
}


		    


