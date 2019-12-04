package net.studionotturno.ForzaQuattro.ui.fx;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import net.studionotturno.ForzaQuattro.domain.MainElements.Hole;
import net.studionotturno.ForzaQuattro.domain.MainElements.Match;
import net.studionotturno.ForzaQuattro.domain.MainElements.Mediator;
import net.studionotturno.ForzaQuattro.domain.MainElements.Winner;
import net.studionotturno.ForzaQuattro.domain.PlayerFactory.Player;
import net.studionotturno.ForzaQuattro.ui.observer.Observer;
import net.studionotturno.ForzaQuattro.ui.observer.Subject;

/**
 * 
 * Questa classe svolge sia il compito di Controller ( MVC ) della GUI con JavaFX
 * che come concrete observer del design pattern Observer;
 * 
 * Si è decido di unire le responsabilità di un
 *  Controller MVC ( svolte il ruolo di catturare le informazioni dalla GUI ),
 * con quelle di concreteObserver( consente di aggiornare i componenti dell GUI prendendo i dati dal modello di dominio (model) )
 * per centralizzare il collegamento con la GUI;
 * 
 * Contiene le informazioni che devono essere aggiornate secondo lo stato del Subject.
 * 
 * @author Federico Raimondi
 *
 * @see Observer
 * @see Subject
 */
public class Controller implements Initializable,Observer{

	@FXML
	public GridPane grid_0;
	
	@FXML
	public Button btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6;
	
	private HashMap<Integer,Button> buttonMap;
	
	private Map<Hole,HoleUI> mapper;	
	private Winner<Player, List<Hole>> winner;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.mapper=new HashMap<Hole,HoleUI>();
		
		this.buttonMap=new HashMap<Integer,Button>();
		this.buttonMap.put(0, btn_0);this.buttonMap.put(1, btn_1);
		this.buttonMap.put(2, btn_2);this.buttonMap.put(3, btn_3);
		this.buttonMap.put(4, btn_4);this.buttonMap.put(5, btn_5);
		this.buttonMap.put(6, btn_6);
		
		//aggancia il subject(Match) all'observer
		Match.getInstance().addObserver(this);
		setComponents();
	}
	

	
	public void setComponents() {
		//assegnamento dei componenti HoleUI come fori sulla View
		Match.getInstance().getBoard().getHoles().stream().forEach(foro->{
			HoleUI r=new HoleUI();
			GridPane.setHalignment(r, HPos.CENTER);
			GridPane.setValignment(r, VPos.CENTER);
			
			this.grid_0.add(r, foro.getCol(), foro.getRow());
			//this.grid_0.add(new Label(foro.getId()), foro.getCol(), foro.getRow());
			this.mapper.put(foro, r);
		});
	}
	
	public void click_Btn_0() {
		try {
			Mediator.getInstance().add(new AtomicInteger(0));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void click_Btn_1() {
		try {
			Mediator.getInstance().add(new AtomicInteger(1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void click_Btn_2() {
		try {
			Mediator.getInstance().add(new AtomicInteger(2));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void click_Btn_3() {
		try {
			Mediator.getInstance().add(new AtomicInteger(3));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void click_Btn_4() {
		try {
			Mediator.getInstance().add(new AtomicInteger(4));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void click_Btn_5() {
		try {
			Mediator.getInstance().add(new AtomicInteger(5));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void click_Btn_6() {
		try {
			Mediator.getInstance().add(new AtomicInteger(6));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Subject subject) {
		
		disableButton();
		
		Platform.runLater(repaint);	
	}

	
	private void disableButton() {
		//scorre la riga in alto per controllare se ci sono token
		//se ci sono cerca il pulsante relativo e lo disabilita
		Match.getInstance().getBoard().getRowsList(5)
			.stream().filter(hole->!hole.isEmpty()).forEach(hole->{
				//blocca i pulsanti
				//hole.getCol()
				this.buttonMap.entrySet().stream().filter(btn->{
					return btn.getKey()==hole.getCol();
				}).forEach(btn2->{
					btn2.getValue().setDisable(true);
				});
			});		
	}


	Runnable repaint=()->{

		HoleUI r=this.mapper.get(Match.getInstance().getLastForo());
		
		
		if(Match.getInstance().getPseudoRound()%2==0)r.setColor(Color.YELLOW);
		else r.setColor(Color.RED);
		r.setSelected(true);
	};

	@Override
	public void updateWinner(Subject subject,Winner<Player, List<Hole>> winner) {
		this.winner=winner;
		System.out.println("winner: "+winner.getPlayer().toString());
		Platform.runLater(repaintWinner);
	}
	
	Runnable repaintWinner=()->{
		Player p=this.winner.getPlayer();
		List<Hole> list=this.winner.getList();
		
		
		list.stream().forEach(foro->{
			HoleUI r=(HoleUI)this.mapper.entrySet().stream().filter(element->{
				return element.getKey().getCol()==foro.getCol() && element.getKey().getRow()==foro.getRow();
			}).limit(1).collect(Collectors.toList()).get(0).getValue();
			
			r.setWinnerColor(r.getColor());
		});
	};

	
}