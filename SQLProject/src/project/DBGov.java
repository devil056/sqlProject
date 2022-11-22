package project;

import java.util.Scanner;

public class DBGov {
	DBHandler dh=new DBHandler();
	void menu() {
		System.out.println("Government app menu.");
		System.out.println("1.Insert New Train data");
		System.out.println("2.Insert new station data");
		System.out.println("3.Update train data");
		System.out.println("4.Update station data");
		System.out.println("5.Delete train data");
		System.out.println("6.Delete station data.");
		Scanner sc = new Scanner(System.in);
		int userChoice = sc.nextInt();
		switch (userChoice) {
		case 1:
			System.out.println("new train data");
			dh.insertTrain();
			break;
		case 2:
			System.out.println("new station data");
			dh.insertStation();
			break;
		case 3:
			System.out.println("udpate train");
			dh.updateTrain();
			break;
		case 4:
			System.out.println("update station");
			dh.updateStation();
			break;
		case 5:
			System.out.println("delete train");
			dh.deleteTrain();
			break;
		case 6:
			System.out.println("delete station");
			dh.deleteStation();
			break;
		}
		sc.close();
	}
}
