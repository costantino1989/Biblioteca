package it.unibg.biblioteca.dao.impl;

import java.util.List;

import it.unibg.biblioteca.model.RichiestaAcquisto;

public interface AcquistoCrudOperation {

	public List<RichiestaAcquisto> getAll();
	public int insert(RichiestaAcquisto r);
	public boolean delete(int codice);
	public boolean update(RichiestaAcquisto r);
	
}
