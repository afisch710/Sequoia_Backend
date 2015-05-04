package servlet.helpers.note;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import event.NewNoteEvent;
import servlet.helpers.ServletRequestHandler;
import utility.ActionCodes;

public class NewNoteRequestHandler implements ServletRequestHandler {

	private final int code = ActionCodes.NEW_NOTE_ACTION;
	
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
		
		try {
			String textContent = request.getParameter("textContent");
			long timeStamp = System.currentTimeMillis();
			double lat = Double.parseDouble(request.getParameter("lat"));
			double lon = Double.parseDouble(request.getParameter("lon"));
			String creatorId = request.getParameter("creatorId");
			double range = Double.parseDouble(request.getParameter("range"));
			int duration = Integer.parseInt(request.getParameter("duration"));
			String visibility = request.getParameter("visibility");
			String tag = request.getParameter("tag");
			String id = UUID.randomUUID().toString();
			
			NewNoteEvent event = new NewNoteEvent();
			
			event.setCreatorId(creatorId);
			event.setDuration(duration);
			event.setId(id);
			event.setLat(lat);
			event.setLon(lon);
			event.setRange(range);
			event.setTag(tag);
			event.setTextContent(textContent);
			event.setTimeStamp(timeStamp);
			event.setVisibility(visibility);
			
			response.getWriter().print(Database.createInstance(null, null).saveNewNote(event));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
