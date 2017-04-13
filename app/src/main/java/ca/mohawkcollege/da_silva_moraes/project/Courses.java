package ca.mohawkcollege.da_silva_moraes.project;

/**
 * Created by da-silva-moraes on 11-Apr-17.
 */

public class Courses {
    private Integer _id;
    private Integer program;
    private Integer semesterNum;
    private String courseCode;
    private String courseTitle;
    private String courseDescription;
    private String courseOwner;
    private Integer optional;
    private Integer hours;

    @Override
    public String toString(){
        return courseTitle;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer getProgram() {
        return program;
    }

    public void setProgram(Integer program) {
        this.program = program;
    }

    public Integer getSemesterNum() {
        return semesterNum;
    }

    public void setSemesterNum(Integer semesterNum) {
        this.semesterNum = semesterNum;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseOwner() {
        return courseOwner;
    }

    public void setCourseOwner(String courseOwner) {
        this.courseOwner = courseOwner;
    }

    public Integer getOptional() {
        return optional;
    }

    public void setOptional(Integer optional) {
        this.optional = optional;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }
}
