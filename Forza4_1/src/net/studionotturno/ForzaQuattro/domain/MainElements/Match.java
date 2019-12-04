package net.studionotturno.ForzaQuattro.domain.MainElements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.studionotturno.ForzaQuattro.domain.PlayerFactory.Player;
import net.studionotturno.ForzaQuattro.ui.observer.Observer;
import net.studionotturno.ForzaQuattro.ui.observer.Subject;

/**
 * Ha le informazioni riguardati i Player e la board ( grasp information expert )
 * 
 * Deve gestire e memorizzare la board, quindi è il creatore di una Bord e
 * delega la creazione dei fori al costruttore di Board
 * 
 * E' un elemento centrale del gioco, un punto di accesso alle informazioni globali.
 * 
 * Essendo anche l'elemento che ha più informazioni del gioco, è un buon cadidato per essere un
 * Concrete Subject del pattern Observer; notifica i cambiamenti di stato proveniente dai dati della business Logic.
 * 
 * Ad inizio gioco verrà chiesto all'utente quale tipo
 * di View (Concrete Observer) usare, ma il Subject(model o business logic) resterà questa
 * 
 */
public class Match implements Subject{

	private Player player1;
	private Player player2;
	private Board board;
	private List<Player> players;
	private List<Observer> observers;
	
	private int pseudoRound=0;
	private int round=0;
	private Hole lastForo;
	
	private static boolean fine=false;
	
	private static Match instance;
	
	public static Match getInstance() {
		if(instance==null) {
			instance= new Match();
		}
			
		return instance;
	}
	
	private Match() {
		this.players=new ArrayList<Player>();
		this.board=new Board();
		this.observers=new ArrayList<Observer>();
	}
	
	public void setPlayers(Player player1,Player player2) {
		this.player1=player1;
		this.player2=player2;
		this.players.add(player1);
		this.players.add(player2);
	}
	
	public List<Player> getPlayers(){
		return this.players;
	}
	
	public Board getBoard() {
		return this.board;
	}
	public int getPseudoRound() {
		return this.pseudoRound;
	}
	public int getRound() {
		return this.round;
	}
	
	public void setLastForo(Hole foro) {
		this.lastForo=foro;
	}
	public Hole getLastForo() {
		return this.lastForo;
	}

/*---------------Override metodi Subject----------------------*/
	
	@Override
	public boolean addObserver(Observer observer) {
		return this.observers.add(observer);
	}

	@Override
	public boolean removeObserver(Observer observer) {
		return this.observers.remove(observer);
	}
	
	@Override
	public List<Observer> getObservers(){
		return this.observers;
	}

	@Override
	public boolean noTify() {
		this.observers.stream().forEach(observer->{
			observer.update(this);
		});
		return true;
	}
	
	//@Override
	public boolean noTifyWinner(Winner<Player, List<Hole>> winner) {
		this.observers.stream().forEach(observer->{
			observer.updateWinner(this,winner);
		});
		return true;
	}
	
/*---------------ATTIVAZIONE CASO D'USO----------------------*/	
	/**
	 * Metodo per avviare il caso d'uso
	 */
	public void playMatch() {
		for(round=0;round<=42 && fine==false;round++) {
			playRound(round);
		}
		//System.out.println("end");
	}

	private void playRound(int i) {
		System.out.println("round: "+i);
		for (Player player : this.players) {
			System.out.println("Player: "+player.getName());
			pseudoRound++;
			player.takeTurn();
			this.noTify();			
			
			try {				
				Thread.currentThread().sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(Comb.getInstance().controlla(player)==true) {
				noTifyWinner(Comb.getInstance().getWinner());
				fine=true;
				break;
			}

		}
		
	}


}