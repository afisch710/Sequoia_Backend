package servlet.helpers.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import servlet.helpers.ServletRequestHandler;
import utility.ActionCodes;

public class GetUserInfoRequestHandler implements ServletRequestHandler {

	private final int code = ActionCodes.GET_USER_INFO_ACTION;
	
	@Override
	public int getRequestActionCode() {
		return code;
	}

	@Override
	public void handleGetRequest(HttpServletRequest request,
			HttpServletResponse response) {

		PrintWriter writer;
		try {
			writer = response.getWriter();
			String userId = request.getParameter("userId");
			writer.print(Database.createInstance(null, null).getUserInfoJson(userId));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void handlePostRequest(HttpServletRequest request,
			HttpServletResponse response) {

	}

}
