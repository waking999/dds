package au.edu.cdu.common.exception;

public class ExceedLongMaxException extends Exception {
	private static final long serialVersionUID = 1L;
	
	
	public ExceedLongMaxException(String msg){
		super(msg);
	}
}
