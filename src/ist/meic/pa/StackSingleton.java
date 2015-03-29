package ist.meic.pa;
import java.util.Stack;

public class StackSingleton {

	public static StackSingleton singleton = null;
	
	public static Stack<TraceObject> history = new Stack<TraceObject>();
	
	public StackSingleton(){
	}
	
	public static StackSingleton getInstance(){
		if (singleton == null )
			singleton = new StackSingleton();
		return singleton;
	}
	
	
	public static void storePrevious(Object object, String className, String fieldName) {
		history.push(new TraceObject(object, className, fieldName));
	}
	
	public static void currentState() {
		
		System.out.println("Imprimir a Stack");
		Object obj = history.get(0).objecto;
		System.out.println("Called Object:" + obj);
		System.out.println("Fields:" + obj.getClass().getDeclaredFields());
		
		for(int i = 0; i < history.size(); i++) {
			System.out.println(history.get(i).classe);
		}
		
		System.out.println("Fim da Impressão!");
		
	}
	
	public static TraceObject restoreState() {
		//undo all actions until size == state
		TraceObject popObj = history.pop();
		return popObj;
	}
	//public stack void push();
}
