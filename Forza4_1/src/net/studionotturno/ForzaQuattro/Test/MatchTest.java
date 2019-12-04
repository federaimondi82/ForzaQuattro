package net.studionotturno.ForzaQuattro.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.studionotturno.ForzaQuattro.domain.MainElements.Hole;
import net.studionotturno.ForzaQuattro.domain.MainElements.Match;
import net.studionotturno.ForzaQuattro.domain.MainElements.Token;
import net.studionotturno.ForzaQuattro.domain.PlayerFactory.PlayerRegistry;
import net.studionotturno.ForzaQuattro.domain.Strategy.StrategyRegister;
import net.studionotturno.ForzaQuattro.ui.ViewsRegister;

class MatchTest {
	
	@BeforeEach
	public void loader() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException{
		
		URL[] urlStrategies= StrategyRegister.getInstance().initPlayerFiles();
		StrategyRegister.getInstance().loadLoader(urlStrategies);
		
		URL[] urls= PlayerRegistry.getInstance().initPlayerFiles();
		PlayerRegistry.getInstance().loadLoader(urls);
	}
	
	@AfterEach
	public final void clearGame() {
		Match.getInstance().getPlayers().clear();
	}
	

	@Test
	public final void testCreatePlayers(){
		
		Match.getInstance().setPlayers(
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer(),
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer());
		
		assertEquals(2, Match.getInstance().getPlayers().size());		
	}
	
	@Test
	public final void testBoardInNotEmpty() {
		Match.getInstance().setPlayers(
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer(),
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer());
		
		assertEquals(42, Match.getInstance().getBoard().getHoles().size());
	}
	
	@Test
	public final void testAddTokensIndexOut() {
		Match.getInstance().setPlayers(
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer(),
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer());
		riempiBoard();
		
		//inserisce un token nella prima colonna per riempirla
		Predicate<Hole> pred=(foro)->{return foro.getCol()==0 && foro.getRow()==5;};
		Match.getInstance().getBoard().getHoles().stream()
					.filter(pred)
					.collect(Collectors.toList())
					.get(0).setToken(new Token("A"));
		
		Map<Integer,ArrayList<Hole>> map=new HashMap<Integer,ArrayList<Hole>>();
		map=Match.getInstance().getBoard().getColonneLibere();
		
		map.entrySet().stream().forEach(foro->{
			System.out.println(foro.getKey()+" - "+foro.getValue().toString());
		});
		
		Predicate<Hole> pred2=(foro)->{return foro.getCol()==0 && foro.getRow()==6;};
		
		assertThrows(IndexOutOfBoundsException.class,() -> Match.getInstance().getBoard().getHoles().stream()
				.filter(pred2)
				.collect(Collectors.toList())
				.get(0).setToken(new Token("A")));
	}
	
	private void riempiBoard() {
		//aggint di tutti i token quasi fino all'orlo
		for(int i=0;i<5;i++) {
			for(int j=0;j<7;j++) {
				for (Hole foro : Match.getInstance().getBoard().getHoles()) {
					if(foro.getCol()==j && foro.getRow()==i) {
						foro.setToken(new Token("A"));
					}
				}				
			}
		}
	}

	@Test
	public final void testPlayRoundNoView() {
		Match.getInstance().setPlayers(
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer(),
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer());
		
		Match.getInstance().playMatch();
	}
	
	
	@Test
	public final void testPlayRoundUsingViewJAVAFX() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		
		URL[] urlViews= ViewsRegister.getInstance().initPlayerFiles();
		ViewsRegister.getInstance().loadLoader(urlViews);
		
		ViewsRegister.getInstance().getView("JavaFx").getView();
		
		Match.getInstance().setPlayers(
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer(),
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer());
		
		Match.getInstance().playMatch();
	}
	
	@Test
	public final void testPlayRoundUsingViewCMD() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		
		URL[] urlViews= ViewsRegister.getInstance().initPlayerFiles();
		ViewsRegister.getInstance().loadLoader(urlViews);
		
		ViewsRegister.getInstance().getView("cmd").getView();
		
		Match.getInstance().setPlayers(
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer(),
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer());
		
		Match.getInstance().playMatch();
	}
	

}
