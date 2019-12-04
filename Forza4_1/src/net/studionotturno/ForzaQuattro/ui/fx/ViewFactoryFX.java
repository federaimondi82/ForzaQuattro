package net.studionotturno.ForzaQuattro.ui.fx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.studionotturno.ForzaQuattro.domain.MainElements.Main;
import net.studionotturno.ForzaQuattro.ui.ViewFactory;

public class ViewFactoryFX  implements ViewFactory{

	ExecutorService exec=Executors.newCachedThreadPool();
	
	@Override
	public void getView() {
		Runnable r1=()->{
			MainViewFX.main(Main.getArgs());
		};
		
		exec.execute(r1);
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
