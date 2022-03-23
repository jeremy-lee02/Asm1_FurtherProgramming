
public class Student {
    private String studentId;
    private String studentName;
    private String birthDate;

    public Student() {

    }

    public Student(String studentId, String studentName, String birthDate) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.birthDate = birthDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }
}
