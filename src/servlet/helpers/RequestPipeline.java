package servlet.helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.helpers.note.DeleteSavedNoteRequestHandler;
import servlet.helpers.note.GetNearbyNotesRequestHandler;
import servlet.helpers.note.GetPrivateNotesRequestHandler;
import servlet.helpers.note.GetSavedNotesRequestHandler;
import servlet.helpers.note.NewNoteRequestHandler;
import servlet.helpers.note.SaveNoteRequestHandler;
import servlet.helpers.user.AddFriendRequestHandler;
import servlet.helpers.user.DeleteUserRequestHandler;
import servlet.helpers.user.GetFriendsRequestHandler;
import servlet.helpers.user.GetMatchingNumbersRequestHandler;
import servlet.helpers.user.GetUserInfoRequestHandler;
import servlet.helpers.user.LoginRequestHandler;
import servlet.helpers.user.NewUserRequestHandler;
import servlet.helpers.user.RemoveFriendRequestHandler;
import servlet.helpers.user.SearchUserByUsernameRequestHandler;
import servlet.helpers.user.UpdateUserPasswordRequestHandler;
import utility.ActionCodes;

public class RequestPipeline {

	public void pipeRequest(int action, HttpServletRequest request, HttpServletResponse response) {
		ServletRequestHandler handler = null;
		switch (action) {
		case ActionCodes.GET_USER_INFO_ACTION:
			handler = new GetUserInfoRequestHandler();
			break;
		case ActionCodes.LOGIN_ACTION:
			handler = new LoginRequestHandler();
			break;
		case ActionCodes.NEW_USER_ACTION:
			handler = new NewUserRequestHandler();
			break;
		case ActionCodes.UPDATE_USER_PASSWORD_ACTION:
			handler = new UpdateUserPasswordRequestHandler();
			break;
		case ActionCodes.NEW_NOTE_ACTION:
			handler = new NewNoteRequestHandler();
			break;
		case ActionCodes.DELETE_USER_ACTION:
			handler = new DeleteUserRequestHandler();
			break;
		case ActionCodes.GET_NEARBY_NOTES_ACTION:
			handler = new GetNearbyNotesRequestHandler();
			break;
		case ActionCodes.GET_MATCHING_NUMBERS_ACTION:
			handler = new GetMatchingNumbersRequestHandler();
			break;
		case ActionCodes.ADD_FRIEND_ACTION:
			handler = new AddFriendRequestHandler();
			break;
		case ActionCodes.GET_FRIENDS_ACTION:
			handler = new GetFriendsRequestHandler();
			break;
		case ActionCodes.SEARCH_BY_USERNAME_ACTION:
			handler = new SearchUserByUsernameRequestHandler();
			break;
		case ActionCodes.GET_PRIVATE_NOTES_ACTION:
			handler = new GetPrivateNotesRequestHandler();
			break;
		case ActionCodes.GET_SAVED_NOTES_ACTION:
			handler = new GetSavedNotesRequestHandler();
			break;
		case ActionCodes.DELETE_SAVED_NOTE_ACTION:
			handler = new DeleteSavedNoteRequestHandler();
			break;
		case ActionCodes.SAVE_NOTE_ACTION:
			handler = new SaveNoteRequestHandler();
			break;
		case ActionCodes.REMOVE_FRIEND_ACTION:
			handler = new RemoveFriendRequestHandler();
			break;
		default:
			// error!
			break;
		}
		
		switch (request.getMethod()) {
		case "GET":
			handler.handleGetRequest(request, response);
			break;
		case "POST":
			handler.handlePostRequest(request, response);
			break;
		default:
			
			break;
		}
	}
}
