package servlet.helpers.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import servlet.helpers.ServletRequestHandler;
import utility.ActionCodes;

public class RemoveFriendRequestHandler implements ServletRequestHandler {

	private final int code = ActionCodes.REMOVE_FRIEND_ACTION;
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
		
		try {
			response.getWriter().print(db.removeFriend(request.getParameter("userId"), request.getParameter("friendId")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
