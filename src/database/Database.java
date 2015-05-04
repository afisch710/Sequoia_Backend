package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;

import model.AlreadyFriendModel;
import model.NoteGpsInfo;
import model.User;

import org.apache.commons.codec.binary.Base64;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;

import utility.OtherConstants;
import utility.ReturnCodes;

import com.google.gson.Gson;

import container.AddFriendEventContainer;
import container.NewNoteEventContainer;
import container.NewUserEventContainer;
import container.SaveNoteEventContainer;
import container.UserBackpackEventContainer;
import event.AddFriendEvent;
import event.NewNoteEvent;
import event.NewUserEvent;
import event.NoteAndCreatorEvent;
import event.UserBackpackEvent;

public class Database {

	//When requesting data, the user must always pass in their authentication token. The token may then be used in the
	//static instantiation method to get a database object. If the authentication token is incorrect, null will be 
	//returned. 
	
	public static Database createInstance(String username, String token) {
		///TODO look up token in database
		// if token matches, create database instance and return it
		// else return null
		
		return new Database();
	}
	
	private Database() {
		
	}
	
	public String removeFriend(String userId, String friendId) {
		
		String list = getFriendsListIds(userId);
		
		if (list.contains(friendId)) {
			DocumentHandle doc = getFriendsListDocumentHandle(userId);
			getDBClient().remove(doc.get_id(), doc.get_rev());
			
			AddFriendEvent event = new AddFriendEvent();
			
			String[] ids = list.split(OtherConstants.ATTRIBUTE_DELIMITER);
			String csv = "";
			
			for (String id : ids) {
				if (!id.equals(friendId)) {
					csv += id + OtherConstants.ATTRIBUTE_DELIMITER;
				}
			}
			
			event.setUserId(userId);
			event.setFriendIds(csv);
			
			AddFriendEventContainer container = new AddFriendEventContainer();
			
			container.setFriendList(event);
			
			if (!event.getFriendIds().equals("")) {
				getDBClient().save(container);
			}
			
			
			return "" + ReturnCodes.REMOVE_FRIEND_SUCCESS;
		}
		else {
			return "" + ReturnCodes.REMOVE_FRIEND_FAILURE;
		}
		
	}
	
	public String deleteSavedNote(String userId, String noteId) {
		UserBackpackEvent backpack = getUserBackpack(userId);
		
		if (backpack != null) {
			if (backpack.getNoteIds().contains(noteId)) {
				String[] ids = backpack.getNoteIds().split(",");
				String csv = "";
				
				for (String id : ids) {
					if (!id.equals(noteId)) {
						csv += id + ",";
					}
				}
				
				backpack.setNoteIds(csv);
				
				DocumentHandle handle = getBackpackDocument(userId);
				
				getDBClient().remove(handle.get_id(), handle.get_rev());
				
				UserBackpackEventContainer container = new UserBackpackEventContainer();
				container.setBackpack(backpack);
				
				getDBClient().save(container);
				
				return "" + ReturnCodes.DELETE_SAVED_NOTE_SUCCESS;
			}
		}
		
		return "" + ReturnCodes.DELETE_SAVED_NOTE_FAILURE;
	}
	
	public String deleteSavedNote(String noteId) {
		DocumentHandle handle = getSavedNoteDocument(noteId);
		
		getDBClient().remove(handle.get_id(), handle.get_rev());
		
		return null;
	}
	
	public NewNoteEvent getNoteById(String noteId) {
		String raw = retrieveDatabaseData(DatabaseConstants.getNoteById(noteId));
		
		if (raw == null || raw.equals("")) {
			return null;
		}
		
		NewNoteEvent event = new NewNoteEvent();
		String[] noteAtt = raw.split(OtherConstants.LIST_DELIMITER)[0].split(OtherConstants.ATTRIBUTE_DELIMITER);
		String id = noteAtt[0];
		String textContent = noteAtt[1];
		long timeStamp = Long.parseLong(noteAtt[2]);
		double lat = Double.parseDouble(noteAtt[3]);
		double lon = Double.parseDouble(noteAtt[4]);
		String creatorId = noteAtt[5];
		double range = Double.parseDouble(noteAtt[6]);
		long duration = Long.parseLong(noteAtt[7]);
		String visibility = noteAtt[8];
		String tag = noteAtt[9];
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
		
		return event;
	}
	
