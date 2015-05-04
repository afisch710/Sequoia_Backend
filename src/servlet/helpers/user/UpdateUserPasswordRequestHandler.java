package servlet.helpers.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.helpers.ServletRequestHandler;
import utility.ActionCodes;
import database.Database;

public class UpdateUserPasswordRequestHandler implements ServletRequestHandler {

	private final int code = ActionCodes.UPDATE_USER_PASSWORD_ACTION;
	
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
		PrintWriter writer;
		try {
			writer = response.getWriter();
			String userId = request.getParameter("userId");
			String oldPass = request.getParameter("oldPassword");
			String newPass = request.getParameter("newPassword");
			
			writer.println(Database.createInstance(null, null).updateUserPassword(userId, oldPass, newPass));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
