package net.studionotturno.ForzaQuattro.domain.Strategy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

public class StrategyRegister {
	
	private static StrategyRegister instance;
	private HashMap<String,Strategy> registry;
	
	private StrategyRegister(){
		this.registry=new HashMap<String, Strategy>();
	}
	
	public void addStrategy(String name,Strategy strategy) {
		this.registry.put(name, strategy);
	}
	
	public static StrategyRegister getInstance() {
		if(instance==null) instance=new StrategyRegister();		
		return instance;
	}
	
	public Strategy getStategy(String name) {
		return this.registry.get(name);
	}
	
	public HashMap<String,Strategy> getStrategies(){
		return this.registry;
	}
	
	public URL[] initPlayerFiles() throws MalformedURLException {
		//recupero della cartella dove sono i file compilati delle Strategy
		String folder = System.getProperty("user.dir");	
		File newPlayerFolder=new File(folder+"\\plugin\\");
		
		//creazione URL da passare al class loader
		URL[] urls= {newPlayerFolder.toURI().toURL()};
		return urls;
	}
	
	public void loadLoader(URL[] urls) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		//legge un file di testo con i factory da caricare
		File newPlayerTXT=new File(System.getProperty("user.dir")+"\\plugin\\strategy.txt");
						
		//lettura del file di testo con le classi
		List<String> list=Files.readAllLines( newPlayerTXT.toPath() );
				
		try(URLClassLoader loader = new URLClassLoader( urls )){
			for(String s1 : list) {
			String s2[] =s1.split(" ");//splitta la linea di testo
			Class<? extends Strategy> clazz= Class//carica le classi
			.forName(s2[1],true,loader)
			.asSubclass(Strategy.class);				
		//memorizza le classi del register con il proprio nome
			addStrategy(s2[0], clazz.getConstructor().newInstance() );				
		}			
		}
	}
	

}