	public List<NoteAndCreatorEvent> getSavedNotes(String userId) {
		UserBackpackEvent backpack = getUserBackpack(userId);
		
		if (backpack == null) {
			return null;
		}
		
		String[] noteIds = backpack.getNoteIds().split(",");
		List<NoteAndCreatorEvent> notes = new ArrayList<NoteAndCreatorEvent>();
		
		for (String id : noteIds) {
			NewNoteEvent note = getSavedNote(id);
			if (note != null) {
				User user = getUserById(note.getCreatorId());
				NoteAndCreatorEvent nac = new NoteAndCreatorEvent();
				
				nac.setCreatorId(note.getCreatorId());
				nac.setDuration(note.getDuration());
				nac.setLat(note.getLat());
				nac.setLon(note.getLon());
				nac.setNoteId(note.getId());
				nac.setPhoneNumber(user.getPhoneNumber());
				nac.setRange(note.getRange());
				nac.setTag(note.getTag());
				nac.setTextContent(note.getTextContent());
				nac.setTimeStamp(note.getTimeStamp());
				nac.setUserId(user.getId());
				nac.setUsername(user.getUsername());
				
				notes.add(nac);
			}
		}
		
		if (notes.isEmpty()) {
			return null;
		}
		
		return notes;
	}
	
	public String getSavedNotesJson(String userId) {
		List<NoteAndCreatorEvent> notes = getSavedNotes(userId);
		
		if (notes == null) {
			return "" + ReturnCodes.GET_SAVED_NOTES_NONE;
		}
		
		return (new Gson()).toJson(notes.toArray());
	}
	
	public NewNoteEvent getSavedNote(String noteId) {
		
		String raw = retrieveDatabaseData(DatabaseConstants.getSavedNoteById(noteId));
		if (raw == null || raw.equals("")) {
			return null;
		}
		
		NewNoteEvent event = new NewNoteEvent();
		String[] noteAtt = raw.split(OtherConstants.LIST_DELIMITER)[0].split(OtherConstants.ATTRIBUTE_DELIMITER);
		String id = noteAtt[0];
		String textContent = noteAtt[1];
		long timeStamp = Long.parseLong(noteAtt[2]);
		double lat = Double.parseDouble(noteAtt[3]);
		double lon = Double.parseDouble(noteAtt[4]);
		String creatorId = noteAtt[5];
		double range = Double.parseDouble(noteAtt[6]);
		long duration = Long.parseLong(noteAtt[7]);
		String visibility = noteAtt[8];
		String tag = noteAtt[9];
//		System.out.println("Parse success");
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
		
		return event;
	}
	
	public UserBackpackEvent getUserBackpack(String userId) {
		String raw = retrieveDatabaseData(DatabaseConstants.getUserBackPackById(userId));
		
		if (raw == null || raw.equals("")) {
			return null;
		}
		
		String[] data = raw.split(OtherConstants.ATTRIBUTE_DELIMITER);
		
		UserBackpackEvent backpack = new UserBackpackEvent();
		
		backpack.setUserId(data[0]);
		if (data.length > 1) {
			backpack.setNoteIds(data[1]);
		}
		else {
			backpack.setNoteIds("");
		}
		
		return backpack;
	}
	
	public String saveExistingNote(String userId, String noteId) {
		
		NewNoteEvent savedNote = getSavedNote(noteId);
		
		if (savedNote == null) {
			// save note
			SaveNoteEventContainer container = new SaveNoteEventContainer();
			NewNoteEvent note = getNoteById(noteId);
			if (note == null) {
				return "" + ReturnCodes.SAVE_NOTE_FAILURE;
			}
			else {
				container.setSavedNote(note);
				getDBClient().save(container);
			}
		}
		
		UserBackpackEvent backpack = getUserBackpack(userId);
		UserBackpackEventContainer container = new UserBackpackEventContainer();
		
		if (backpack == null) {
			backpack = new UserBackpackEvent();
			backpack.setUserId(userId);
			backpack.setNoteIds(noteId + ",");
			
			container.setBackpack(backpack);
			
			getDBClient().save(container);
			
			return "" + ReturnCodes.SAVE_NOTE_SUCCESS;
		}
		else {
			backpack.setNoteIds(backpack.getNoteIds() + noteId + ",");
			
			// remove old backpack doc
			DocumentHandle handle = getBackpackDocument(userId);
			
			getDBClient().remove(handle.get_id(), handle.get_rev());
			
			container.setBackpack(backpack);
			getDBClient().save(container);
			
			return "" + ReturnCodes.SAVE_NOTE_SUCCESS;
		}
		
	}
	
