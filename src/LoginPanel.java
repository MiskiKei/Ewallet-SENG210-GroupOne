import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    public static String username;
    public LoginPanel() {
        setLayout(new GridLayout(3, 2));
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        add(new JLabel("Username: "));
        add(usernameField);
        add(new JLabel("Password: "));
        add(passwordField);
//        Dimension preferredSize = new Dimension(300, 100);
//        setPreferredSize(preferredSize);
    }

    public boolean login() {
        username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        return SQLStatements.login(username, password);
    }

    public boolean createAccount() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        return SQLStatements.createUser(username, password);
    }
    public static String getUser() {
    	return username;
    }
}

