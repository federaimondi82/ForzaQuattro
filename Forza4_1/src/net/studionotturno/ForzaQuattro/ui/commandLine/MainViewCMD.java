package net.studionotturno.ForzaQuattro.ui.commandLine;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import net.studionotturno.ForzaQuattro.domain.MainElements.Match;

public class MainViewCMD{
	
	
	private Controller controller;
	private static MainViewCMD instance;
	
	
	public MainViewCMD() {
		this.controller=new Controller();
		Match.getInstance().addObserver(this.controller);
	}
	
	
	public static MainViewCMD getInstance() {
		if(instance==null) instance=new MainViewCMD();
		return instance;
	}


	public void aspettaInput() throws InterruptedException, IOException, ExecutionException {
		this.controller.receaveInput();		
	}

}
