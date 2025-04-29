/*
 * @autor Lorenzo Santosuosso 20050494
 */

package model;

public class Utente {
	
	private String email;
	private String nome;
	

	/**
     * Costruttore di Utente.
     * @param email Email dell'utente
     * @param nome Nome dell'utente
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
     * @return Email dell'utente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Restituisce il nome dell'utente.
     * @return Nome dell'utente
     */
    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome + " (" + email + ")";
    }

}
