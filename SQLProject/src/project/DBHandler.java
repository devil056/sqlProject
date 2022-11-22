package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//port : 64983
public class DBHandler {
	Connection con;

	DBHandler() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("driver loaded");
		} catch (Exception e) {
			System.out.println("error loading driver");
		}
	}

	void connectDB() {
		String url = "jdbc:sqlserver://localhost:64983;databaseName=murari;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
		try {
			if (con == null) {
				con = DriverManager.getConnection(url);
				System.out.println("Connected to database");
			}
		} catch (SQLException e) {
			System.out.println("error connecting to database");
		}
	}

	void disconnectDB() {
		try {
			if (con != null) {
				con.close();
				System.out.println("Disconnected from database");
			}
		} catch (SQLException e) {
			System.out.println("error disconnecting.");
		}
	}

	void verifyLogin(String email, String password, String status) {
		// select * from
		// murari.dbo.userValidation('kireet0272@gmail.com','validP@ssword','user')
		String query = "select * from murari.dbo.userValidation('" + email + "','" + password + "','" + status + "')";
		int rowCount = 0;
		try {
			if (con == null) {
				connectDB();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(query);
				while (rs.next()) {
					System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
					++rowCount;
				}
				if (rowCount == 1) {
					if (status.equalsIgnoreCase("user")) {
						DBUser du = new DBUser();
						du.menu();
					} else if (status.equals("admin")) {
						DBGov dg = new DBGov();
						dg.menu();
					}
				}
				disconnectDB();
			}
		} catch (SQLException e) {
			System.out.println("error verifying the user");
		}
	}

	void registerUser(String email, String firstName, String lastName, String pass) {
		String qu = "exec userInsert @email='" + email + "',@first='" + firstName + "',@last='" + lastName + "',@pass='"
				+ pass + "'";
		try {
			connectDB();
			Statement s = con.createStatement();
			boolean res = s.execute(qu);
			if (!res) {
				System.out.println("user account registered.");
			} else {
				System.out.println("user registration failed.");
			}
			disconnectDB();
		} catch (SQLException e) {
			System.out.println("Error registering account.");
		}
	}

	void dbQueryRunner(int i) {
		DBUser du = new DBUser();
		String query = "";
		if (i == 1) {
			query = "exec getTrains";
		} else if (i == 2) {
			query = "exec getStations";
		} else if (i == 3) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the train code: ");
			String trainCode = sc.next();
			query = "exec getTrainData @trainCode='" + trainCode + "';";
			sc.close();
		} else if (i == 4) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the station ID: ");
			String staticId = sc.next();
			query = "exec getStationData @stationCode='" + staticId + "';";
			sc.close();
		}
		try {
			if (con == null) {
				connectDB();
				Statement s = con.createStatement();
				ResultSet r = s.executeQuery(query);
				if (i == 1) {
					while (r.next()) {
						System.out.println(r.getString("train_code") + " " + r.getString("train_name"));
					}
				} else if (i == 2) {
					while (r.next()) {
						System.out.println(r.getString("station_id") + " " + r.getString("station_name"));
					}
				} else if (i == 3) {
					while (r.next()) {
						System.out.println(r.getString("station_id") + " " + r.getTime("start_time") + " "
								+ r.getTime("stop_time"));
					}
				} else if (i == 4) {
					while (r.next()) {
						System.out.println(r.getString("train_code") + " " + r.getString("station_id") + " "
								+ r.getTime("start_time") + " " + r.getTime("stop_time"));
					}
				}
			}
			System.out.println("------------------------------");
		} catch (SQLException e) {
			System.out.println("error fetching data.");
		} finally {
			du.menu();
		}
	}

	// train Code and train Name
	void insertTrain() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the train code: ");
		String trainCode = sc.next();
		System.out.println("Enter the train name: ");
		String trainName = sc.next();
		String query = "exec trainInsert @trainCode='" + trainCode + "',@trainName='" + trainName + "';";
		sc.close();
		try {
			if (con == null) {
				connectDB();
				Statement st = con.createStatement();
				boolean out = st.execute(query);
				if (!out) {
					System.out.println("Inserted new train data successfully.");
				} else {
					System.out.println("Unable to insert data.");
				}
			}
			disconnectDB();
		} catch (SQLException e) {
			System.out.println("Error while inserting the new traind data.");
		}

	}

	// station id, station name, station address

	void insertStation() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the station id: ");
		String stationId = sc.next();
		System.out.println("Enter the station name: ");
		String stationName = sc.next();
		System.out.println("Enter the station address: ");
		String stationAddr = sc.next();
		sc.close();
		String query = "exec stationInsert @stationId='" + stationId + "',@station_name='" + stationName
				+ "',@station_addr='" + stationAddr + "';";
		try {
			if (con == null) {
				connectDB();
				Statement st = con.createStatement();
				boolean res = st.execute(query);
				if (!res) {
					System.out.println("Inserted new station.");
				} else {
					System.out.println("Unable to insert the station data.");
				}
			}
		} catch (SQLException e) {
			System.out.println("error while inserting the station data");
		}
	}

	void updateTrain() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the train code that you wish to update: ");
		String oldTrainCode = sc.next();
		System.out.println("Enter the new train code: ");
		String newCode = sc.next();
		System.out.println("Enter the train name you wish to set: ");
		String trainName = sc.next();
		String query = "exec updateTrainCode @trainCode='" + oldTrainCode + "',@newTrainCode='" + newCode
				+ "',@newTrainName='" + trainName + "';";
		sc.close();
		try {
			connectDB();
			Statement st = con.createStatement();
			boolean result = st.execute(query);
			if (!result) {
				System.out.println("updated successfully the train data in the trains and schedule sheets.");
			} else {
				System.out.println("Failed to update the train data.");
			}

		} catch (Exception e) {
			System.out.println("error updating train data");
		}
	}

	void updateStation() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the station id that you wish to update: ");
		String stationID = sc.next();
		System.out.println("Enter the new station ID: ");
		String newStationId = sc.next();
		System.out.println("Enter the station name you wish to set: ");
		String newStationName = sc.next();
		System.out.println("Enter the new station address: ");
		String newStationAddr = sc.next();
		String query = "exec updateStation @stationId='" + stationID + "',@newStationID='" + newStationId
				+ "',@newStationName='" + newStationName + "',@newStationAddr='" + newStationAddr + "';";
		sc.close();
		try {
			connectDB();
			Statement st = con.createStatement();
			boolean result = st.execute(query);
			if (!result) {
				System.out.println("updated successfully the station data in the station and schedule sheets.");
			} else {
				System.out.println("Failed to update the station data.");
			}

		} catch (Exception e) {
			System.out.println("error updating train data");
		}

	}

	void deleteTrain() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the train code you wish to delete: ");
		String trainCode = sc.next();
		String query = "exec dropTrain @trainCode='" + trainCode + "';";
		sc.close();
		try {
			if (con == null) {
				connectDB();
				Statement st = con.createStatement();
				boolean result = st.execute(query);
				if (!result) {
					System.out.println("Successfully deleted the train data.");
				} else {
					System.out.println("failed to delete the train data.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Error while deleting the train data from the tables.");
		}
	}

	void deleteStation() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the station code you wish to delete: ");
		String stationID = sc.next();
		String query = "exec dropStation @stationId='" + stationID + "';";
		sc.close();
		try {
			if (con == null) {
				connectDB();
				Statement st = con.createStatement();
				boolean result = st.execute(query);
				if (!result) {
					System.out.println("Successfully deleted the station data.");
				} else {
					System.out.println("failed to delete the station data.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Error while deleting the station data from the tables.");
		}
	}

}
