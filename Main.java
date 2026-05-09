package com.sms;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            // Step 1 - Get Connection
            Connection con = DBConnection.getConnection();

            // Step 2 - Create StudentDAO object
            StudentDAO dao = new StudentDAO();

            int choice;

            do {
                // Menu
                System.out.println("\n===== Student Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Search Student by Name");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("0. Exit");
                System.out.print("Enter Choice: ");
                choice = sc.nextInt();
              

                switch(choice) {
                    case 1: dao.addStudent(con); break;
                    case 2: dao.viewStudent(con); break;
                    case 3: dao.searchByName(con); break;
                    case 4: dao.updateStudent(con); break;
                    case 5: dao.deleteStudent(con); break;
                    case 0: System.out.println("Goodbye! "); break;
                    default: System.out.println("Invalid Choice! Try Again!");
                }

            } while(choice != 0);

            // Step 3 - Close connection
            con.close();
            System.out.println("Database Connection Closed!");

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}