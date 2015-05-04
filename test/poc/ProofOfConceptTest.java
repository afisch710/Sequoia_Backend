package poc;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import utility.OtherConstants;
import utility.ReturnCodes;
import database.Database;
import database.DatabaseConstants;
import event.NewNoteEvent;
import event.NewUserEvent;


public class ProofOfConceptTest {

	
	
		@Test
		public void test() {
			Database db = Database.createInstance(null, null);
			String userId = "01d7b199-a152-4d6c-ae32-0e03156e3c6c"; // birgs
			String friendId = "7be1c7de-1a89-4b12-a142-f368b647ecf6"; // steve
			String friendId2 = "092aca4e-5c6a-4c7b-aaa3-e33b887090fb"; // alex
			System.out.println(db.addFriend(friendId, userId));
//			System.out.println(db.removeFriend(friendId, userId));
			String list = "6304058967" + OtherConstants.ATTRIBUTE_DELIMITER + "6307449852" + OtherConstants.ATTRIBUTE_DELIMITER;
//			System.out.println(db.getNumbersOfUsersFromList(userId, list));
			System.out.println(db.getFriendsListIds(friendId));
		}

}
