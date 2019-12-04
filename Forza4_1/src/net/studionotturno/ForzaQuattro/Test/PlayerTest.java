package net.studionotturno.ForzaQuattro.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.studionotturno.ForzaQuattro.domain.MainElements.Match;
import net.studionotturno.ForzaQuattro.domain.PlayerFactory.Player;
import net.studionotturno.ForzaQuattro.domain.PlayerFactory.PlayerRegistry;


class PlayerTest {
		
	@BeforeEach
	public void loadPlayer() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException{
		URL[] urls= PlayerRegistry.getInstance().initPlayerFiles();
		PlayerRegistry.getInstance().loadLoader(urls);
	}
	
	@AfterEach
	public final void clearGame() {
		Match.getInstance().getPlayers().clear();
	}

	@Test
	public final void testPlayersIsNull() {
		Match.getInstance().getPlayers().clear();
		Match.getInstance().setPlayers(null, null);
		Match.getInstance().getPlayers().forEach(player->assertNull(player));
	}
	
	@Test
	public final void testCreateToken() {
		Match.getInstance().setPlayers(
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer(),
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer());
		
		Match.getInstance().getPlayers().stream().forEach(player->{
			assertNotEquals(24,player.getToken().size());
			assertEquals(25,player.getToken().size());
			assertTrue(!(player.getToken().size()<25), "");
			assertTrue(!(player.getToken().size()>25), "");
		});
	}

}
