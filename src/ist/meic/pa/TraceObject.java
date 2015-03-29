package ist.meic.pa;

public class TraceObject {

	public Object objecto = null;
	public String classe = null;
	public String metodo = null;
	public Object valor = null;
	
	public TraceObject(Object obj, String classToStore, String methodToStore){
		
		objecto = obj;
		classe = classToStore;
		metodo = methodToStore;
	}
	
}
