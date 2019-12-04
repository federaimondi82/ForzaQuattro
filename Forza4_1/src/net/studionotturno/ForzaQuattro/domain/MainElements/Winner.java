package net.studionotturno.ForzaQuattro.domain.MainElements;

import java.util.List;

import net.studionotturno.ForzaQuattro.domain.PlayerFactory.Player;

public class Winner <P extends Player,L extends List> {

	private P player;
	private L list;
	
	/**
	 * @param player
	 * @param list
	 */
	public Winner(P player,L list) {
		this.player=player;
		this.list=list;
	}
	/**
	 * @return the player
	 */
	public P getPlayer() {
		return player;
	}

	/**
	 * @return the list
	 */
	public L getList() {
		return list;
	}
	/*
	public void put(Player player, List<Hole> list) {
		this.player=player;
		this.list=list;
		
	}
	*/
	
}
