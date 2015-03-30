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
//			ist.meic.pa.StackSingleton.getInstance().storePrevious(null, args[0], "main", restArgs);
//			ist.meic.pa.StackSingleton.getInstance().currentState();
			classLoader.run(args[0], restArgs);
//			ist.meic.pa.StackSingleton.getInstance().restoreState();
			
			System.out.println("Program over, exiting...");
		} catch (Exception e) {
			throw e;
			//StackSingleton.getInstance().currentState();
			//return;
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
	
	public static Class<?> toClass(String classname ) {
		if( classname.equals("java.lang.Integer")) return int.class;
		if( classname.equals("java.lang.Boolean")) return boolean.class;
	    if( classname.equals("java.lang.Byte")) return byte.class;
	    if( classname.equals("java.lang.Short")) return short.class;
	    if( classname.equals("java.lang.Long")) return long.class;
	    if( classname.equals("java.lang.Float")) return float.class;
	    if( classname.equals("java.lang.Double")) return double.class;
		return null;
	}
	
	public static Object faztudo(Object classObj, Class<?> classe, String metodo, Object[] args) throws Throwable {
		try {
			
			Method m;
			Object returnValue = null;
			boolean construtorBasico = false;
			// PRINTS INICIAIS - APAGAR PARA PRODUCAO
			System.out.println("Chamada ao FazTudo : " + classe.getName() + " . " + metodo);
			System.out.println("argumentos ("+args.length+")");
			for (int i = 0; i < args.length; i++) {
				System.out.println(i + " - " + args[i].toString() + "/" +args[i].getClass().getName());
				
			}
			//System.out.println("Esta chamada tem "+args.length+" argumento(s).");

			// GUARDAR ACESSO AO OBJECTO E INVOCACAO DE METODO NA STACK
			
			//StackSingleton.getInstance().storePrevious(classObj, classe.getName(), metodo, args);
			System.out.println("primeiro checkpoint");
			// INVOCACAO DO METODO VARIA EM FUNCAO DA EXISTENCIA DE ARGUMENTOS
			if (args.length > 0) {
				// CRIAR O ARRAY COM AS CLASSES DOS ARGUMENTOS
				System.out.println("segundo checkpoint");
				Class<?>[] arrayDasClassesDosArgumentos = new Class<?>[args.length];
				System.out.println("terceiro checkpoint");
				for (int i = 0; i < args.length; i++) {
					System.out.println("internal checkpoint");
					if( args[i].getClass().getName().equals("java.lang.Integer")) arrayDasClassesDosArgumentos[i] = int.class;
					else if( args[i].getClass().getName().equals("java.lang.Boolean")) arrayDasClassesDosArgumentos[i] = boolean.class;
					else if( args[i].getClass().getName().equals("java.lang.Byte")) arrayDasClassesDosArgumentos[i] = byte.class;
					else if( args[i].getClass().getName().equals("java.lang.Short")) arrayDasClassesDosArgumentos[i] = short.class;
					else if( args[i].getClass().getName().equals("java.lang.Long")) arrayDasClassesDosArgumentos[i] = long.class;
					else if( args[i].getClass().getName().equals("java.lang.Float")) arrayDasClassesDosArgumentos[i] = float.class;
					else if( args[i].getClass().getName().equals("java.lang.Double")) arrayDasClassesDosArgumentos[i] = double.class;
					else
						arrayDasClassesDosArgumentos[i] = args[i].getClass();
					//System.out.println("Argumento "+i+" do tipo: "+args[i].getClass().getName());
				}
				// INVOCAR METODO COM ARGUMENTOS
				System.out.println("quarto checkpoint" + classe.getName());

				m = classe.getMethod(metodo,arrayDasClassesDosArgumentos);
				System.out.println("quinto checkpoint");
				EvalShell.setM(m);
				System.out.println("sexto checkpoint");
				System.out.println(m.getName());
				returnValue = m.invoke(classObj, args);
			} else {
				// INVOCAR METODO SEM ARGUMENTOS
				m = classe.getMethod(metodo);
				EvalShell.setM(m);
				System.out.println(m.getName());
				returnValue = m.invoke(classObj);
			}
			
			// SE NAO FORAM LANCADAS EXCEPCOES, REMOVER ESTA INVOCACAO DA STACK
			
			//StackSingleton.getInstance().restoreState();
			return returnValue;
		} catch (InvocationTargetException e) {
			System.out.println("causa: " + e.getCause());
			//e.getCause().printStackTrace();
			Object x = EvalShell.shell((Exception) e.getCause());
			//System.out.println("return type : "+x.getClass().getName() + " - " + x.toString());
			return x;

		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
	}
}
