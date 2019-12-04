package net.studionotturno.ForzaQuattro.ui.fx;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Classe principale per l'avvio dell'interfaccia grafica del giocatore.
 * @author Federico Raimondi
 *
 */
public class MainViewFX extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("gui_1.fxml"));
		
		Scene myScene=new Scene(root);	
		
        primaryStage.setScene(myScene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
