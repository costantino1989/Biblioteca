package it.unibg.biblioteca.dao.impl;

import java.util.Map;

import it.unibg.biblioteca.model.Utente;

public interface UtenteCrudOperation {

	public Map<Integer,Utente> getAll();
	public int insert(Utente u);
	public boolean delete(int codice);
	public boolean update(Utente u);
	public boolean autentica(Utente u);
	
}
