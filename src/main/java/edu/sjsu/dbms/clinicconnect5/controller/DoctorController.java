package edu.sjsu.dbms.clinicconnect5.controller;

import edu.sjsu.dbms.clinicconnect5.dao.DoctorDAO;
import edu.sjsu.dbms.clinicconnect5.model.Doctor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

    @RestController
    @RequestMapping("/api/doctors")
    @CrossOrigin(origins = "*")
    public class DoctorController {

        private  DoctorDAO doctorDAO;

        public DoctorController(DoctorDAO doctorDAO) {
            this.doctorDAO = doctorDAO;
        }

        @GetMapping("/search")
        public List<Doctor> searchDoctor(@RequestParam int deptId) {
            return doctorDAO.searchDoctor(deptId);
        }

        @PostMapping("/add")
        public ResponseEntity<?> addDoctor(@RequestBody Doctor doctor) {
            doctorDAO.addDoctor(doctor);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteDoctor(@PathVariable String id) {
            doctorDAO.deleteDoctor(id);
            return ResponseEntity.noContent().build();
        }

    }
