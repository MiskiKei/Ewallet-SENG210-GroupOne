import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class DropDownCurrencyChange {
    public static void main(String[] args) {
        // Create a JFrame (window) to hold the drop-down menu
        JFrame frame = new JFrame("Drop-down Menu Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create an array of options
        String[] options = { DifferentCurrency.cur1, DifferentCurrency.cur2, DifferentCurrency.cur3 };
        
        // Create a drop-down menu using the options array
        JComboBox<String> dropdown = new JComboBox<>(options);
        
        // Set the initial selected option
        dropdown.setSelectedIndex(0);
        
        // Create if statement for options
        dropdown.addActionListener(new ActionListener() {
            public void actionPerformed1(ActionEvent e) {
                String selectedOption = (String) dropdown.getSelectedItem();
                String sym = DifferentCurrency.sym1;
                if (selectedOption.equals(DifferentCurrency.cur1)) {
                	sym = DifferentCurrency.sym1;
                    System.out.println("Option 1 selected. Performing action 1.");
                } else if (selectedOption.equals(DifferentCurrency.cur2)) {
                	sym = DifferentCurrency.sym2;
                    System.out.println("Option 2 selected. Performing action 2.");
                } else if (selectedOption.equals(DifferentCurrency.cur3)) {
                	sym = DifferentCurrency.sym3;
                    System.out.println("Option 3 selected. Performing action 3.");
                }
            }

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
        
        // Add the drop-down menu to the frame
        frame.add(dropdown);
        
        // Set the size of the frame and make it visible
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
