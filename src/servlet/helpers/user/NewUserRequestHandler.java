package servlet.helpers.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import event.NewUserEvent;
import servlet.helpers.ServletRequestHandler;
import utility.ActionCodes;

public class NewUserRequestHandler implements ServletRequestHandler {

	private final int code = ActionCodes.NEW_USER_ACTION;
	
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
			String id = UUID.randomUUID().toString();
			String phoneNumber = request.getParameter("number");
			String username = request.getParameter("username");
			String encryptedPassword = request.getParameter("password");
			
			NewUserEvent event = new NewUserEvent();
			event.setId(id);
			event.setEncryptedPassword(encryptedPassword);
			event.setPhoneNumber(phoneNumber);
			event.setUsername(username);
			event.setCreatedTimeStamp(System.currentTimeMillis());
			
			writer.print(Database.createInstance(null, null).saveNewUser(event));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
