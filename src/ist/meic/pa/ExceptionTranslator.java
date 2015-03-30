package ist.meic.pa;
import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class ExceptionTranslator implements Translator {
	
	@Override
	public void onLoad(ClassPool arg0, String arg1) throws NotFoundException,
			CannotCompileException {
		if (	arg1.startsWith("ist.meic.pa.") || 
				arg1.startsWith("java.") || 
				arg1.startsWith("javassist.")) 
			return;
		System.out.println("O translator carregou a class : "+arg1);
		
		for(CtMethod cm : ClassPool.getDefault().get(arg1).getDeclaredMethods()) {
			System.out.println("got in : "+cm.getName());
			cm.instrument(new ExprEditor(){
				public void edit(MethodCall m) throws CannotCompileException {
							//System.out.println("O translator substituiu uma chamada a " + m.getMethod() + " em "+ m.getFileName() + ":" + m.getLineNumber());
							m.replace("{ ist.meic.pa.StackSingleton.getInstance().storePrevious($0, $class.getName(),\"" + m.getMethodName() + "\",$args); " +
										"$_ = ($r) ist.meic.pa.DebuggerCLI.faztudo($0, $class,\"" + m.getMethodName() + "\",$args); " +
										"ist.meic.pa.StackSingleton.getInstance().restoreState();}");
				}
			});
			if (cm.getName().equals("main")) {
				cm.insertBefore("{ ist.meic.pa.StackSingleton.getInstance().storePrevious(null, $class.getName(),\""+cm.getName()+"\",(String[])$args[0]); }");
				cm.insertAfter("{ ist.meic.pa.StackSingleton.getInstance().restoreState(); }");
			}
		}
	}	
	
	@Override
	public void start(ClassPool arg0) throws NotFoundException,
			CannotCompileException {
			
		
	}

}
