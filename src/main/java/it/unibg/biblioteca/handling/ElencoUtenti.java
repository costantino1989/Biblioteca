package it.unibg.biblioteca.handling;

import java.util.Map;
import java.util.Map.Entry;

import it.unibg.biblioteca.dao.impl.UtenteCrudOperation;
import it.unibg.biblioteca.model.Gestore;
import it.unibg.biblioteca.model.Utente;

public class ElencoUtenti {

	private final Map<Integer,Utente> listaUtente;
	private final UtenteCrudOperation utenteDao;

	public ElencoUtenti(UtenteCrudOperation utenteDao) {
		super();
		this.utenteDao=utenteDao;
		listaUtente = utenteDao.getAll();
	}
	
	public Map<Integer, Utente> getListaUtente() {
		return listaUtente;
	}

	public boolean creaUtenti(Utente u) {
		int codice = utenteDao.insert(u);
		if(codice==-1) {
			return false;
		}
		u.setCodice(codice);
		listaUtente.put(codice, u);
		return true;
	}
	
	public boolean eliminaUtente(int codice) {
		if(listaUtente.containsKey(codice)) {
			if(!utenteDao.delete(codice)) {
				return false;
			}
			listaUtente.remove(codice);
			return true;
		}
		return false;
	}
	
	public Utente cercaUtenteCodice(int i) {
		if(listaUtente.containsKey(i)) {
			return listaUtente.get(i);
		}
		return null;
	}
	
	public Utente cercaUtenteNomeCogn(String nome, String cognome) {
		Utente u = null;
		for (Entry<Integer, Utente> entry : listaUtente.entrySet()) {
            if(entry.getValue().getNome().equalsIgnoreCase(nome) || entry.getValue().getCognome().equalsIgnoreCase(cognome) ) {
            	u=entry.getValue();
            }
        }
		return u;
	}
	
	public boolean updateUtente(Utente u) {
		if(listaUtente.containsKey(u.getCodice())) {
			//Utente oldU;
			//if((oldU=listaUtente.remove(u.getCodice()))!=null) {
				//listaUtente.put(u.getCodice(), u);
				if(utenteDao.update(u)) {
					listaUtente.put(u.getCodice(), u);
					return true;
				}
				//listaUtente.put(oldU.getCodice(), oldU);
			//}
			return false;
		}
		return false;
	}
	
}
