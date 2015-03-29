package ist.meic.pa;
import javassist.*;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Translator;
import java.lang.reflect.*;

public class DebuggerCLI {
	
	/**
	 * @param args
	 * @throws CannotCompileException 
	 * @throws NotFoundException 
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		try {
			//Integer a = new Integer("abc");
			System.out.println("test!");
			Translator translator = new ExceptionTranslator();
			ClassPool pool = ClassPool.getDefault();
			Loader classLoader = new Loader(); 
			classLoader.addTranslator(pool, translator);
			String[] restArgs = new String[args.length - 1] ;
			classLoader.run("Test", null);
			System.arraycopy(args, 1, restArgs, 0, restArgs.length);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void faztudoo(){
		System.out.println("fez tudo");
	}
	
	public static void leargs(Object o){
		System.out.println(o.getClass().getName());
	}
	
	public static void leargs(Object ... args) {
		System.out.println(args.length);
	} 
	
	public static Object faztudo(Object classObj, Class<?> classe, String metodo, Object[] args) throws Throwable {
		try {
		
			Method m;
			Object returnValue = null;
			boolean construtorBasico = false;
			// PRINTS INICIAIS - APAGAR PARA PRODUCAO
			System.out.println("Chamada ao FazTudo : " + classe.getName() + " . " + metodo);					
			System.out.println("Esta chamada tem "+args.length+" argumento(s).");

			// GUARDAR ACESSO AO OBJECTO E INVOCACAO DE METODO NA STACK
			
			//StackSingleton.getInstance().storePrevious(classe, classe.getClass().getName(), metodo);
			//StackSingleton.getInstance().currentState();
			
			// INVOCACAO DO METODO VARIA EM FUNCAO DA EXISTENCIA DE ARGUMENTOS
			if (args.length > 0) {
				// CRIAR O ARRAY COM AS CLASSES DOS ARGUMENTOS
				Class<?>[] arrayDasClassesDosArgumentos = new Class<?>[args.length];
				for (int i = 0; i < args.length; i++) {
					arrayDasClassesDosArgumentos[i] = args[i].getClass();
					System.out.println("Argumento "+i+" do tipo: "+args[i].getClass().getName());
				}
				// INVOCAR METODO COM ARGUMENTOS
				m = classe.getMethod(metodo,arrayDasClassesDosArgumentos);
				returnValue = m.invoke(classObj, args);
			} else {
				// INVOCAR METODO SEM ARGUMENTOS
				m = classe.getMethod(metodo);
				returnValue = m.invoke(classObj);
			}
			
			// SE NAO FORAM LANCADAS EXCEPCOES, REMOVER ESTA INVOCACAO DA STACK
			
			//StackSingleton.getInstance().restoreState();
			//StackSingleton.getInstance().currentState();
			 
			return returnValue;
		} catch (InvocationTargetException e) {
			// TODAS AS EXCEPCOES LANCADAS ATRAVES DE REFLEXAO VÊM DENTRO DE INVOCATIONTARGETEXCEPTION
			System.out.println(e.getCause());
			return e.getCause();
		} catch (Exception e) {
			System.out.println(e.getCause());
			//EvalShell.shell();
			return e;
		}
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
