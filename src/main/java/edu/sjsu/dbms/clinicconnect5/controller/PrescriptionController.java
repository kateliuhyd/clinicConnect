package edu.sjsu.dbms.clinicconnect5.controller;

import edu.sjsu.dbms.clinicconnect5.dao.AppointmentDAO;
import edu.sjsu.dbms.clinicconnect5.dao.PrescriptionDAO;
import edu.sjsu.dbms.clinicconnect5.model.AppointmentDetails;
import edu.sjsu.dbms.clinicconnect5.model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PrescriptionController {

    private final PrescriptionDAO prescriptionDao;

    public PrescriptionController(PrescriptionDAO prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    @GetMapping("/getPrescriptions")
    public ResponseEntity<List<Prescription>> getPrescriptions(@RequestParam("patientId") String patientId) {
        List<Prescription> medicines = prescriptionDao.getPrescriptions(patientId);
        return ResponseEntity.ok(medicines);
    }
}
