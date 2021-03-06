import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
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
            //run loop; terminates if Q is selected by the user
            while(run){
                System.out.println("Welcome to Grade Inquiry! Please select an operation:");
                System.out.println("G - get grade");
                System.out.println("A - add grade");
                System.out.println("V - view gradebook");
                System.out.println("Q - quit");
                input = scan.nextLine().toUpperCase();
                
                switch(input){
                    case "G":
                        System.out.println("Please enter a student name");
                        input = scan.nextLine();
                        Double response = stub.studentGrade(input);
                        System.out.println("Grade: " + response);
                        break;
                    case "A":
                        System.out.println("Please enter a student name");
                        String student_name2 = scan.nextLine();
                        System.out.println("Please enter a student grade");
                        Double grade_in = scan.nextDouble();
                        scan.nextLine();
                        stub.addStudentGrade(student_name2, grade_in);
                        break;
                    case "V":
                        HashMap<String, Double> gradeBook = stub.getGradeBook();
                        System.out.println(gradeBook.toString());
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