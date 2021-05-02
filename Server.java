
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.io.*;

public class Server implements GradeInquiry  {

    private HashMap<String, Double> gradeBook = new HashMap<String, Double>();
    
    public Server() {}

    private void saveGradeBook() throws IOException, FileNotFoundException{
        FileOutputStream fileOutputStream = new FileOutputStream("objects.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this.gradeBook);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public String sayHello() {
        return "Hello, world!";
    }

    public Double studentGrade(String student_name){
        return gradeBook.get(student_name);
    }

    public void addStudentGrade(String student_name, Double grade) throws IOException{
        gradeBook.put(student_name, grade);
        saveGradeBook();
    } 

    public static void main(String args[]){
        try {
            Server obj = new Server();
            try{
                FileInputStream fileInputStream = new FileInputStream("objects.ser");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                HashMap<String,Double> saved_gradebook = (HashMap<String, Double>) objectInputStream.readObject();
                obj.gradeBook = saved_gradebook;
                objectInputStream.close();
            } catch (ClassNotFoundException | IOException e){
                //e.printStackTrace();
            }

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