package hello;

public class User {
    enum Status { ACCEPTED, REJECTED, INPROGRESS }

    private long id;
    private String name;
    private String email;
    private Status status;

    public User() {
        // No-args constructor
    }

    public User(long id, String name, String email, Status status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
