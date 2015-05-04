package servlet;

import static org.junit.Assert.assertEquals;
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

import servlet.helpers.user.NewUserRequestHandler;
import utility.ReturnCodes;
import database.Database;

public class NewUserTest {

	private String userNameUnique = "ce9a4664-ecd2-4782-a456-9b9e852e5c2e";
	private String password = "test";
	private String phoneNumber = "5555555555";
	
	@Before
	public void userSetup() {
		
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
			when(request.getParameter("number")).thenReturn(phoneNumber);
			when(request.getParameter("password")).thenReturn(password);
			
			/*NewUser servlet = new NewUser();
			servlet.doPost(request, response);
			writer.flush();*/
			
			// new test
			NewUserRequestHandler handler = new NewUserRequestHandler();
			handler.handlePostRequest(request, response);
			writer.flush();
			// new test
			
			assertEquals(ReturnCodes.NEW_USER_SUCCESS, Integer.parseInt(stringWriter.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@After
	public void userCleanup() {
		String id = Database.createInstance(null, null).getUserId(userNameUnique);
		Database.createInstance(null, null).deleteUser(id, password);
	}
}
