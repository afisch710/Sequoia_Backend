package database;

//sample: https://3b8d7b13-d98d-4bb1-9451-12c2c57a6e29-bluemix.cloudant.com/hangout/_design/user/_list/listuser/getuser?key=%22test%22
public class DatabaseConstants {

	// Database Info
	public static final String HOST = "fb725ec4-24da-4b99-bc68-409223ec0a86-bluemix.cloudant.com";
	//https://fb725ec4-24da-4b99-bc68-409223ec0a86-bluemix.cloudant.com/crumbz/_design/user/_list/listuser/getuser?key="steve"
	public static final String USERNAME = "beaselpenuedendreyeadesc";
	public static final String PASSWORD = "YXO55uRJe0SyRpcsJktAvO46";
	public static final String DB_NAME = "crumbz";
	
	// Character to URL conversions
	private static final String URL_DOUBLE_QUOTE_CODE = "%22";
	private static final String AND = "&";
	private static final String INCLUDE_DOCS = "include_docs=true";
	
	// URLs
	private static final String GET_ACCOUNT_PASSWORD_SUFFIX = "_design/user/_list/listuser/getuser?key=";
	private static final String GET_ACCOUNT_ID_SUFFIX = "_design/user/_list/listuser/getid?key=";
	private static final String GET_ACCOUNT_INFO_SUFFIX = "_design/user/_list/listuserinfo/getdoc?key=";
	private static final String GET_ACCOUNT_PASSWORD_FROM_ID_SUFFIX = "_design/user/_list/listuser/getpassfromid?key=";
	private static final String GET_ALL_NOTES_GPS_SUFFIX = "_design/note/_list/listnotegps/getallnotes?";
	private static final String GET_ALL_NOTES_SUFFIX = "_design/note/_list/listnote/getallnotes?";
	private static final String IS_MATCHING_NUMBER_SUFFIX = "_design/user/_list/listuser/isuser?key=";
	private static final String GET_ACCOUNT_FRIENDS_LIST_WITH_ID_SUFFIX = "_design/user/_list/listfriendslist/getfriendslist?key=";
	private static final String GET_USER_INFO_BY_NUMBER_SUFFIX = "_design/user/_list/listuserinfo/getuserbynumber?key=";
	private static final String GET_USER_BY_USERNAME_SUFFIX = "_design/user/_list/listuserinfo/getuserbyusername?key=";
	private static final String GET_NOTE_BY_ID = "_design/note/_list/listnote/getnoteinfodoc?key=";
	private static final String GET_SAVED_NOTE_BY_ID = "_design/note/_list/listsavednote/getsavednotebyid?key=";
	private static final String GET_USER_BACKPACK_SUFFIX = "_design/note/_list/listbackpack/getuserbackpack?key=";
	
	// Document Retrieval URLs
	private static final String GET_ACCOUNT_DOCUMENT_ID_SUFFIX = "_design/user/_list/listdochandle/getdoc?key=";
	private static final String GET_NOTE_DOCUMENT_ID_SUFFIX = "_design/note/_list/listdochandle/getnoteinfodoc?key=";
	private static final String GET_ACCOUNT_FRIENDS_LIST_DOCUMENT_ID_SUFFIX = "_design/user/_list/listdochandle/getfriendslistdoc?key=";
	private static final String GET_USER_BACKPACK_DOC_SUFFIX = "_design/note/_list/listdochandle/getuserbackpack?key=";
	private static final String GET_USER_SAVED_NOTE_DOC_SUFFIX = "_design/note/_list/listdochandle/getsavednotebyid?key=";
	
