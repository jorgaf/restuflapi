package ec.edu.utpl.sistemas.api.rest.model;

import java.util.ArrayList;
import java.util.List;

public class ListStudent {
    private static List<Student> students = new ArrayList<>();

    static {
        students.add(new Student("Rafael", "Correa", 40));
        students.add(new Student("Lenin", "Moreno", 56));
        students.add(new Student("Grabriela", "Rivadeneira", 45));
        students.add(new Student("José", "Castillo", 65));
        students.add(new Student("Jeanine", "Cruz", 28));
    }

    public List<Student> getStudents() {

        return students;
    }

}
