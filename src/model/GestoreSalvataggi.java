/*
 * @autor Lorenzo Santosuosso 20050494
 */

package model;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * <h2>Classe GestoreSalvataggi</h2>
 * <p>
 * All'interno di questa classe è gestita la logica dei salvataggi su file.txt.
 * <br>
 * Nella classe vi sono due stringhe costanti {@code FILE_PATH_ANNUNCI} e {@code FILE_PATH_UTENTI} che rappresentano
 * il percorso del salvataggio dei file. Inoltre vi sono le seguenti funzioni pubbliche:
 * <lu>
 * 		<li>{@code salvaBacheca} : funzione che salva lo stato corrente della bacheca</li>
 * 		<li>{@code caricaBacheca} : funzione che carica lo stato della bacheca salvata</li>
 * 		<li>{@code salvaUtenti} : funzione che salva gli utenti su file TXT</li>
 * 		<li>{@code caricaUtenti} : carica gli utenti salvati</li>
 * <lu>
 * </p>
 */
public class GestoreSalvataggi {
	
	
    private static final String FILE_PATH_ANNUNCI = "salvataggi/annunci.txt";
    private static final String FILE_PATH_UTENTI = "salvataggi/utenti.txt";
    
    /**
     * <h2>Salva lo stato corrente della bacheca su file.</h2>
     * <p>
     * Ogni annuncio viene serializzato in formato testuale, separando i campi con il carattere pipe ("|").
     * Viene anche salvato il valore corrente del prossimo ID disponibile per gli annunci.
     * </p>
     * 
     * @param bacheca la bacheca contenente gli annunci da salvare
     * @throws IOException generato qualora vi fosse un errore durante la scrittura del file e quindi nel salvataggio
     */
    public static void salvaBacheca(Bacheca bacheca) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH_ANNUNCI))) {
            // Salva il valore corrente dell'ID
            writer.println("NEXT_ID=" + Annuncio.getNextId());

            for (Annuncio a : bacheca) {
                String tipo = a instanceof AnnuncioVendita ? "VENDITA" : "ACQUISTO";
                String parole = String.join(",", a.getParoleChiave());
                writer.print(tipo + "|" + a.getId() + "|" + a.getUtente().getMail() + "|" + a.getUtente().getNome() + "|" + a.getNomeArticolo() + "|" + a.getPrezzo() + "|" + parole);

                if (a instanceof AnnuncioVendita vendita) {
                    writer.print("|" + vendita.getDataScadenza());
                }

                writer.println(); // newline
            }
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio della bacheca: " + e.getMessage());
        }
    }
    
    
    /**
     * <h2>Carica una bacheca a partire dal file precedentemente salvato.</h2>
     * <p>
     * Ricostruisce gli oggetti Annuncio, sia di vendita che di acquisto,
     * in base ai dati presenti nel file. Imposta anche il prossimo ID disponibile
     * per gli annunci leggendo la prima riga ove si memorizza il NEXT_ID da assegnare al contatore
     * che assegna gli ID degli annunci.
     * Se il file non esiste, restituisce una bacheca vuota.
     * </p>
     *
     * @return una nuova istanza di {@code Bacheca} popolata con gli annunci letti dal file
     * @throws IOException generato nel caso in cui ci fossero degli errori durante il caricamento della bacheca
     */
    public static Bacheca caricaBacheca() {
        Bacheca bacheca = new Bacheca();
        File file = new File(FILE_PATH_ANNUNCI);
        
        //Se non esiste il file dove salvo i dati della bacheca allora viene creato
        if (!file.exists()) {
        	file.getParentFile().mkdirs(); // Crea la cartella
        	return bacheca; //restituisci bacheca vuota
        }
        
        //Nel caso esista invece effettua una try per caricare gli elementi della bacheca salvata
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine && line.startsWith("NEXT_ID=")) {
                    int nextId = Integer.parseInt(line.substring("NEXT_ID=".length()));
                    Annuncio.setNextId(nextId);
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split("\\|");

                String tipo = parts[0];
                int id = Integer.parseInt(parts[1]);
                String email = parts[2];
                String nome = parts[3];
                String articolo = parts[4];
                double prezzo = Double.parseDouble(parts[5]);
                Set<String> paroleChiave = new HashSet<>(Arrays.asList(parts[6].split(",")));
                Utente utente = new Utente(email, nome);

                Annuncio annuncio;
                if (tipo.equals("VENDITA")) {
                    LocalDate dataScadenza = LocalDate.parse(parts[7]);
                    annuncio = new AnnuncioVendita(utente, articolo, prezzo, paroleChiave, dataScadenza);
                } else {
                    annuncio = new AnnuncioAcquisto(utente, articolo, prezzo, paroleChiave);
                }

                // imposta l'id manualmente
                setAnnuncioId(annuncio, id);

                bacheca.addAnnuncio(annuncio);
            }
        } catch (IOException e) {
            System.out.println("Errore durante la lettura della bacheca: " + e.getMessage());
        }

        return bacheca;
    }

    /**
     * <h2>Imposta manualmente l'ID di un annuncio utilizzando la riflessione.</h2>
     * <p>
     * Questo metodo accede al campo privato id della classe Annuncio
     * e lo modifica direttamente, operazione necessaria per ripristinare gli ID
     * durante il caricamento da file, questo proprio perchè ogni ID deve essere univoco
     * </p>
     *
     * @param annuncio l'annuncio a cui assegnare un ID specifico
     * @param id l'ID da assegnare
     * @throws RuntimeException se si verifica un errore durante l'accesso al campo riflessivo
     */
    private static void setAnnuncioId(Annuncio annuncio, int id) {
        try {
            java.lang.reflect.Field field = Annuncio.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(annuncio, id);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'impostazione dell'ID dell'annuncio: " + e.getMessage());
        }
    }
    
    /**
     * <h2>Salva la lista degli utenti registrati su file.</h2>
     * <p>
     * Ogni utente viene salvato su una riga, nel formato:
     * <code>email|nome</code>
     * </p>
     *
     * @param utenti lista di utenti da salvare
     * @throws IOException nel caso ci fossero problemi nella scrittura degli utenti
     */
    public static void salvaUtenti(List<Utente> utenti) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH_UTENTI))) {
            for (Utente utente : utenti) {
                writer.println(utente.getMail() + "|" + utente.getNome());
            }
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio degli utenti: " + e.getMessage());
        }
    }
    
    /**
     * <h2>Carica gli utenti salvati da file.</h2>
     * <p>
     * Il file deve contenere una riga per ogni utente nel formato:
     * <code>email|nome</code>
     * </p>
     *
     * @return lista di utenti caricati dal file
     * @throws IOException nel caso ci fossero problemi nella lettura degli utenti
     */
    public static List<Utente> caricaUtenti() {
        List<Utente> utenti = new ArrayList<>();
        File file = new File(FILE_PATH_UTENTI);
        if (!file.exists()) {
            file.getParentFile().mkdirs(); // Crea la directory se non esiste
            return utenti;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String email = parts[0];
                    String nome = parts[1];
                    utenti.add(new Utente(email, nome));
                }
            }
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento degli utenti: " + e.getMessage());
        }

        return utenti;
    }
    
}
