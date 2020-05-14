import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class CSVReader {
	public static  final String DATEIPFAD= "";
	public static String InputLine = "";
	public static String [][] arr;
	public static int anzahlZeilen = 0;
	public static int anzahlSpalten = 0;

	public static void main(String[]args) {

		einlese();
		Arrayausgeben(); 
	}
	public static void einlese() {
		AnzahlSpalteZeile();
		arr = new String [anzahlZeilen][anzahlSpalten];
		Scanner sc = null;
		int zZaehler = 0;
		try {
			sc = new Scanner(new BufferedReader(new FileReader(DATEIPFAD)));
			while (sc.hasNextLine()) {
				InputLine= sc.nextLine();
				String[] inArr = InputLine.split("/t ");
				for (int x = 0; x < inArr.length; x++) {
					arr[zZaehler][x]=inArr[x];
				}
				zZaehler++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("[einlese]: "+e);
		}
	}
	public static void Arrayausgeben() {
		int zeile;
		int spalte;
		for( zeile = 0;zeile<anzahlZeilen; zeile++) {
			for( spalte = 0;spalte<anzahlSpalten; spalte++) {
				System.out.println(arr[0][spalte]);
				System.out.println(arr[zeile][spalte]+"\n");
			}
		}
	}
	public static void AnzahlSpalteZeile() {
		Scanner sc = null;
		try {
			sc = new Scanner(new BufferedReader(new FileReader(DATEIPFAD)));
			sc.useDelimiter("\t");
			while (sc.hasNextLine()) {
				InputLine=sc.nextLine();
				String[] inArr = InputLine.split("\t ");
				anzahlZeilen++;
				anzahlSpalten = inArr.length;
			}
		} catch (Exception e ) {
			System.out.println("[AnzahlSpalteZeile]: "+e);
		}
	}
}
