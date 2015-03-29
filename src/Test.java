
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
		//int a = Integer.parseInt("abc");
		//System.out.println(a);
		del();
		throw new Exception("Oops.");
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("teste de refleccao!");
		Test2 test = new Test2();
		try{
			test.main1(new String[] {"123"});
		} catch (Exception e) { 
			throw e;
		}
	}
	

}