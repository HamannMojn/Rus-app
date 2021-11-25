package dk.au.mad21fall.projekt.rus_app;

public class Tutor {

    private String FirstName;
    private String LastName;
    private String TutorName;

    public Tutor() {}

    public Tutor(String firstName, String lastName, String tutorName) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.TutorName = tutorName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getTutorName() {
        return TutorName;
    }

    public void setTutorName(String tutorName) {
        TutorName = tutorName;
    }
}
