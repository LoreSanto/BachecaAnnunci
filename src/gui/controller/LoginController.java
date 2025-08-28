/*
 * @autor Lorenzo Santosuosso 20050494
 */

package gui.controller;

import gui.vista.LoginFrame;
import gui.vista.BachecaGUI;
import model.*;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class LoginController {
	
    private LoginFrame loginView;

    /**
     * <h2>Costruttore del LoginController</h2>
     *
     * @param loginViwe
     */
    public LoginController(LoginFrame loginView) {
        this.loginView = loginView;
    }
    
    /**
     * <h2>Pulsante di login</h2>
     *
     * <p>
     * Mediante questa funzione effettuo il login premendo il pulsante di login.
     * Se non esiste la mail tra quelle salvate si effettua una registrazione.
     * Una volta fatto tutto ciò si aprirà la bacheca degli annunci con tutti gli eventuali salvataggi
     * </p>
     */
    public void handleLogin() {
        String email = loginView.getEmail();
        String nome = loginView.getNome();
        
        try {
        	
        	//verifico che la mail sia stata inserita
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(loginView, "Inserisci un'email.");
                return;
            }

            List<Utente> utenti = GestoreSalvataggi.caricaUtenti();
            Optional<Utente> utenteOpt = utenti.stream()
                    .filter(u -> u.getMail().equalsIgnoreCase(email))
                    .findFirst();

            Utente utente;
            //Verifico se l'utente sia già presente (effettua il login) oppure no (effettua registrazione)
            if (utenteOpt.isPresent()) {
            	
            	//Se è presente, ma è stato inserito il nome visualizzo il seguente messaggio
            	if(!nome.isEmpty()) {
            		JOptionPane.showMessageDialog(loginView, "La mail è già registrata");
                    return;
            	}
            	
                utente = utenteOpt.get();
            } else {
            	
            	//Verifico che abbia inserito il nome utente
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
            
        	
        }catch(Exception e) {
        	
        	JOptionPane.showMessageDialog(loginView, "Errore: " + e.getMessage());
        	return;
        }
        
        
    }
}