	public String getPrivateNotes(String userId) {
		List<NewNoteEvent> allNotes = getAllNotes();
		if (allNotes == null || allNotes.isEmpty()) {
			return "" + ReturnCodes.GET_PRIVATE_NOTES_NONE;
		}
		else {
			List<NoteAndCreatorEvent> privateNotes = new ArrayList<NoteAndCreatorEvent>();
			
			for (NewNoteEvent note : allNotes) {
				if (note.getVisibility().contains(userId)) {
					NoteAndCreatorEvent nac = new NoteAndCreatorEvent();

					User user = getUserById(note.getCreatorId());
					
					nac.setCreatorId(note.getCreatorId());
					nac.setDuration(note.getDuration());
					nac.setLat(note.getLat());
					nac.setLon(note.getLon());
					nac.setNoteId(note.getId());
					nac.setPhoneNumber(user.getPhoneNumber());
					nac.setRange(note.getRange());
					nac.setTag(note.getTag());
					nac.setTextContent(note.getTextContent());
					nac.setTimeStamp(note.getTimeStamp());
					nac.setUserId(user.getId());
					nac.setUsername(user.getUsername());
					
					privateNotes.add(nac);
				}
			}
			
			if (privateNotes.isEmpty()) {
				return "" + ReturnCodes.GET_PRIVATE_NOTES_NONE;
			}
			
			return (new Gson()).toJson(privateNotes.toArray());
		}
	}
	
	public String searchUserByUsername(String username) {
		User user = getUserByUsername(username);
		
		if (user == null) {
			return "" + ReturnCodes.SEARCH_BY_USERNAME_DOES_NOT_EXIST;
		}
		else {
			return (new Gson()).toJson(user);
		}
	}
	
	public User getUserByUsername(String username) {
		String raw = retrieveDatabaseData(DatabaseConstants.getUserByUsername(username));
		
		if (raw == null || raw.equals("")) {
			return null;
		}
		
		String[] attributes = raw.split(OtherConstants.LIST_DELIMITER)[0].split(OtherConstants.ATTRIBUTE_DELIMITER);
		
		User user = new User();
		
		user.setId(attributes[0]);
		user.setUsername(attributes[1]);
		user.setPhoneNumber(attributes[2]);
		user.setCreatedTimeStamp(Long.parseLong(attributes[3]));
		
		return user;
	}
	
	public String getFriendsList(String userId) {
		List<User> friends = new ArrayList<User>();
		String rawIds = getFriendsListIds(userId);
		
		if (rawIds == null || rawIds.equals("")) {
			return "" + ReturnCodes.GET_FRIENDS_NONE;
		}
		
		String[] ids = rawIds.split(OtherConstants.ATTRIBUTE_DELIMITER);
		
		for (String id : ids) {
			friends.add(getUserById(id));
		}
		
		if (friends.isEmpty()) {
			return "" + ReturnCodes.GET_FRIENDS_NONE;
		}
		
		return (new Gson()).toJson(friends.toArray());
	}
	
