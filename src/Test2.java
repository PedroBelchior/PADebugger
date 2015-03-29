
public class Test2 {
	public int b = 0;
	public String a = "123";
	
	//comment
	
	public void main1(String[] args) throws Exception {
		Test3 teste = new Test3();
		try {
			teste.Excepto();
			throw new Exception("Test2 Exception.");
		} catch (Exception e) {
			throw e;
		}
	}
}
