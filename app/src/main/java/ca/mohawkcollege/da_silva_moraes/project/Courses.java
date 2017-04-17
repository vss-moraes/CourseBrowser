package ca.mohawkcollege.da_silva_moraes.project;

class Courses {
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

    Integer get_id() {
        return _id;
    }

    void set_id(Integer _id) {
        this._id = _id;
    }

    Integer getProgram() {
        return program;
    }

    void setProgram(Integer program) {
        this.program = program;
    }

    Integer getSemesterNum() {
        return semesterNum;
    }

    void setSemesterNum(Integer semesterNum) {
        this.semesterNum = semesterNum;
    }

    String getCourseCode() {
        return courseCode;
    }

    void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    String getCourseTitle() {
        return courseTitle;
    }

    void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    String getCourseDescription() {
        return courseDescription;
    }

    void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    String getCourseOwner() {
        return courseOwner;
    }

    void setCourseOwner(String courseOwner) {
        this.courseOwner = courseOwner;
    }

    Integer getOptional() {
        return optional;
    }

    void setOptional(Integer optional) {
        this.optional = optional;
    }

    Integer getHours() {
        return hours;
    }

    void setHours(Integer hours) {
        this.hours = hours;
    }
}
