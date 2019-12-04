package net.studionotturno.ForzaQuattro.domain.PlayerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import net.studionotturno.ForzaQuattro.domain.MainElements.Hole;
import net.studionotturno.ForzaQuattro.domain.MainElements.Match;
import net.studionotturno.ForzaQuattro.domain.MainElements.Mediator;
import net.studionotturno.ForzaQuattro.domain.MainElements.Token;
import net.studionotturno.ForzaQuattro.domain.Strategy.Strategy;
import net.studionotturno.ForzaQuattro.ui.commandLine.MainViewCMD;
import net.studionotturno.ForzaQuattro.ui.observer.Observer;

public class RealPlayer implements Player {

	private String name;
	private List<Token> tokens;
	private List<Hole> foriFull;	
	
	public RealPlayer(String name) {
		this.name=name;
		this.tokens=new ArrayList<Token>();	
		this.foriFull=new ArrayList<Hole>();
		createToken();
	}


	@Override
	public String getName() {
		return this.name;
	}


	@Override
	public void setName(String nome) {
		this.name=name;
		
	}


	@Override
	public void createToken() {
		for(int i=0;i<25;i++) {
			this.tokens.add(new Token(this.name));
		}		
	}


	@Override
	public List<Token> getToken() {
		return this.tokens;
	}
	
	@Override
	public synchronized void takeTurn() {
		
		//viene scelto l'ultimo token
		Token t =this.tokens.get(this.tokens.size()-1);
		
		controlGameModality();

		Hole f=null;
		AtomicInteger indexColumn = null;
		try {
			indexColumn=Mediator.getInstance().get();
			f = getHoleChoosed(indexColumn);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ArrayList<Hole> l=Match.getInstance().getBoard().getColumnsList(indexColumn.get());
		//aggiornamento dei fori nella colonna
		Match.getInstance().getBoard().addToken(l);
		//posizionamento del token nel foro
		f.setToken(t);
		//il foro viene aggiunto in una lista per ricordare i fori con token di questo giocatore
		this.addHole(f);
		System.out.println("new: "+f);
		//viene inviato al subject(in questo caso il Match) il dato dell'utimo foro inserito
		//per consentire un aggiornamento dell'interfaccia
		Match.getInstance().setLastForo(f);
		//il valore viene rimesso a null altrimenti non si ferma
		Mediator.getInstance().setColChoosed();
			
		//viene rimosso l'ultimo token
		this.tokens.remove(this.tokens.size()-1);
	}
	
	


	private void controlGameModality() {
		Predicate<Observer> pred=(obs)->{
			return obs.getClass().toString().equals("class net.studionotturno.ForzaQuattro.ui.commandLine.Controller");
		};
		
		if(Match.getInstance().getObservers().stream().filter(pred).count()>0) {
			try {
				MainViewCMD.getInstance().aspettaInput();
			} catch (InterruptedException | IOException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
	}


	@Override
	public void addHole(Hole f) {
		this.foriFull.add(f);
		
	}


	@Override
	public List<Hole> getFullHoles() {
		return this.foriFull;
	}


	@Override
	public void setStrategy(Strategy strategy) {
	}


	@Override
	public Strategy getStategy() {
		return null;
	}
	
}