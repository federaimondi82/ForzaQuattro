package net.studionotturno.ForzaQuattro.domain.MainElements;

import java.util.concurrent.atomic.AtomicInteger;

import net.studionotturno.ForzaQuattro.domain.PlayerFactory.RealPlayer;
import net.studionotturno.ForzaQuattro.ui.fx.Controller;

/**
 * Media le azioni tra controller e input dell'utente;
 * è l'intermediario tra le scelte del RealPlayer usando un controller.
 * Funge anche da sistema Producer/Consumer:<br>
 * Producer perchè riceve l'input da un Controller dopo l'input dell'utente, ma fino a che non avviene<br>
 * mette il thred in stato di waiting;<br>
 * la parte del Producer perchè solo quando l'utente ha scelto qualcosa passa il valore<br>
 *  all'interno del {@link net.studionotturno.ForzaQuattro.domain.PlayerFactory.Player#takeTurn takeTurn}
 * 
 * @author Federico Raimondi
 * 
 * @see Controller
 * @see RealPlayer
 */
public class Mediator {
	
	private AtomicInteger colChoosed;
	private static Mediator instance;
	
	private Mediator(){
	}
	
	public static Mediator getInstance() {
		if(instance==null) instance=new Mediator();
		return instance;
	}
	
	public synchronized AtomicInteger get() throws InterruptedException {
		while(this.colChoosed==null) {
			//System.out.println("Aspetto inserimento realPlayer");
			wait();
		}
		this.notifyAll();
		return colChoosed;
	}
	
	public synchronized void add(AtomicInteger i) throws InterruptedException {
		while(this.colChoosed!=null) {
			//System.out.println("il real ha pigiato qualcosa");
			wait();
		}
		this.notifyAll();
		this.colChoosed=i;
	}

	public void setColChoosed() {
		this.colChoosed=null;
		
	}

	

}
