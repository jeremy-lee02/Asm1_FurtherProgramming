
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EnrolmentSystem implements StudentEnrolmentManager {
    static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Student> studentList = new ArrayList<>();
    public static ArrayList<Course> courseList = new ArrayList<>();
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
    public static String readCsv(){
        boolean reading = false;
        String fileName = "1";

        while (!reading){
            System.out.println("Enter file name in src folder. Press '1' to load default file.");
            fileName = scanner.nextLine();
            String defaultName  = "src\\default.csv";
            if (!fileName.equals("1")){
                defaultName = "src\\"+ fileName;
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(defaultName));
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
                System.out.println("Read file success!");
                reading = true;
            }catch (Exception e){
                System.out.println("Cannot find file!");
                reading = false;
            }
        }
        return fileName;
    }

    //Display Student and Course
    public void displayStudent(){
        System.out.println("Display Student List:");
        System.out.printf("%-30s" ,"Student ID");
        System.out.printf("%-30s" ,"Student Name");
        System.out.println();
        System.out.println("---------------------------------------------");
        for (Student s: studentList
        ) {
            System.out.printf("%-30s" ,s.getStudentId());
            System.out.printf("%-30s" ,s.getStudentName());
            System.out.println();
        }
    }
    public void displayCourse(){
        System.out.println("Display valid course:");
        System.out.printf("%-30s" ,"Course ID");
        System.out.printf("%-30s" ,"Course Name");
        System.out.println();
        System.out.println("-------------------------------------------");
        for (Course c: courseList
             ) {
            System.out.printf("%-30s" ,c.getCourseId());
            System.out.printf("%-30s" ,c.getCourseName());
            System.out.println();
        }
        System.out.println("Enter course ID: ");

    }
    // Input Validation
    // Is valid student
    public boolean isValidStudent(ArrayList<Student> studentList, String sID){
        for (Student s: studentList
             ) {
            if (s.getStudentId().equals(sID)){
                return true;
            }
        }
        return false;
    }
    // Is valid course.
    public boolean isValidCourse(ArrayList<Course> courseList, String courseID){
        for (Course c : courseList){
            if (c.getCourseId().equals(courseID)){
                return true;
            }
        }
        return false;
    }
    // Semester Validation
    public boolean isValidSem(String sem){
        String regex = "202[0-4][A-C]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sem);
        if (matcher.find()){
            return true;
        }
        return false;
    }
    public boolean isEnrol(Student student, Course course){
        for (StudentEnrolment se: studentEnrolmentList
             ) {
            if ((se.getStudent().getStudentId().equals(student.getStudentId())) && se.getCourse().getCourseId().equals(course.getCourseId())){
                return false;
            }
        }
        return true;
    }

    // Get Student and Course Object base on ID.
    public Student assignStudent(String sID){
        Student newStudent = null;
        for (Student s: studentList
             ) {
            if (sID.equals(s.getStudentId())){
                newStudent = s;
                break;
            }
        }
        return newStudent;
    }
    public Course assignCourse(String cID){
        Course newCourse = null;
        for (Course c: courseList
             ) {
            if (cID.equals(c.getCourseId())){
                newCourse = c;
                break;
            }
        }
        return newCourse;
    }

    // CRUD method
    @Override
    public void add() {
        Student s = null;
        Course c = null;
        String input;
        do {
            displayStudent();
            System.out.println("Enter student Id to enrol:");
            input = scanner.next();
            if (!isValidStudent(studentList,input)){
                System.out.println("Invalid Student");
            }
        }while (!isValidStudent(studentList,input));
        System.out.println("This student is Valid!");
        s = assignStudent(input);
        System.out.println("Display "+ assignStudent(input).getStudentName() + " Enrolment:" );
        for (StudentEnrolment stE:studentEnrolmentList
        ) {
            if (input.equals(stE.getStudent().getStudentId())){
                System.out.println("* "+stE.getCourse().getCourseName() + " with course id: " + stE.getCourse().getCourseId());
            }
        }
        do {
            do {
                displayCourse();
                input = scanner.next();
                if (!isValidCourse(courseList,input)){
                    System.out.println("No course with " + input +" ID!");
                }
            }while (!isValidCourse(courseList, input));
            //System.out.println("This Course is Valid!");
            c = assignCourse(input);
            if (!isEnrol(s,c)){
                System.out.println(s.getStudentName() + " has already enrolled to " + c.getCourseName());
            }
        }while (!(isEnrol(s,c)));
        System.out.println(s.getStudentName() + " can enrol to " + c.getCourseName());
        do {
            System.out.println("Enter Semester:");
            input = scanner.next();
            if (!isValidSem(input)){
                System.out.println("Invalid Semester!");
            }
        }while (!isValidSem(input));
        StudentEnrolment se = new StudentEnrolment(s,c,input);
        studentEnrolmentList.add(se);
        System.out.println("Enrol Success!");
        System.out.println(s.getStudentId()+", " + s.getStudentName() + ", " + c.getCourseId() + ", " +c.getCourseName() +", " + se.getSemester());
    }

    // Get student ID
    // Ask to add or delete.
    @Override
    public void update() {
        String sID = null;
        String course = null;
        int option = 0;
        Student s = null;
        Course c = null;
        do {
            displayStudent();
            System.out.println("Enter student Id to enrol:");
            sID = scanner.next();
            if (!isValidStudent(studentList,sID)){
                System.out.println("Invalid Student");
            }
        }while (!isValidStudent(studentList,sID));
        System.out.println("This student is Valid!");
        System.out.println("Display "+ assignStudent(sID).getStudentName() + " courses:");
        System.out.printf("%-20s", "CID");
        System.out.printf("%-40s", "Course name");
        System.out.printf("%-20s", "Credits");
        System.out.printf("%-20s", "Semester");
        System.out.println();
        for (StudentEnrolment se:studentEnrolmentList
        ) {
            if (sID.equals(se.getStudent().getStudentId())){
                System.out.printf("%-20s", se.getCourse().getCourseId());
                System.out.printf("%-40s", se.getCourse().getCourseName());
                System.out.printf("%-20s", se.getCourse().getCredits());
                System.out.printf("%-20s", se.getSemester());
                System.out.println();
            }
        }
        s = assignStudent(sID);
        do {
            System.out.println("[1] Add");
            System.out.println("[2] Delete");
            option = getOption();
            switch (option){
                case 1:
                    do {
                        do {
                            displayCourse();
                            course = scanner.next();
                            if (!isValidCourse(courseList,course)){
                                System.out.println("No course with " + course +" ID!");
                            }
                        }while (!isValidCourse(courseList, course));
                        //System.out.println("This Course is Valid!");
                        c = assignCourse(course);
                        if (!isEnrol(s,c)){
                            System.out.println(s.getStudentName() + " has already enrolled to " + c.getCourseName());
                        }
                    }while (!(isEnrol(s,c)));
                    System.out.println(s.getStudentName() + " can enrol to " + c.getCourseName());
                    String sem = null;
                    do {
                        System.out.println("Enter Semester:");
                        sem = scanner.next();
                        if (!isValidSem(sem)){
                            System.out.println("Invalid Semester!");
                        }
                    }while (!isValidSem(sem));
                    StudentEnrolment se = new StudentEnrolment(s,c,sem);
                    studentEnrolmentList.add(se);
                    System.out.println("Enrol Success!");
                    System.out.println(s.getStudentId()+", " + s.getStudentName() + ", " + c.getCourseId() + ", " +c.getCourseName() +", " + se.getSemester());
                    option = 0;
                    break;
                case 2:
                    ArrayList<StudentEnrolment> enrolments = new ArrayList<>();
                    ArrayList<Course> validCourse = new ArrayList<>();
                    for (StudentEnrolment stE:studentEnrolmentList
                    ) {
                        if (sID.equals(stE.getStudent().getStudentId())){
                            enrolments.add(stE);
                            validCourse.add(stE.getCourse());
                        }
                    }
                    do {
                        //System.out.println("Enter Course ID:");
                        course = scanner.nextLine();
                    }while (!isValidCourse(validCourse,course));
                    c = assignCourse(course);
                    StudentEnrolment oneEnrolment = null;
                    boolean isFound = false;
                    for (StudentEnrolment stE: studentEnrolmentList
                    ) {
                        isFound = stE.getCourse().getCourseId().equalsIgnoreCase(course) && stE.getStudent().getStudentId().equalsIgnoreCase(sID);
                        if (isFound) {
                            oneEnrolment = stE;
                        }
                    }
                    studentEnrolmentList.remove(oneEnrolment);
                    System.out.println(oneEnrolment.getStudent().getStudentName() + " has been removed from " + oneEnrolment.getCourse().getCourseName() + " in" + oneEnrolment.getSemester());
                    break;
            }
        }while(option!=0);

    }
    // Get student ID and Course ID to delete the enrolment.
    @Override
    public void delete() {
        Student s = null;
        Course c = null;
        String studentID;
        String courseID;
        displayStudent();
        ArrayList<StudentEnrolment> enrolments = new ArrayList<>();
        ArrayList<Course> validCourse = new ArrayList<>();
        //Ask student ID and display
        do {
            System.out.println("Enter student ID:");
            studentID = scanner.nextLine();
        }while (!isValidStudent(studentList,studentID));
        // Display that student's enrolment
        System.out.println("Display "+ assignStudent(studentID).getStudentName() + " Enrolment:" );
        s = assignStudent(studentID);
        for (StudentEnrolment stE:studentEnrolmentList
        ) {
            if (studentID.equals(stE.getStudent().getStudentId())){
                enrolments.add(stE);
                validCourse.add(stE.getCourse());
                System.out.println("* "+stE.getCourse().getCourseName() + " with course id: " + stE.getCourse().getCourseId());
            }
        }
        do {
            System.out.println("Enter Course ID of that student:");
            courseID = scanner.nextLine();
            if (!isValidCourse(validCourse,courseID)){
                System.out.println("Invalid");
            }
        }while (!isValidCourse(validCourse,courseID));
        c = assignCourse(courseID);
        System.out.println(s);
        System.out.println(c);
        StudentEnrolment oneEnrolment = null;
        boolean isFound = false;
        for (StudentEnrolment se: studentEnrolmentList
             ) {
            isFound = se.getCourse().getCourseId().equalsIgnoreCase(courseID) && se.getStudent().getStudentId().equalsIgnoreCase(studentID);
            if (isFound) {
                oneEnrolment = se;
            }
        }
        studentEnrolmentList.remove(oneEnrolment);
        System.out.println(oneEnrolment.getStudent().getStudentName() + " has been removed from " + oneEnrolment.getCourse().getCourseName() + " in" + oneEnrolment.getSemester());
    }

    // Get student ID or Course ID.
    // Display Student info Or Course info.
    public void displayStudentCourses(){
        String sID;
        do {
            displayStudent();
            System.out.println("Enter student ID:");
            sID = scanner.nextLine();
            if (!isValidStudent(studentList,sID)){
                System.out.println("There is no student with "+sID +" id");
            }
        }while (!isValidStudent(studentList,sID));
        System.out.println("Valid Student");
        System.out.println("Display "+ assignStudent(sID).getStudentName() + " courses:");
        System.out.printf("%-20s", "CID");
        System.out.printf("%-40s", "Course name");
        System.out.printf("%-20s", "Credits");
        System.out.printf("%-20s", "Semester");
        System.out.println();
        for (StudentEnrolment se: studentEnrolmentList
             ) {
            if (se.getStudent().getStudentId().equals(sID)){
                System.out.printf("%-20s", se.getCourse().getCourseId());
                System.out.printf("%-40s", se.getCourse().getCourseName());
                System.out.printf("%-20s", se.getCourse().getCredits());
                System.out.printf("%-20s", se.getSemester());
                System.out.println();
            }
        }
    }
    public void displayCourseStudents(){
        String cID;
        do {
            displayCourse();
            cID = scanner.nextLine();
            if (!isValidStudent(studentList,cID)){
                System.out.println("There is no student with "+cID +" id");
            }
        }while (!isValidCourse(courseList,cID));
        System.out.println("Valid Student");
        System.out.println("Display student in "+ assignCourse(cID).getCourseName() + " course:");
        System.out.printf("%-20s", "SID");
        System.out.printf("%-30s", "Student name");
        System.out.printf("%-20s", "Date of Birth:");
        System.out.println();
        for (StudentEnrolment se: studentEnrolmentList
        ) {
            if (se.getCourse().getCourseId().equals(cID)){
                System.out.printf("%-20s", se.getStudent().getStudentId());
                System.out.printf("%-30s", se.getStudent().getStudentName());
                System.out.printf("%-20s", se.getStudent().getBirthDate());
                System.out.println();
            }
        }
    }
    public void displayCoursesSem(){
        String sem;
        do {
            System.out.println("Enter a semester: ");
            sem = scanner.nextLine();
            if (!isValidSem(sem)){
                System.out.println("Invalid Sem");
            }
        }while (!isValidSem(sem));
        System.out.println("Display courses in " +sem + ":");
        System.out.printf("%-20s", "CID");
        System.out.printf("%-35s", "Course name");
        System.out.printf("%-20s", "Credits");
        System.out.println();
        ArrayList<Course> semCourseList = new ArrayList<>();
        boolean isAdded = false;
        for (StudentEnrolment se : studentEnrolmentList
             ) {
            if (sem.equals(se.getSemester())){
                for (Course c: semCourseList
                     ) {
                    if (c.getCourseId().equals(se.getCourse().getCourseId())){
                        isAdded = true;
                        break;
                    }
                }
                if (!isAdded) {
                    semCourseList.add(se.getCourse());
                }else {
                    isAdded = false;
                }
            }
        }
        for (Course c: semCourseList
             ) {
            System.out.printf("%-20s", c.getCourseId());
            System.out.printf("%-35s", c.getCourseName());
            System.out.printf("%-20s", c.getCredits());
            System.out.println();
        }
    }
    @Override
    public void getOne() {
        int option;
        do {
            System.out.println("[1] Print student's courses");
            System.out.println("[2] Print students in a course");
            System.out.println("[3] Print courses in semester");
            option = getOption();
            switch (option){
                case 1:
                    displayStudentCourses();
                    break;
                case 2:
                    displayCourseStudents();
                    break;
                case 3:
                    displayCoursesSem();
                    break;
            }
        }while(option!=0);

    }
    // Display all Enrolment list.
    public static void displayAll(){
        System.out.println("Display enrolments: ");
        System.out.printf("%-20s", "SID");
        System.out.printf("%-30s", "Student Name");
        System.out.printf("%-20s", "Date of birth");
        System.out.printf("%-20s", "CID");
        System.out.printf("%-40s", "Course name");
        System.out.printf("%-20s", "Credits");
        System.out.printf("%-20s", "Semester");
        System.out.println();

    }
    @Override
    public void getAll() {
        displayAll();
        for (StudentEnrolment se: studentEnrolmentList
             ) {
                System.out.printf("%-20s", se.getStudent().getStudentId());
                System.out.printf("%-30s", se.getStudent().getStudentName());
                System.out.printf("%-20s", se.getStudent().getBirthDate());
                System.out.printf("%-20s", se.getCourse().getCourseId());
                System.out.printf("%-40s", se.getCourse().getCourseName());
                System.out.printf("%-20s", se.getCourse().getCredits());
                System.out.printf("%-20s", se.getSemester());
                System.out.println();
        }

    }

    //TODO:
    // 1. Save file.
    // 2. Check if file is saved before exit.
    // 3. Ask to save if it have not.

    public static void main(String[] args) throws IOException {
        EnrolmentSystem enrolmentSystem = new EnrolmentSystem();
        String fileName = readCsv();
        int option;
        do {
            menu();
            option = getOption();
            switch (option){
                case 1 :
                    enrolmentSystem.add();
                    break;
                case 2 :
                    enrolmentSystem.update();break;
                case 3 : enrolmentSystem.delete();break;
                case 4 : enrolmentSystem.getOne();break;
                case 5 : enrolmentSystem.getAll();break;
            }
        }while (option!=0);
    }

}
