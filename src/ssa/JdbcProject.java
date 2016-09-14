package ssa;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class JdbcProject {

//		static String url;
//		public static final String userName="";
//		public static final String pass="";
		static Connection myConn=null;
		static Statement stmt=null;
		static ResultSet rs=null;
		static int prereq;
		static String reqstring;
		static ArrayList<String> columnName = new ArrayList<String>();
		static ArrayList<String> columnType = new ArrayList<String>();
		static ArrayList<String> columnAuto = new ArrayList<String>();
		static ArrayList<String> columnPrecision = new ArrayList<String>();
		static ArrayList<String> columnScale = new ArrayList<String>();
		
		public static void main(String[] args) throws SQLException {
			
			
			try {
				Properties props = new Properties();
				props.load(new FileInputStream("jdbcProject.properties"));
		
				String theUser = props.getProperty("user");
				String thePassword = props.getProperty("password");
				String theDbUrl = props.getProperty("dburl");
				
				fetchData(theDbUrl, theUser, thePassword);
				insertData(theDbUrl, theUser, thePassword);
	//			fetchData(theDbUrl, theUser, thePassword);
				fetchGeorge(theDbUrl, theUser, thePassword);
				update(theDbUrl, theUser, thePassword);
				fetchGeorge(theDbUrl, theUser, thePassword);
				deleteRecord(theDbUrl, theUser, thePassword);
				fetchData(theDbUrl, theUser, thePassword);
				backup2(theDbUrl, theUser, thePassword);
	//			backup3(theDbUrl, theUser, thePassword);

			
			}
			catch(Exception exc){
					exc.printStackTrace();
			}
			finally {
				if(myConn!=null)
					myConn.close();
				if(stmt!=null)
					stmt.close();
			}
		}
		public static void close(Connection con, Statement stmt, ResultSet rs) throws SQLException {
			if(con!=null)
				con.close();
			if(stmt!=null)
				stmt.close();
			if(rs!=null)
				rs.close();}
		
		public static void fetchData(String url, String user, String pass) throws SQLException {
			try {
				myConn = DriverManager.getConnection(url, user, pass);
				stmt = myConn.createStatement();
				rs = stmt.executeQuery("select * from student;");
				while(rs.next()) {
					System.out.println("First Name " + rs.getString("first_name") + " Last Name: " + rs.getString("last_name") + " id " + rs.getInt("id") + " GPA: " + rs.getDouble("gpa"));
				}
			}catch(Exception exc){
				exc.printStackTrace();
			}finally {
				close(myConn, stmt, rs);}
		}
		
		public static void update(String url, String user, String pass) throws SQLException{
			try {
				myConn = DriverManager.getConnection(url, user, pass);
				stmt = myConn.createStatement();
				stmt.executeUpdate("update student set gpa = 3.0, sat = 1450, major_id = 1 where id = 13");
			}catch(Exception ex){
				ex.printStackTrace();
			}finally {
				if(myConn!=null)
					myConn.close();
				if(stmt!=null)
					stmt.close();}
		}
		
		public static void insertData(String url, String user, String pass) throws SQLException{
			try {
				myConn = DriverManager.getConnection(url, user, pass);
				stmt = myConn.createStatement();
				stmt.executeUpdate("insert student values (13, 'George', 'Washington',4.0,1600,NULL)");
			}catch(Exception ex){
				ex.printStackTrace();
			}finally {
				if(myConn!=null)
					myConn.close();
				if(stmt!=null)
					stmt.close();}
		}
		
		public static void deleteRecord(String url, String user, String pass) throws SQLException{
			try {
				myConn = DriverManager.getConnection(url, user, pass);
				stmt = myConn.createStatement();
				stmt.executeUpdate("delete from student where last_name = 'Washington' and sat = 1450;");
			}catch(Exception ex){
				ex.printStackTrace();
			}finally {
				if(myConn!=null)
					myConn.close();
				if(stmt!=null)
					stmt.close();}
		}
		
		public static void fetchGeorge(String url, String user, String pass) throws SQLException {
			try {
				myConn = DriverManager.getConnection(url, user, pass);
				stmt = myConn.createStatement();
				rs = stmt.executeQuery("select * from student where first_name = 'George' and last_name = 'Washington';");
				System.out.println("First Name  Last Name   GPA   SAT  Major-Code ");
				while(rs.next())
				System.out.println(rs.getString("first_name") + "      " + rs.getString("last_name") + "  " + rs.getDouble("gpa") + "  " + rs.getInt("sat") + "       " + rs.getInt("major_id"));
			}catch(Exception exc){
					exc.printStackTrace();
			}finally {
					close(myConn, stmt, rs);}
		}
		
		
		public static void backup(String url, String user, String pass) throws SQLException {
			try {
				myConn = DriverManager.getConnection(url, user, pass);
				stmt = myConn.createStatement();
				rs = stmt.executeQuery("select * from student;");
				while(rs.next())
				
				
				System.out.println("Insert student values (" + rs.getInt("id") + ", '" + rs.getString("first_name") + "', '" + rs.getString("last_name") + "', " + rs.getDouble("gpa") + ", " + rs.getInt("sat") + ", " + rs.getInt("major_id") + ");");
			}catch(Exception exc){
					exc.printStackTrace();
			}finally {
					close(myConn, stmt, rs);}
		}
		
		
		
		public static void backup2(String url, String user, String pass) throws SQLException {
			try {
				myConn = DriverManager.getConnection(url, user, pass);
				stmt = myConn.createStatement();
				
				//Backup of Major
				rs = stmt.executeQuery("select * from major;");
				while(rs.next())
				System.out.println("Insert major values (" + rs.getInt("id") + ", '" + rs.getString("description") + "', " + rs.getInt("minimum_sat") + ");");
				
				//Backup of Student
				rs = stmt.executeQuery("select * from student;");
				while(rs.next())
					System.out.println("Insert student values (" + rs.getInt("id") + ", '" + rs.getString("first_name") + "', '" + rs.getString("last_name") + "', " + rs.getDouble("gpa") + ", " + rs.getInt("sat") + ", " + rs.getInt("major_id") + ");");
				
				//Backup of Instructor
				rs = stmt.executeQuery("select * from instructor;");
				while(rs.next())
					System.out.println("Insert instructor values (" + rs.getInt("id") + ", '" + rs.getString("first_name") + "', '" + rs.getString("last_name") + "', " + rs.getInt("major_id") + ", " + rs.getInt("years_experience") + ", " + rs.getInt("tenure") + ");");
				
				//Backup of Class
				rs = stmt.executeQuery("select * from class;");
				while(rs.next())
					System.out.println("Insert class values (" + rs.getInt("id") + ", '" + rs.getString("description") + "', " + rs.getInt("instructor_id") + ");");
				
				//Backup of Grade
				rs = stmt.executeQuery("select * from grade;");
				while(rs.next())
					System.out.println("Insert grade values (" + rs.getInt("id") + ", '" + rs.getString("description") + "');");
				
				//Backup of Assignment
				rs = stmt.executeQuery("select * from assignment;");
				while(rs.next())
					System.out.println("Insert assignment values (" + rs.getInt("id") + ", " + rs.getInt("student_id") + ", " + rs.getInt("assignment_nbr") + ", " + rs.getInt("grade_id") + ", " + rs.getInt("class_id") + ");");
			
				//Backup of Major_Class_Relationship
				rs = stmt.executeQuery("select * from major_class_relationship;");
				while(rs.next())
					System.out.println("Insert major_class_relationship values (" + rs.getInt("id") + ", " + rs.getInt("major_id") + ", " + rs.getInt("class_id") + ");");
				
				//Backup of Student_Class_Relationship
				rs = stmt.executeQuery("select * from student_class_relationship;");
				while(rs.next())
					System.out.println("Insert student_class_relationship values (" + rs.getInt("id") + ", " + rs.getInt("student_id") + ", " + rs.getInt("class_id") + ");");
				
				//Backup of Prerequisite
				rs = stmt.executeQuery("select * from prerequisite;");

				while(rs.next())
//				prereq = rs.getInt("prereq");
//				System.out.println(rs.getInt("prereq"));
//					if (prereq < 1) {
//						reqstring = "NULL";}
//					else {
//						reqstring = String.valueOf(prereq);
//					}
					System.out.println("Insert prerequisite values (" + rs.getInt("id") + ", " + rs.getInt("class_id") + ", " + rs.getInt("prereq") + ");");
				
				
				
				
				
			}catch(Exception exc){
					exc.printStackTrace();
			}finally {
					close(myConn, stmt, rs);}
		}
		
		
		public static void backup3(String url, String user, String pass) throws SQLException {
			try {
				myConn = DriverManager.getConnection(url, user, pass);
				stmt = myConn.createStatement();
				rs = stmt.executeQuery("select * from student;");
				ResultSetMetaData rsmd = rs.getMetaData();

				int numberOfColumns = rsmd.getColumnCount();
				
				
				for (int i = 0; i < numberOfColumns; i++) {
					columnName.add(rsmd.getColumnName(i+1));
				}
				for (int i = 0; i < numberOfColumns; i++) {
					columnType.add(rsmd.getColumnTypeName(i+1));
				}
				for (int i = 0; i < numberOfColumns; i++) {
					columnAuto.add(String.valueOf(rsmd.isAutoIncrement(i+1)));
				}	
				for (int i = 0; i < numberOfColumns; i++) {
					columnPrecision.add(String.valueOf(rsmd.getPrecision(i+1)));
				}
				
				for (int i = 0; i < numberOfColumns; i++) {
					columnScale.add(String.valueOf(rsmd.getScale(i+1)));
				}
				
//				for (int i = 0; i < numberOfColumns; i++) {
//					columnKeys.add(String.valueOf(rsmd.getImportedKeys(i+1)));
//				}
				
				
				
				
				
				System.out.println(numberOfColumns);
				for (String column : columnName) {
					System.out.println(column);
				}
			
				System.out.println(numberOfColumns);
				for (String type : columnType) {
					System.out.println(type);
				}
				
				System.out.println(numberOfColumns);
				for (String type : columnAuto) {
					System.out.println(type);
				}
				
				System.out.println(numberOfColumns);
				for (String type : columnPrecision) {
					System.out.println(type);
				}
				
				System.out.println(numberOfColumns);
				for (String type : columnScale) {
					System.out.println(type);
				}
				
			}catch(Exception exc){
					exc.printStackTrace();
			}finally {
					close(myConn, stmt, rs);}
		}
	}

