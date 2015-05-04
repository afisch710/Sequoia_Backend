package utility;

public class ReturnCodes {

	// Login
	public static final int LOGIN_SUCCESS = 1000;
	public static final int LOGIN_USERNAME_DOES_NOT_EXIST = 1001;
	public static final int LOGIN_INCORRECT_PASSWORD = 1002;
	
	// Update User
	public static final int UPDATE_USER_PASSWORD_SUCCESS = 1050;
	public static final int UPDATE_USER_PASSWORD_FAILURE_INCORRECT_OLD_PASS = 1051;
	public static final int UPDATE_USER_PASSWORD_FAILURE_UNKNOWN = 1052;
	
	// Delete User
	public static final int DELETE_USER_SUCCESS = 1075;
	public static final int DELETE_USER_FAILURE = 1076;
	
	// New User
	public static final int NEW_USER_SUCCESS = 1100;
	public static final int NEW_USER_USERNAME_ALREADY_EXISTS = 1101;
	
	// Get User
	public static final int GET_USER_INFO_SUCCESS = 1120;
	public static final int GET_USER_INFO_DOES_NOT_EXIST = 1121;
	
	// New Note
	public static final int NEW_NOTE_SUCCESS = 1200;
	public static final int NEW_NOTE_FAILURE = 1201;
	
	// Delete note
	public static final int DELETE_NOTE_SUCCESS = 1210;
	public static final int DELETE_NOTE_FAILURE = 1211;
	
	// Get nearby notes
	public static final int GET_NEARBY_NOTES_SUCCESS = 1220;
	public static final int GET_NEARBY_NOTES_NONE = 1221;
	
	// Get matching numbers
	public static final int GET_MATCHING_NUMBERS_SUCCESS = 1250;
	public static final int GET_MATCHING_NUMBERS_NONE = 1251;
	
	// Add friend
	public static final int ADD_FRIEND_SUCCESS = 1300;
	public static final int ADD_FRIEND_FAILURE = 1301;
	
	// Get Friends
	public static final int GET_FRIENDS_NONE = 1311;
	
	// Search by username
	public static final int SEARCH_BY_USERNAME_DOES_NOT_EXIST = 1321;
	
	// Get private notes
	public static final int GET_PRIVATE_NOTES_NONE = 1331;
	
	// Save note
	public static final int SAVE_NOTE_SUCCESS = 1340;
	public static final int SAVE_NOTE_FAILURE = 1341;
	
	// get saved notes
	public static final int GET_SAVED_NOTES_NONE = 1351;
	
	// delete saved notes
	public static final int DELETE_SAVED_NOTE_SUCCESS = 1360;
	public static final int DELETE_SAVED_NOTE_FAILURE = 1361;
	
	// remove friend
	public static final int REMOVE_FRIEND_SUCCESS = 1370;
	public static final int REMOVE_FRIEND_FAILURE = 1371;
	
}
