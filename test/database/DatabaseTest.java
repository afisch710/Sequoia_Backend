package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.UUID;

import model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utility.ReturnCodes;

import com.google.gson.Gson;

import event.NewNoteEvent;
import event.NewUserEvent;


// GIT TEST

public class DatabaseTest {
	private String userNameUnique = "ce9a4664-ecd2-4782-a456-9b9e852e5c2e";
	private String userId = "test";
	private String password = "test";
	private String phoneNumber = "5555555555";
	private String newPass = "new";
	private long createdTimeStamp;
	
	// used for create user test, not any other test!
	private String locUserNameUnique = "9f90c40d-c5a2-4aa5-8c92-aa332691d3a0";
	private String locUserId = "createUserTest";
	private String locPassword = "testLoc";
	private String locPhoneNumber = "5555555555";
	
	// used for create note test
	private String textContent = "This note is for testing purposes.";
	private long timeStamp = System.currentTimeMillis();
	private long lat = 0;
	private long lon = 0;
	private String creatorId = "Test";
	private double range = 400;
	private int duration = 100000;
	private String visibility = "public";
	private String tag = "test";
	private String noteId = "test";
	
	// used for delete note test
	private String createAndDeleteNoteId = "createAndDelete";
	
	@Before
	public void setup() {
		Database db = Database.createInstance(null, null);
		// delete user if exists (only will happen if test fails before deletion)
		int status = db.deleteUser(userId, password);
		while (status != ReturnCodes.DELETE_USER_FAILURE) {
			status = db.deleteUser(userId, password);						
		}
		
		status = db.deleteUser(userId, newPass);
		while (status != ReturnCodes.DELETE_USER_FAILURE) {
			status = db.deleteUser(userId, newPass);						
		}
		
		NewUserEvent user = new NewUserEvent();
		createdTimeStamp = System.currentTimeMillis();
		user.setCreatedTimeStamp(createdTimeStamp);
		user.setId(userId);
		user.setPhoneNumber(phoneNumber);
		user.setEncryptedPassword(password);
		user.setUsername(userNameUnique);
		
		int statusCode = db.saveNewUser(user);
		if (statusCode != ReturnCodes.NEW_USER_SUCCESS) {
			fail("User creation status: " + statusCode);
		}
	}
	
	@Test
	public void createAndDeleteNote() {
		NewNoteEvent event = new NewNoteEvent();
		
		event.setCreatorId(creatorId);
		event.setDuration(duration);
		event.setId(createAndDeleteNoteId);
		event.setLat(lat);
		event.setLon(lon);
		event.setRange(range);
		event.setTag(tag);
		event.setTextContent(textContent);
		event.setTimeStamp(timeStamp);
		event.setVisibility(visibility);
		
		Database db = Database.createInstance(null, null);
		int code = db.saveNewNote(event);
		assertEquals(ReturnCodes.NEW_NOTE_SUCCESS, code);
		
		//TODO: check note range here by passing in coordinates
		String note = db.getNearbyNotes(0, 0);
		System.out.println(note);
		assertEquals(true, note.contains(createAndDeleteNoteId));
		assertEquals(false, db.getNearbyNotes(0, 1).contains(createAndDeleteNoteId));
		
		assertEquals(ReturnCodes.DELETE_NOTE_SUCCESS, db.deleteNote(createAndDeleteNoteId));
	}
	
	/*@Test
	public void deleteNote() {
		
		NewNoteEvent event = new NewNoteEvent();
		
		event.setCreatorId(creatorId);
		event.setDuration(duration);
		event.setId(deleteNoteId);
		event.setLat(lat);
		event.setLon(lon);
		event.setRange(range);
		event.setTag(tag);
		event.setTextContent(textContent);
		event.setTimeStamp(timeStamp);
		event.setVisibility(visibility);
		
		Database db = Database.createInstance(null, null);
		assertEquals(ReturnCodes.NEW_NOTE_SUCCESS, db.saveNewNote(event));
		
		assertEquals(ReturnCodes.DELETE_NOTE_SUCCESS, db.deleteNote(deleteNoteId));
		
		
	}
	*/
	
	@Test
	public void createUser() {
		NewUserEvent user = new NewUserEvent();
		user.setUsername(locUserNameUnique);
		user.setId(locUserId);
		user.setEncryptedPassword(locPassword);
		user.setPhoneNumber(locPhoneNumber);
		
		Database db = Database.createInstance(null, null);
		assertEquals(ReturnCodes.NEW_USER_SUCCESS, db.saveNewUser(user));
	}
	
	@Test
	public void verifyIdByUsername() {
		Database db = Database.createInstance(null, null);
		assertEquals(db.getUserId(userNameUnique), userId);
	}
	
	@Test
	public void verifyPasswordById() {
		Database db = Database.createInstance(null, null);
		assertEquals(db.getAccountPasswordFromId(userId), password);
	}
	
	@Test
	public void verifyPasswordByUsername() {
		Database db = Database.createInstance(null, null);
		assertEquals(db.getAccountPasswordFromUsername(userNameUnique), password);
	}
	
	@Test
	public void verifyUserJsonRetrieval() {
		Database db = Database.createInstance(null, null);
		Gson gson = new Gson();
		User model = new User();
		model.setCreatedTimeStamp(createdTimeStamp);
		model.setUsername(userNameUnique);
		model.setPhoneNumber(phoneNumber);
		model.setId(userId);
		assertEquals(db.getUserInfoJson(userId).split("\\|\\|")[1], gson.toJson(model));
	}
	
	@Test
	public void verifyUserPasswordUpdate() {
		Database db = Database.createInstance(null, null);
		assertEquals(ReturnCodes.UPDATE_USER_PASSWORD_SUCCESS, db.updateUserPassword(userId, password, newPass));
	}
	
	@Test
	public void deleteUser() {
		Database db = Database.createInstance(null, null);
		createUser();
		assertEquals(ReturnCodes.DELETE_USER_SUCCESS, db.deleteUser(locUserId, locPassword));
	}
	
	@After
	public void cleanup() {
		Database db = Database.createInstance(null, null);
		// delete user if exists (only will happen if test fails before deletion)
		int status = db.deleteUser(userId, password);
		while (status != ReturnCodes.DELETE_USER_FAILURE) {
			status = db.deleteUser(userId, password);						
		}
		
		status = db.deleteUser(userId, newPass);
		while (status != ReturnCodes.DELETE_USER_FAILURE) {
			status = db.deleteUser(userId, newPass);						
		}
		
		status = db.deleteUser(locUserId, locPassword);
		while (status != ReturnCodes.DELETE_USER_FAILURE) {
			status = db.deleteUser(locUserId, locPassword);						
		}
		
		status = db.deleteNote(noteId);
		while (status != ReturnCodes.DELETE_NOTE_FAILURE) {
			status = db.deleteNote(noteId);						
		} 
		
	}
	
}
