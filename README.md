# Biblioteca
Quetso progetto è una rappresentazione di una parte di una gestione di una biblioteca, per questo non sono stati implementati tutti gli aspetti come ad esempio la possibilità di prendere in noleggio un libro.
Lo sviluppo comprende:

- **creazione di una richiesta di acquisto di un libro**
- **cancellazione di una richiesta di acquisto**
- **modifica della richiesta di acquisto**
- **creazione di un utente**
- **cancellazione di un utente**

la creazione e cancellazione di un utente sono sempre in funzione della richiesta, ovvero, se si crea una richiesta e non è presente l'unete nel database, allora verrà creato l'utente.
Mentre se si elimina un utente bisogna eliminare anhce tutte le richieste ad esso associato.

## Creazione di una richiesta di acquisto
Il **Getsore** alla ricezione di un acquisto di un libro da parte di un **Utente**, verifica se ha a disposizione in catalogo il libro richiesto ed in caso di esito positivo procede alla creazione della richiesta.
A questo punto verifica se esiste l'Utente ed in caso di esito negativo procede alla sua creazione; diminuisce la disponibilità a catalogo e crea la richiesta associata al libro aggiungendo l'utente ad una lista utenti settando la quantità da acquistare.

**N.B. Potrebbero esserci già presenti delle richieste sul libro richiesto dall'utente, in questo caso si sommano le quantità di tutte le richieste degli utenti con la nuova richiesta e si aggiunge l'utente alla lsta degli utenti**  

## Cancellazione di una richiesta di acquisto ##
Il Gestore potrebbe ricevere un evento di cancellazione della richiesta da parte dell'Utente. Il Gestore reinserisce la quantità richiesta a catalogo e procede alla cancellazione della richiesta. Se nella lista degli utenti esiste più di un utente allora verrà eliminato l'utente dalla lista utenti e viene diminuita la quantità richiesta dall'oggetto Richiesta, se nella lista utenti c'è solo un utente ed è colui che ha richiesto la cancellazione, allora verrà eliminata anche la richiesta.

## Modifica della richiesta di acquisto
Il Gestore procede alla modifica della richiesta di acquisto, es. aumento o diminuzione della quantità richiesta di un utente. viene modificato l'oggetto della richiesta e fatto un aggiornamento al database.

## Creazione di un Utente
Nel momento in cui avviene la creazione della richiesta di acquisto di un utente, se tale utente non è presente nel sistema verrà creato(verrà creato anche un record nel database).

## Cancellazione di un utente 
Il Gestore, al momento della cancellazione di un Utente dal sistema, verifica se esistono delle richieste associate a lui. Nel caso ci siano delle richieste associate viene eliminato l'utente dalla lista utente e nel caso ci fosse solo lui nella lista viene eliminata la richiesta stessa e aggiornato il database eliminando il record con l'informazioni Utente e le relazioni a lui associate.

## Vincoli
1. Utenti e Gestori vengono identificati attraverso un codice che se non settato è di default a -1, ma al momento della loro creazione nel database tale valore dovrà essere >=0
2. ogni libro ha un isbn, valore univoco alfanumerico, non può essere nullo
3. ogni attributo di Utente, Gestore, Libro, RichiestAcquisto, UtenteLibro e tutte le classi Dao non devono essere nulli.
