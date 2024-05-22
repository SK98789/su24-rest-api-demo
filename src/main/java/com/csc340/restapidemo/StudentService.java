package com.csc340.restapidemo;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class StudentService {
    //File object for the json file
    File studentFile = new File("src/main/resources/StudentInformation.json");

    //Mapper from the Jackson Library
    ObjectMapper objectMapper = new ObjectMapper();

    public Object getAllStudents() {
        try {
            return objectMapper.readValue(studentFile, new TypeReference<List<Student>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Student getStudentByID(int id){
        try {
            //Read in the list of all students from the file
            List<Student> studentList = objectMapper.readValue(studentFile, new TypeReference<List<Student>>(){});

            //Loop through the list of Student objects until the Student with the correct ID is found
            for(Student s: studentList){
                if(s.getId() == id){
                    return s;
                }
            }
            //If no such student exists, return null
            return null;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object createStudent(Student student){
        try {
            //Read in current file content as a list of Student objects
            List<Student> studentList = objectMapper.readValue(studentFile, new TypeReference<List<Student>>(){});

            //Add the new Student object to the list
            studentList.add(student);

            //Write all Student objects back to the file
            objectMapper.writeValue(studentFile, studentList);

            //return a list of the Student objects
            return studentList;



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Object editStudent(int id, Student student){
        try {
            //Read in current file content as a list of Student objects
            List<Student> studentList = objectMapper.readValue(studentFile, new TypeReference<List<Student>>(){});

            //Loop through the Student list
            for(int i = 0; i<studentList.size(); i++){
                //if the Student object at index i has the correct ID, replace it with the
                //new Student object, write all students back to the file, and return all students.
                if(studentList.get(i).getId() == id){
                    studentList.set(i, student);
                    //Write all Student objects back to the file
                    objectMapper.writeValue(studentFile, studentList);
                    return (studentList);
                }
            }
            //If the student with the specified ID is not in the list, append the new student
            //to the list, write all students to the file, and return all students.
            studentList.add(student);
            objectMapper.writeValue(studentFile, studentList);
            return (studentList);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public Object deleteStudent(int id){

        try {
            //Read in current file content as a list of Student objects
            List<Student> studentList = objectMapper.readValue(studentFile, new TypeReference<List<Student>>(){});

            for(int i = 0; i<studentList.size(); i++){
                //if the Student object at index i has the correct ID, remove it from the
                //list, write all students back to the file, and return all students.
                if(studentList.get(i).getId() == id){
                    studentList.remove(i);
                    //Write all Student objects back to the file
                    objectMapper.writeValue(studentFile, studentList);
                    return (studentList);
                }
            }
            //If student is not found, do not write to the file and return list
            return (studentList);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