	public String getNearbyNotes(String userId, double lat, double lon) {
		
		List<NewNoteEvent> allNotes = getAllNotes();
		boolean add = false;
		if (allNotes == null || allNotes.isEmpty()) {
			return "" + ReturnCodes.GET_NEARBY_NOTES_NONE;
		}
		else {
			List<NoteAndCreatorEvent> notes = new ArrayList<NoteAndCreatorEvent>();
			
			for (NewNoteEvent note : allNotes) {
				if (distance(note.getLat(), note.getLon(), lat, lon) < note.getRange()) {
					add = false;
					if (note.getCreatorId().equals(userId)) {
						add = true;
					}
					else if (note.getVisibility().equalsIgnoreCase("public")) {
						add = true;
					}
					else if (note.getVisibility().equalsIgnoreCase("friends")) {
						if (getFriendsListIds(note.getCreatorId()).contains(userId)) {
							add = true;
						}
					}
					else {
						if (note.getVisibility().contains(userId)) {
							add = true;
						}
					}
					
					if (add) {
						NoteAndCreatorEvent nac = new NoteAndCreatorEvent();
						User user = getUserById(note.getCreatorId());
						
						nac.setCreatorId(note.getCreatorId());
						nac.setDuration(note.getDuration());
						nac.setLat(note.getLat());
						nac.setLon(note.getLon());
						nac.setNoteId(note.getId());
						nac.setPhoneNumber(user.getPhoneNumber());
						nac.setRange(note.getRange());
						nac.setTag(note.getTag());
						nac.setTextContent(note.getTextContent());
						nac.setTimeStamp(note.getTimeStamp());
						nac.setUserId(user.getId());
						nac.setUsername(user.getUsername());
						
						notes.add(nac);
						
					}
				}
			}
			
			if (notes.isEmpty()) {
				return "" + ReturnCodes.GET_NEARBY_NOTES_NONE;
			}
			
			return (new Gson()).toJson(notes.toArray());
		}
	}
	
	public int addFriend(String userId, String friendId) {
		String friendsRaw = getFriendsListIds(userId);
		AddFriendEventContainer container = new AddFriendEventContainer();
		AddFriendEvent event = new AddFriendEvent();
		if (friendsRaw == null || friendsRaw.equals("")) {
			// create friend document
			event.setUserId(userId);
			event.setFriendIds(friendId + OtherConstants.ATTRIBUTE_DELIMITER);
			container.setFriendList(event);
			getDBClient().save(container);
		}
		else {
			// append friend document
			if (friendsRaw.contains(friendId)) {
				return ReturnCodes.ADD_FRIEND_FAILURE;
			}
			DocumentHandle doc = getFriendsListDocumentHandle(userId);
			if (doc == null) {
				return ReturnCodes.ADD_FRIEND_FAILURE;
			}
			event.setUserId(userId);
			event.setFriendIds(friendsRaw + friendId + OtherConstants.ATTRIBUTE_DELIMITER);
			container.setFriendList(event);
			getDBClient().remove(doc.get_id(), doc.get_rev());
			getDBClient().save(container);
		}
		return ReturnCodes.ADD_FRIEND_SUCCESS;
	}
	
	public DocumentHandle getFriendsListDocumentHandle(String userId) {
		String raw = retrieveDatabaseData(DatabaseConstants.getFriendsListDocumentHandle(userId));
		if (raw == null || raw.equals("")) {
			return null;
		}
		String[] handle = raw.split(OtherConstants.ATTRIBUTE_DELIMITER);
		
		return new DocumentHandle(handle[0], handle[1]);
	}
	
	public String getFriendsListIds(String userId) {
		return retrieveDatabaseData(DatabaseConstants.getFriendsListUrl(userId));
	}
	
	public String getNumbersOfUsersFromList(String userId, String list) {
		
		String[] numbers = list.split(OtherConstants.ATTRIBUTE_DELIMITER);
		String csv = "";
		String json = "";
		List<AlreadyFriendModel> users = new ArrayList<AlreadyFriendModel>();
		boolean matchFound = false;
		String[] friendIds = getFriendsListIds(userId).split(OtherConstants.ATTRIBUTE_DELIMITER);
		List<String> ids = new ArrayList<String>();
		
		for (String id : friendIds) {
			ids.add(id);
		}
		
		for (String number : numbers) {
			String user = getUserByNumber(number);
			if (user != null && !user.equals("")) {
//				System.out.println(user);
				// create user
				AlreadyFriendModel userModel = new AlreadyFriendModel();
//				NewUserEvent userModel = new NewUserEvent();
				String[] userData = user.split(OtherConstants.ATTRIBUTE_DELIMITER);
				userModel.setId(userData[0]);
				userModel.setUsername(userData[1]);
				userModel.setPhoneNumber(userData[2]);
				userModel.setCreatedTimeStamp(Long.parseLong(userData[3]));
				if (ids.contains(userModel.getId())) {
					userModel.setAlreadyFriend("true");
				}
				else {
					userModel.setAlreadyFriend("false");
				}
				// add to list
				users.add(userModel);
				matchFound = true;
			}
			/*if (isUserNumber(number)) {
				csv += number + ",";
				matchFound = true;
			}*/
		}
		
		if (matchFound) {
			return "" + ReturnCodes.GET_MATCHING_NUMBERS_SUCCESS + "||" + (new Gson()).toJson(users.toArray());
		}
		else {
			return "" + ReturnCodes.GET_MATCHING_NUMBERS_NONE;
		}
	}
	
