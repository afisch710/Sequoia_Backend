package servlet.helpers.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import servlet.helpers.ServletRequestHandler;
import utility.ActionCodes;

public class SearchUserByUsernameRequestHandler implements
		ServletRequestHandler {

	private final int code = ActionCodes.SEARCH_BY_USERNAME_ACTION;
	
	@Override
	public int getRequestActionCode() {
		return code;
	}

	@Override
	public void handleGetRequest(HttpServletRequest request,
			HttpServletResponse response) {
		Database db = Database.createInstance(null, null);
		
		try {
			response.getWriter().print(db.searchUserByUsername(request.getParameter("username")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handlePostRequest(HttpServletRequest request,
			HttpServletResponse response) {

	}

}
