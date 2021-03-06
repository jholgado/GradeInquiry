import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface GradeInquiry extends Remote {
    String sayHello() throws RemoteException;
    Double studentGrade(String student_name) throws RemoteException;
    void addStudentGrade(String student_name, Double grade) throws RemoteException, IOException;
    HashMap<String, Double> getGradeBook() throws RemoteException;
}