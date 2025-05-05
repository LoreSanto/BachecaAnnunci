/*
 * @autor Lorenzo Santosuosso 20050494
 */

package model;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class GestoreSalvataggi {
	
	
    private static final String FILE_PATH = "salvataggi/annunci.txt";

    public static void salvaBacheca(Bacheca bacheca) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            // Salva il valore corrente dell'ID
            writer.println("NEXT_ID=" + Annuncio.getNextId());

            for (Annuncio a : bacheca) {
                String tipo = a instanceof AnnuncioVendita ? "VENDITA" : "ACQUISTO";
                String parole = String.join(",", a.getParoleChiave());
                writer.print(tipo + "|" + a.getId() + "|" + a.getUtente().getEmail() + "|" + a.getUtente().getNome() + "|" + a.getNomeArticolo() + "|" + a.getPrezzo() + "|" + parole);

                if (a instanceof AnnuncioVendita vendita) {
                    writer.print("|" + vendita.getDataScadenza());
                }

                writer.println(); // newline
            }
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio della bacheca: " + e.getMessage());
        }
    }

    public static Bacheca caricaBacheca() {
        Bacheca bacheca = new Bacheca();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
        	file.getParentFile().mkdirs(); // Crea la cartella se non esiste
        	return bacheca; // file non esistente, restituisci bacheca vuota
        }

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

                bacheca.aggiungiAnnuncio(annuncio);
            }
        } catch (IOException e) {
            System.out.println("Errore durante la lettura della bacheca: " + e.getMessage());
        }

        return bacheca;
    }

    // Metodo "sporco" per settare un ID privato (serve riflessione)
    private static void setAnnuncioId(Annuncio annuncio, int id) {
        try {
            java.lang.reflect.Field field = Annuncio.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(annuncio, id);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'impostazione dell'ID dell'annuncio", e);
        }
    }
}
