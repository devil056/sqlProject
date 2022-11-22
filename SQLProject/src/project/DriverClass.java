package project;

import java.util.Scanner;

public class DriverClass {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to the RailwayCrossing App");
		System.out.println("1.Government App Login");
		System.out.println("2.User App Login");
		System.out.println("3.Register as new user");
		System.out.println("4. Exit");
		int userChoice = sc.nextInt();
		if (userChoice == 1) {
			System.out.println("Government App");
			Authentication au = new Authentication();
			au.governmentApp();
		} else if (userChoice == 2) {
			System.out.println("User app");
			Authentication au = new Authentication();
			au.userApp();
		} else if (userChoice == 3) {
			System.out.println("New user registration");
			Authentication au = new Authentication();
			au.registerUser();
		}
		sc.close();
	}
}
