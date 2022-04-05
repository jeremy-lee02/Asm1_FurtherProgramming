import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class EnrolmentSystemTest {

    public EnrolmentSystem enrolmentSystem = new EnrolmentSystem();
    public ArrayList<Student> studentList = new ArrayList<>();
    public ArrayList<Course> courseList = new ArrayList<>();
    public ArrayList<StudentEnrolment> studentEnrolmentList = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception {

    }
// Test add, update, delete function. (Checking validation)
    @Test
    public void testValidSIDAndCID(){
        Student student = new Student("s3752975","Tin Le","02/01/2000");
        Course course = new Course("COSC2440","Further programming","12");
        StudentEnrolment se = new StudentEnrolment(student,course,"2022A");
        studentList.add(student);
        courseList.add(course);
        boolean resultStudent = enrolmentSystem.isValidStudent(studentList,"s3752975");
        boolean resultStudent2 = enrolmentSystem.isValidStudent(studentList,"s375297"); // Wrong statement
        boolean resultCourse = enrolmentSystem.isValidCourse(courseList,"COSC2440");
        boolean resultCourse2 = enrolmentSystem.isValidCourse(courseList,"COSC244"); // Wrong statement
        // Test sID
        assertEquals(true, resultStudent);
        assertEquals(false, resultStudent2);
        // Test CID
        assertEquals(true,resultCourse);
        assertEquals(false,resultCourse2);
    }
    @Test
    public void testSem(){
        boolean resultSem = enrolmentSystem.isValidSem("2022A");
        boolean resultSem2 = enrolmentSystem.isValidSem("20saa");
        assertEquals(true,resultSem);
        assertEquals(false,resultSem2);

    }




}