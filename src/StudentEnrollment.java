package A1_FurtherProgramming;

public class StudentEnrollment {
    private Student student;
    private Course course;
    private String semester;

    public StudentEnrollment() {

    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "StudentEnrollment{" +
                "student=" + student +
                ", course=" + course +
                ", semester='" + semester + '\'' +
                '}';
    }
}



