package ist.meic.pa;
import javassist.*;
import java.io.InputStreamReader;
import java.io.BufferedReader;
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
			//Integer a = new Integer("abc");;
			System.out.println("test!");
			Translator translator = new ExceptionTranslator();
			ClassPool pool = ClassPool.getDefault();
			Loader classLoader = new Loader(); 
			classLoader.addTranslator(pool, translator);
			String[] restArgs = new String[args.length - 1] ;
			System.arraycopy(args, 1, restArgs, 0, restArgs.length);
			classLoader.run(args[0], restArgs);
			System.out.println("Program over, exiting...");
		} catch (Exception e) {
			System.out.println(e);
			//StackSingleton.getInstance().currentState();
			System.out.println("Exception reached the top level, exiting program...");
			return;
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
			
			StackSingleton.getInstance().storePrevious(classObj, classe.getName(), metodo, args);
			
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
				EvalShell.setM(m);
				returnValue = m.invoke(classObj, args);
			} else {
				// INVOCAR METODO SEM ARGUMENTOS
				m = classe.getMethod(metodo);
				EvalShell.setM(m);
				returnValue = m.invoke(classObj);
			}
			
			// SE NAO FORAM LANCADAS EXCEPCOES, REMOVER ESTA INVOCACAO DA STACK
			
			StackSingleton.getInstance().restoreState();
			 
			return returnValue;
		} catch (InvocationTargetException e) {
			System.out.println(e.getCause());
			return EvalShell.shell((Exception) e.getCause());

		} catch (Exception e) {
			System.out.println(e.getCause());
			return e;
		}
	}
}
