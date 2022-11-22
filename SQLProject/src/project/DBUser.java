package project;

import java.util.Scanner;

public class DBUser {
	DBUser() {
		System.out.println("welcome user");
	}

	void getTrains(int i) {
		DBHandler dh = new DBHandler();
		dh.dbQueryRunner(i);
	}

	void menu() {
		System.out.println("Choose your choice");
		System.out.println("1.Get Trains.");
		System.out.println("2.Get Stops.");
		System.out.println("3.Get train timings.");
		System.out.println("4.Check the trains timings at a stop.");
		System.out.println("5.Exit");
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		if (choice == 1) {
			System.out.println("Getting trains");
			getTrains(1);

		} else if (choice == 2) {
			System.out.println("Getting stops");
			getTrains(2);
		}else if(choice==3) {
			System.out.println("Getting particular train data.");
			getTrains(3);
		}else if(choice==4) {
			System.out.println("Getting a particular station data.");
			getTrains(4);			
		}
		sc.close();
	}
}
