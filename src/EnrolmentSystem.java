
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;



public class EnrolmentSystem implements StudentEnrolmentManager {
    Scanner scanner = new Scanner(System.in);
    public static ArrayList<Student> studentList = new ArrayList<Student>();
    public static ArrayList<Course> courseList = new ArrayList<Course>();
    public static ArrayList<StudentEnrolment> studentEnrolmentList = new ArrayList<>();

    // Menu option
    public static void menu(){
        System.out.println("Welcome to Enrolment System:");
        System.out.println("----------------------------");
        System.out.println("[1] Add Enrolment");
        System.out.println("[2] Update Enrolment");
        System.out.println("[3] Delete Enrolment");
        System.out.println("[4] Display One Record");
        System.out.println("[5] Display All Records");
        System.out.println("[0] Exit");
    }
    // Get user's input
    public static int getOption(){
        int option;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Choice: ");
        option = scanner.nextInt();
        return option;
    }

    // Read csv file
    // Add data to student and course list
    public static void readCsv(String filename){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                boolean studentIsAdded = false;
                boolean courseIsAdded = false;
                String [] row = line.split(",");
                // Check if students and course is in the list.
                // Add student and course to the list.
                for (Student s: studentList
                     ) {
                    if (s.getStudentId().equals(row[0])){
                        studentIsAdded = true;
                        break;
                    }
                }
                if (!studentIsAdded){
                    studentList.add(new Student(row[0], row[1],row[2]));
                }
                for (Course c:courseList
                     ) {
                    if (c.getCourseId().equals(row[3])){
                        courseIsAdded = true;
                        break;
                    }
                }
                if (!courseIsAdded){
                    courseList.add(new Course(row[3], row[4], row[5]));
                }

                // Add enrolments into list
                studentEnrolmentList.add(new StudentEnrolment(new Student(row[0], row[1],row[2]),new Course(row[3], row[4], row[5]),row[6]));

            }
            System.out.println(studentList);
            System.out.println(courseList);
            System.out.println(studentEnrolmentList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Display add
    public void displayAdd(){
        System.out.println("Display Student List:");
        System.out.println(studentList);
        System.out.println("Enter student Id to enrol:");
    }
    // Is valid student
    public boolean isValidStudent(ArrayList<Student> studentList, String sID){
        for (Student s: studentList
             ) {
            if (s.getStudentId().equals(sID)){
                System.out.println("This student is valid!");
                return true;
            }
        }
        System.out.println("Invalid student");
        return false;
    }
    public boolean isValidCourse(ArrayList<Course> courseList, String courseID){

        for (Course c : courseList){
//
            if (c.getCourseId().equals(courseID)){
                System.out.println("Valid course!");
                return true;
            }
        }
        System.out.println("Invalid Course!");
        return false;
    }
    // CRUD method
    @Override
    public void add() {
        String input;
//        do {
//            displayAdd();
//            input = scanner.next();
//        }while (!isValidStudent(studentList,input));
        do {
            System.out.println("Display valid course:");
            System.out.println(courseList);
            System.out.println("Enter course ID: ");
            input = scanner.next();
        }while (!isValidCourse(courseList, input));

    }

    @Override
    public void update() {
        System.out.println("2");
    }

    @Override
    public void delete() {
        System.out.println("3");
    }

    @Override
    public void getOne() {
        System.out.println("4");
    }

    @Override
    public void getAll() {
        System.out.println("5");
    }


    public static void main(String[] args) {
        EnrolmentSystem enrolmentSystem = new EnrolmentSystem();
        readCsv("src\\default.csv");
        int option;
        do {
            menu();
            option = getOption();
            switch (option){
                case 1 :
                    enrolmentSystem.add();
                    break;
                case 2 :
                    enrolmentSystem.update();option = 0;break;
                case 3 : enrolmentSystem.delete();option = 0;break;
                case 4 : enrolmentSystem.getOne();option = 0;break;
                case 5 : enrolmentSystem.getAll();option = 0;break;
            }
        }while (option!=0);

    }

}
