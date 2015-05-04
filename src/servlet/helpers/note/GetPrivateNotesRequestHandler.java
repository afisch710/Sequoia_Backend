package servlet.helpers.note;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import servlet.helpers.ServletRequestHandler;
import utility.ActionCodes;

public class GetPrivateNotesRequestHandler implements ServletRequestHandler {

	private final int code = ActionCodes.GET_PRIVATE_NOTES_ACTION;
	
	@Override
	public int getRequestActionCode() {
		return code;
	}

	@Override
	public void handleGetRequest(HttpServletRequest request,
			HttpServletResponse response) {

		Database db = Database.createInstance(null, null);
		
		try {
			response.getWriter().print(db.getPrivateNotes(request.getParameter("userId")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handlePostRequest(HttpServletRequest request,
			HttpServletResponse response) {

	}

}
