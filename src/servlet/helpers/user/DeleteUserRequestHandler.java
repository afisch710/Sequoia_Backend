package servlet.helpers.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.helpers.ServletRequestHandler;
import utility.ActionCodes;
import database.Database;

public class DeleteUserRequestHandler implements ServletRequestHandler {

	private final int code = ActionCodes.DELETE_USER_ACTION;
	
	@Override
	public int getRequestActionCode() {
		return code;
	}

	@Override
	public void handleGetRequest(HttpServletRequest request,
			HttpServletResponse response) {

	}

	@Override
	public void handlePostRequest(HttpServletRequest request,
			HttpServletResponse response) {

		Database db = Database.createInstance(null, null);
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		try {
			response.getWriter().print(db.deleteUser(userId, password));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
