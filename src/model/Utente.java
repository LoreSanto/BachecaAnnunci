/*
 * @autor Lorenzo Santosuosso 20050494
 */

package model;

public class Utente {
	
	private String email;
	private String nome;
	

	/**
     * <h2>Costruttore di Utente.</h2>
     * <p>
     * All'interno vengono inseriti i parametri che rappresentano i singoli utenti.
     * 
     * @param email Email dell'utente
     * @param nome Nome dell'utente
     * @throws IllegalArgumentException generato se vi è null email o nome, quindi non validi
     */
    public Utente(String email, String nome) {
        if (email == null || nome == null) {
            throw new IllegalArgumentException("Email e nome non possono essere nulli.");
        }
        this.email = email;
        this.nome = nome;
    }
    
    /**
     * Restituisce l'email dell'utente.
     * 
     * @return Email dell'utente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Restituisce il nome dell'utente.
     * 
     * @return Nome dell'utente
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Metodo che ritorna in formato stringa un singolo utente con: nome, email.
     * <p>
     * La stringa di ritorno racchiude i dettagli dell'utente, ognuno separato dal carattere "|"
     * 
     * @return Stringa con le caratteristiche dell'annuncio
     */
    @Override
    public String toString() {
        return nome + " (" + email + ")";
    }

}
