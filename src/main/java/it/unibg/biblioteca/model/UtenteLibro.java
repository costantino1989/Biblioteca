package it.unibg.biblioteca.model;

public class UtenteLibro {

	private final Utente utente;
	private final int quantita;
	public UtenteLibro(Utente utente, int quantita) {
		super();
		this.utente = utente;
		this.quantita = quantita;
	}
	public Utente getUtente() {
		return utente;
	}
	public int getQuantita() {
		return quantita;
	}

}
