package net.studionotturno.ForzaQuattro.ui.fx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

public class HoleUI extends Circle/* Ellipse*/{
	
	private boolean selected;
	private Color color;
	
	public HoleUI() {
		super();
		this.setCenterX(10);
		this.setCenterY(10);
		this.setRadius(30.0f);
		this.selected=false;
		this.setStroke(Color.BLACK);
		this.color=Color.WHITE;
		this.prefHeight(17.0);	this.prefWidth(9.0);
		this.setSelected(false);
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		if(selected==true)this.setFill(this.color);
		else this.setFill(Color.WHITE);
		this.selected = selected;
	}
	
	public void setColor(Color color) {
		this.color=color;
		this.setFill(color);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setWinnerColor(Color color) {
		if(color.equals(Color.YELLOW)) this.color=Color.GOLDENROD;
		else if(color.equals(Color.RED)) this.color=Color.DARKRED;
		
		this.setFill(this.color);
	}
	
	

}
