package net.studionotturno.ForzaQuattro.domain.PlayerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.studionotturno.ForzaQuattro.domain.MainElements.Hole;
import net.studionotturno.ForzaQuattro.domain.MainElements.Match;
import net.studionotturno.ForzaQuattro.domain.MainElements.Token;
import net.studionotturno.ForzaQuattro.domain.Strategy.Strategy;

/**
 * Responsabilità di conoscere i dettagli di un giocatore,
 * come ad esempio il nome ed altro, responsabilità di conoscere( grasp Expert )
 * 
 * utilizza i token, quindi secondo il patter grasp creator è anche il creatore
 *  di token (ogni giocatore ha i suoi)
 *  
 *  Ha una visione dei token che ha inserito nella Board, conosce i Fori riempiti con i propri token
 */
public interface Player {


	/**
	 * @return Il nome del giocatore
	 */
	public String getName();
	
	public void setName(String nome);
	
	/**
	 * Crea 25 Token per uqesto giocatore;
	 */
	public void createToken();
	
	public List<Token> getToken();

	public void takeTurn();
	
	/**
	 * Aggiunge il foro con un token ad una lista
	 * @param f
	 */
	public void addHole(Hole f);
	
	/**
	 * Consente di tenere traccia dei fori con un token del giocatore
	 * @return la lista dei fori con un token di questo giocatore
	 */
	public List<Hole> getFullHoles();
	

	/**Ritorna la colonna scelta dal Player
	 * Ogni colonna è un insieme di Hole creata ad inizio partita dalle quali viene tolto un Hole pieno
	 * dopo ogni mossa di ogni giocatore.
	 * @return la colonna scelta dal Player
	 */
	//public ArrayList<Hole> getColChoosed();
	
	
	/**
	 * Ritorna il foro dove andrà inserito il token
	 * Filtra tutti i fori della colonna scelta che sono vuoti(non hanno il gettone)<br>
	 * e ritorna il foro più in basso
	 * @param i
	 * @return
	 */
	default Hole getHoleChoosed(AtomicInteger i) {
		//filtra tutti i fori della colonna scelta
		Predicate<Hole> predColonna=(foro)->{return i.get()==foro.getCol();};		
		//filtra tutti i fori della colonna scelta, i quali sono vuoti(non hanno il gettone)
		Predicate<Hole> predRiga=(foro)->{return foro.isEmpty(); };
		
		Comparator<Hole> comp=(foro1,foro2)->{return foro1.getRow().compareTo(foro2.getRow());};
		
		Hole f=null;
		
		while(f==null) {
			try {
				f=Match.getInstance().getBoard().getHoles().stream()
						.filter(predColonna).filter(predRiga).min(comp).get();
			}catch(NullPointerException | NoSuchElementException e) {}
		}
		
		return f;
	}
	
	public void setStrategy(Strategy strategy);
	
	public Strategy getStategy();
	

}