package Util;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that pretends to be the Yelp API
 */
public class RestaurantDataParser{
	
    private static Boolean ready = false;

    /**
     * Initializes the DB with json data
     *
     * @param responseString the Yelp json string
     */
    public static void Init(String responseString) {
    	System.out.println("INTITITITITIIITIITITII");
    	ArrayList<Business> busList = new ArrayList<Business>();
        if (ready) {
            return;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //TODO check if you've done the initialization
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ready = true;
        Gson gson = new Gson();
			String temp = "";
			String filename = Constant.FileName;
			try (InputStream inputStream = RestaurantDataParser.class.getResourceAsStream(filename);)
			{
				if (inputStream == null) {
					System.out.println("file not found");
				}
				Scanner in = new Scanner(inputStream);
					while (in.hasNext()) {
						temp += in.nextLine();
					}
				in.close();
			} 
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File not found");
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("cannot open file");
			} 
			BusinessHelper helper = new BusinessHelper();
			helper = gson.fromJson(temp, BusinessHelper.class);
//     			ArrayList<Business> busList = new ArrayList<Business>();

			busList = helper.getBusinesses();
			for (int i=0; i< busList.size(); i++) {
				if (helper.getBusinesses().get(i) != null) {
					System.out.println(busList.get(i).getName());
					System.out.println(busList.get(i).getId());
					System.out.println(busList.get(i).getAlias());
					System.out.println(busList.get(i).getImage());
					System.out.println(busList.get(i).getIs_closed());
					System.out.println(busList.get(i).getUrl());
					System.out.println(busList.get(i).getReviewCount());
					System.out.println(busList.get(i).getCategories().get(0).getAlias()); 
				}
			}
     	
     		
        
        //TODO iterate the businessHelper array and insert every business into the DB
     		
     	    String db = "jdbc:mysql://localhost:3306/pa2";
     	    
     		String user = Constant.DBUserName;
     		String pwd = Constant.DBPassword;
     		try {
    			Class.forName("com.mysql.cj.jdbc.Driver");
    		} catch (ClassNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
     		
     		for (int i = 0; i<busList.size(); i++) {
     			System.out.println(busList.get(i).getImage());
     			String restDet = "INSERT INTO Restaurant_details (details_id, image_url, address, phone_no,estimated_price,yelp_url) VALUES (?, ?, ?, ?, ?, ?)";
		    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
		    			PreparedStatement ps = conn.prepareStatement(restDet);) {
		    				Integer id = i;
			    			ps.setString(1, id.toString());
			    			ps.setString(2, busList.get(i).getImage());
			    			ps.setString(3, busList.get(i).getLocation().getAddress());
			    			ps.setString(4, busList.get(i).getPhone());
			    			ps.setString(5, busList.get(i).getPrice());
			    			ps.setString(6, busList.get(i).getUrl());
			    			int row = ps.executeUpdate();
		    			} catch (SQLException ex) {
		    				System.out.println ("SQLException: " + ex.getMessage());
		    			}
		    	
		    	String ratingDet = "INSERT INTO Rating_details (rating_id, review_count, rating) VALUES (?, ?, ?)";
		    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
		    			PreparedStatement ps = conn.prepareStatement(ratingDet);) {
		    				Integer id = i;
		    				Integer count = busList.get(i).getReviewCount();
		    				Double ratingD = busList.get(i).getRating();
			    			ps.setString(1, id.toString());
			    			ps.setString(2, count.toString());
			    			ps.setString(3, ratingD.toString());
			    			int row = ps.executeUpdate();
		    			} catch (SQLException ex) {
		    				System.out.println ("SQLException: " + ex.getMessage());
		    			}
		    	
		    	String rest = "INSERT INTO Restaurant (restaurant_id, restaurant_name, details_id, rating_id) VALUES (?, ?, ?, ?)";
		    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
		    			PreparedStatement ps = conn.prepareStatement(rest);) {
		    				Integer id = i;
			    			ps.setString(1, busList.get(i).getId());
			    			ps.setString(2, busList.get(i).getName());
			    			ps.setString(3, id.toString());
			    			ps.setString(4, id.toString());
			    			int row = ps.executeUpdate();
		    			} catch (SQLException ex) {
		    				System.out.println ("SQLException: " + ex.getMessage());
		    			}
		    	
