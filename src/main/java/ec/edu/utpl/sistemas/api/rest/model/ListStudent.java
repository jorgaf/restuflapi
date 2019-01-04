package ec.edu.utpl.sistemas.api.rest.model;

import java.util.ArrayList;
import java.util.List;

public class ListStudent {

    private static List<Student> students = new ArrayList<>();

    static {
        students.add(new Student("Jonnathan", "Armijos", 26));
        students.add(new Student("Patricio", "Lema", 26));
        students.add(new Student("Andrea", "Caza", 23));
        students.add(new Student("Dayana", "Tamayo", 25));
        students.add(new Student("Evelyn", "Olalla", 29));
        students.add(new Student("Juan", "Ochoa", 24));
        students.add(new Student("Israel", "Montaño", 30));
        students.add(new Student("Alexandra", "Espinoza", 27));
        students.add(new Student("Eduardo", "Calderon", 26));
        students.add(new Student("Belén", "Reátegui", 29));
    }

    public List<Student> getStudents() {

        return students;
    }

}
