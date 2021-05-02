import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    private Client() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            GradeInquiry stub = (GradeInquiry) registry.lookup("GradeInquiry");
            boolean run = true;
            Scanner scan = new Scanner(System.in);
            String input;
            while(run){
                System.out.println("Welcome to Grade Inquiry! Please select and operation:");
                System.out.println("G - get grade");
                System.out.println("Q - quit");
                input = scan.nextLine().toUpperCase();
                switch(input){
                    case "G":
                        System.out.println("Please enter a student name");
                        input = scan.nextLine();
                        Double response = stub.studentGrade(input);
                        System.out.println("Grade: " + response);
                        break;
                    case "Q":
                        System.out.println("exiting client");
                        run = false;
                        break;
                    default:
                        System.out.println("invalid command");
                        break;
                }
            }
            scan.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}