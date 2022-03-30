import javax.servlet.ServletException;
import Util.Constant;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class RegisterDispatcher
 */
@WebServlet("/LoginDispatch")
public class LoginDispatcher extends HttpServlet {
	@Serial
    private static final long serialVersionUID = 1L;
    private static final String db = "jdbc:mysql://localhost:3306/pa2";
    
	String user = Constant.DBUserName;
	String pwd = Constant.DBPassword;
    /**
     * Default constructor.
     */
    public LoginDispatcher() {
    	//test
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO
    	
    	//had to add this to make jdbc connect
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String email = request.getParameter("email-login");
    	String password = request.getParameter("password-login");
    	String newUser = "INSERT INTO USERS (user_name, email, password) VALUES (?, ?, ?)";
		String emailCheck = "SELECT * from USERS WHERE email = ?";
		
		System.out.println(email);
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
			PreparedStatement ps = conn.prepareStatement(emailCheck);) {
	    		ps.setString(1, email);
	    		//ps.setString(2, password);
	    		final ResultSet resultSet = ps.executeQuery();
	    		if(resultSet.next()) {
	    		    //email already in database
	    		    if(resultSet.getString("password").compareTo(password)==0) {
	    		    	System.out.println("logged in");
	    		    	String formattedName=resultSet.getString("user_name").replace(' ','+');
	    		    	Cookie ck=new Cookie("username",formattedName);
	    		    	ck.setMaxAge(60 * 60);
	    		    	response.addCookie(ck);
	    		    	request.setAttribute("sign", "yes");
	    		    	//request.getRequestDispatcher("WebContent/index.jsp").forward(request, response);
	    		    	response.sendRedirect("WebContent/index.jsp");
	   			    }
	   			    else if(resultSet.getString("password").compareTo(password)!=0) {
	   			    	System.out.println("wrong password");
	   			    	request.setAttribute("wrongpass",true);
	   			    	request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
	   			    }
    			}
	    		else {
	    			//not duplicate, make new
	    			
	   				System.out.println("Account with email does not exist");
	   				request.setAttribute("wrongemail",true);
	   			    request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
	    		}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	
			}
    }
    

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
