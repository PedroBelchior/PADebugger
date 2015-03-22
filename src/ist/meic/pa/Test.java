package ist.meic.pa;

public class Test {
	public int counter;
	
	Test(){
		this.counter = 0;
	}
	
	public void add() throws Exception {
		this.counter = this.counter+1;
		throw new Exception("Oops.");
	}
	
	public static void main(){
		System.out.println("teste de refleccao!");
	}
}
