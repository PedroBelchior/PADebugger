
/*
 * TODO:
 * - Implement code from the slides
 * - Call stack portion is in "Undoable Programs" section
 * - 
 */

package ist.meic.pa;
import java.io.IOException;
import java.lang.reflect.*;

import javassist.*;

public class DebuggerCLI {
	
	/**
	 * @param args
	 * @throws NotFoundException 
	 * @throws CannotCompileException 
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, NoSuchMethodException, SecurityException {
		System.out.println("test!");
		
//		ClassPool pool = ClassPool.getDefault();
//		CtClass cc = pool.get("ist.meic.pa.Test");
//		CtField cf = CtField.make("static int xpto = 123;", cc);
//		cc.addField(cf);
//		Class<?> rtClass = cc.toClass();
//		Method main = rtClass.getMethod("main", args.getClass());
//		System.out.println(cc.toString());
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
