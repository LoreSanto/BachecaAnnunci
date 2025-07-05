package gui.controller;

import gui.vista.LoginFrame;
import gui.vista.BachecaGUI;
import model.*;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class LoginController {
    private LoginFrame loginView;

    public LoginController(LoginFrame loginView) {
        this.loginView = loginView;
    }

    public void handleLogin() {
        String email = loginView.getEmail();
        String nome = loginView.getNome();

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "Inserisci un'email.");
            return;
        }

        List<Utente> utenti = GestoreSalvataggi.caricaUtenti();
        Optional<Utente> utenteOpt = utenti.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();

        Utente utente;
        if (utenteOpt.isPresent()) {
            utente = utenteOpt.get();
        } else {
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(loginView, "Inserisci il nome per registrarti.");
                return;
            }
            utente = new Utente(email, nome);
            utenti.add(utente);
            GestoreSalvataggi.salvaUtenti(utenti);
        }

        Bacheca bacheca = GestoreSalvataggi.caricaBacheca();

        // Apri la GUI principale
        new BachecaGUI(bacheca, utente);
        loginView.dispose(); // Chiudi la finestra di login
    }
}
