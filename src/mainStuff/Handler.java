package mainStuff;
import manager.KeyManager;

public class Handler {
	
	private Engine e;
	
	public Handler(Engine e){
		this.e = e;
	}
	
	public KeyManager getKeyManager(){
		return e.getKeyManager();
	}

}
