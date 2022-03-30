import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import com.google.gson.*;
import Util.Business;
import Util.RestaurantDataParser;
import Util.Constant;
/**
 * Servlet implementation class SearchDispatcher
 */
@WebServlet("/Search")
public class SearchDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public SearchDispatcher() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        // TODO get json file as stream, Initialize FakeYelpAPI by calling its initalize
        RestaurantDataParser.Init("test");
        // method
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO
    	String query = request.getParameter("input");
    	String select = request.getParameter("picker");
    	String sorting = request.getParameter("sort");
    	Boolean error = false;
    	System.out.println("sorting"+request.getParameter("sort"));
    	
    	//validation
    	if (sorting == null || query == null || query.isEmpty()) {
	    	if (sorting == null) {
	    		request.setAttribute("missingSort", "yes");
	    		System.out.println("sort miss");
	    		error = true;
	    		//request.getRequestDispatcher("WebContent/search.jsp").forward(request,response);
	    	}
	    	if (query == null || query.isEmpty()) {
	    		request.setAttribute("missingSearch", "yes");
	    		System.out.println("search miss");
	    		error = true;
	    		//request.getRequestDispatcher("WebContent/search.jsp").forward(request,response);
	    	}
    	}
    	if (error) {
    		request.setAttribute("error", "yes");
    		request.getRequestDispatcher("WebContent/search.jsp").forward(request,response);
    	}
    	
    	//nothing wrong with query
    	else {
    		request.setAttribute("error","no");
	    	System.out.println(query+" "+select+" "+sorting);
	    	//RestaurantDataParser parse = new RestaurantDataParser();
	    	//ArrayList<Business> list=RestaurantDataParser.getBusinesses(query,sorting,select);
	    	ArrayList<Business> list2=RestaurantDataParser.getBusinesses(query, sorting, select);
	    	for (int i = 0; i<list2.size();i++) {
	    		System.out.println(list2.get(i).getName());
	    		System.out.println(list2.get(i).getAddy());
	    		System.out.println(list2.get(i).getRating());
	    	}
	    	//String[] test = {"a"};
	    	//RestaurantDataParser.main(test);
	    	request.setAttribute("data", list2);
	    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WebContent/search.jsp");
	    	dispatcher.forward(request, response);
	    	//request.getRequestDispatcher("WebContent/search.jsp").forward(request,response);
    	}
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}