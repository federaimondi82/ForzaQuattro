package net.studionotturno.ForzaQuattro.domain.MainElements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Contiene una serie di holes (6 rows e 7 columns di holes) dove vengono posizionati i gettoni (token);
 * 
 * dovendo mantenere la memoria dei holes è responsabile anche di crearli
 */
public class Board {

	private static final int rows = 6;
	private static final int columns = 7;
	private List<Hole> holes;
	private HashMap<Integer,ArrayList<Hole>> columnsList;
	private HashMap<Integer,ArrayList<Hole>> rowsList;
	
	
	/**
	 * Costruttore, costruisce un oggetto di tipo Board che contiene una serie di fori 
	 * @see Hole
	 */
	public Board() {
		this.holes=new ArrayList<Hole>();
		this.columnsList=new HashMap<Integer,ArrayList<Hole>>();
		this.rowsList=new HashMap<Integer,ArrayList<Hole>>();
		createHoles();
	}
	
	/**
	 * Consente di aggiungre una serie di fori alla board,
	 * viene richiamto direttamente dal costruttore
	 */
	private void createHoles() {
		int count=0;
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				Hole h=new Hole(j,i,count,null);
				this.holes.add(h);
				count++;
			}
		}	
		
		trackColumn();
		trackRows();
	}

	private void trackColumn() {
		for(int i=0;i<columns;i++) {
			ArrayList<Hole> list=new ArrayList<Hole>();//lista dei fori in colonna
			for(int j=0;j<rows;j++) {
				Hole h=this.getHole(j,i);
				Hole below=this.getHole(h.getId()-7);
				h.setBelow(below);
				list.add(h);
			}
			this.columnsList.put(i,list);//lista delle colonne di fori
		}		
	}
	
	private void trackRows() {
		for(int i=0;i<rows;i++) {
			ArrayList<Hole> list=new ArrayList<Hole>();//lista dei fori in riga
			for(int j=0;j<columns;j++) {
				Hole h=this.getHole(i,j);
				list.add(h);
			}
			this.rowsList.put(i,list);//lista delle colonne di fori
		}		
	}
	
	public ArrayList<Hole> getColumnsList(int i){
		return this.columnsList.get(i);
	}
	
	public ArrayList<Hole> getRowsList(int i){
		return this.rowsList.get(i);
	} 
	
	public HashMap<Integer,ArrayList<Hole>> getRowsList(){
		return this.rowsList;
	}

	/**La lista dei fori che fanno parte della Board, ogni Hole ha le proprie coordinate nella Board
	 * @return
	 */
	public List<Hole> getHoles(){
		return this.holes;
	}
	
	/**
	 * @return le righe della board
	 */
	public int getRighe() {
		return rows;
	}
	
	/**
	 * @return le colonne della board
	 */
	public int getColonne() {
		return columns;
	}
	
	public Hole getHole(int riga,int colonna) {
		return this.holes.stream().filter(foro->{
			return foro.getCol()==colonna && foro.getRow()==riga;
		}).findFirst().get();
	}
	
	public Hole getHole(Integer id) {
		Predicate<Hole> pred=(hole)->hole.getId()==id;
		Hole h=null;
		try {
			h=this.holes.stream().filter(pred).findFirst().get();
		}catch(IndexOutOfBoundsException | NoSuchElementException e) {}
		return h;
	}
	
	public Map<Integer,ArrayList<Hole>> getColonneLibere() {

		Predicate<Entry<Integer,ArrayList<Hole>>> pred=(list)->list.getValue().size()>0;
		return  this.columnsList.entrySet().stream().filter(pred)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public void addToken(ArrayList<Hole> col) {
		Predicate<Entry<Integer,ArrayList<Hole>>> pred=(list->{
			return list.getValue().equals(col);
		});
		
		ArrayList<Hole> list=this.columnsList.entrySet().stream().filter(pred).findFirst().get().getValue();
		
		list.remove(0);
		System.out.println("");
		
	}


	
}