package ist.meic.pa;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class EvalShell {
	
	public static Method method;
	public EvalShell() {}
	
	public static void setM(Method m) {
		method = m;
	}
	
	public static Object toObject( Class clazz, String value ) {
	    if( Boolean.class == clazz || Boolean.TYPE == clazz) return Boolean.parseBoolean( value );
	    if( Byte.class == clazz || Byte.TYPE == clazz) return Byte.parseByte( value );
	    if( Short.class == clazz || Short.TYPE == clazz) return Short.parseShort( value );
	    if( Integer.class == clazz || Integer.TYPE == clazz) return Integer.parseInt( value );
	    if( Long.class == clazz || Long.TYPE == clazz) return Long.parseLong( value );
	    if( Float.class == clazz || Float.TYPE == clazz) return Float.parseFloat( value );
	    if( Double.class == clazz || Double.TYPE == clazz) return Double.parseDouble( value );
	    return value;
	}
	
	public static Object shell(Exception ex) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			System.out.println("DebuggerCLI :> ");
			
			String[] splitresult = null;

			try {
				splitresult = br.readLine().split(" ");
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			if(splitresult[0].equals("Abort")) {
				System.out.println("Good Bye!!!");
				System.exit(0);
				
			} else if(splitresult[0].equals("Info")) {
				System.out.println("Stack aqui");
				StackSingleton.getInstance().currentState();
				
			} else if(splitresult[0].equals("Set")) {
				TraceObject obj = StackSingleton.getInstance().returnlastObj();
				Field field = obj.objecto.getClass().getDeclaredField(splitresult[1]);
				System.out.println("Field: " + field + " tipo do Object " + field.getType());
				field.set(obj.objecto, toObject(field.getType(), splitresult[2]));
				
			} else if(splitresult[0].equals("Get")) {
				TraceObject obj = StackSingleton.getInstance().returnlastObj();
				//System.out.println(obj.objecto.getClass() + "Class");
				Field field = obj.objecto.getClass().getDeclaredField(splitresult[1]);
				System.out.println(field.get(obj.objecto));
				
			} else if(splitresult[0].equals("Throw")) {
				throw ex;
				
			} else if(splitresult[0].equals("Retry")) {
				
			} else if(splitresult[0].equals("Return")) {
				System.out.println("entrei");
				System.out.println(method.getName() + " method name " + method.getReturnType() + " Class " + splitresult[1] + " Valor ");
				//toObject(method.getReturnType(), splitresult[1]);
				System.out.println(toObject(method.getReturnType(), splitresult[1]));
				return toObject(method.getReturnType(), splitresult[1]);
				
			} else
				System.out.println("Invalid Command");
		}
	}
}
