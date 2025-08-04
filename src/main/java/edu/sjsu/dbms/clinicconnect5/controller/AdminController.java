package edu.sjsu.dbms.clinicconnect5.controller;

import edu.sjsu.dbms.clinicconnect5.model.Department;
import edu.sjsu.dbms.clinicconnect5.model.Doctor;
import edu.sjsu.dbms.clinicconnect5.model.Patient;
import edu.sjsu.dbms.clinicconnect5.model.Specialization;
import edu.sjsu.dbms.clinicconnect5.dao.DepartmentDAO;
import edu.sjsu.dbms.clinicconnect5.dao.DoctorDAO;
import edu.sjsu.dbms.clinicconnect5.dao.PatientDAO;
import edu.sjsu.dbms.clinicconnect5.dao.SpecializationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminController {

    @Autowired private PatientDAO patientDao;
    @Autowired private DoctorDAO doctorDao;
    @Autowired private DepartmentDAO departmentDao;
    @Autowired private SpecializationDAO specializationDao;

    // --- Patient CRUD ---
    @GetMapping("/patients")
    public List<Patient> listPatients() {
        return patientDao.findAll();
    }

    @PostMapping("/patients")
    public ResponseEntity<Void> addPatient(@RequestBody Patient p) {
        return patientDao.addPatient(p) == 1
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        return patientDao.deletePatient(id) == 1
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    // --- Doctor CRUD ---
    @GetMapping("/doctors")
    public List<Doctor> listDoctors() {
        return doctorDao.findAllDoctors();
    }

    @PostMapping("/doctors")
    public ResponseEntity<Void> addDoctor(@RequestBody Doctor d) {
        return doctorDao.addDoctor(d) == 1
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable String id) {
        return doctorDao.deleteDoctor(id) == 1
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    // --- Department CRUD ---
    @GetMapping("/departments")
    public List<Department> listDepartments() {
        return departmentDao.findAll();
    }

    @PostMapping("/departments")
    public ResponseEntity<Void> addDepartment(@RequestBody Department d) {
        return departmentDao.add(d) == 1
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.badRequest().build();
    }

    // --- Specialization CRUD ---
    @GetMapping("/specializations")
    public List<Specialization> listSpecializations() {
        return specializationDao.findAll();
    }

    @PostMapping("/specializations")
    public ResponseEntity<Void> addSpecialization(@RequestBody Specialization s) {
        return specializationDao.add(s) == 1
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.badRequest().build();
    }
}
