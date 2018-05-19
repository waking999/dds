package au.edu.cdu.common.exception;

public class MOutofNException extends Exception {
	private static final long serialVersionUID = 1L;
	

	public MOutofNException(String msg){
		super(msg);
	}
}