	public String getNumbersOfUsersFromList(String list) {
		String[] numbers = list.split(OtherConstants.ATTRIBUTE_DELIMITER);
		String csv = "";
		String json = "";
		List<NewUserEvent> users = new ArrayList<NewUserEvent>();
		boolean matchFound = false;
		
		for (String number : numbers) {
			String user = getUserByNumber(number);
			if (user != null && !user.equals("")) {
//				System.out.println(user);
				// create user
				NewUserEvent userModel = new NewUserEvent();
				String[] userData = user.split(OtherConstants.ATTRIBUTE_DELIMITER);
				userModel.setId(userData[0]);
				userModel.setUsername(userData[1]);
				userModel.setPhoneNumber(userData[2]);
				userModel.setCreatedTimeStamp(Long.parseLong(userData[3]));
				// add to list
				users.add(userModel);
				matchFound = true;
			}
			/*if (isUserNumber(number)) {
				csv += number + ",";
				matchFound = true;
			}*/
		}
		
		if (matchFound) {
			return "" + ReturnCodes.GET_MATCHING_NUMBERS_SUCCESS + "||" + (new Gson()).toJson(users.toArray());
		}
		else {
			return "" + ReturnCodes.GET_MATCHING_NUMBERS_NONE;
		}
	}
	
	public String getUserByNumber(String number) {
		
		String results = retrieveDatabaseData(DatabaseConstants.getUserByNumberURL(number));
		if (results == null || results.equals("")) {
			return null;
		}
		
		return results;
	}
	
	public boolean isUserNumber(String number) {
		
		String results = retrieveDatabaseData(DatabaseConstants.getIsUserNumberURL(number));
		if (results == null || results.equals("")) {
			return false;
		}
		
		return true;
	}
	
	public String getNearbyNotes(double lat, double lon) {
		List<NewNoteEvent> allNotes = getAllNotes();
		//TODO fix this!
		if (allNotes == null || allNotes.isEmpty()) {
			return "";
		}
		else {
			List<NewNoteEvent> nearbyNotes = new ArrayList<NewNoteEvent>();
			
			for (NewNoteEvent note : allNotes) {
				if (distance(note.getLat(), note.getLon(), lat, lon) < note.getRange()) {
					nearbyNotes.add(note);
				}
			}
			
			if (nearbyNotes.isEmpty()) {
				return "";
			}
			
			return (new Gson()).toJson(nearbyNotes.toArray());
		}
	}
	
	public List<NewNoteEvent> getAllNotes() {
		List<NewNoteEvent> notes = new ArrayList<NewNoteEvent>();
		
		String csv = retrieveDatabaseData(DatabaseConstants.getAllNotesURL());
		
		if (csv == null || csv.equals("")) {
			return null;
		}
		
		String[] csvNotes = csv.split(OtherConstants.LIST_DELIMITER);

		for (String csvNote : csvNotes) {
			NewNoteEvent event = new NewNoteEvent();
			String[] noteAtt = csvNote.split(OtherConstants.ATTRIBUTE_DELIMITER);
			String id = noteAtt[0];
			String textContent = noteAtt[1];
			long timeStamp = Long.parseLong(noteAtt[2]);
			double lat = Double.parseDouble(noteAtt[3]);
			double lon = Double.parseDouble(noteAtt[4]);
			String creatorId = noteAtt[5];
			double range = Double.parseDouble(noteAtt[6]);
			long duration = Long.parseLong(noteAtt[7]);
			String visibility = noteAtt[8];
			String tag = noteAtt[9];
//			System.out.println("Parse success");
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
			
			// delete notes that have expired
			if (event.isExpired()) {
				deleteNote(event.getId());
			}
			else {
				notes.add(event);
			}
		}
		if (notes == null || notes.isEmpty()) {
			return null;
		}
		else {
			return notes;
		}
	}
	
