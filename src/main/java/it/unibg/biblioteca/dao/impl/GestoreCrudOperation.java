package it.unibg.biblioteca.dao.impl;

import java.util.Map;

import it.unibg.biblioteca.model.Gestore;
import it.unibg.biblioteca.model.Utente;

public interface GestoreCrudOperation {

	public Map<Integer,Gestore> getAll();
	public int insert(Gestore g);
	public boolean delete(int codice);
	public boolean update(Gestore g);
	public boolean autentica(Gestore u);
	
}
