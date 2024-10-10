import interfaces.Operable;

public class Lamp implements Operable {
    private String name;
    private boolean status;

    public Lamp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Lamp{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public void turnOn() {
        this.status = true;
    }

    @Override
    public void turnOff() {
        this.status = false;
    }
}