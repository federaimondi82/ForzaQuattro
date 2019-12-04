package net.studionotturno.ForzaQuattro.domain.PlayerFactory;

import java.util.Random;

import net.studionotturno.ForzaQuattro.domain.Strategy.Strategy;

public class Bot1Factory implements PlayerFactory {

	@Override
	public Player createPlayer() {
		Random rand=new Random();
		return new Bot1("Bot-"+rand.nextInt(1001));
	}

}