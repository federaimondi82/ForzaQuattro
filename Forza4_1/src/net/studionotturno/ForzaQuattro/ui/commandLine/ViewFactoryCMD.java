package net.studionotturno.ForzaQuattro.ui.commandLine;

import net.studionotturno.ForzaQuattro.ui.ViewFactory;

public class ViewFactoryCMD implements ViewFactory{
	
	@Override
	public void getView() {
		MainViewCMD.getInstance();
	}

}
