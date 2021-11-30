package dk.au.mad21fall.projekt.rus_app.Models;

public class Tutor {

    private String FirstName;
    private String LastName;
    private String TutorName;
    private String Email;

    public Tutor() {}

    public Tutor(String firstName, String lastName, String tutorName, String email) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.TutorName = tutorName;
        this.Email = email;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
