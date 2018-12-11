package ec.edu.utpl.sistemas.api.rest.service;

import com.google.gson.Gson;
import ec.edu.utpl.sistemas.api.rest.model.ListStudent;
import ec.edu.utpl.sistemas.api.rest.model.Student;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class ServerApiWeb {
    public static void main(String[] args) {
        Gson gson = new Gson();
        ListStudent listStudent = new ListStudent();

        options("/*",
                (req, res) -> {
                    String accessControlRequestHeaders = req
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        res.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = req
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        res.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((req, res) -> {
            /*if (!req.headers("Accept").equalsIgnoreCase("application/json")) {
                halt(406);
            }*/
            res.header("Access-Control-Allow-Origin", "*");
        });

        after((req, res) -> res.header("Content-Type", "application/json"));

        get("/students", (req, res) -> gson.toJson(listStudent.getStudents()));

        get("/student/:pos", (req, res) -> {
            int pos;

            pos = NumberUtils.toInt(req.params(":pos"), -1);
            if (pos != -1) {
                if (pos >= 0 && pos < listStudent.getStudents().size()) {
                    return gson.toJson(listStudent.getStudents().get(pos));
                } else {
                    halt(404);
                }
            } else {
                halt(400);
            }


            return null;
        });

        get("/students/filter", (req, res) -> {
            if(req.queryMap().hasKey("name")) {
                return gson.toJson(listStudent.getStudents().
                        stream().
                        filter(s -> s.getName().equalsIgnoreCase(req.queryMap().get("name").value())).
                        collect(Collectors.toList()));
            } else if(req.queryMap().hasKey("lastname")) {
                return gson.toJson(listStudent.getStudents().
                        stream().
                        filter(s -> s.getLastName().equalsIgnoreCase(req.queryMap().get("lastname").value()))
                        .collect(Collectors.toList()));
            } else if(req.queryMap().hasKey("age")) {
                return gson.toJson(listStudent.getStudents().
                        stream().
                        filter(s -> s.getAge() == req.queryMap().get("age").integerValue()).
                        collect(Collectors.toList()));
            }
            halt(400);
            return "";
        });

        post("/student", (req, res) -> {
            int id;
            Student newStudent;

            if(req.body() != null && !req.body().trim().equals("")) {
                newStudent = gson.fromJson(req.body(), Student.class);
                listStudent.getStudents().add(newStudent);
                id = listStudent.getStudents().size() - 1;
                printList(listStudent.getStudents());

                res.status(201);
                return id;
            } else {
                halt(400);
            }
            return "";
        });

        put("/student/:pos", (req, res) -> {
            int pos;
            Student student;

            pos = NumberUtils.toInt(req.params(":pos"), -1);

            if(pos != -1) {
                if(pos >= 0 && pos < listStudent.getStudents().size()) {
                    if(req.body() != null && !req.body().trim().equals("")) {
                        student = gson.fromJson(req.body(), Student.class);
                        listStudent.getStudents().set(pos, student);
                        printList(listStudent.getStudents());
                        res.status(204);
                    } else {
                        halt(400);
                    }
                } else {
                    halt(400);
                }
            } else {
                halt(400);
            }
            return "";
        });

        delete("/student/:pos", (req, res) -> {
            var pos = NumberUtils.toInt(req.params(":pos"), -1);
            if (pos != -1) {
                if (pos >= 0 && pos < listStudent.getStudents().size()) {
                    listStudent.getStudents().remove(pos);
                    printList(listStudent.getStudents());
                    res.status(204);
                } else {
                    halt(400);
                }
            } else {
                halt(400);
            }
            return "";
        });
    }

    private static void printList(List<Student> listStudents) {
        listStudents.forEach(System.out::println);
    }
}
