package Util;
import java.util.*;


/**
 * The class used to model a business
 */
public class Business {
	public String id;
	public String alias;
	public String name;
	public String image_url;
	public Boolean is_closed;
	public String url;
	public int review_count;
	public ArrayList<Category> categories; 
	public String categoryL; 
	public double rating;
	//public double coordinates[];
	public String transactions[];
	public String price;
	public Location location;
	public String phone;
	public String display_phone;
	public double distance;
	public String address;
	
	//this business object contains all that is necessary for front-end
	public Business(String id, String name, String image_url,
    		String url, String categoryL,
    		double rating, String price, String phone, String address, int revCount) {
		this.id = id;
    	this.name = name;
    	this.image_url = image_url;
    	this.url = url;
    	this.categoryL = categoryL;
    	this.rating = rating;
    	this.price = price;
    	this.phone = phone;
    	this.address = address;
    	this.review_count = revCount;
	
	}
	
	//make smaller business from bigger business
	public Business(Business bigger) {
		this.id = bigger.id;
    	this.name = bigger.name;
    	this.image_url = bigger.image_url;
    	this.url = bigger.url;
    	this.categoryL = bigger.categoryL;
    	this.rating = bigger.rating;
    	this.price = bigger.price;
    	this.phone = bigger.phone;
	}
	
	//this business object contains all that is necessary for json-parsing (everything)
    public Business(String id, String alias, String name, String image_url, Boolean is_closed, 
    		String url, int review_count, ArrayList<Category> categories,
    		double rating, String transactions[], String price,
    		Location location, String phone,
    		String display_phone, double distance) {
    	
        //TODO Initialization code
    	this.id = id;
    	this.alias = alias;
    	this.name = name;
    	this.image_url = image_url;
    	this.is_closed = is_closed;
    	this.url = url;
    	this.review_count = review_count;
    	this.categories = categories;
    	this.rating = rating;
    	//this.coordinates = coordinates;
    	this.transactions = transactions;
    	this.price = price;
    	this.location = location;
    	this.phone = phone;
    	this.display_phone = display_phone;
    	this.distance = distance;
    	
    }
   

    //TODO Add Getters (No Setters as the business does not change in our assignment once constructed)
    public String getId() {
		return id;
	}
    public String getAddy() {
    	return address;
    }
    public String getAlias() {
		return alias;
	}
    public String getName() {
		return name;
	}
    public String getImage() {
		return image_url;
	}
    public Boolean getIs_closed() {
		return is_closed;
	}
    public String getUrl() {
		return url;
	}
    public int getReviewCount() {
		return review_count;
	}
    public ArrayList<Category> getCategories() {
		return categories;
	}
    public String getCategoryL() {
		return categoryL;
	}
    
    public double getRating() {
		return rating;
	}

    public String[] getTransactions() {
		return transactions;
	}
    public String getPrice() {
		return price;
	}
    public Location getLocation() {
		return location;
	}
    public String getPhone() {
		return phone;
	}
    public String getDisplayPhone() {
		return display_phone;
	}
    public double getDistance() {
		return distance;
	}
    
    public class Location{
    	private ArrayList<String> display_address;
    	public String getAddress() {
    		String address = "";
    		for (int i=0; i<display_address.size(); i++) {
    			if(display_address.get(i) !=null) {
    				address += display_address.get(i);
    			}
    			if(i != display_address.size()-1) {
    				address += " ";
    			}
    			
    	}
    	return address;
    	}
    
    }
    
    
    class Category{
    	private String alias;
    	private String title;
    	
    	public String getAlias() {
    		return alias;
    	}
    	public void setAlias(String alias) {
    		this.alias = alias;
    	}
    	public String getTitle() {
    		return title;
    	}
    	public void setTitle(String title) {
    		this.title = title;
    	}
    }
   
    
}