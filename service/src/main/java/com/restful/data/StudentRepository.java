package com.restful.data;

import com.restful.model.Student;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, UUID> {
    List<Student> findAll(Example<Student> student);
}