	public List<NoteGpsInfo> getAllGPSNoteInfo() {
		List<NoteGpsInfo> notes = new ArrayList<NoteGpsInfo>();
		
		String csv = retrieveDatabaseData(DatabaseConstants.getAllNotesGpsCoordinatesURL());
		String[] csvNotes = csv.split(OtherConstants.LIST_DELIMITER);
		
		for (String csvNote : csvNotes) {
			String[] noteAtt = csvNote.split(OtherConstants.ATTRIBUTE_DELIMITER);
			NoteGpsInfo gpsInfo = new NoteGpsInfo();
			
			gpsInfo.setId(noteAtt[0]);
			gpsInfo.setLat(Long.parseLong(noteAtt[1]));
			gpsInfo.setLon(Long.parseLong(noteAtt[2]));
			
			notes.add(gpsInfo);
		}
		
		return notes;
	}
	
	public int saveNewNote(NewNoteEvent event) {
		NewNoteEventContainer container = new NewNoteEventContainer();
		container.setNoteInfo(event);
//		System.out.println(container.getNoteInfo().getId());
		getDBClient().save(container);
		
		return ReturnCodes.NEW_NOTE_SUCCESS;
	}
	
	public int deleteNote(String noteId) {
		DocumentHandle doc = getNoteDocument(noteId);
		
		if (doc != null) {
			getDBClient().remove(doc.get_id(), doc.get_rev());
			
			return ReturnCodes.DELETE_NOTE_SUCCESS;
		}
		
		return ReturnCodes.DELETE_NOTE_FAILURE;
	}
	
	public DocumentHandle getNoteDocument(String noteId) {
		String handleString = retrieveDatabaseData(DatabaseConstants.getNoteInfoDocumentHandle(noteId));
		
		if (handleString != null && !handleString.equals("")) {
			String[] handleArr = handleString.split(OtherConstants.ATTRIBUTE_DELIMITER);
			
			return new DocumentHandle(handleArr[0], handleArr[1]);
		}
		
		return null;
	}
	
	public String getAccountPasswordFromUsername(String username) {
		return retrieveDatabaseData(DatabaseConstants.getAccountPasswordURL(username));
	}
	
	public String getAccountPasswordFromId(String userId) {
		return retrieveDatabaseData(DatabaseConstants.getAccountPasswordFromIdURL(userId));
	}
	
	public String getUserId(String username) {
		return retrieveDatabaseData(DatabaseConstants.getUserIdURL(username));
	}
	
	public int updateUserPassword(String userId, String oldPass, String newPass) {
		if (getAccountPasswordFromId(userId).equals(oldPass)) {
			String userJson = getUserInfoJson(userId);
			Gson gson = new Gson();
			User userInfo = gson.fromJson(userJson.split("\\|\\|")[1], User.class);
			DocumentHandle doc = getUserDocument(userId);
			getDBClient().remove(doc.get_id(), doc.get_rev());
			
			NewUserEvent event = new NewUserEvent();
			event.setId(userInfo.getId());
			event.setUsername(userInfo.getUsername());
			event.setPhoneNumber(userInfo.getPhoneNumber());
			event.setCreatedTimeStamp(userInfo.getCreatedTimeStamp());
			event.setEncryptedPassword(newPass);
			
			saveNewUser(event);
			
			return ReturnCodes.UPDATE_USER_PASSWORD_SUCCESS;
		}
		else {
			return ReturnCodes.UPDATE_USER_PASSWORD_FAILURE_INCORRECT_OLD_PASS;
		}
	}
	
