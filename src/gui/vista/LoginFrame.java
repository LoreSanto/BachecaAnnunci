/*
 * @autor Lorenzo Santosuosso 20050494
 */

package gui.vista;

import gui.controller.LoginController;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
    private JTextField emailField;
    private JTextField nomeField;
    private JButton loginButton;
    private LoginController controller;

    /**
     * <h2>Carico la GUI del login</h2>
     */
    public LoginFrame() {
        super("Login Bacheca");
        controller = new LoginController(this);

        emailField = new JTextField(20);
        nomeField = new JTextField(20);
        loginButton = new JButton("Login");

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Nome (solo se nuovo utente):"));
        panel.add(nomeField);
        panel.add(new JLabel());
        panel.add(loginButton);

        loginButton.addActionListener(e -> controller.handleLogin());

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Mi restituisce la mail di chi sta usando la bacheca
     */
    public String getEmail() {
        return emailField.getText().trim();
    }
    
    /**
     * Mi restituisce il nome di chi sta usando la bacheca
     */
    public String getNome() {
        return nomeField.getText().trim();
    }
}