	// Methods
	public static String getAccountPasswordURL(String username) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_ACCOUNT_PASSWORD_SUFFIX + URL_DOUBLE_QUOTE_CODE + username + URL_DOUBLE_QUOTE_CODE;
	}
	
	public static String getUserIdURL(String username) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_ACCOUNT_ID_SUFFIX + URL_DOUBLE_QUOTE_CODE + username + URL_DOUBLE_QUOTE_CODE;
	}
	
	public static String getUserInfoURL(String userId) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_ACCOUNT_INFO_SUFFIX + URL_DOUBLE_QUOTE_CODE + userId + URL_DOUBLE_QUOTE_CODE + AND + INCLUDE_DOCS;
	}
	
	public static String getAccountPasswordFromIdURL(String userId) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_ACCOUNT_PASSWORD_FROM_ID_SUFFIX + URL_DOUBLE_QUOTE_CODE + userId + URL_DOUBLE_QUOTE_CODE;
	}
	
	public static String getUserDocumentHandleFromUserId(String userId) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_ACCOUNT_DOCUMENT_ID_SUFFIX + URL_DOUBLE_QUOTE_CODE + userId + URL_DOUBLE_QUOTE_CODE + AND + INCLUDE_DOCS; 
	}
	
	public static String getNoteInfoDocumentHandle(String noteId) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_NOTE_DOCUMENT_ID_SUFFIX + URL_DOUBLE_QUOTE_CODE + noteId + URL_DOUBLE_QUOTE_CODE + AND + INCLUDE_DOCS;
	}
	
	public static String getAllNotesGpsCoordinatesURL() {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_ALL_NOTES_GPS_SUFFIX + INCLUDE_DOCS;
	}
	
	public static String getAllNotesURL() {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_ALL_NOTES_SUFFIX + INCLUDE_DOCS;
	}

	public static String getIsUserNumberURL(String number) {
		return "https://" + HOST + "/" + DB_NAME + "/" + IS_MATCHING_NUMBER_SUFFIX + URL_DOUBLE_QUOTE_CODE + number + URL_DOUBLE_QUOTE_CODE;
	}

	public static String getFriendsListUrl(String userId) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_ACCOUNT_FRIENDS_LIST_WITH_ID_SUFFIX + URL_DOUBLE_QUOTE_CODE + userId + URL_DOUBLE_QUOTE_CODE + AND + INCLUDE_DOCS;
	}
	
	public static String getFriendsListDocumentHandle(String userId) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_ACCOUNT_FRIENDS_LIST_DOCUMENT_ID_SUFFIX + URL_DOUBLE_QUOTE_CODE + userId + URL_DOUBLE_QUOTE_CODE + AND + INCLUDE_DOCS;
	}
	
	public static String getUserByNumberURL(String number) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_USER_INFO_BY_NUMBER_SUFFIX + URL_DOUBLE_QUOTE_CODE + number + URL_DOUBLE_QUOTE_CODE + AND + INCLUDE_DOCS;
	}

	public static String getUserByUsername(String username) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_USER_BY_USERNAME_SUFFIX + URL_DOUBLE_QUOTE_CODE + username + URL_DOUBLE_QUOTE_CODE + AND + INCLUDE_DOCS;
	}

	public static String getNoteById(String noteId) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_NOTE_BY_ID + URL_DOUBLE_QUOTE_CODE + noteId + URL_DOUBLE_QUOTE_CODE + AND + INCLUDE_DOCS;
	}

	public static String getSavedNoteById(String noteId) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_SAVED_NOTE_BY_ID + URL_DOUBLE_QUOTE_CODE + noteId + URL_DOUBLE_QUOTE_CODE + AND + INCLUDE_DOCS;
	}
	
	public static String getUserBackPackById(String userId) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_USER_BACKPACK_SUFFIX + URL_DOUBLE_QUOTE_CODE + userId + URL_DOUBLE_QUOTE_CODE + AND + INCLUDE_DOCS;
	}
	
	public static String getUserBackpackDoc(String userId) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_USER_BACKPACK_DOC_SUFFIX + URL_DOUBLE_QUOTE_CODE + userId + URL_DOUBLE_QUOTE_CODE + AND + INCLUDE_DOCS;
	}

	public static String getSavedNoteDocumentById(String noteId) {
		return "https://" + HOST + "/" + DB_NAME + "/" + GET_USER_SAVED_NOTE_DOC_SUFFIX + URL_DOUBLE_QUOTE_CODE + noteId + URL_DOUBLE_QUOTE_CODE + AND + INCLUDE_DOCS;
	}
}



