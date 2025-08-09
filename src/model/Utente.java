/*
 * @autor Lorenzo Santosuosso 20050494
 */

package model;


/**
 * <h2>Classe Utente</h2>
 * <p>
 * La classe rappresenta un utente con nome e email.
 * Permette la creazione di un'istanza tramite costruttore,
 * e l'accesso ai dati tramite metodi getter.
 * </p>
 */
public class Utente {
	
	private String nome;//nome dell'utente
	private String mail;//email dell'utente
	
	/**
	 * <h2>Costruttore Utente</h2>
	 * <p>
	 * Crea un nuovo utente con nome ed email.
	 * Entrambi i parametri devono essere non nulli e non vuoti.
	 * L'email deve rispettare la struttura base: nome utente, '@', dominio e suffisso.
	 * </p>
	 * @param nome Nome dell'utente
	 * @param mail Email dell'utente
	 * @throws IllegalArgumentException se nome o mail sono nulli, vuoti o non validi
	 */
	public Utente(String nome,String mail) {
		if(nome == null || mail == null || nome.isBlank() || mail.isBlank()) { 			
			throw new IllegalArgumentException("La mail e nome non possono essere vuoti");
		}
		if(!mail.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")){
			throw new IllegalArgumentException("La mail non è valida");
		}
		
		this.mail = mail;
		this.nome = nome;
		
	}

	/**
	 * <h2>Restituzione nome utente</h2>
	 * 
	 * @return nome Nome dell'utente
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * <h2>Restituzione mail utente</h2>
	 * 
	 * @return mail mail dell'utente
	 */
	public String getMail() {
		return mail;
	}
	
	 /**
     * <h2>Metodo che ritorna in formato stringa un singolo utente con: nome, email.</h2>
     * <p>
     * La stringa di ritorno racchiude i dettagli dell'utente
     * </p>
     * 
     * @return Stringa con i dati dell'utente
     */
	@Override
	public String toString() {
		return nome + " (" + mail + ")";
	}
	
	

}
