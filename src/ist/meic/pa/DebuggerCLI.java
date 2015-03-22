
/*
 * TODO:
 * - Implement code from the slides
 * - Call stack portion is in "Undoable Programs" section
 * - 
 */

package ist.meic.pa;
import javassist.*;

public class DebuggerCLI {
	
	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		System.out.println("test!");
		Translator translator = new ExceptionTranslator();
		ClassPool pool = ClassPool.getDefault(); 
		Loader classLoader = new Loader(); 
		classLoader.addTranslator(pool, translator); 
		String[] restArgs = new String[args.length - 1] ;
		System.arraycopy(args, 1, restArgs, 0, restArgs.length);
		classLoader.loadClass("Test");
		//classLoader.run("Test", null );
	}

	public void Abort(){
		System.exit(0);
	}
	
	public void Info(){}
	
	public void Throw(){}
	
	public void Return(int value){}
	
	public void Get(String fName){}
	
	public void Set(String fName){}
	
	public void Retry(){}


}
