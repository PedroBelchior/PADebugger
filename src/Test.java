
public class Test {
	public static int counter;
	
	public Test(){
		counter = 0;
	}
	
	public static void del() throws Exception {
		counter = 0;
		throw new Exception("d");
	}
	
	public static void add() throws Exception {
		
		counter = counter+1;
		int a = Integer.parseInt("abc");
		del();
		//throw new Exception("Oops.");
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("teste de refleccao!");
		try{
			add();
		} catch (Exception e) { 
			throw e;
		}
	}
	

}