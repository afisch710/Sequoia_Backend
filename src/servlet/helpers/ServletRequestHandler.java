package servlet.helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServletRequestHandler {

	public int getRequestActionCode();
	
	public void handleGetRequest(HttpServletRequest request, HttpServletResponse response);
	
	public void handlePostRequest(HttpServletRequest request, HttpServletResponse response);
}
