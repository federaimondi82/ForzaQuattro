package net.studionotturno.ForzaQuattro.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

import net.studionotturno.ForzaQuattro.domain.MainElements.Match;
import net.studionotturno.ForzaQuattro.domain.PlayerFactory.PlayerRegistry;
import net.studionotturno.ForzaQuattro.ui.fx.MainViewFX;

class GuiFxTest {

	@Test
	void testStartViewFx() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		
		
		String[] args=new String[1];
		ExecutorService exec=Executors.newCachedThreadPool();
		
		URL[] urls= PlayerRegistry.getInstance().initPlayerFiles();
		PlayerRegistry.getInstance().loadLoader(urls);
		
		Match.getInstance().setPlayers(
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer(),
				PlayerRegistry.getInstance().getPlayer("bot1").createPlayer());
		
		Runnable r2=()->{
			Match.getInstance().playMatch();
		};
		
		Runnable r1=()->{
			MainViewFX.main(args);
		};
		
		exec.execute(r1);
		exec.execute(r2);
		
		
		
	}

}
