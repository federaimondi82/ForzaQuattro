package net.studionotturno.ForzaQuattro.domain.MainElements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.studionotturno.ForzaQuattro.domain.PlayerFactory.PlayerRegistry;
import net.studionotturno.ForzaQuattro.domain.Strategy.StrategyRegister;
import net.studionotturno.ForzaQuattro.ui.ViewsRegister;
import net.studionotturno.ForzaQuattro.ui.commandLine.MainViewCMD;
import net.studionotturno.ForzaQuattro.ui.fx.Controller;
import net.studionotturno.ForzaQuattro.ui.fx.MainViewFX;
import net.studionotturno.ForzaQuattro.ui.observer.Observer;

public class Main {
	
	private static String[] args2;//variabile per tenere traccia degli argomenti in input
	private static BufferedReader reader= new BufferedReader(new InputStreamReader(System.in));
	
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException, InterruptedException, ExecutionException {
		
		args2=args;
		ExecutorService exec=Executors.newCachedThreadPool();
		
		loadComponents();
		
		setupPlayers();
		setupMod();

		Runnable r2=()->{
			Match.getInstance().playMatch();
		};

		exec.execute(r2);
		
	}
	
	/**Dopo aver cacirato tutti i componenti viene richiesto all'utente che tipo di gicoo vuole giocare
	 * @throws IOException
	 */
	private static void setupPlayers() throws IOException {
				
		System.out.println("scegli difficoltà");
		StrategyRegister.getInstance().getStrategies().entrySet().stream().forEach(strat->{
			System.out.println(strat.getKey());
		});
		String s=reader.readLine();
		String bot="";
		switch(s) {
			case "level1": bot="bot1";break;
			case "level2": bot="bot2";break;
			case "level3": bot="bot3";break;
			case "level4": bot="bot4";break;
			case "level5": bot="bot5";break;
		}
		
		Match.getInstance().setPlayers(
				PlayerRegistry.getInstance().getPlayer(bot).createPlayer(),
				PlayerRegistry.getInstance().getPlayer("real").createPlayer());	
	}
	
	private static void setupMod() throws IOException {
		
		System.out.println("scegli modalità");
		ViewsRegister.getInstance().getViews()
			.entrySet().stream().forEach(view->System.out.println(view.getKey()));
		String v=reader.readLine();
		ViewsRegister.getInstance().getView(v).getView();
	}
	

	/**
	 * 
	 * Carica tutte le diverce classi necessarie per caricare i Register dei Player e delle Strategy
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IOException
	 */
	public static void loadComponents() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		
		URL[] urlsPlayers= PlayerRegistry.getInstance().initPlayerFiles();
		PlayerRegistry.getInstance().loadLoader(urlsPlayers);
		
		URL[] urlStrategies= StrategyRegister.getInstance().initPlayerFiles();
		StrategyRegister.getInstance().loadLoader(urlStrategies);
		
		URL[] urlViews= ViewsRegister.getInstance().initPlayerFiles();
		ViewsRegister.getInstance().loadLoader(urlViews);
	}

	
	
	public static String[] getArgs() {
		return args2;		
	}
	
}
