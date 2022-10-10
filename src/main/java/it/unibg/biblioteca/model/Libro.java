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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (anno != other.anno)
			return false;
		if (autori == null) {
			if (other.autori != null)
				return false;
		} else if (!autori.equals(other.autori))
			return false;
		if (editore == null) {
			if (other.editore != null)
				return false;
		} else if (!editore.equals(other.editore))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (pagine != other.pagine)
			return false;
		if (titolo == null) {
			if (other.titolo != null)
				return false;
		} else if (!titolo.equals(other.titolo))
			return false;
		return true;
	}
	
}
