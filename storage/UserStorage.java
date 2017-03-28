package storage;
import java.sql.*;

public class UserStorage
{
	private Connection connect() 
	{
		String url = "jdbc:sqlite:lib/1H.db";
		Connection conn = null;
		try
		{
			conn = DriverManager.getConnection(url);
	   }
		catch (SQLException e) 
		{
	        System.out.println(e.getMessage());
	   }
		return conn;
	}
	
	public void selectAll()
	{
		String sql = "SELECT userID, authorizationLevel, username, password, fullName, address, phoneNumber, "
        		   + "email, hasSavedCard, cardNumber, cardHolderName, cardCVC, cardExpiryDate FROM Users";
		try 
		(Connection conn = this.connect();
		Statement stmt = conn.createStatement(); 
		ResultSet rs = stmt.executeQuery(sql);)
		{
			// loop through the result set
	      while (rs.next())
	      {
/*	      	System.out.println(rs.getInt("userID") + "\t" +
	      							rs.getInt("authorizationLevel") + "\t" +
	                           rs.getString("username") + "\t" +
	                           rs.getString("password") + "\t" +
	                           rs.getString("fullName") + "\t" +
	                           rs.getString("address") + "\t" +                                   
	                           rs.getInt("phoneNumber") + "\t" +
	                           rs.getString("email") + "\t" +
		          				   rs.getBoolean("hasSavedCard") + "\t" +
		          				   rs.getInt("cardNumber") + "\t" +
		          				   rs.getString("cardHolderName") + "\t" +
		          				   rs.getInt("cardCVC") + "\t" +
		          				   rs.getString("cardExpiryDate")); //Ath, náum í með String, ekki getDate;
		          				   */
	      } //while (rs.next())
		} //try block
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
      }

	} //selectAll()
	
}
