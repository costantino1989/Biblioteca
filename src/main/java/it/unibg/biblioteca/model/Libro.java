package it.unibg.biblioteca.model;

public class Libro {

	private String autori;
	private String titolo;
	private String editore;
	private int anno;
	private int pagine;
	private String isbn;
	private int disponibilita;
	
	public Libro(String autori, String titolo, String editore, int anno, int pagine, String isbn) {
		super();
		this.autori = autori;
		this.titolo = titolo;
		this.editore = editore;
		this.anno = anno;
		this.pagine = pagine;
		this.isbn = isbn;
		this.disponibilita = 0;
	}
	
	public int getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}

	public String getAutori() {
		return autori;
	}

	public void setAutori(String autori) {
		this.autori = autori;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getEditore() {
		return editore;
	}

	public void setEditore(String editore) {
		this.editore = editore;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public int getPagine() {
		return pagine;
	}

	public void setPagine(int pagine) {
		this.pagine = pagine;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Override
	public boolean equals(Object obj) {
		Libro other = (Libro) obj;
		if (other==null || anno != other.anno || (autori == null && other.autori != null) || !autori.equals(other.autori) || 
				(editore == null && other.editore != null) || !editore.equals(other.editore) || (isbn == null && other.isbn != null)
				|| !isbn.equals(other.isbn) || pagine != other.pagine || (titolo == null && other.titolo != null || !titolo.equals(other.titolo))) {
			return false;
		}
		return true;
	}
	
}
