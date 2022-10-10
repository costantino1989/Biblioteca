package it.unibg.biblioteca.model;

import java.util.List;

public class RichiestaAcquisto {

	private int codice;
	private int quantita;
	private Libro libro;
	private List<UtenteLibro> listaUtenti;
	// 0=in acquisto, 1=cancellato, 2=acquistato
	private int stato;
	
	public RichiestaAcquisto(Libro libro, List<UtenteLibro> listaUtenti, int codice) {
		this.libro=libro;
		this.listaUtenti=listaUtenti;
		this.codice=codice;
		this.stato = 0;
	}
	
	public RichiestaAcquisto(Libro libro, List<UtenteLibro> listaUtenti) {
		this.libro=libro;
		this.listaUtenti=listaUtenti;
		this.codice=-1;
		this.stato = 0;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public int getCodice() {
		return codice;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public List<UtenteLibro> getListaUtenti() {
		return listaUtenti;
	}

	public void setListaUtenti(List<UtenteLibro> listaUtenti) {
		this.listaUtenti = listaUtenti;
	}

	public UtenteLibro getUtenteLibro(Utente u) {
		for(UtenteLibro utenteibro: this.listaUtenti) {
			if(utenteibro.getUtente().getCodice()==u.getCodice()) {
				return utenteibro;
			}
		}
		return null;
	}
	
	
}
