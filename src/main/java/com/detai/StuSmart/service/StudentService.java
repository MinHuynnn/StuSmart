package com.detai.StuSmart.service;

import com.detai.StuSmart.model.Student;
import com.detai.StuSmart.repository.StudentRepository;
import com.detai.StuSmart.repository.TeacherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;


    public Student authentication(String username, String password)
    {
        // viet theo bieu thuc lambda
//        return studentRepository.findByUsername(username)
//                .filter(student -> student.getPassword().equals(password))
//                .orElse(null);
// optional có giá trị dung tra ve va giu lai object trong optional. neu sai tra ve empty()
        Optional<Student> optional = studentRepository.findByUsername(username);
        if (optional.isPresent()) {
            Student student = optional.get();
            if (student.getPassword().equals(password)) {
                // in ra json
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(student);
                    System.out.println("✅ Đăng nhập thành công đây là student json: " + json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // ket thuc json
                return student;
            }
        }
        // in ra json
            System.out.println("✅ xin lỗi không có ai tên: " + username);
        // ket thuc json
        return null;
    }

    // các hàm xử lý API (ví dụ: register, update...)

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getById(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Student update(Integer id, Student updatedStudent) {
        Student existing = studentRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setFirstname(updatedStudent.getFirstname());
            existing.setLastname(updatedStudent.getLastname());
            existing.setPhone(updatedStudent.getPhone());
            existing.setAddress(updatedStudent.getAddress());
            existing.setIdcard(updatedStudent.getIdcard());
            existing.setUsername(updatedStudent.getUsername());
            existing.setPassword(updatedStudent.getPassword());
            return studentRepository.save(existing);
        }
        return null;
    }

    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }
}
