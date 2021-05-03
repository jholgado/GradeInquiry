
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.io.*;

public class Server implements GradeInquiry  {

    private HashMap<String, Double> gradeBook = new HashMap<String, Double>();
    
    public Server() {}

    //serializes the gradeBook
    private void saveGradeBook() throws IOException, FileNotFoundException{                 
        FileOutputStream fileOutputStream = new FileOutputStream("gradeBook.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this.gradeBook);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    //test method
    public String sayHello() {
        return "Hello, world!";
    }

    //fetches student grade associated with the given name
    public Double studentGrade(String student_name){
        return gradeBook.get(student_name);
    }

    //adds an entry to the gradebook or updates existing entry if the student already exists
    public void addStudentGrade(String student_name, Double grade) throws IOException{
        gradeBook.put(student_name, grade);
        saveGradeBook();
    } 

    public static void main(String args[]){
        try {
            Server obj = new Server();
           
            //loads gradebook from file, skips if not found
            try{
                FileInputStream fileInputStream = new FileInputStream("gradeBook.ser");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                HashMap<String,Double> saved_gradebook = (HashMap<String, Double>) objectInputStream.readObject();
                obj.gradeBook = saved_gradebook;
                objectInputStream.close();
            } catch (ClassNotFoundException | IOException e){
                //e.printStackTrace();
            }

            //create a stub to interface with client
            GradeInquiry stub = (GradeInquiry) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("GradeInquiry", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}