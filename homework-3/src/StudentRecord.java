import java.util.HashMap;
import java.util.Map;

public class StudentRecord {
    Map<String, Integer> studentRecord = new HashMap<>();

    public void addStudent(String studentName, int studentScore) {
        studentRecord.put(studentName, studentScore);
    }

    public Integer removeStudent(String studentName) {
        return studentRecord.remove(studentName);
    }

    public int getGrade(String studentName) {
        return studentRecord.get(studentName);
    }

    public boolean isStudentInRecord(String studentName) {
        return studentRecord.containsKey(studentName);
    }

    public Map<String, Integer> getStudentRecord() {
        return studentRecord;
    }
}