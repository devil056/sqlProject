package project;

import java.util.Scanner;

public class Authentication {
	public void governmentApp() {
		Scanner sc = new Scanner(System.in);
		System.out.println("email id: ");
		String userName = sc.next();
		System.out.println("Password: ");
		String password = sc.next();
		System.out.println(userName + " " + password);
		DBHandler db = new DBHandler();
		db.verifyLogin(userName, password, "admin");
		sc.close();
	}

	public void userApp() {
		Scanner sc = new Scanner(System.in);
		System.out.println("email id: ");
		String userName = sc.next();
		System.out.println("Password: ");
		String password = sc.next();
		System.out.println(userName + " " + password);
		DBHandler db = new DBHandler();
		db.verifyLogin(userName, password, "user");
		sc.close();
	}

	public void registerUser() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your email: ");
		String email = sc.next();
		System.out.println("Enter your first name: ");
		String firstName = sc.next();
		System.out.println("Enter your last name: ");
		String lastName = sc.next();
		System.out.println("Enter your password:");
		String password = sc.next();
		DBHandler db = new DBHandler();
		db.registerUser(email, firstName, lastName, password);
		sc.close();
	}
}
