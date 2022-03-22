
public class Course {
    private String courseId;
    private String courseName;
    private String credits;
  ;

    public Course() {

    }

    public Course(String courseId, String courseName, String credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCredits() {
        return credits;
    }


    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credits=" + credits +
                '}';
    }
}
