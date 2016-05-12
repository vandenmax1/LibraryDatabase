package sample;

import sample.models.SignIn;
import sample.models.Student;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Max on 5/12/2016.
 */
public class DBToFileWriter {
    public static void writeSignIns() throws IOException {
        LocalDate currentTime = LocalDate.now();
        String fileName = "C:\\Users\\user\\textfilearea\\"+ "Sign In's on " + currentTime + ".txt";

        File file = new File(fileName);
        FileWriter writer = new FileWriter(file);
        BufferedWriter writerBuffer = new BufferedWriter(writer);

        List<SignIn> all = SignIn.find.all();
            try {
                writerBuffer.write("Entries for "+ currentTime + "\n");
                writerBuffer.newLine();
            }  catch (IOException e) {
                e.printStackTrace();
            }

        all.stream().forEach(signIn ->{
            try {
                writerBuffer.write( "Student: " +
                          formatStudentID(String.valueOf(signIn.getStudent()))   + " Time In: "
                        + String.valueOf(signIn.getTimeIn()) +" Time Out: "+ String.valueOf(signIn.getTimeOut())
                        +" Manual: "+String.valueOf(signIn.isWasManual()) + "\n");
                writerBuffer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writerBuffer.close();
    }

    public static void writeStudents() throws IOException {
        LocalDate currentTime = LocalDate.now();
        String fileName = "C:\\Users\\user\\textfilearea\\" + "Students on "+ currentTime + ".txt";

        File file = new File(fileName);
        FileWriter writer = new FileWriter(file);
        BufferedWriter writerBuffer = new BufferedWriter(writer);

        List<Student> students = Student.find.all();

            students.stream().forEach(student1 ->{
                try {
                    writerBuffer.write(student1.getFirstName() +", "+ student1.getLastName()
                            + " ID: " + student1.getStudentID()+ " Database ID: " +  " Amount of Logins: " +  student1.signIns.size()
                            + " Specific ID: Student@"+ student1.id);
                    writerBuffer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
         writerBuffer.close();
    }

    public static void terminateAllLiveClients(){
        List<SignIn> all = SignIn.find.all();
        all.stream().forEach(signIn3-> {
            if(signIn3.getTimeOut() == null){
                signIn3.setTimeOut(LocalDateTime.now());
                signIn3.setWasManual(true);
                signIn3.save();
            }
        });
    }

    public static String formatStudentID(String string){
        string = string.substring(14,23);
        return string;
    }
}

// I'm including this to remind myself that sometimes when you program you can always do better as formatStudentID(String.valueOf(student1.signIns.stream().map(signIn -> {return signIn.getStudent().toString();}).collect(Collectors.joining(", ")))));
// was just replaced with student.id      the irony lol .................