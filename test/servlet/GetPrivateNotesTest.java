package servlet;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.Database;
import event.NewNoteEvent;
import event.NewUserEvent;

public class GetPrivateNotesTest {

	private String encryptedPassword1 = "pass";
	private String id1 = "user1";
	private String phoneNumber1 = "1";
	private String username1 = "user1";
	
	private String encryptedPassword2 = "pass";
	private String id2 = "user2";
	private String phoneNumber2 = "2";
	private String username2 = "user2";
	
	private String encryptedPassword3 = "pass";
	private String id3 = "user3";
	private String phoneNumber3 = "3";
	private String username3 = "user3";
	
	private String encryptedPassword4 = "pass";
	private String id4 = "user4";
	private String phoneNumber4 = "4";
	private String username4 = "user4";
	
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
	
	// private note
	private String textContent3 = "This is a private note.";
	private long timeStamp3 = System.currentTimeMillis();
	private long lat3 = 1;
	private long lon3 = 1;
	private String creatorId3 = id1;
	private double range3 = 400;
	private int duration3 = 100000;
	private String visibility3 = id2 + ",";
	private String tag3 = "test";
	private String noteId3 = "note3";
	
	@Before
	public void setup() {
		Database db = Database.createInstance(null, null);
		
		NewUserEvent user1 = new NewUserEvent();
		NewUserEvent user2 = new NewUserEvent();
		NewUserEvent user3 = new NewUserEvent();
		NewUserEvent user4 = new NewUserEvent();
		
		user1.setCreatedTimeStamp(System.currentTimeMillis());
		user1.setEncryptedPassword(encryptedPassword1);
		user1.setId(id1);
		user1.setPhoneNumber(phoneNumber1);
		user1.setUsername(username1);
		
		user2.setCreatedTimeStamp(System.currentTimeMillis());
		user2.setEncryptedPassword(encryptedPassword2);
		user2.setId(id2);
		user2.setPhoneNumber(phoneNumber2);
		user2.setUsername(username2);
		
		user3.setCreatedTimeStamp(System.currentTimeMillis());
		user3.setEncryptedPassword(encryptedPassword3);
		user3.setId(id3);
		user3.setPhoneNumber(phoneNumber3);
		user3.setUsername(username3);
		
		user4.setCreatedTimeStamp(System.currentTimeMillis());
		user4.setEncryptedPassword(encryptedPassword4);
		user4.setId(id4);
		user4.setPhoneNumber(phoneNumber4);
		user4.setUsername(username4);
		
		db.saveNewUser(user1);
		db.saveNewUser(user2);
		db.saveNewUser(user3);
		
		db.addFriend(user1.getId(), user2.getId());
		db.addFriend(user1.getId(), user3.getId());
		
		db.addFriend(user2.getId(), user1.getId());
		
		// Friends
		// user1 -> user2
		// user1 -> user3
		// user2 -> user1
		
		NewNoteEvent note1 = new NewNoteEvent();
		NewNoteEvent note2 = new NewNoteEvent();
		NewNoteEvent note3 = new NewNoteEvent();
		
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
		
		note3.setCreatorId(creatorId3);
		note3.setDuration(duration3);
		note3.setId(noteId3);
		note3.setLat(lat3);
		note3.setLon(lon3);
		note3.setRange(range3);
		note3.setTag(tag3);
		note3.setTextContent(textContent3);
		note3.setTimeStamp(timeStamp3);
		note3.setVisibility(visibility3);
		
		db.saveNewNote(note1);
		db.saveNewNote(note2);
		db.saveNewNote(note3);
	}
	
	@Test
	public void test() {
		Database db = Database.createInstance(null, null);
		
		//System.out.println("User 1 private notes: " + db.getPrivateNotes(id1));
		//System.out.println("User 2 private notes: " + db.getPrivateNotes(id2));
		//System.out.println("User 3 private notes: " + db.getPrivateNotes(id3));
		
		assertEquals(db.getPrivateNotes(id1), "1331");
		assertEquals(db.getPrivateNotes(id2).contains("note3"), true);
		assertEquals(db.getPrivateNotes(id3), "1331");
	}
	
	@After
	public void cleanup() {
		Database db = Database.createInstance(null, null);
		
		db.deleteUser(id1, encryptedPassword1);
		db.deleteUser(id2, encryptedPassword2);
		db.deleteUser(id3, encryptedPassword3);
		
		db.deleteNote(noteId1);
		db.deleteNote(noteId2);
		db.deleteNote(noteId3);
	}

}
