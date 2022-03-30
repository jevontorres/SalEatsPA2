<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Details</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
            href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"
            rel="stylesheet">
    <script src="https://kit.fontawesome.com/3204349982.js"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="css/details.css">
    <% //TODO iterate the cookie and check if user registered
        Boolean registered = false;
        
        if ((String)request.getAttribute("out") != null){
        	registered = false;
        }
        int userId = 0;
        System.out.println(request);
        Cookie ck[]=request.getCookies();
	 	if (ck!=null && !ck[0].getValue().equals("") ){
		 	for(int i=0;i<ck.length;i++){
		 		System.out.println(ck[i].getValue());
		 		String newName = ((String) ck[i].getName()).replace('+',' ');
				if ( newName.compareTo("username")==0){
					registered = true;
					userId = i;
				}
			}
	 	} 

 	%>
</head>
<%
    //TODO perform search

    //TODO check if logged in
%>

	<body>
		<div id="navbar">
		    		
		    		<div id="logo">
		    			<a href="index.jsp">SalEats!</a>
		    			 <%
		
		    			 	if (registered){
		    			 		String usernameFormatted = ck[userId].getValue().replace('+',' ');
			        			out.println("Hello, "+usernameFormatted+"!");
		    			 	} 
		    			 
		    			 %>
		    			 
		    		</div>
		    	
		    	<div id="buttons">
		    			<a href="index.jsp">Home</a>
		    			<% if (!registered){
		    				out.println("<a href='/PA2/WebContent/auth.jsp'>Login / Register</a>");}
		    			%>
		    			<% if (registered){
		    				out.println("<a href='/PA2/LogoutDispatch'>Logout</a>");}
		    			%>
		    			
		    			
		    	</div>
		</div>
	    	
		    <div id="uInput">
					
				<form action="/PA2/Search" method = "get">
					<select id="picker" name="picker">
						<%System.out.println(request.getParameter("picker")); %>
						<option > Category</option>
						<option> Restaurant</option>
					</select>
					<input type="text" id="searchBox" name ="input">
					<button type="submit" name="search" id="search"><i class = 'fa fa-search' id="sButton"></i></button>
					<input type="radio" class="radio" name="sort" value="price" <%if (request.getParameter("sort") != null && request.getParameter("sort").compareTo("price")==0){
							out.println("checked=\"checked\"");
						}
						%>>
					Price
					<input type="radio" class="radio" name="sort" value="Rating" <%if (request.getParameter("sort") != null && request.getParameter("sort").compareTo("Rating")==0){
							out.println("checked=\"checked\"");
						}
						%>>
					Rating
					<input type="radio" class="radio" name="sort" value="Review Count" <%if (request.getParameter("sort") != null && request.getParameter("sort").compareTo("Review Count")==0){
							out.println("checked=\"checked\"");
						}
						%>>
					Review Count
				</form>			
			</div>
	
	<!-- TODO -->
	
	<br>
	<%@page import="java.util.*"
	    import ="Util.Business"
	    import ="Util.RestaurantDataParser"
	    %>
	<% Business b = RestaurantDataParser.getBusiness(request.getParameter("busId")); %>
	    		<div id = "restName">
		    			<p>
			<%--     				http://localhost:8080/PA2/Search?picker=Category&input=urg&search=&sort=Review+Count
		    				link =details.jsp+busid+"&picker="+request.getAttribute("picker")+"&input="+request.getAttribute("input")+"&search="++"&sort="+request.getAttribute("sort")
		    				<%
		    				String pick = ""
		    				String input =""
		    				String sort = ""
		    				%> --%>
		    				<%out.println(b.getName());%>
		    			</p>
	    			</div>
	    		<div class = "box">
	    			
	    			<div class="img-holder">
	    				<img src = <%=b.getImage()%>>
	    			</div>
					<div id = info>
	    			<p>
	    				Address: <%=b.getAddy()%>
	    			</p>
	    			<p>
	    				Phone No.: <%=b.getPhone()%>
	    			</p>
	    			<p>
	    				Price: <%=b.getPrice()%>
	    			</p>
					<p>
	    				Categories: <%
	    				String Cs = b.getCategoryL();
	    				ArrayList<String> cList = new ArrayList<>(Arrays.asList(Cs.split(",")));
	    				for (int i=0; i<cList.size();i++){
	    					out.println(cList.get(i));
	    					if (i<cList.size()-1){
	    						out.println(", ");
	    					}
	    				}
	    				%>
	    			</p>
	    			<p>
	  					<%
	    				for (int i=0; i<(int)b.getRating();i++){
	    					if (i==0){out.println("Rating: ");}
	    					out.println("<span class = 'fa fa-star'> </span>");
	    				}
	    				%>
	    			</p>
	
	    			
	    			</div>
	    		</div>
	
	</body>
</html>