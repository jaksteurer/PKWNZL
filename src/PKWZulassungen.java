import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class PKWZulassungen extends Application {			

	//Hashmap erstellen in der die Zahlen pro Jahr von Audi, Bmw und Mercedes gespeichert werden. 
	static HashMap<Integer, Object[]> zulassungen = new HashMap<Integer, Object[]>();

	public static void main(String[] args) {
		connectionToMySql();
		launch();
	}
	public static void connection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e) {
			System.out.println("[connection.catch]: " + e);
		}
	}
	public static void connectionToMySql () {
		//Daten für MYSQL-Server
		connection();
		String url = "jdbc:mysql://localhost:3306/neuzulassungen";
		String user ="root";
		String password = "hallo123";
		try {
			//Java mit MYSQL-Server verbinden
			Connection connect = DriverManager.getConnection(url, user, password);
			//WICHTIG: spaltenname im select muss gleich sein wie in Zeile 50 data.getObject(Spaltenname);
			for(int i = 2000;i<=2020;i++) {
				//SQL Abfrage für alle 20 Jahre
				PreparedStatement sqlstatement = (PreparedStatement) connect.prepareStatement("SELECT SUM(Audi) as audi ,Sum(MERCEDES)"
						+"as mercedes, sum(BMW) as bmw FROM neuzulassungen.zulassungenpkw where ZULASSUNG like '"+i+"%';");
				ResultSet data = sqlstatement.executeQuery();
				if(data.next()) {
					//import in Hashmap
					zulassungen.put(i, new Object[] {data.getObject("audi"), data.getObject("bmw"), data.getObject("mercedes")});
				}
			}
			//Ausgabe in der Console
			for(int key : zulassungen.keySet()) {
				System.out.println("\tAudi\tBmw\tMercedes");
				System.out.println(key + " " + Arrays.toString(zulassungen.get(key)) );
			}
		}catch (Exception e) {
			System.out.println("[connectionToMySql.catch]: " + e);
		}
	}
	@SuppressWarnings({ "unused", "unchecked" })
	public void start(Stage s) throws Exception{
		//X-Achse erstellen und beschriften
		CategoryAxis xAchse = new CategoryAxis();
		xAchse.setLabel("[Jahr]");
		//Y-Achse erstellen und beschriften
		Axis<Number> yAchse = new NumberAxis();
		yAchse.setLabel("[Neuzugelassene Autos]");

		//XYChart.Data<String, Number> data = null; 
		//Diagramm erzeugen mit Titel
		BarChart<String, Number> chart = new BarChart<String, Number>(xAchse, yAchse);
		chart.setTitle("Zugelassene Autos pro Jahr");

		BarChart.Series<String, Number> seriesMercedes = new XYChart.Series<String, Number>();
		BarChart.Series<String, Number> seriesAudi = new XYChart.Series<String, Number>();
		BarChart.Series<String, Number> seriesBmw = new XYChart.Series<String, Number>();
		for (int year = 2000; year <= 2020; year++) {
			seriesAudi.getData().add(new XYChart.Data<String, Number>(Integer.toString(year), (Number) zulassungen.get(year)[0]));
			seriesBmw.getData().add(new XYChart.Data<String, Number>(Integer.toString(year), (Number) zulassungen.get(year)[1]));
			seriesMercedes.getData().add(new XYChart.Data<String, Number>(Integer.toString(year), (Number) zulassungen.get(year)[2]));
		}
		seriesAudi.setName("Audi");
		seriesBmw.setName("Bmw");
		seriesMercedes.setName("Mercedes");
		chart.getData().addAll(seriesAudi,seriesBmw,seriesMercedes);
		s.setScene(new Scene(chart));
		s.show();		
	}
}
