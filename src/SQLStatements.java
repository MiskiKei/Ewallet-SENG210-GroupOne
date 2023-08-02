import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLStatements {
	private static String tableName;
	private static String dbURLembedded;
	private static int typeID;
	private static Connection conn = null; //adding a comment
    private static Statement stmt = null;
    
    
    
    
    public void createDatabase() {
    	dbURLembedded = "jdbc:derby:data/myDB;create=true";
        try {
            Connection connection = DriverManager.getConnection(dbURLembedded);

            createConnection();
            stmt = conn.createStatement();
            stmt.execute("CREATE TABLE currency (typeid INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, currency_name VARCHAR(50), currency_rate DECIMAL(10,2))"); 
            stmt.execute("CREATE TABLE users ( id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), username VARCHAR(50) NOT NULL, password VARCHAR(100) NOT NULL, PRIMARY KEY (id), UNIQUE (username) )");
            stmt.execute("CREATE TABLE Income_type (typeid INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, type VARCHAR(50)) ");
            stmt.execute("CREATE TABLE income (Income_id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, amount DECIMAL(10, 2), typeid INT, month VARCHAR(50), userid VARCHAR(255), FOREIGN KEY (typeid) REFERENCES income_type(typeid))"); 
            stmt.execute("CREATE TABLE expense_type (typeid INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, type VARCHAR(50))");
            stmt.execute("CREATE TABLE expense (Expense_id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, amount DECIMAL(10, 2), typeid INT, userid VARCHAR(255), FOREIGN KEY (typeid) REFERENCES expense_type(typeid))");
            stmt.execute("INSERT INTO expense_type (type) VALUES ('Car Expenses'), ('Recreational'), ('Groceries'), ('Bills')");
            stmt.execute("INSERT INTO Income_type (type) VALUES ('Salary'), ('Bonus'), ('Investments'), ('Other')");
            
            System.out.println("New database created");
            //create other tables here.

        } catch (SQLException e) {
            e.printStackTrace();
            createConnection();
            System.out.println("Database is already created");
        }
    }

	
	public static void insertExpense(double amount, String type) { //pass variables for insert statement
	        try
	        {
	        	tableName = "Expense";
	        	if (type.equals("Car Expenses")) {
	        		typeID = 1;
	        	} else if (type.equals("Recreational")) {
	        		typeID = 2;
	        	} else if (type.equals("Groceries")) {
	        		typeID = 3;
	        	} else if (type.equals("Bills")) {
	        		typeID = 4;
	        	}
	            stmt = conn.createStatement();
	            stmt.execute("INSERT INTO expense (amount, typeid, userid) VALUES (" + amount + ",  " + typeID + ", 'USER')");
	            stmt.close();
	            System.out.println("added");
	        }
	        catch (SQLException sqlExcept)
	        {
	            sqlExcept.printStackTrace();
	            System.out.println("Failed to Add");
	        }
	    }
	public static List expenseSize() {
	    try {
	        stmt = conn.createStatement();
	        tableName = "Expense";
	        ResultSet results = stmt.executeQuery("Select Expense_id FROM " + tableName);
	        List<String> columnValues = new ArrayList<>();
	        while (results.next()) {
                String value = results.getString("Expense_id");
                columnValues.add(value);
            }
	        for (String value : columnValues) {
                System.out.println(value);
            }

	        results.close();
	        stmt.close();
		    return columnValues;
	    } catch (SQLException sqlExcept) {
	        sqlExcept.printStackTrace();
	    }
		return null;

	}
	
	public static List expenseSizebyType(String val) {
	    try {
	    	if (val.equals("Car Expenses")) {
        		typeID = 1;
        	} else if (val.equals("Recreational")) {
        		typeID = 2;
        	} else if (val.equals("Groceries")) {
        		typeID = 3;
        	} else if (val.equals("Bills")) {
        		typeID = 4;
        	}
	        stmt = conn.createStatement();
	        tableName = "Expense";
	        ResultSet results = stmt.executeQuery("Select Expense_id FROM " + tableName + " where typeid = " + typeID);
	        List<String> columnValues = new ArrayList<>();
	        while (results.next()) {
                String value = results.getString("Expense_id");
                columnValues.add(value);
            }
	        for (String value : columnValues) {
                System.out.println(value);
            }

	        results.close();
	        stmt.close();
		    return columnValues;
	    } catch (SQLException sqlExcept) {
	        sqlExcept.printStackTrace();
	    }
		return null;

	}
	public static Object[] selectExpense(String type) {
	    try {
	    	if (type.equals("Car Expenses")) {
        		typeID = 1;
        	} else if (type.equals("Recreational")) {
        		typeID = 2;
        	} else if (type.equals("Groceries")) {
        		typeID = 3;
        	} else if (type.equals("Bills")) {
        		typeID = 4;
        	}
	        stmt = conn.createStatement();
	        tableName = "Expense";
	        String query = "SELECT et.type, ex.amount, ex.userid FROM expense ex left join expense_type et on ex.typeid = et.typeid where ex.expense_id = " + type;
	        System.out.println(query);
	        ResultSet results = stmt.executeQuery(query);
	        Object[] returnQuery = new Object[3]; //return the results
	        // Check if the result set contains data
	        while (results.next()) {
	        // Process the data in the result set
	        String typeName = results.getString(1);
	        String amountName = results.getString(2);
	        String userName = results.getString(3);
	        returnQuery[0] = typeName;
	        returnQuery[1] = amountName;
	        returnQuery[2] = userName;
	        System.out.println(typeName + amountName + userName);
	        return returnQuery;
	        }
	        results.close();
	        stmt.close();
	    } catch (SQLException sqlExcept) {
	        sqlExcept.printStackTrace();
	    }
        return null;
	}

	    
	    
	public static Object[] selectExpenseByType(String type) {
	    try {
	    	if (type.equals("Car Expenses")) {
        		typeID = 1;
        	} else if (type.equals("Recreational")) {
        		typeID = 2;
        	} else if (type.equals("Groceries")) {
        		typeID = 3;
        	} else if (type.equals("Bills")) {
        		typeID = 4;
        	}
	        stmt = conn.createStatement();
	        tableName = "Expense";
	        String query = "SELECT et.type, ex.amount, ex.userid FROM expense ex left join expense_type et on ex.typeid = et.typeid where ex.expense_id = " + type;
	        System.out.println(query);
	        ResultSet results = stmt.executeQuery(query);
	        Object[] returnQuery = new Object[3]; //return the results
	        // Check if the result set contains data
	        while (results.next()) {
	        // Process the data in the result set
	        String typeName = results.getString(1);
	        String amountName = results.getString(2);
	        String userName = results.getString(3);
	        returnQuery[0] = typeName;
	        returnQuery[1] = amountName;
	        returnQuery[2] = userName;
	        System.out.println(typeName + amountName + userName);
	        return returnQuery;
	        }
	        results.close();
	        stmt.close();
	    } catch (SQLException sqlExcept) {
	        sqlExcept.printStackTrace();
	    }
        return null;
	}

	
	public static void insertIncome(double amount, String type, String month) {
	    try {
	       tableName = "Income";

	        if (type.equals("Salary")) {
	        	typeID = 1;
	        } else if (type.equals("Bonus")) {
	            typeID = 2;
	        } else if (type.equals("Investments")) {
	            typeID = 3;
	        } else if (type.equals("Other")) {
	            typeID = 4;
	        }

	        stmt = conn.createStatement();
	        stmt.execute("INSERT INTO " + tableName + " (amount, typeid, month, userid) VALUES (" + amount + ", " + typeID + ", '" + month + "', 'USER')");
	        stmt.close();
	        System.out.println("Income added successfully.");
	    } catch (SQLException sqlExcept) {
	        sqlExcept.printStackTrace();
	        System.out.println("Failed to add income.");
	    }
	}
	
	
	public static List<Object[]> selectAllIncome() {
	    List<Object[]> incomeData = new ArrayList<>();
	    try {
	        stmt = conn.createStatement();
	        String tableName = "Income";
	        String query = "SELECT it.type, inc.amount, inc.month, inc.userid FROM income inc LEFT JOIN income_type it ON inc.typeid = it.typeid"; // Removed "AS in" alias
	        System.out.println(query);
	        ResultSet results = stmt.executeQuery(query);
	        while (results.next()) {
	            String typeName = results.getString(1);
	            String amountName = results.getString(2);
	            String monthName = results.getString(3);
	            String userName = results.getString(4);
	            Object[] rowResults = new Object[] { typeName, amountName, monthName, userName };
	            incomeData.add(rowResults);
	        }
	        results.close();
	        stmt.close();
	    } catch (SQLException sqlExcept) {
	        sqlExcept.printStackTrace();
	    }
	    return incomeData;
	}


	
	public static List<Object[]> selectIncomeByType(String type) {
	    List<Object[]> incomeData = new ArrayList<>();
	    try {
	        stmt = conn.createStatement();
	        String tableName = "Income";
	        String query = "SELECT it.type, inc.amount, inc.month, inc.userid FROM income inc LEFT JOIN income_type it ON inc.typeid = it.typeid WHERE it.type = '" + type + "'";
	        System.out.println(query);
	        ResultSet results = stmt.executeQuery(query);
	        while (results.next()) {
	            String typeName = results.getString(1);
	            String amountName = results.getString(2);
	            String monthName = results.getString(3);
	            String userName = results.getString(4);
	            Object[] rowResults = new Object[] { typeName, amountName, monthName, userName };
	            incomeData.add(rowResults); // Add the rowResults array to the incomeData list
	        }
	        results.close();
	        stmt.close();
	    } catch (SQLException sqlExcept) {
	        sqlExcept.printStackTrace();
	    }
	    return incomeData;
	}





	    private static void createConnection()
	    {
	        try
	        {
	        	conn = DriverManager.getConnection(dbURLembedded); 
	            //Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();

	        }
	        catch (Exception except)
	        {
	            except.printStackTrace();
	        }
	        System.out.println("Found connection");
	    }


	}