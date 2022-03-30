import javax.servlet.ServletException;
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
import Util.Constant;
/**
 * Servlet implementation class RegisterDispatcher
 */
@WebServlet("/RegisterDispatch")
public class RegisterDispatch extends HttpServlet {
	@Serial
    private static final long serialVersionUID = 1L;
    private static final String db = "jdbc:mysql://localhost:3306/pa2";
    
	String user = Constant.DBUserName;
	String pwd = Constant.DBPassword;
    /**
     * Default constructor.
     */
    public RegisterDispatch() {
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
    	String email = request.getParameter("email-register");
    	String name = request.getParameter("name-register");
    	String password = request.getParameter("password-register");
    	String confirm = request.getParameter("confirm-password-register");
    	Boolean error = false;
    	String check = request.getParameter("terms-checkbox");
    	Boolean checkFlag = false;
    	
    	if (request.getParameter("google")!=null) {
    		System.out.println("here!");
    		String googleSql = "SELECT * from USERS WHERE email = ?";
    		try (Connection conn = DriverManager.getConnection(db, user, pwd);
        			PreparedStatement emailCheck1 = conn.prepareStatement(googleSql);) {
    				emailCheck1.setString(1, email);
    				final ResultSet resultSet = emailCheck1.executeQuery();
    	    		if(resultSet.next()) {
    	    			//email already there
//    	    			error = true;
//    	    			request.setAttribute("dupemail",true);
    	    			String url = "LoginDispatch?email-login=";
    	    			url+=email;
    	    			url+="&password-login=";
    	    			System.out.println("password "+ resultSet.getString("password"));
    	    			url+=resultSet.getString("password");
    	    			response.sendRedirect(url);
	   			    	//request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
    	    		}
    	    		
    	    		else {
    	    			if (email == null || name == null || password == null || confirm == null
    	    		    		|| email.isEmpty() || name.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
    	    		    		error = true;
    	    		    		request.setAttribute("blank",true);
    	    		    	}
    	    		    	
    	    		    	if (check != null && check.compareToIgnoreCase("on")==0) {
    	    		    		checkFlag = true;
    	    		    	}
    	    		    	//validation
    	    		    	if (password.compareTo(confirm)!=0) {
    	    		    		//password no match
    	    		    		error = true;
    	    		    		request.setAttribute("passmatch",true);
    	    				    //request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
    	    		    	}
    	    		    	if (email != null) {
    	    		    		
    	    		    		//email validation with regex
    	    		    		String regex = "^(.+)@(.+)";
    	    					if (!email.matches(regex)) {
    	    						error = true;
    	    						request.setAttribute("inval",true);
    	    					}
    	    		    		
    	    					else {
    	    			    		String emailSQL = "SELECT * from USERS WHERE email = ?";
    	    			    		try (Connection connG1 = DriverManager.getConnection(db, user, pwd);
    	    			        			PreparedStatement emailCheck = conn.prepareStatement(emailSQL);) {
    	    			    				emailCheck.setString(1, email);
    	    			    				final ResultSet resultSetG1 = emailCheck.executeQuery();
    	    			    	    		if(resultSet.next()) {
    	    			    	    			//email already there
    	    			    	    			error = true;
    	    			    	    			request.setAttribute("dupemail",true);
    	    				   			    	//request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
    	    			    	    		}
    	    			    	    		} catch (SQLException e) {
    	    									// TODO Auto-generated catch block
    	    									e.printStackTrace();
    	    								}
    	    					}
    	    		    	}
    	    		    	if (name != null) {
    	    		    		//duplicate name
    	    		    		
    	    		    		String userSQL = "SELECT * from USERS WHERE user_name = ?";
    	    		    		try (Connection googleConn1 = DriverManager.getConnection(db, user, pwd);
    	    		        			PreparedStatement userCheck = googleConn1.prepareStatement(userSQL);) {
    	    		    				userCheck.setString(1, name);
    	    		    				final ResultSet resultSetG = userCheck.executeQuery();
    	    		    	    		if(resultSetG.next()) {
    	    		    	    			//name already there
    	    		    	    			error = true;
    	    		    	    			request.setAttribute("dupname",true);
    	    			   			    	//request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
    	    		    	    		}
    	    		    	    		} catch (SQLException e) {
    	    								// TODO Auto-generated catch block
    	    								e.printStackTrace();
    	    							}
    	    		    	}
    	    		    	if (!checkFlag || checkFlag == null) {
    	    		    		error = true;
    	    		    		request.setAttribute("terms",false);
    	    		    	}
    	    		    	//dispatch after all errors collected
    	    		    	if (error) {
    	    		    		request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
    	    		    	}
    	    		    	
    	    		    	String sql = "INSERT INTO USERS (user_name, email, password) VALUES (?, ?, ?)";
    	    		    	if (!error) {
    	    			    	try (Connection googleConn = DriverManager.getConnection(db, user, pwd);
    	    			    			PreparedStatement ps = googleConn.prepareStatement(sql);) {
    	    				    			ps.setString(1, name);
    	    				    			ps.setString(2, email);
    	    				    			ps.setString(3, password);
    	    				    			int row = ps.executeUpdate();
    	    			    			} catch (SQLException ex) {
    	    			    				System.out.println ("SQLException: " + ex.getMessage());
    	    			    			}
    	    			    	//stolen from login
    	    			    	System.out.println("logged in");
    	    			    	String formattedName=name.replace(' ','+');
    	    			    	Cookie ck=new Cookie("username",formattedName);
    	    			    	ck.setMaxAge(60 * 60);
    	    			    	response.addCookie(ck);
    	    			    	request.setAttribute("sign", "yes");
    	    			    	//request.getRequestDispatcher("WebContent/index.jsp").forward(request, response);
    	    			    	response.sendRedirect("WebContent/index.jsp");
    	    		    	}
    	    		
    	    		}
    	    		
    	    		
    	    		} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    		
    		
    		
    	}
    	else {
    		if (email == null || name == null || password == null || confirm == null
    	    		|| email.isEmpty() || name.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
    	    		error = true;
    	    		request.setAttribute("blank",true);
    	    	}
    	    	
    	    	if (check != null && check.compareToIgnoreCase("on")==0) {
    	    		checkFlag = true;
    	    	}
    	    	//validation
    	    	if (password.compareTo(confirm)!=0) {
    	    		//password no match
    	    		error = true;
    	    		request.setAttribute("passmatch",true);
    			    //request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
    	    	}
    	    	if (email != null) {
    	    		
    	    		//email validation with regex
    	    		String regex = "^(.+)@(.+)";
    				if (!email.matches(regex)) {
    					error = true;
    					request.setAttribute("inval",true);
    				}
    	    		
    				else {
    		    		String emailSQL = "SELECT * from USERS WHERE email = ?";
    		    		try (Connection conn = DriverManager.getConnection(db, user, pwd);
    		        			PreparedStatement emailCheck = conn.prepareStatement(emailSQL);) {
    		    				emailCheck.setString(1, email);
    		    				final ResultSet resultSet = emailCheck.executeQuery();
    		    	    		if(resultSet.next()) {
    		    	    			//email already there
    		    	    			error = true;
    		    	    			request.setAttribute("dupemail",true);
    			   			    	//request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
    		    	    		}
    		    	    		} catch (SQLException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}
    				}
    	    	}
    	    	if (name != null) {
    	    		//duplicate name
    	    		
    	    		String userSQL = "SELECT * from USERS WHERE user_name = ?";
    	    		try (Connection conn = DriverManager.getConnection(db, user, pwd);
    	        			PreparedStatement userCheck = conn.prepareStatement(userSQL);) {
    	    				userCheck.setString(1, name);
    	    				final ResultSet resultSet = userCheck.executeQuery();
    	    	    		if(resultSet.next()) {
    	    	    			//name already there
    	    	    			error = true;
    	    	    			request.setAttribute("dupname",true);
    		   			    	//request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
    	    	    		}
    	    	    		} catch (SQLException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
    	    	}
    	    	if (!checkFlag || checkFlag == null) {
    	    		error = true;
    	    		request.setAttribute("terms",false);
    	    	}
    	    	//dispatch after all errors collected
    	    	if (error) {
    	    		request.getRequestDispatcher("WebContent/auth.jsp").forward(request, response);
    	    	}
    	    	
    	    	String sql = "INSERT INTO USERS (user_name, email, password) VALUES (?, ?, ?)";
    	    	if (!error) {
    		    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
    		    			PreparedStatement ps = conn.prepareStatement(sql);) {
    			    			ps.setString(1, name);
    			    			ps.setString(2, email);
    			    			ps.setString(3, password);
    			    			int row = ps.executeUpdate();
    		    			} catch (SQLException ex) {
    		    				System.out.println ("SQLException: " + ex.getMessage());
    		    			}
    		    	//stolen from login
    		    	System.out.println("logged in");
    		    	String formattedName=name.replace(' ','+');
    		    	Cookie ck=new Cookie("username",formattedName);
    		    	ck.setMaxAge(60 * 60);
    		    	response.addCookie(ck);
    		    	request.setAttribute("sign", "yes");
    		    	//request.getRequestDispatcher("WebContent/index.jsp").forward(request, response);
    		    	response.sendRedirect("WebContent/index.jsp");
    	    	}
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