		    	String cat = "INSERT INTO Category (category_id, category_name, restaurant_id)VALUES (?, ?, ?)";
		    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
		    			PreparedStatement ps = conn.prepareStatement(cat);) {
		    				Integer id = i;
		    				ps.setString(1, id.toString());
		    				String catList = "";
		    				for (int j=0; j<busList.get(i).getCategories().size();j++) {
		    					catList+=busList.get(i).getCategories().get(j).getAlias();
		    					if (j<busList.get(i).getCategories().size()-1) {
		    						catList+=", ";
		    					}
		    				}
			    			ps.setString(2, catList);
			    			ps.setString(3, busList.get(i).getId());
			    			int row = ps.executeUpdate();
		    			} catch (SQLException ex) {
		    				System.out.println ("SQLException: " + ex.getMessage());
		    			}
     		
     			}	
     		}
    

    public static Business getBusiness(String id) {
    	String db = "jdbc:mysql://localhost:3306/PA2";
 	    
 		String user = Constant.DBUserName;
 		String pwd = Constant.DBPassword;
    	try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //TODO return business based on id
        String search = "SELECT * FROM Restaurant AS r "
        			+ "JOIN Restaurant_details AS d on r.details_id = d.details_id "
        			+ "JOIN Category as c on c.restaurant_id = r.restaurant_id "
        			+ "JOIN Rating_details as rd on r.rating_id = rd.rating_id "
        			+ "WHERE r.restaurant_id = '"+id+"'";
        String RId;
        String name;
        String image_url;
		String url;
		String categories;
		double rating;
		String price; 
		String display_phone;
		String address;
		
		//try to find business with specific id, with all neccesary data to make a smaller business object
        try (Connection conn = DriverManager.getConnection(db, user, pwd);
    			PreparedStatement ps = conn.prepareStatement(search);) {

	    			final ResultSet resultSet = ps.executeQuery();
	    			if(resultSet.next()) {
    	    			//only certain fields are neccesary to create small business object
	    				RId = resultSet.getString("r.restaurant_id");
	    				name = resultSet.getString("restaurant_name"); 
	    				address = resultSet.getString("address");
	    		        image_url = resultSet.getString("image_url");
	    		        rating = Double.parseDouble(resultSet.getString("rating"));
	    		        url = resultSet.getString("yelp_url");
	    		        price = resultSet.getString("estimated_price");
	    		        display_phone = resultSet.getString("phone_no");
	    		        categories = resultSet.getString("category_name");
	    		        int revCount = Integer.parseInt(resultSet.getString("review_count"));
	    		        Business result = new Business(RId, name, image_url,
	    		        		 url, categories,
	    		        		 rating,  price,  display_phone, address, revCount);
	    		        return result;
    	    		}
    			} catch (SQLException ex) {
    				System.out.println ("SQLException: " + ex.getMessage());
    			}
        return null;
    }

    //i dont think this could ever effectively be called
    public static Business getBusiness(String id, ArrayList<Business> list) {
    	for (int i=0; i<list.size();i++) {
    		if (id.compareToIgnoreCase(list.get(i).getId())==0) {
    			return list.get(i);
    		}
    	}
    	return null;
 
    }
    /**
     * @param keyWord    the search keyword
     * @param sort       the sort option (price, review count, rating)
     * @param searchType search in category or name
     * @return the list of business matching the criteria
     */
    public static ArrayList<Business> getBusinesses(String keyWord, String sort, String searchType) {
    	String db = "jdbc:mysql://localhost:3306/pa2";
 	    
 		String user = Constant.DBUserName;
 		String pwd = Constant.DBPassword;
 		System.out.println("getBusi");
        ArrayList<Business> businesses = new ArrayList<Business>();
        System.out.println("list");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //TODO get list of business based on the param
        System.out.println("donetry");
        String search = "";
        
        //searching by restaurant name
        if (searchType.compareToIgnoreCase("Restaurant")==0){
        	searchType = "restaurant_name";
        	
        	//sort by price
        	if (sort.compareToIgnoreCase("price")==0) {
        		search = "SELECT * FROM Restaurant AS r "
        				+ "JOIN Restaurant_details AS d on r.details_id = d.details_id "
        				+ "WHERE restaurant_name LIKE '%"+keyWord+"%' "
        				+ "ORDER BY d.estimated_price ASC";
        	}
        	//sort by review count
        	if (sort.compareToIgnoreCase("review count")==0) {
        		search = "SELECT * FROM Restaurant AS r "
        				+ "JOIN Rating_details AS d on r.rating_id = d.rating_id "
        				+ "WHERE restaurant_name LIKE '%"+keyWord+"%' "
        				+ "ORDER BY d.review_count DESC";
        	}
        	//sort by rating
        	if (sort.compareToIgnoreCase("rating")==0) {
        		search = "SELECT * FROM Restaurant AS r "
        				+ "JOIN Rating_details AS d on r.rating_id = d.rating_id "
        				+ "WHERE restaurant_name LIKE '%"+keyWord+"%' "
        				+ "ORDER BY d.rating DESC, d.review_count DESC";
        				//sort rating high to low, ties review count high to low
        	}
        	
        }
        
        //searching for the category name
        else {
        	searchType = "category_name";
        	//sort by price
        	if (sort.compareToIgnoreCase("price")==0) {
        		search = "SELECT * FROM Category AS c "
        				+ "JOIN Restaurant AS r on c.restaurant_id = r.restaurant_id "
                        + "JOIN Restaurant_details AS d on r.details_id = d.details_id " 
        				+ "WHERE category_name LIKE '%"+keyWord+"%' "
        				+ "ORDER BY d.estimated_price ASC";
        	}
        	//sort by review count
        	if (sort.compareToIgnoreCase("review count")==0) {
        		System.out.println("here");
        		search = "SELECT * FROM Category AS c "
        				+ "JOIN Restaurant AS r on c.restaurant_id = r.restaurant_id "
                        + "JOIN Rating_details AS d on r.rating_id = d.rating_id " 
        				+ "WHERE category_name LIKE '%"+keyWord+"%' "
        				+ "ORDER BY d.review_count DESC";
        	}
        	//sort by rating
        	if (sort.compareToIgnoreCase("rating")==0) {
        		search = "SELECT * FROM Category AS c "
        				+ "JOIN Restaurant AS r on c.restaurant_id = r.restaurant_id "
                        + "JOIN Rating_details AS d on r.rating_id = d.rating_id " 
        				+ "WHERE category_name LIKE '%"+keyWord+"%' "
        				+ "ORDER BY d.rating DESC, d.review_count DESC";
        	}
        }
        
        //add the found businesses to the final list, creating front-end friendly business objects found by id
    	try (Connection conn = DriverManager.getConnection(db, user, pwd);
    			PreparedStatement ps = conn.prepareStatement(search);) {
    		System.out.println(search);
	    			final ResultSet resultSet = ps.executeQuery();
	    			while(resultSet.next()) {
	    				businesses.add(getBusiness(resultSet.getString("restaurant_id")));
	   			    	
    	    		}
    			} catch (SQLException ex) {
    				System.out.println ("SQLException: " + ex.getMessage());
    			}
        
        return businesses;

    }
    public static void main(String[] args) {
    	Init("test");
    	ArrayList<Business> testing = getBusinesses("mex","review count","category");
    	for (int i=0; i<testing.size();i++) {
    		System.out.println(testing.get(i).getName());
    	}
    }
}

//Code adapted from https://stackoverflow.com/questions/23070298/get-nested-json-object-with-gson-using-retrofit
class BusinessDeserializer implements JsonDeserializer<Business> {
    @Override
    public Business deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
    	System.out.println("deser");
    	JsonElement content = je.getAsJsonObject().get("businesses");
        return new Gson().fromJson(content, Business.class);
    }
}