package ist.meic.pa;
import java.lang.reflect.*;
import java.util.NoSuchElementException;
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
		if (history.size()==0) {
			// System.out.println("Stack is currently empty.");
			return;
		} else {
			// System.out.println("Stack is currently "+history.size()+" levels deep.");
		}
		Object obj = history.lastElement().objecto;
		System.out.println("Called Object:  " + obj);
		Field[] field = null;
		
		
		try {
			field = Class.forName(history.lastElement().classe).getDeclaredFields();

			if(field.length > 0) {
				System.out.print("       Fields:  ");
			}
			for(int k = 0; k < field.length; k++) {
				if(k > 0) System.out.print(" ");
				System.out.print(field[k].getName());
			}
			System.out.println();
			

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println("Call Stack:  ");
		for(int i = history.size() - 1; i >= 0; i--) {
			TraceObject tmpObj = history.get(i);
			System.out.print(tmpObj.classe + "." + tmpObj.metodonome + "(");
			for (int j = 0; j < tmpObj.argumentos.length; j++){
				if (j > 0) System.out.print(",");
				System.out.print(tmpObj.argumentos[j]);
			}
			System.out.println(") - "+tmpObj.argumentos.length);
		}
		
		//System.out.println("Fim da Impressao!");
		
	}
	
	public static TraceObject returnlastObj() {
		try {		
			return history.lastElement();
		} catch (NoSuchElementException nsee) {
			return null;
		}
	}
	
	public static TraceObject restoreState() {
		TraceObject popObj = history.pop();
		return popObj;
	}

}
