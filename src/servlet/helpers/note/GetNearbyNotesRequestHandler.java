package servlet.helpers.note;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import servlet.helpers.ServletRequestHandler;
import utility.ActionCodes;
import utility.ReturnCodes;

public class GetNearbyNotesRequestHandler implements ServletRequestHandler {

	private final int code = ActionCodes.GET_NEARBY_NOTES_ACTION;
	
	@Override
	public int getRequestActionCode() {
		return code;
	}

	@Override
	public void handleGetRequest(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			double lat = Double.parseDouble(request.getParameter("lat"));
			double lon = Double.parseDouble(request.getParameter("lon"));
			String userId = request.getParameter("userId");
			String nearbyCsv;
			
			Database db = Database.createInstance(null, null);
			
			nearbyCsv = db.getNearbyNotes(userId, lat, lon);
			
			if (nearbyCsv == null || nearbyCsv.equals("") || nearbyCsv.equals("1221")) {
				response.getWriter().print(ReturnCodes.GET_NEARBY_NOTES_NONE);
			}
			else {
				response.getWriter().print(ReturnCodes.GET_NEARBY_NOTES_SUCCESS + "||" + nearbyCsv);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handlePostRequest(HttpServletRequest request,
			HttpServletResponse response) {

	}

}
