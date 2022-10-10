package it.unibg.biblioteca.handling;

import java.util.Map;

import it.unibg.biblioteca.dao.impl.GestoreCrudOperation;
import it.unibg.biblioteca.model.Gestore;

public class ElencoGestori {
	
	private final Map<Integer,Gestore> listaGestori;
	private final GestoreCrudOperation gestoreDao;
	
	public ElencoGestori(GestoreCrudOperation gestoreDao) {
		super();
		this.gestoreDao=gestoreDao;
		this.listaGestori=gestoreDao.getAll();
	}

	public boolean creaGestore(String usarname, String password, String nome, String cognome, GestioneCatalogo gestioneCatalogo, ElencoRichiesteAcquisto elencoRichieste, ElencoUtenti elencoUtenti) {
		Gestore g = new Gestore(usarname, password, nome, cognome, gestioneCatalogo, elencoRichieste, elencoUtenti);
		int codice = gestoreDao.insert(g);
		if(codice==-1){
			return false;
		}
		g.setCodice(codice);
		listaGestori.put(codice, g);
		return true;
	}
	
	public Gestore eliminaGestore(int codice) {
		Gestore g;
		if(listaGestori.containsKey(codice) && (g=listaGestori.remove(codice))!=null ){
			if(!gestoreDao.delete(codice)) {
				return null;
			}else {
				return g;
			}
		}
		return null;
	}

	public Map<Integer, Gestore> getListaGestori() {
		return listaGestori;
	}
	
	public boolean autenticaGestore(Gestore gestore) {
		return gestoreDao.autentica(gestore);
	}
	
	public boolean modificaGestore(Gestore gestore) {
		if(listaGestori.containsKey(gestore.getCodice())) {
			Gestore g;
			if((g=listaGestori.remove(gestore.getCodice()))!=null) {
				if(gestoreDao.update(gestore)) {
					listaGestori.put(gestore.getCodice(), gestore);
					return true;
				}
				listaGestori.put(g.getCodice(), g);
			}
			return false;
		}
		return false;
	}
	
}
