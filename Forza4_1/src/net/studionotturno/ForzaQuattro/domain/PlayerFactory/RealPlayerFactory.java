package net.studionotturno.ForzaQuattro.domain.PlayerFactory;

public class RealPlayerFactory implements PlayerFactory{

	@Override
	public Player createPlayer() {
		return new RealPlayer("John_"+(int)(Math.random()*10000));
	}

}
