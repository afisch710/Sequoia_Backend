package servlet.helpers.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import servlet.helpers.ServletRequestHandler;
import utility.ActionCodes;

public class GetMatchingNumbersRequestHandler implements ServletRequestHandler {

	private final int code = ActionCodes.GET_MATCHING_NUMBERS_ACTION;
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
			String userId = request.getParameter("userId");
			String numbers = request.getParameter("numbers");
			response.getWriter().print(db.getNumbersOfUsersFromList(userId, numbers));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
