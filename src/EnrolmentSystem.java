
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;



public abstract class EnrolmentSystem implements StudentEnrolmentManager {
    public static ArrayList<Student> studentList = new ArrayList<Student>();
    public static ArrayList<StudentEnrolment> studentEnrolmentList = new ArrayList<>();




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
    public static int getOption(){
        int option;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Choice: ");
        option = scanner.nextInt();
        return option;
    }

    // Read csv file
    public static void readCsv(String filename){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line = "";
            System.out.printf("%-50s", "SID");
            System.out.printf("%-50s", "Student Name");
            System.out.printf("%-50s", "Date of birth");
            System.out.printf("%-50s", "CID");
            System.out.printf("%-50s", "Course name");
            System.out.printf("%-50s", "Credits");
            System.out.printf("%-50s", "Semester");
            System.out.println();
            while ((line = bufferedReader.readLine()) != null){
                String [] row = line.split(",");
                for (int i = 0; i < row.length; i++){
                    System.out.printf("%-50s", row[i]);
                }
                System.out.println();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        StudentEnrolmentManager system = new StudentEnrolmentManager()  {
            @Override
            public void add() {
                System.out.println("1");
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
        };
        int option;
        do {
            menu();
            option = getOption();
            switch (option){
                case 1 : system.add();break;
                case 2 : system.update();break;
                case 3 : system.delete();break;
                case 4 : system.getOne();break;
                case 5 : readCsv("src\\default.csv");break;
                case 0: break;
            }
        }while (option!=0);

    }

}
