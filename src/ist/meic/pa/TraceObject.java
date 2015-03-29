package ist.meic.pa;


public class TraceObject {

	public Object objecto = null;
	public String classe = null;
	public String metodonome = null;
	public Object[] argumentos = null;
	
	
	public TraceObject(Object obj, String classToStore, String methodToStore, Object[] args){
		
		objecto = obj;
		classe = classToStore;
		metodonome = methodToStore;
		argumentos = args;
	}
	
}
