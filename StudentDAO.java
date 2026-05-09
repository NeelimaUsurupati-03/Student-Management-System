package com.sms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StudentDAO {

	Scanner sc = new Scanner(System.in);

	public void addStudent(Connection con) throws SQLException
	{
		String query = "INSERT INTO `Student`"
				+ " (`name`,`email`,`phone`,`branch`,`cgpa`)"
				+ " VALUES(?,?,?,?,?)";

		PreparedStatement pstmt=con.prepareStatement(query);

		System.out.print("Enter Name: ");
		pstmt.setString(1, sc.nextLine());

		System.out.print("Enter Email: ");
		pstmt.setString(2, sc.nextLine());

		System.out.print("Enter Phone: ");
		pstmt.setString(3, sc.nextLine());

		System.out.print("Enter Branch : ");
		pstmt.setString(4, sc.nextLine());

		System.out.print("Enter CGPA: ");
		pstmt.setDouble(5, sc.nextDouble());

		int rows = pstmt.executeUpdate();
		System.out.println( rows+" Student Added Successfully!");

	}

	public void viewStudent(Connection con) throws SQLException
	{
		String Query = "SELECT * FROM Student";

		Statement stmt=con.createStatement();

		ResultSet res=stmt.executeQuery(Query);

		System.out.println("\n--- All Students ---");

		while(res.next())
		{
			System.out.printf("%-2d %-20s %-25s %-12s %-8s %-5.1f\n",
					res.getInt("id"),
					res.getString("name"),
					res.getString("email"),
					res.getString("phone"),
					res.getString("branch"),
					res.getDouble("cgpa"));
		}
	}

	public void searchByName(Connection con) throws SQLException {

		String query = "SELECT * FROM student WHERE name LIKE ?";

		PreparedStatement pstmt = con.prepareStatement(query);

		System.out.print("Enter Student Name to search: ");
		pstmt.setString(1, "%" + sc.next() + "%");

		ResultSet rs = pstmt.executeQuery();

		int count = 0;

		System.out.println("\n--- Students Found ---");
		System.out.printf("%-5s %-15s %-25s %-12s %-8s %-5s\n",
				"ID", "Name", "Email", "Phone", "Branch", "CGPA");
		System.out.println("-".repeat(75));

		while(rs.next()) {
			System.out.printf("%-10d %-15s %-25s %-12s %-8s %-5.1f\n",
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("email"),
					rs.getString("phone"),
					rs.getString("branch"),
					rs.getDouble("cgpa"));
			count++;
		}

		if(count == 0) {
			System.out.println("No Student Found with that Name!");
		} else {
			System.out.println("\n--- " + count + " Student(s) Found ---");
		}
	}
	
	
	public void updateStudent(Connection con) throws SQLException {

		// Step 1 - Ask ID
		System.out.print("Enter Student ID to update: ");
		int id = sc.nextInt();

		// Step 2 - Check if student exists
		String checkQuery = "SELECT * FROM student WHERE id=?";
		PreparedStatement checkStmt = con.prepareStatement(checkQuery);
		checkStmt.setInt(1, id);
		ResultSet rs = checkStmt.executeQuery();

		if(!rs.next()) {
			System.out.println("No Student Found with that ID!");
			return;
		}

		// Step 3 - Show current details
		System.out.println("\n--- Current Details ---");
		System.out.println("1. Name   : " + rs.getString("name"));
		System.out.println("2. Email  : " + rs.getString("email"));
		System.out.println("3. Phone  : " + rs.getString("phone"));
		System.out.println("4. Branch : " + rs.getString("branch"));
		System.out.println("5. CGPA   : " + rs.getDouble("cgpa"));

		// Step 4 - Ask what to update
		System.out.println("\nWhat do you want to update?");
		System.out.println("1. Name");
		System.out.println("2. Email");
		System.out.println("3. Phone");
		System.out.println("4. Branch");
		System.out.println("5. CGPA");
		System.out.print("Enter Choice: ");
		int choice = sc.nextInt();

		// Step 5 - Update only that field
		String updateQuery = sc.nextLine();

		switch(choice) {
		case 1: updateQuery = "UPDATE student SET name=? WHERE id=?"; break;
		case 2: updateQuery = "UPDATE student SET email=? WHERE id=?"; break;
		case 3: updateQuery = "UPDATE student SET phone=? WHERE id=?"; break;
		case 4: updateQuery = "UPDATE student SET branch=? WHERE id=?"; break;
		case 5: updateQuery = "UPDATE student SET cgpa=? WHERE id=?"; break;
		default: System.out.println("Invalid Choice!"); return;
		}

		PreparedStatement updateStmt = con.prepareStatement(updateQuery);

		// Step 6 - Get new value
		System.out.print("Enter New Value: ");

		if(choice == 5) {
			updateStmt.setDouble(1, sc.nextDouble());
		} else {
			updateStmt.setString(1, sc.nextLine());
		}

		updateStmt.setInt(2, id);

		// Step 7 - Execute
		int rows = updateStmt.executeUpdate();

		if(rows > 0) {
			System.out.println("Student Updated Successfully! ");
		} else {
			System.out.println("Update Failed!");
		}
	}
	
	public void deleteStudent(Connection con) throws SQLException {

	    // Step 1 - Ask ID
	    System.out.print("Enter Student ID to delete: ");
	    int id = sc.nextInt();

	    // Step 2 - Check if student exists and show details
	    String checkQuery = "SELECT * FROM student WHERE id=?";
	    PreparedStatement checkStmt = con.prepareStatement(checkQuery);
	    checkStmt.setInt(1, id);
	    ResultSet rs = checkStmt.executeQuery();

	    if(!rs.next()) {
	        System.out.println("No Student Found with that ID!");
	        return;
	    }

	    // Step 3 - Show current details
	    System.out.println("\n--- Student Details ---");
	    System.out.println("ID     : " + rs.getInt("id"));
	    System.out.println("Name   : " + rs.getString("name"));
	    System.out.println("Email  : " + rs.getString("email"));
	    System.out.println("Phone  : " + rs.getString("phone"));
	    System.out.println("Branch : " + rs.getString("branch"));
	    System.out.println("CGPA   : " + rs.getDouble("cgpa"));

	    // Step 4 - Confirm deletion
	    System.out.print("\nAre you sure you want to delete? (yes/no): ");
	    String confirm = sc.next();

	    if(confirm.equalsIgnoreCase("yes")) {

	        // Step 5 - Delete
	        String deleteQuery = "DELETE FROM student WHERE id=?";
	        PreparedStatement deleteStmt = con.prepareStatement(deleteQuery);
	        deleteStmt.setInt(1, id);

	        int rows = deleteStmt.executeUpdate();

	        if(rows > 0) {
	            System.out.println("Student Deleted Successfully! ");
	        } else {
	            System.out.println("Delete Failed!");
	        }

	    } else {
	        System.out.println("Deletion Cancelled! ");
	    }
	}

}
