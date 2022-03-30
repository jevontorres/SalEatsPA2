<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Search</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
            href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"
            rel="stylesheet">
    <link rel="stylesheet" href="/PA2/WebContent/css/search.css">
    <script src="https://kit.fontawesome.com/3204349982.js"
            crossorigin="anonymous"></script>
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
    <%
        //TODO perform search

        //TODO check if logged in
    %>
    <style>
	    #error {
			margin: auto;
			padding-top: 20px;
			width: 95%;
			color: red;
			font-weight: bold;
			font-size: 20px;
		}
		#result{
			margin: auto;
			padding-top: 20px;
			width: 95%;
			color: gray;
			font-weight: bold;
			font-size: 20px;
		}
		#zero{
			padding-left: 35px;
		}
    </style>
</head>
<body>
<!-- TODO -->
	<div id="navbar">
    		
    		<div id="logo">
    			<a href="/PA2/WebContent/index.jsp#">SalEats!</a>
    			 <%

    			 	if (registered){
    			 		String usernameFormatted = ck[userId].getValue().replace('+',' ');
	        			out.println("Hello, "+usernameFormatted+"!");
    			 	} 
    			 
    			 %>
    			 
    		</div>
    	
    		<div id="buttons">
    			<a href="/PA2/WebContent/index.jsp#">Home</a>
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
				<option <%if (request.getParameter("picker").compareTo("Category")==0){
					out.println("selected=\"selected\"");
				}
				%>> Category</option>
				<option <%if (request.getParameter("picker").compareTo("Restaurant")==0){
					out.println("selected=\"selected\"");
				}
				%>> Restaurant</option>
			</select>
			<input type="text" id="searchBox" name ="input" value=<%=request.getParameter("input")%>>
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
	<%
	if (request.getAttribute("missingSort")!=null && request.getAttribute("missingSearch")!=null){
		out.println("<div id=\"error\">Missing a sort and search parameter please try again.</div>");
	}
	else if (request.getAttribute("missingSort")!=null){
		out.println("<div id=\"error\">Missing a sort parameter please try again and select a sorter.</div>");
	}
	else if (request.getAttribute("missingSearch")!=null){
		out.println("<div id=\"error\">Missing a search parameter please try again with a query.</div>");
	}
	%>
	<%
	String errorMess = (String) request.getAttribute("error");
	if ( errorMess.compareTo("no")==0 ){
		out.println("<div id =\"result\">Results for "+request.getParameter("input")+" in "+request.getParameter("picker")+"</div>");
	}
	%>
	
	<%-- <c:forEach var="restaurant" items="${data}">
	
    </c:forEach> --%>
    
    <%@page import="java.util.ArrayList"
    import ="Util.Business"
    %>
    <%
    if (errorMess.compareTo("no")==0){
	    ArrayList<Business> list = (ArrayList<Business>)request.getAttribute("data");
	    if (list.size() == 0){
	    	out.print("<p id=\"zero\"> 0 Results found<p>");
	    }
	    else{
	    	for (Business b:list){
	    		%>
	    		<br>
	    		<div class = "box">
	    			<div class="img-holder">
	    				<img src = <%=b.getImage()%>>
	    			</div>
	    			<div id="info">
	    			<div id= "restName">
	    			<p style="font-size: 20px">
	<%--     				http://localhost:8080/PA2/Search?picker=Category&input=urg&search=&sort=Review+Count
	    				link =details.jsp+busid+"&picker="+request.getAttribute("picker")+"&input="+request.getAttribute("input")+"&search="++"&sort="+request.getAttribute("sort")
	    				<%
	    				String pick = ""
	    				String input =""
	    				String sort = ""
	    				%> --%>
	    				<a href ="WebContent/details.jsp?busId=<%=b.getId()%>"><%=b.getName()%></a>
	    			</p>
	    			</div>
	    			<p>
	    				Price: <%=b.getPrice()%>
	    			</p>
					<p>
	    				Review Count: <%=b.getReviewCount()%>
	    			</p>
	    			<p>
	  					<%
	    				for (int i=0; i<(int)b.getRating();i++){
	    					if (i==0){out.println("Rating: ");}
	    					out.println("<span class = 'fa fa-star'> </span>");
	    				}
	    				%>
	    			</p>
	    			<p>
	    				
	    				<a href =<%=b.getUrl()%> target=_blank>Yelp Link</a>
	    			</p>
	
	    			
	    			</div>
	    		</div>
	    		<%
	    	}
	    }
    }
	%>
	
</body>
</html>