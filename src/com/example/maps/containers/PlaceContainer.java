package com.example.maps.containers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.data.util.BeanItemContainer;

public class PlaceContainer extends BeanItemContainer<Place> 
  implements Serializable {
  private static Connection conn=null;
  public PlaceContainer() throws InstantiationException, 
    IllegalAccessException {
    
	super (Place.class);
  }
  public static PlaceContainer getSimilarPlaces(String placeName) { 
	  PlaceContainer placeContainer = null;
	  try{
		placeContainer = new PlaceContainer();  
	    if (conn== null) {
		  conn=getConn();
		  Statement select = conn.createStatement();
		  String selectStatement= "SELECT place_name from places where place_name LIKE " +
            "\"%"+ placeName + "%\";";
		   System.out.println(selectStatement);
		   ResultSet result = select.executeQuery(selectStatement);
			while (result.next()) {
		     Place similarPlace = new Place();
		     similarPlace.setPlaceName(result.getString(1));
		     placeContainer.addItem(similarPlace);
			}
	    }
	  } catch (SQLException e){
	      e.printStackTrace();
	    } catch (InstantiationException e) {
		    e.printStackTrace(); 
	       } catch (IllegalAccessException e) {
		       e.printStackTrace();    
	         }
	   return placeContainer;
  }
  
  private static Connection getConn() {
	Connection conn = null;
    String url = "jdbc:mysql://localhost:3306/";
    String db = "makany_dev";
    String driver = "com.mysql.jdbc.Driver";
    String user = "root";
    String pass = "";
    try {
      Class.forName(driver).newInstance();
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    try {       
         conn = DriverManager.getConnection(url+db, user, pass);
    } catch (SQLException e) {
        System.err.println("Mysql Connection Error: ");
	    e.printStackTrace();
	    }
	 return conn;
  }
}