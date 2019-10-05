package com.restful;

import com.restful.api.StudentDto;
import com.restful.data.StudentRepository;
import com.restful.model.Student;
import java.lang.reflect.Type;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    @Autowired
    public StudentService(final StudentRepository studentRepository, final ModelMapper mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    public StudentDto findStudentById(UUID id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return mapper.map(student.get(), StudentDto.class);
        }
        throw new NoSuchElementException("no such element found in database");
    }

    @Transactional(readOnly = true)
    public List<StudentDto> getStudent(UUID id, String firstName, String lastName, Float gpa) {
        Student student = new Student();
        /*
        By default, fields having null values are ignored, and strings are matched using the store specific defaults.
        Examples can be built by either using the of factory method or by using ExampleMatcher. Example is immutable.
         */
        ExampleMatcher matcher = ExampleMatcher.matching();

        if (firstName != null) {
            student.setFirstName(firstName);
            //Ignore gpa because gpa is defined as float and it's default value is 0.0, not empty
            //模糊查询匹配开头
            matcher = matcher.withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.startsWith());
        }

        if (lastName != null) {
            student.setLastName(lastName);
            matcher = matcher.withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.ignoreCase().endsWith());
        }

        if (gpa != null) {
            student.setGpa(gpa);
            matcher = matcher.withMatcher("gpa", ExampleMatcher.GenericPropertyMatchers.exact());
        } else {
            matcher = matcher.withIgnorePaths("gpa");
        }

        if (id != null) {
            student.setUuid(id);
            matcher = matcher.withIgnorePaths("gpa", "firstName", "lastName"); //即不管gpa是什么值都不加入查询条件
        }

        List<Student> students = studentRepository.findAll(Example.of(student, matcher));
        Type listType = new TypeToken<List<StudentDto>>() {
        }.getType();
        return mapper.map(students, listType);
    }

    public StudentDto addStudent(StudentDto studentDto) {
        Student student = mapper.map(studentDto, Student.class);
        Student savedStudent = studentRepository.save(student);
        return mapper.map(savedStudent, StudentDto.class);
    }

    public StudentDto updateStudent(UUID id, StudentDto studentDto) {
        if (id.equals(studentDto.getUuid())) {
            Student updatedStudent = studentRepository.save(mapper.map(studentDto, Student.class));
            return mapper.map(updatedStudent, StudentDto.class);
        }
        throw new IllegalArgumentException("student id does not match the id in request body");
    }
}
