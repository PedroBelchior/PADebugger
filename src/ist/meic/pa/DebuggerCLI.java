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
			Integer a = new Integer("abc");
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
	
	public static Object faztudo(Object classObj, Class<?> classe, String metodo, Object[] args) throws Exception {
		try {
		
			Method m;
			Object returnValue;
			boolean construtorBasico = false;
			//StackSingleton.getInstance().storePrevious(classObj, classe.getName(), metodo);
			// PRINTS INICIAIS
			System.out.println("Chamada ao FazTudo : " + classe.getName() + " . " + metodo);					
			System.out.println("Esta chamada tem "+args.length+" argumento(s).");
			// CRIAR O ARRAY COM AS CLASSES DOS ARGUMENTOS
			Class<?>[] arrayDasClassesDosArgumentos = new Class<?>[args.length];
			for (int i = 0; i < args.length; i++) {
				arrayDasClassesDosArgumentos[i] = args[i].getClass();
				System.out.println("Argumento "+i+" do tipo: "+args[i].getClass().getName());
			}
			// PRINTS AOS CONSTRUTORES
			for (Constructor<?> con : classe.getDeclaredConstructors()) {
				System.out.print(con.getName() + "(");
				for (Class<?> cla : con.getParameterTypes())
					System.out.print(cla.getName()+",");
				System.out.println(")");
				if (con.getParameterTypes().length == 0) {
					System.out.println("Esta Classe Tem Construtores Sem Argumentos");
					construtorBasico = true;
				}
			}
			
			// INICIALIZAR O OBJECTO ATRAVES DOS CONSTRUTORES
			if (classObj == null) {
				System.out.println("antes da excepcao : "+classe.getName());
				if (construtorBasico) classObj = classe.newInstance();
				else {
					Constructor<?> constructor = classe.getConstructor(arrayDasClassesDosArgumentos);
					classObj = constructor.newInstance(args);
				}
				System.out.println(classObj.getClass().getName());
			}
			returnValue = null;
			if (args.length > 0) {
				m = classe.getMethod(metodo,arrayDasClassesDosArgumentos);
				returnValue = m.invoke(classObj, args);
			} else if (args.length == 0){
				m = classe.getMethod(metodo);
				m.setAccessible(true);
				returnValue = m.invoke(classObj);
			}
			
			//StackSingleton.getInstance().storePrevious(classe, classe.getClass().getName(), metodo);
			//StackSingleton.getInstance().currentState();
			//StackSingleton.getInstance().restoreState();
			//StackSingleton.getInstance().currentState();
			
			 
			return returnValue;
		} catch (Exception e) {
			System.out.println(e);
			//EvalShell.shell();
			throw e;
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
