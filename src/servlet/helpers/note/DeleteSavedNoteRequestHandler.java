package servlet.helpers.note;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import servlet.helpers.ServletRequestHandler;
import utility.ActionCodes;

public class DeleteSavedNoteRequestHandler implements ServletRequestHandler {

	private final int code = ActionCodes.DELETE_SAVED_NOTE_ACTION;
	
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
			response.getWriter().print(db.deleteSavedNote(request.getParameter("userId"), request.getParameter("noteId")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
