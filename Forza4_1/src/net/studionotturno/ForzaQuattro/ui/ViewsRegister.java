package net.studionotturno.ForzaQuattro.ui;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

public class ViewsRegister {
	
	
	private static ViewsRegister instance;
	private HashMap<String,ViewFactory> register;
	
	
	private ViewsRegister() {
		this.register=new HashMap<String, ViewFactory>();
	}
	
	public static ViewsRegister getInstance() {
		if(instance==null) instance=new ViewsRegister();
		return instance;
	}
	
	public void addView(String name,ViewFactory view) {
		this.register.put(name, view);
	}
	
	
	public HashMap<String,ViewFactory> getViews(){
		return this.register;
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
		File newPlayerTXT=new File(System.getProperty("user.dir")+"\\plugin\\views.txt");
						
		//lettura del file di testo con le classi
		List<String> list=Files.readAllLines( newPlayerTXT.toPath() );
				
		try(URLClassLoader loader = new URLClassLoader( urls )){
			for(String s1 : list) {
			String s2[] =s1.split(" ");//splitta la linea di testo
			Class<? extends ViewFactory> clazz= Class//carica le classi
			.forName(s2[1],true,loader)
			.asSubclass(ViewFactory.class);				
		//memorizza le classi del register con il proprio nome
			addView(s2[0], clazz.getConstructor().newInstance() );				
		}			
		}
	}

	public ViewFactory getView(String v) {
		return this.register.get(v);
		
	}
	

}
