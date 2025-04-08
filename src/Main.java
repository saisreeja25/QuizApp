import java.util.Scanner;

public class Main {
	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);

	        System.out.print("🎤 Enter Player Name: ");
	        String name = sc.nextLine();
	        System.out.print("📧 Enter Email: ");
	        String email = sc.nextLine();
	        System.out.print("📞 Enter Phone Number: ");
	        String phone = sc.nextLine();

	        Player player = new Player(name, email, phone);
	        new QuizGame(player).start();
	    }
	
}