	public int deleteUser(String userId, String password) {
		if (getAccountPasswordFromId(userId).equals(password)) {
			DocumentHandle doc = getUserDocument(userId);
			getDBClient().remove(doc.get_id(), doc.get_rev());
			
			return ReturnCodes.DELETE_USER_SUCCESS;
		}
		return ReturnCodes.DELETE_USER_FAILURE;
	}
	
	public int saveNewUser(NewUserEvent userEvent) {
		if (getAccountPasswordFromUsername(userEvent.getUsername()).equals("")) {
			
			NewUserEventContainer container = new NewUserEventContainer();
			container.setUserInfo(userEvent);
			
			getDBClient().save(container);
			return ReturnCodes.NEW_USER_SUCCESS;
		} 
		else {
			return ReturnCodes.NEW_USER_USERNAME_ALREADY_EXISTS;
		}
	}
	
	public String getUserInfoJson(String userId) {
		User user = getUserById(userId);
		
		if (user == null) {
			return "" + ReturnCodes.GET_USER_INFO_DOES_NOT_EXIST;
		}
		return "" + ReturnCodes.GET_USER_INFO_SUCCESS + "||" + (new Gson()).toJson(user);
	}
	
	public User getUserById(String userId) {
		String rawData = retrieveDatabaseData(DatabaseConstants.getUserInfoURL(userId));
		
		if (rawData == null || rawData.equals("")) {
			return null;
		}
		
		String[] attributes = rawData.split(OtherConstants.ATTRIBUTE_DELIMITER);
		User user = new User();
		user.setId(attributes[0]);
		user.setUsername(attributes[1]);
		user.setPhoneNumber(attributes[2]);
		user.setCreatedTimeStamp(Long.parseLong(attributes[3]));
		
		return user;
	}
	
	private String retrieveDatabaseData(String urlString) {
		String data = null;
		
		URL url;
		try {
			url = new URL(urlString);
			
			URLConnection conn = openConnection(url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			// reads from file or url
			data = "";
			String line = null;
			while ((line = reader.readLine()) != null) {
				String realline = line.replaceAll("&#39;", "'");
				realline = line.replaceAll("%27;", "'");
				data += realline;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "URL: " + urlString;
		}
		
		return data;
	}
	
	private URLConnection openConnection(URL url) throws IOException {
		URLConnection conn = url.openConnection();
		String userpass = DatabaseConstants.USERNAME + ":" + DatabaseConstants.PASSWORD;
		String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
		conn.setRequestProperty ("Authorization", basicAuth);
		conn.setDoInput(true);
		conn.setDoOutput(false);
		
		return conn;
	}
	
	private CouchDbClient getDBClient() {
		CouchDbProperties properties = new CouchDbProperties()
		  .setDbName(DatabaseConstants.DB_NAME)
		  .setCreateDbIfNotExist(false)
		  .setProtocol("https")
		  .setHost(DatabaseConstants.HOST)
		  .setPort(443)
		  .setUsername(DatabaseConstants.USERNAME)
		  .setPassword(DatabaseConstants.PASSWORD)
		  .setMaxConnections(100)
		  .setConnectionTimeout(0);
		
		return new CouchDbClient(properties);
	}
	
	private DocumentHandle getUserDocument(String userId) {
		String[] csv = retrieveDatabaseData(DatabaseConstants.getUserDocumentHandleFromUserId(userId)).split(OtherConstants.ATTRIBUTE_DELIMITER);
		
		return new DocumentHandle(csv[0], csv[1]);
	}
	
	private DocumentHandle getSavedNoteDocument(String noteId) {
		String[] csv = retrieveDatabaseData(DatabaseConstants.getSavedNoteDocumentById(noteId)).split(OtherConstants.ATTRIBUTE_DELIMITER);
		
		return new DocumentHandle(csv[0], csv[1]);
	}
	
	private DocumentHandle getBackpackDocument(String userId) {
		String[] csv = retrieveDatabaseData(DatabaseConstants.getUserBackpackDoc(userId)).split(OtherConstants.ATTRIBUTE_DELIMITER);
		
		return new DocumentHandle(csv[0], csv[1]);
	}
	
	private double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515; // converting to miles
		dist = dist * 1.609344 * 1000; // converting to meters
		
		return dist;
	}
	
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}


}
