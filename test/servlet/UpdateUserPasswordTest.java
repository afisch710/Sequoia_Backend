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

import servlet.helpers.user.UpdateUserPasswordRequestHandler;
import utility.ReturnCodes;
import database.Database;
import event.NewUserEvent;

public class UpdateUserPasswordTest {

	private String userNameUnique = "ce9a4664-ecd2-4782-a456-9b9e852e5c2e";
	private String userId = "test";
	private String password = "test";
	private String newPass = "newTest";
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
		// no get method
		assertEquals(true, true);
	}
	
	@Test
	public void post() {
		try {
			HttpServletRequest request = mock(HttpServletRequest.class);
			HttpServletResponse response = mock(HttpServletResponse.class);
			StringWriter stringWriter = new StringWriter();
			PrintWriter writer = new PrintWriter(stringWriter);
			when(response.getWriter()).thenReturn(writer);
			when(request.getParameter("userId")).thenReturn(userId);
			when(request.getParameter("oldPassword")).thenReturn(password);
			when(request.getParameter("newPassword")).thenReturn(newPass);
			
			/*UpdateUserPassword servlet = new UpdateUserPassword();
			servlet.doPost(request, response);
			writer.flush();*/
			
			// new test
			UpdateUserPasswordRequestHandler handler = new UpdateUserPasswordRequestHandler();
			handler.handlePostRequest(request, response);
			writer.flush();
			// new test

			assertEquals(ReturnCodes.UPDATE_USER_PASSWORD_SUCCESS, Integer.parseInt(stringWriter.toString().trim()));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@After
	public void userCleanup() {
		Database.createInstance(null, null).deleteUser(userId, password);
		Database.createInstance(null, null).deleteUser(userId, newPass);
	}
}
