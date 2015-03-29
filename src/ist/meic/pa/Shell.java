package ist.meic.pa;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


public class Shell {
	public static void main(String[] args) throws ClassNotFoundException {
		
		commands();
		
	}
	
	public static Class<?> getClass(String s) {
		
		Class<?> theClass;
		Object newinst = null;
		try {
			theClass = Class.forName(s);
			newinst = theClass.newInstance();			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newinst.getClass();
	}
	
	public static void commands() {
		boolean loop = true;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Object lastresult = null;
		Object[] lastresultArray = null;
		//verificar qual dos resultados escolher se arrray ou simples
		boolean lastResultArrayflag = false;
		//Armazenar resultados
		TreeMap<String, Object> database = new TreeMap<String, Object>();

		try {
			while(loop) {
				
				System.out.println("Command:> ");
				String[] splitresult = null;
				String classe = null;
				String type = null;
				
				//Comando 1 ou 2 argumentos
				splitresult = br.readLine().split(" ");
				if(splitresult.length == 2) {
					classe = splitresult[0];
					type = splitresult[1];
				} else
					classe = splitresult[0];
				
				//Comando Class, Set, Get ou Index
				if(classe.equals("Class")) {
					
					String s = type;
					Class<?> newClass = getClass(s);
					//Guarda a classe em lastresult
					System.out.println(newClass);
					lastresult = newClass.getName();
					lastResultArrayflag = false;
					
				} else if(classe.equals("Set")) {
					
					database.put(type, lastresult);
					System.out.println("Saved name for object of type: " + lastresult.getClass());
					System.out.println(lastresult);
					
				} else if(classe.equals("Get")) {
					System.out.println(database.get(type));
					
				} else if(classe.equals("Index")) {
					//verifica se o ultimo resultado e array caso seja comando index e possivel
					if(lastResultArrayflag) {
						for(int i = 0; i < lastresultArray.length; i++) {
							if(i == Integer.parseInt(type)) {
								System.out.println(lastresultArray[i] + " o que � isto " + lastresultArray[i].getClass() + " e isto!");
								lastresult = lastresultArray[i].getClass().getName();
								System.out.println(lastresultArray[i]);
								break;
							}
						}
					}
				} else
					try {
						//Comandos gerais
						System.out.println(" ao menos aqui! " + classe + " " + type);
						System.out.println(lastresult.toString()+ "yahaysad");
						Class<?> newClass = getClass(lastresult.toString());
						System.out.println(newClass + " pois e");
						Method methodClass = newClass.getClass().getMethod(classe);
						System.out.println(methodClass);
						System.out.println("passou");
						switch (splitresult.length) {
						case 1:
							System.out.println("tento aqui!");

							if(methodClass.invoke(newClass).getClass().isArray()) {
								System.out.println("entrou!");
								Object[] invokeUnderOriginalClass = (Object[]) methodClass.invoke(newClass);
								for(int i = 0; i < invokeUnderOriginalClass.length; i++) {
									System.out.println(invokeUnderOriginalClass[i]);
								}
								lastresultArray = invokeUnderOriginalClass;
								lastResultArrayflag = true;
							} else {
								Object invokeUnderOriginalClass = methodClass.invoke(lastresult);
								System.out.println(invokeUnderOriginalClass);
								lastresult = invokeUnderOriginalClass;
								lastResultArrayflag = false;
							}
							break;
						case 2:
							//methodClass.invoke(lastresult).getClass().isArray()
							if(false) {
								System.out.println(methodClass.invoke(lastresult) + "yes");
								Object[] invokeUnderOriginalClass = (Object[]) methodClass.invoke(lastresult, type);
								for(int i = 0; i < invokeUnderOriginalClass.length; i++) {
									System.out.println(invokeUnderOriginalClass[i]);
								}
								lastresultArray = invokeUnderOriginalClass;
								lastResultArrayflag = true;
							} else {
								System.out.println("vim parar aqui sim! ");
								System.out.println(lastresult + "     " + newClass);
								Object invokeUnderOriginalClass = methodClass.invoke(newClass, type);
								System.out.println(invokeUnderOriginalClass);
								lastresult = invokeUnderOriginalClass;
								lastResultArrayflag = false;
							}
							break;
						default:
							throw new Exception();
						}
							
					} catch (Exception e) {
						System.out.println(e.getMessage());
						System.out.println("Invalid Command, pls insert Class, Set, Get or Index");
					}
			}
				
		} catch (Exception e) {
				System.out.println("invalid input");
		}
	}
}
