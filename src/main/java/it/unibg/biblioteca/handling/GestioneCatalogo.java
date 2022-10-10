package it.unibg.biblioteca.handling;

import java.util.List;

import it.unibg.biblioteca.dao.impl.LibroCrudOperation;
import it.unibg.biblioteca.model.Libro;


public class GestioneCatalogo {

	private final List<Libro> listaLibri;
	private final LibroCrudOperation libroDao;

	public GestioneCatalogo(LibroCrudOperation libroDao) {
		super();
		this.libroDao = libroDao;
		this.listaLibri = libroDao.getAll();
	}

	public List<Libro> getListaLibri() {
		return listaLibri;
	}
	 
	public boolean aggiungiLibro(Libro libro) {
		if(libroDao.insert(libro)==-1) {
			return false;
		}
		return listaLibri.add(libro);
	}
	
	
	public Libro cercaISBN(String isbn) {
		for(Libro l: this.listaLibri) {
			if(isbn.equalsIgnoreCase(l.getIsbn())) {
				return l;
			}
		}
		return null;
	}
	
	public boolean rimuoviLibro(String isbn) {
		for(Libro libro : listaLibri) {
			if(libro.getIsbn().equals(isbn)) {
				if(listaLibri.remove(libro)) {
					if(libroDao.delete(isbn)) {
						return true;
					}else {
						listaLibri.add(libro);
					}
				}
				return false;
			}		
		}
		return false;
	}
	
	public boolean updateLibro(Libro l) {
		for(Libro libro : listaLibri) {
			if(libro.getIsbn().equals(l.getIsbn())) {
				if(listaLibri.remove(libro)) {
					if(listaLibri.add(l) && libroDao.update(l)) {
							return true;
					}
					listaLibri.add(libro);
				}
				return false;
			}
		}
		return false;
	}
	
	public boolean diminuisciDisponibilita(String isbn, int quantita) {
		for(Libro libro : listaLibri) {
			if(libro.getIsbn().equals(isbn)) {
				if(libro.getDisponibilita()-quantita>=0) {
					libro.setDisponibilita(libro.getDisponibilita()-quantita);
					if(libro.getDisponibilita()==0) {
						this.rimuoviLibro(isbn);			
					}
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	public boolean aumentaDisponibilita(String isbn, int quantita) {
		for(Libro libro : listaLibri) {
			if(libro.getIsbn().equals(isbn)) {
				libro.setDisponibilita(libro.getDisponibilita()+quantita);		
				return true;
			}
		}
		return false;
	}
	
}
