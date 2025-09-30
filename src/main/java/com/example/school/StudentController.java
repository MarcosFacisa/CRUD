package com.example.school;

import com.example.school.model.Student;
import com.example.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.example.school.dto.StudentDTO;
import com.example.school.repository.CourseRepository;
import com.example.school.model.Course;
import java.util.HashSet;

// Controlador REST responsável pelo CRUD de estudantes
@RestController
@RequestMapping("/students")
public class StudentController {


    // Injeta o repositório de estudantes
    @Autowired
    private StudentRepository studentRepository;

    // Injeta o repositório de cursos
    @Autowired
    private CourseRepository courseRepository;


    // Retorna todos os estudantes cadastrados
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    // Busca um estudante pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    // Cria um novo estudante a partir de um DTO (permite informar endereço e cursos)
    @PostMapping
    public Student createStudent(@RequestBody StudentDTO dto) {
        Student student = new Student();
        // Define os dados básicos
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        // Se o endereço foi informado, cria e associa
        if (dto.getAddress() != null) {
            com.example.school.model.Address addr = new com.example.school.model.Address();
            addr.setStreet(dto.getAddress().getStreet());
            addr.setCity(dto.getAddress().getCity());
            addr.setState(dto.getAddress().getState());
            addr.setZipCode(dto.getAddress().getZipCode());
            student.setAddress(addr);
        }
        // Se IDs de cursos foram informados, busca e associa
        if (dto.getCourses() != null && !dto.getCourses().isEmpty()) {
            HashSet<Course> courses = new HashSet<Course>();
            for (Long courseId : dto.getCourses()) {
                Course course = courseRepository.findById(courseId).orElse(null);
                if (course != null) {
                    courses.add(course);
                }
            }
            student.setCourses(courses);
        }
        // Salva o estudante no banco
        return studentRepository.save(student);
    }


    // Atualiza todos os dados de um estudante existente
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Student student = optionalStudent.get();
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setAddress(studentDetails.getAddress());
        return ResponseEntity.ok(studentRepository.save(student));
    }


    // Atualiza parcialmente os dados de um estudante (apenas campos enviados)
    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Student student = optionalStudent.get();
        if (studentDetails.getFirstName() != null) student.setFirstName(studentDetails.getFirstName());
        if (studentDetails.getLastName() != null) student.setLastName(studentDetails.getLastName());
        if (studentDetails.getEmail() != null) student.setEmail(studentDetails.getEmail());
        if (studentDetails.getAddress() != null) student.setAddress(studentDetails.getAddress());
        return ResponseEntity.ok(studentRepository.save(student));
    }

    // Remove um estudante pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
