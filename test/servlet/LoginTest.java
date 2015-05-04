package servlet;

import static org.junit.Assert.*;
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

import servlet.helpers.user.LoginRequestHandler;
import utility.ReturnCodes;
import database.Database;
import event.NewUserEvent;

public class LoginTest {

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
			when(request.getParameter("username")).thenReturn(userNameUnique);
			when(request.getParameter("password")).thenReturn(password);
			
			/*Login servlet = new Login();
			servlet.doPost(request, response);
			writer.flush();*/
			
			// new test
			LoginRequestHandler handler = new LoginRequestHandler();
			handler.handlePostRequest(request, response);
			writer.flush();
			// new test
			
			assertEquals("" + ReturnCodes.LOGIN_SUCCESS + "," + userId, stringWriter.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void userCleanup() {
		Database.createInstance(null, null).deleteUser(userId, password);
	}

}
