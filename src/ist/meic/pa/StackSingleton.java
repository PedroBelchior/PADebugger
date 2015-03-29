package ist.meic.pa;
import java.lang.reflect.*;
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
	
	
	public static void storePrevious(Object object, String className, String metodo, Object[] args) {
		history.push(new TraceObject(object, className, metodo, args));
	}
	
	public static void currentState() {
	
		Object obj = history.lastElement().objecto;
		System.out.println("Called Object:" + obj);
		Field[] field = null;
		if(obj != null) {
			field = obj.getClass().getDeclaredFields();
			
			for(int k = 0; k < field.length; k++) {
				System.out.println("Fields:" + field[k].getName());
			}
		}
		
		for(int i = history.size() - 1; i >= 0; i--) {
			TraceObject tmpObj = history.get(i);
			System.out.print(tmpObj.classe + "." + tmpObj.metodonome + "(");
			for (int j = 0; j < tmpObj.argumentos.length; j++){
				if (j > 1) System.out.print(",");
				System.out.print(tmpObj.argumentos[j]);
			}
			System.out.println(")");
		}
		
		System.out.println("Fim da Impress‹o!");
		
	}
	
	public static TraceObject restoreState() {
		//undo all actions until size == state
		TraceObject popObj = history.pop();
		return popObj;
	}
	//public stack void push();;
}
