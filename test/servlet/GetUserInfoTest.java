package servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import servlet.helpers.user.GetUserInfoRequestHandler;

import com.google.gson.Gson;

import database.Database;
import event.NewUserEvent;

public class GetUserInfoTest {

	private String userNameUnique = "ce9a4664-ecd2-4782-a456-9b9e852e5c2e";
	private String userId = "test";
	private String password = "test";
	private String phoneNumber = "5555555555";
	private long createdTimeStamp;
	
	@Before
	public void userSetup() {
		NewUserEvent user = new NewUserEvent();
		createdTimeStamp = System.currentTimeMillis();
		user.setCreatedTimeStamp(createdTimeStamp);
		user.setId(userId);
		user.setPhoneNumber(phoneNumber);
		user.setEncryptedPassword(password);
		user.setUsername(userNameUnique);
		Database.createInstance(null, null).saveNewUser(user);
	}
	
	@Test
	public void get() {
		try {
			HttpServletRequest request = mock(HttpServletRequest.class);
			HttpServletResponse response = mock(HttpServletResponse.class);
			StringWriter stringWriter = new StringWriter();
			PrintWriter writer = new PrintWriter(stringWriter);
			when(response.getWriter()).thenReturn(writer);
			when(request.getParameter("userId")).thenReturn(userId);
			
			/*GetUserInfo servlet = new GetUserInfo();
			servlet.doGet(request, response);
			writer.flush();*/
			
			// new test
			GetUserInfoRequestHandler handler = new GetUserInfoRequestHandler();
			handler.handleGetRequest(request, response);
			writer.flush();
			// new test

			Gson gson = new Gson();
			String json = stringWriter.toString().split("\\|\\|")[1];
			NewUserEvent user = gson.fromJson(json, NewUserEvent.class);
			assertEquals(userId, user.getId());
			assertEquals(phoneNumber, user.getPhoneNumber());
			assertEquals(userNameUnique, user.getUsername());
//			assertEquals(createdTimeStamp, user.getCreatedTimeStamp());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.toString());
		}
		//{"id":"test","username":"ce9a4664-ecd2-4782-a456-9b9e852e5c2e","phoneNumber":"5555555555","createdTimeStamp":1424235272843}

	}
	
	@Test
	public void post() {
		// no post method
		assertEquals(true, true);
	}
	
	@After
	public void userCleanup() {
		Database.createInstance(null, null).deleteUser(userId, password);
	}

}
