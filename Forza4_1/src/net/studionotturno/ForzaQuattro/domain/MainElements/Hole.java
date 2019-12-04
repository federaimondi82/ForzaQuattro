package net.studionotturno.ForzaQuattro.domain.MainElements;

import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Fa parte della board;
 * ha la responsabilità di tenere traccia dei token nel gioco ( Expert );
 * 
 * Viene usato nelle combinazioni pe trovare la fila di 4 token
 * 
 * @see Comb
 */
public class Hole {

	private Integer col;
	private Integer row;
	private Token token;
	private Hole below;
	private Integer id;
	private Integer x;
	private Integer y;

	/**
	 * 
	 * @param col
	 * @param row
	 * @param token
	 */
	public Hole(Integer col, Integer row, Integer id,Token token) {
		this.col=col;
		this.row=row;
		this.id=id;
		this.token=token;
	}
	
	public void setBelow(Hole below) {
		
		try {
			this.below=below;
		}catch(IndexOutOfBoundsException e) {}
		
		/*Predicate<Hole> pred=(foro)->{return foro.col==this.col && foro.row==(this.row-1);};
		try {
			this.below=Match.getInstance().getBoard().getHoles()
					.stream().filter(pred).findFirst().get();
		}catch(IndexOutOfBoundsException e) {}*/
		
	}
	
	public Hole getBelow() {
		try {
			return this.below;	
		}catch(NullPointerException e) {}
		return below;
		
	}

	public boolean isEmpty() {
		return this.token==null;
	}
	
	public void setToken(Token t) {
		this.token=t;
	}

	public Token getToken() {
		return this.token;
	}

	

	@Override
	public String toString() {
		return "(" + col + "," + row + "), id=" + id + "]";
	}

	/**
	 * @return the col
	 */
	public Integer getCol() {
		return col;
	}

	/**
	 * @return the row
	 */
	public Integer getRow() {
		return row;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getX() {
		return this.x;
	}

	public Integer getY() {
		return this.y;
	}
	

}