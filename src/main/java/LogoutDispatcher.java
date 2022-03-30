import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import Util.Constant;
/**
 * Servlet implementation class LogoutDispatcher
 */
@WebServlet("/LogoutDispatch")
public class LogoutDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @throws ServletException 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	System.out.println("SIGNOUT");
        // TODO Remove userID cookie
    	Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                //cookie.setValue("");
                //cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                System.out.println("deleted"+cookie.getValue());
            }
        request.setAttribute("out","yes");
        //request.getRequestDispatcher("WebContent/index.jsp").forward(request, response);
        response.sendRedirect("/PA2/WebContent/index.jsp");
    }

    /**
     * @throws ServletException 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }

}
