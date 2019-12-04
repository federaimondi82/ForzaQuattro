package net.studionotturno.ForzaQuattro.domain.Strategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import net.studionotturno.ForzaQuattro.domain.MainElements.Hole;
import net.studionotturno.ForzaQuattro.domain.MainElements.Match;
import net.studionotturno.ForzaQuattro.domain.PlayerFactory.Player;

public class Level_1 implements Strategy{

	
	@Override
	public ArrayList<Hole> getColChoosed(Player player) {

		ArrayList<Hole> colChoosed=null;
		
		//restituisce tutte le colonne che non sono piene
		//possono non essere presenti tutte le chiavi
		Map<Integer,ArrayList<Hole>> columnsNotFull=new HashMap<Integer,ArrayList<Hole>>();
		columnsNotFull=Match.getInstance().getBoard().getColonneLibere();
		
		AtomicInteger j=new AtomicInteger(columnsNotFull.size());
		
		Predicate<Entry<Integer,ArrayList<Hole>>> pred=(element)->{
			Random rand=new Random();
			int i=(rand.nextInt(j.get())+1);
			return element.getKey()==i;
		};
		//fino a che non c'è un riscontro tra il numero casuale e la chiave si ripete l'operazione
		while(colChoosed==null) {
			try {
				colChoosed=columnsNotFull.entrySet().stream().filter(pred).findFirst().get().getValue();
			}catch(NoSuchElementException e) {}
			
		}
		return colChoosed;
	}

}
