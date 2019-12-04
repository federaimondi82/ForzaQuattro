package net.studionotturno.ForzaQuattro.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import net.studionotturno.ForzaQuattro.domain.MainElements.Board;
import net.studionotturno.ForzaQuattro.domain.MainElements.Match;

class BoardTest {

	@Test
	void testCreateFori() {
		Match.getInstance().getBoard().getHoles().stream().forEach(foro->{
			assertTrue(foro!=null);
		});
		Match.getInstance().getBoard().getHoles().stream().forEach(foro->{
			try {
				System.out.println(foro.toString()+" below: "+foro.getBelow().toString());
			}catch(NullPointerException e) {}
		});
	}
	
	@Test
	public final void testPrintHoles() {
		Match.getInstance().getBoard().getHoles().forEach(hole->{
			System.out.println(hole.toString());
		});
	}
	
	@Test
	public final void testPrintRows() {
		
		System.out.println("\n---0---1---2---3---4---5---6--");
		for(int i=5;i>=0;i--) {
			System.out.print(i);
			Match.getInstance().getBoard().getRowsList(i).stream().forEach(hole->{
				if(!hole.isEmpty()) {
					System.out.print("| O ");
				}else System.out.print("|   ");
			});
			System.out.print("|");
			System.out.println("\n------------------------------");
		}
	}

}
