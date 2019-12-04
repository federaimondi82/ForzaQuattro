package net.studionotturno.ForzaQuattro.ui.commandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

import net.studionotturno.ForzaQuattro.domain.MainElements.Hole;
import net.studionotturno.ForzaQuattro.domain.MainElements.Match;
import net.studionotturno.ForzaQuattro.domain.MainElements.Mediator;
import net.studionotturno.ForzaQuattro.domain.MainElements.Winner;
import net.studionotturno.ForzaQuattro.domain.PlayerFactory.Player;
import net.studionotturno.ForzaQuattro.ui.observer.Observer;
import net.studionotturno.ForzaQuattro.ui.observer.Subject;

public class Controller implements Observer{
	
	private BufferedReader reader2;
	private ExecutorService exec;	
	
	public Controller() {
		this.exec=Executors.newCachedThreadPool();
	}
	
	Runnable runna=()->{
		Integer i=null;
		
		InputStreamReader input=new InputStreamReader(System.in);
		this.reader2=new BufferedReader(input);
		
		while(i==null) {
			try {
				i=Integer.parseInt(this.reader2.readLine());
			} catch (NullPointerException | IndexOutOfBoundsException | NumberFormatException | IOException e)  {
				System.out.println("Non ho capito");
			}
		}
		try {
			Mediator.getInstance().add(new AtomicInteger(i));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	};
	
	public void receaveInput() throws InterruptedException, IOException, ExecutionException {
		exec.execute(runna);
	}

	

	@Override
	public void update(Subject subject) {
		
		String one=Match.getInstance().getPlayers().get(0).getName();
		
		System.out.println("---0---1---2---3---4---5---6--");
		for(int i=5;i>=0;i--) {
			System.out.print(i);
			Match.getInstance().getBoard().getRowsList(i).stream().forEach(hole->{
				if(!hole.isEmpty()) {
					if(hole.getToken().getId().equals(one))System.out.print("| O ");
					else System.out.print("| * ");
				}else System.out.print("|   ");
			});
			System.out.print("|");
			System.out.println("\n------------------------------");
		}
		
	}

	@Override
	public void updateWinner(Subject subject, Winner<Player, List<Hole>> winner) {
		System.out.println("End Game");
		System.out.println("The winner is: "+winner.getPlayer().getName());
		
	}

}
