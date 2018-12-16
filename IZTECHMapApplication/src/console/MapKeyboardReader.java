package console;
import java.util.Scanner;

public class MapKeyboardReader {

	private Scanner scanner;
	
	public MapKeyboardReader() {
		scanner = new Scanner(System.in);
	}
	
	public int readOperationNumber() {
		return scanner.nextInt();
	}
	
	public int readCategoryNumber() {
		return scanner.nextInt();
	}
	
	public int readTypeNumber() {
		return scanner.nextInt();
	}
	
	public int readId() {
		return scanner.nextInt();
	}
	
	public String readName() {
		return scanner.nextLine();
	}
}
