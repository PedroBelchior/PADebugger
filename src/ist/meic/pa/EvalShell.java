package ist.meic.pa;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class EvalShell {
	public EvalShell() {}
	
	public static void shell() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			System.out.println("DebuggerCLI :> ");
			
			String[] splitresult = null;

			try {
				splitresult = br.readLine().split(" ");


				System.out.println("Antes do If");
				
				if(splitresult[0].equals("Abort")) {
					
				} else if(splitresult[0].equals("Info")) {
					System.out.println("Stack aqui");
					StackSingleton.getInstance().currentState();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
