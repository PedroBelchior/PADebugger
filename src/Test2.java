
public class Test2 {
	public int b = 0;
	public String a = "123";
	
	//comment
	
	public int main1(String[] args) throws Exception {
		Test3 teste = new Test3();
		try {
			throw new Exception("Test2 Exception.");
		} catch (Exception e) {
			return 10;
		}
	}
}
