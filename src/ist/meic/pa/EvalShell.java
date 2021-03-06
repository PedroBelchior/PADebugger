package ist.meic.pa;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class EvalShell {
	
	public static Method metodo;
	public EvalShell() {}
	
	public static void setM(Method m) {
		metodo = m;
	}
	
	public static Object toObject( Class clazz, String value ) {
		System.out.println((Integer.TYPE == clazz));
		if( Integer.class == clazz || Integer.TYPE == clazz) return Integer.parseInt( value );
	    if( Boolean.class == clazz || Boolean.TYPE == clazz) return Boolean.parseBoolean( value );
	    if( Byte.class == clazz || Byte.TYPE == clazz) return Byte.parseByte( value );
	    if( Short.class == clazz || Short.TYPE == clazz) return Short.parseShort( value );
	    if( Long.class == clazz || Long.TYPE == clazz) return Long.parseLong( value );
	    if( Float.class == clazz || Float.TYPE == clazz) return Float.parseFloat( value );
	    if( Double.class == clazz || Double.TYPE == clazz) return Double.parseDouble( value );
	    return value;
	}
	
	public static Object shell(Exception ex) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			System.out.print("DebuggerCLI :> ");
			
			String[] splitresult = null;

			try {
				splitresult = br.readLine().split(" ");
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(splitresult[0].equalsIgnoreCase("Abort")) {
				System.out.println("Good Bye!!!");
				System.exit(0);
				
			} else if(splitresult[0].equalsIgnoreCase("Info")) {
				//System.out.println("Stack aqui");
				StackSingleton.getInstance().currentState();
				
			} else if(splitresult[0].equalsIgnoreCase("Set")) {
				if (splitresult.length < 3) {
					System.out.println("You must specify a field and a value to be set.");
				} else {
					TraceObject obj = StackSingleton.getInstance().returnlastObj();
					if (obj != null) {
						Field field = null;
						try {
							field = obj.objecto.getClass().getDeclaredField(splitresult[1]);
							field.setAccessible(true);
							//System.out.println("Field: " + field + " tipo do Object " + field.getType());
							field.set(obj.objecto, toObject(field.getType(), splitresult[2]));
						} catch (NoSuchFieldException nsfe){ 
							// field com este nome nao existe
							System.out.println("The Object does not have a field \""+splitresult[1]+"\".");
						} catch (SecurityException se) { 
							// nao conseguimos tornar este field acessivel
							System.out.println("Field \""+splitresult[1]+"\" could not be made accessible.");
						} catch (IllegalArgumentException iae) { 
							// valor de tipo errado para este field
							System.out.println("The value provided was the wrong type ("+splitresult[2].getClass().getName()+") for field \""+splitresult[1]+"\" ("+field.getType()+").");
						} catch (IllegalAccessException iae) { 
							// field protegido/privado
							System.out.println("Field \""+splitresult[1]+"\" is protected.");
						}
					} else
						System.out.println("Null Object has no fields.");
				}
				
			} else if(splitresult[0].equalsIgnoreCase("Get")) {
				if (splitresult.length > 1){
					TraceObject obj = StackSingleton.getInstance().returnlastObj();
					if (obj!=null) {
						try {
							Field field = obj.objecto.getClass().getDeclaredField(splitresult[1]);
							field.setAccessible(true);
							System.out.println(field.get(obj.objecto));
						} catch (NoSuchFieldException nsfe){
							System.out.println("The Object does not have a field \""+splitresult[1]+"\".");
						}
					} else {
						System.out.println("Null Object has no fields (Possible static method call without instantiation).");
					}
				} else {
					System.out.println("No field specified. You must specify the field's name.");
				}				
				
			} else if(splitresult[0].equalsIgnoreCase("Throw")) {
				StackSingleton.getInstance().restoreState();
				throw ex;
				
			} else if(splitresult[0].equalsIgnoreCase("Retry")) {
				TraceObject tmpObj = StackSingleton.getInstance().returnlastObj();
				try {
					DebuggerCLI.faztudo(tmpObj.objecto, Class.forName(tmpObj.classe), tmpObj.metodonome, tmpObj.argumentos);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					System.out.println("Error " +  tmpObj.classe + " Not found!");
				}
				
			} else if(splitresult[0].equalsIgnoreCase("Return")) {
				if (splitresult.length < 2)
					System.out.println("You must provide a return value (ie: 'Return 2')");
				else {
					//StackSingleton.getInstance().restoreState();
					System.out.println("tipo de retorno para o metodo "+metodo.getName()+": " +metodo.getReturnType().getName());
					System.out.println(toObject(metodo.getReturnType(), splitresult[1]).getClass());
					return toObject(metodo.getReturnType(), splitresult[1]);
				}

			} else if(splitresult[0].equalsIgnoreCase("Help") || splitresult[0].equalsIgnoreCase("?")) {
				System.out.println("Available Commands:");
				System.out.println("ABORT : terminates the execution of this application");
				System.out.println("INFO : provides information on object and stack");
				System.out.println("SET field value: sets object field with value");
				System.out.println("GET value : gets value of object field");
				System.out.println("THROW : re-throws exception");
				System.out.println("RETRY : retries the last method call");
				System.out.println("RETURN value : returns value");
				System.out.println("HELP : shows this list.");
				System.out.println("---");
			} else
				System.out.println("Invalid Command");
		}
	}
}
