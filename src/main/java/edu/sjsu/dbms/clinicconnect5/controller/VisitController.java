package edu.sjsu.dbms.clinicconnect5.controller;


import edu.sjsu.dbms.clinicconnect5.dao.MedicalRecordDAO;
import edu.sjsu.dbms.clinicconnect5.dao.VisitSummaryDAO;
import edu.sjsu.dbms.clinicconnect5.model.AppointmentDetails;
import edu.sjsu.dbms.clinicconnect5.model.MedicalRecord;
import edu.sjsu.dbms.clinicconnect5.model.Prescription;
import edu.sjsu.dbms.clinicconnect5.model.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VisitController {

    private final VisitSummaryDAO visitDao;
    @Autowired
    private MedicalRecordDAO recordDao;

    public VisitController(VisitSummaryDAO visitSummaryDao) {
        this.visitDao = visitSummaryDao;
    }

    @GetMapping("/getVisitSummary")
    public ResponseEntity<List<Visit>> getVisitSummary(@RequestParam("patientId") String patientId) {
        List<Visit> visits = visitDao.summaryOfVisits(patientId);
        return ResponseEntity.ok(visits);
    }

    /**
     * Fetch this patient’s medical_record row
     */

    @GetMapping("/medical-record")
    public ResponseEntity<MedicalRecord> getMedicalRecord(@RequestParam String patientId) {

        return recordDao.findByPatientId(patientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
