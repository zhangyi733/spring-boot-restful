package com.restful.rest;

import com.restful.StudentService;
import com.restful.api.StudentDto;
import com.restful.model.PagedResponse;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${resource.path}")
public class Controller {

    private final StudentService service;

    @Autowired
    public Controller(final StudentService service) {
        this.service = service;
    }

    @GetMapping(path = "{id}")
    public StudentDto findStudentById(@PathVariable(name = "id", required = true) final UUID id) throws ExecutionException, InterruptedException {
        //Start a new thread to query data. This is just for testing, there is no need to start a new thread
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Future<StudentDto> future = executor.submit(() -> service.findStudentById(id));
        //future.get is still a block way
        return future.get();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<StudentDto> getStudent(@RequestParam(name = "id", required = false) UUID id,
        @RequestParam(name = "firstName", required = false) String firstName, @RequestParam(name = "lastName", required = false) String lastName,
        @RequestParam(name = "gpa", required = false) Float gpa, @RequestParam(name = "timestamp", required = false) Instant timestamp,
        @RequestParam(required = false) UUID startAfter,
        @PageableDefault(sort = {"timestamp", "seqId"}, direction = Sort.Direction.DESC) Pageable pageable) {
        List<StudentDto> studentDtos;
        PagedResponse<StudentDto> pagedResponse = new PagedResponse<>();
        studentDtos = service.getStudent(id, firstName, lastName, gpa);
        if (pageable.getPageSize() == studentDtos.size()) {
            pagedResponse.setNextId(studentDtos.get(studentDtos.size() - 1).getUuid());
        }
        pagedResponse.setContent(studentDtos);
        return pagedResponse;
    }

    @PostMapping
    public StudentDto addStudent(@RequestBody StudentDto studentDto) {
        return service.addStudent(studentDto);
    }

    @PutMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDto updateStudent(@PathVariable(name = "id") UUID id, @RequestBody StudentDto studentDto) {
        return service.updateStudent(id, studentDto);
    }

}
