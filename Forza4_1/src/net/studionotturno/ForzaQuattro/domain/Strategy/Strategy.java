package net.studionotturno.ForzaQuattro.domain.Strategy;
import java.util.ArrayList;

import net.studionotturno.ForzaQuattro.domain.MainElements.Hole;
import net.studionotturno.ForzaQuattro.domain.PlayerFactory.Player;

public interface Strategy {
	
	public ArrayList<Hole> getColChoosed(Player player);

}
