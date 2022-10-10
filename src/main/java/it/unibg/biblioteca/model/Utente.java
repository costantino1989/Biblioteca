package it.unibg.biblioteca.model;

public class Utente extends UserParents{

	private int quantita;
	
	public Utente(String nome, String cognome, String password, String usarname) {
		super(nome, cognome, password, usarname);
		quantita = 0;
	}
	
	public Utente(String nome, String cognome, int codice, String password, String usarname) {
		super(nome, cognome, codice, password, usarname);
		quantita = 0;
	}
	
	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

}
