package servlet.helpers.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.helpers.ServletRequestHandler;
import utility.ActionCodes;
import database.Database;

public class GetFriendsRequestHandler implements ServletRequestHandler {

	private final int code = ActionCodes.GET_FRIENDS_ACTION;
	
	@Override
	public int getRequestActionCode() {
		return code;
	}

	@Override
	public void handleGetRequest(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter("userId");
		Database db = Database.createInstance(null, null);
		
		try {
			response.getWriter().print(db.getFriendsList(userId));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handlePostRequest(HttpServletRequest request,
			HttpServletResponse response) {
		
	}

}
