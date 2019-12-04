package net.studionotturno.ForzaQuattro.ui.observer;

import java.util.List;

import net.studionotturno.ForzaQuattro.domain.MainElements.Hole;
import net.studionotturno.ForzaQuattro.domain.MainElements.Match;
import net.studionotturno.ForzaQuattro.domain.MainElements.Winner;
import net.studionotturno.ForzaQuattro.domain.PlayerFactory.Player;

/**
 * Secondo il design pattern Observer, un Observer fornisce una interfaccia per gli oggetti che devono essere notificati dal Subject.
 * Rappresenta una UI, che sia una interfaccia grafica o una a linea di comando.
 * @see Subject
 */
public interface Observer {

	/**
	 * Consente di aggiornare una Vista in base ai dati del Subject
	 */
	public void update(Subject subject);

	public void updateWinner(Subject subject,Winner<Player, List<Hole>> winner);

}