import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class DropDownCurrencyChange extends JFrame {
    public DropDownCurrencyChange() {
        setTitle("Currency Change");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create an array of options
        String[] options = { DifferentCurrency.cur1, DifferentCurrency.cur2, DifferentCurrency.cur3 };

        // Create a drop-down menu using the options array
        JComboBox<String> dropdown = new JComboBox<>(options);

        // Set the initial selected option
        dropdown.setSelectedIndex(0);

        // Set the preferred size of the JComboBox
        dropdown.setPreferredSize(new Dimension(150, 20)); // Adjust the width and height according to your preference

        // Create if statement for options
        dropdown.addActionListener(new ActionListener() { //options chosen but does not affect any other class 
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) dropdown.getSelectedItem();
                String sym = DifferentCurrency.sym1;
                if (selectedOption.equals(DifferentCurrency.cur1)) {
                    sym = DifferentCurrency.sym1;
                    System.out.println("Currently using France version of currency.");
                } else if (selectedOption.equals(DifferentCurrency.cur2)) {
                    sym = DifferentCurrency.sym2;
                    System.out.println("Currently using Japanese version of currency.");
                } else if (selectedOption.equals(DifferentCurrency.cur3)) {
                    sym = DifferentCurrency.sym3;
                  System.out.println("Currently using US version of currency.");
                }
            }
        });

        // Add the drop-down menu to the frame
        add(dropdown);

        pack();
        setVisible(true);
        setSize(850, 100);
        setLocationRelativeTo(null);
    }
}
