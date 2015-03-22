package ist.meic.pa;
import java.io.IOException;

import javassist.*;

public class DebuggerCLI {

	/**
	 * @param args
	 * @throws NotFoundException 
	 * @throws CannotCompileException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
		System.out.println("test!");
		
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get("ist.meic.pa.Test");
		CtField cf = CtField.make("static int xpto = 123;", cc);
		cc.addField(cf);
		//cc.setSuperclass(pool.get("test.Point"));
		//cc.writeFile();
		System.out.println(cc.toString());
	}

}
