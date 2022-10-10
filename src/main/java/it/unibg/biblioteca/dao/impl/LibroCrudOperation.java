package it.unibg.biblioteca.dao.impl;

import java.util.List;

import it.unibg.biblioteca.model.Libro;

public interface LibroCrudOperation {

	public List<Libro> getAll();
	public int insert(Libro l);
	public boolean delete(String isbn);
	public boolean update(Libro l);
	
}
