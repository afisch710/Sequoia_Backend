package servlet;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import servlet.helpers.note.GetSavedNotesRequestHandler;
import utility.ReturnCodes;
import database.Database;
import database.DatabaseConstants;
import event.NewNoteEvent;
import event.NewUserEvent;

public class SaveNoteTest {

	private String encryptedPassword1 = "pass";
	private String id1 = "user1";
	private String phoneNumber1 = "1";
	private String username1 = "user1";
	
	// public note
	private String textContent1 = "This is a public note.";
	private long timeStamp1 = System.currentTimeMillis();
	private long lat1 = 1;
	private long lon1 = 1;
	private String creatorId1 = id1;
	private double range1 = 400;
	private int duration1 = 100000;
	private String visibility1 = "public";
	private String tag1 = "test";
	private String noteId1 = "note1";
	
	// friends note
	private String textContent2 = "This is a friends only note.";
	private long timeStamp2 = System.currentTimeMillis();
	private long lat2 = 1;
	private long lon2 = 1;
	private String creatorId2 = id1;
	private double range2 = 400;
	private int duration2 = 100000;
	private String visibility2 = "friends";
	private String tag2 = "test";
	private String noteId2 = "note2";
	
	@Before
	public void setup() {
		Database db = Database.createInstance(null, null);
		
		NewUserEvent user1 = new NewUserEvent();
		user1.setCreatedTimeStamp(System.currentTimeMillis());
		user1.setEncryptedPassword(encryptedPassword1);
		user1.setId(id1);
		user1.setPhoneNumber(phoneNumber1);
		user1.setUsername(username1);
		
		db.saveNewUser(user1);
		
		NewNoteEvent note1 = new NewNoteEvent();
		NewNoteEvent note2 = new NewNoteEvent();
		
		note1.setCreatorId(creatorId1);
		note1.setDuration(duration1);
		note1.setId(noteId1);
		note1.setLat(lat1);
		note1.setLon(lon1);
		note1.setRange(range1);
		note1.setTag(tag1);
		note1.setTextContent(textContent1);
		note1.setTimeStamp(timeStamp1);
		note1.setVisibility(visibility1);
		
		db.saveNewNote(note1);
		
		note2.setCreatorId(creatorId2);
		note2.setDuration(duration2);
		note2.setId(noteId2);
		note2.setLat(lat2);
		note2.setLon(lon2);
		note2.setRange(range2);
		note2.setTag(tag2);
		note2.setTextContent(textContent2);
		note2.setTimeStamp(timeStamp2);
		note2.setVisibility(visibility2);
		
		db.saveNewNote(note2);
	}
	
	@Test
	public void test() {
		Database db = Database.createInstance(null, null);
		
		assertEquals(db.getSavedNotesJson(id1).equals("" + ReturnCodes.GET_SAVED_NOTES_NONE), true);
		db.saveExistingNote(id1, noteId1);
//		System.out.println("Note 1 has been saved:");
//		System.out.println(db.getSavedNotesJson(id1));
		assertEquals(db.getSavedNotesJson(id1).contains(noteId1), true);
		assertEquals(db.getSavedNotesJson(id1).contains(noteId2), false);
		
		db.saveExistingNote(id1, noteId2);
//		System.out.println("Note 2 has been saved:");
		System.out.println(db.getSavedNotesJson(id1));
		assertEquals(db.getSavedNotesJson(id1).contains(noteId1), true);
		assertEquals(db.getSavedNotesJson(id1).contains(noteId2), true);
		
	}
	
	@After
	public void cleanup() {
		Database db = Database.createInstance(null, null);
		
		db.deleteUser(id1, encryptedPassword1);
		
		db.deleteNote(noteId1);
		db.deleteNote(noteId2);
		
		db.deleteSavedNote(id1, noteId1);
		db.deleteSavedNote(id1, noteId2);
		assertEquals(db.getSavedNotesJson(id1).equals("" + ReturnCodes.GET_SAVED_NOTES_NONE), true);
	}

}
