package utility;

public class ActionCodes {

	// User
	public static final int GET_USER_INFO_ACTION = ReturnCodes.GET_USER_INFO_DOES_NOT_EXIST/10;
	public static final int LOGIN_ACTION = ReturnCodes.LOGIN_SUCCESS/10;
	public static final int NEW_USER_ACTION = ReturnCodes.NEW_USER_SUCCESS/10;
	public static final int UPDATE_USER_PASSWORD_ACTION = ReturnCodes.UPDATE_USER_PASSWORD_SUCCESS/10;
	public static final int DELETE_USER_ACTION = ReturnCodes.DELETE_USER_SUCCESS/10;
	public static final int GET_MATCHING_NUMBERS_ACTION = ReturnCodes.GET_MATCHING_NUMBERS_NONE/10;
	public static final int ADD_FRIEND_ACTION = ReturnCodes.ADD_FRIEND_SUCCESS/10;
	public static final int GET_FRIENDS_ACTION = ReturnCodes.GET_FRIENDS_NONE/10;
	public static final int SEARCH_BY_USERNAME_ACTION = ReturnCodes.SEARCH_BY_USERNAME_DOES_NOT_EXIST/10;;
	public static final int REMOVE_FRIEND_ACTION = ReturnCodes.REMOVE_FRIEND_SUCCESS/10;
	
	// Note
	public static final int NEW_NOTE_ACTION = ReturnCodes.NEW_NOTE_SUCCESS/10;
	public static final int DELETE_NOTE_ACTION = ReturnCodes.DELETE_NOTE_SUCCESS/10;
	public static final int GET_NEARBY_NOTES_ACTION = ReturnCodes.GET_NEARBY_NOTES_NONE/10;
	public static final int GET_PRIVATE_NOTES_ACTION = ReturnCodes.GET_PRIVATE_NOTES_NONE/10;
	public static final int SAVE_NOTE_ACTION = ReturnCodes.SAVE_NOTE_SUCCESS/10;
	public static final int GET_SAVED_NOTES_ACTION = ReturnCodes.GET_SAVED_NOTES_NONE/10;
	public static final int DELETE_SAVED_NOTE_ACTION = ReturnCodes.DELETE_SAVED_NOTE_SUCCESS/10;
}
