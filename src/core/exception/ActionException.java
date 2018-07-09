package core.exception;

public class ActionException extends SysException {

	public ActionException() {
		super("请求发送错误");
	}

	public ActionException(String message) {
		super(message);
	}
	
}
