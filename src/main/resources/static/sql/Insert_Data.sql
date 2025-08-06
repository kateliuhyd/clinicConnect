INSERT INTO ClinicDB.Address (addr_id,address_line,city,state,zip_code,country) VALUES
    ('addr1','123 Heartbeat Ave','Campbell','CA','95008','USA'),
    ('addr2','456 Atrium Way','San Jose','CA','95112','USA'),
    ('addr3','676 Skin St','Cupertino','CA','95014','USA'),
    ('addr4','667 Hearing Health Ct','Santa Clara','CA','95014', 'USA'),
    ('addr5','200 Elm St','San Jose','CA','95112','USA'),
    ('addr6','201 Elm St','Sunnyvale','CA','94087','USA'),
    ('addr7','900 Maple St','San Jose','CA','95112','USA'),
    ('addr8','901 Maple St','Saratoga','CA','95071','USA')
;

INSERT INTO ClinicDB.User (user_id,password,user_type, addr_id) VALUES
	 ('doc101','pass001','doctor','addr1'),
	 ('doc102','pass002','doctor','addr2'),
	 ('doc103','pass003','doctor','addr3'),
	 ('doc104','pass004','doctor','addr4'),
	 ('pat001','pw001','patient','addr5'),
	 ('pat002','pw002','patient','addr6');

INSERT INTO ClinicDB.Department (dept_id,dept_name,dept_desc,numDoctors) VALUES
	 (1,'Cardiology','Heart care and blood circulation',5),
	 (2,'Neurology','Brain and nervous system',4),
	 (3,'Pediatrics','Children’s health',6),
	 (4,'Orthopedics','Muscle and bone treatments',3),
	 (5,'Dermatology','Skin and hair care',2),
	 (6,'Oncology','Cancer diagnostics',4),
	 (7,'Radiology','Medical imaging',3),
	 (8,'Urology','Urinary system specialists',2),
	 (9,'ENT','Ear, Nose & Throat',3),
	 (10,'General Medicine','Broad healthcare services',6);

INSERT INTO Specialization (specialization_id, specialization_name) VALUES
    ('s1', 'Family Practice'),
    ('s2', 'Electrophysiology'),
    ('s3', 'Cardiology'),
    ('s4', 'Audiology');

INSERT INTO ClinicDB.Doctor (doc_id,first_name,last_name,dob, dept_id,specialization_id) VALUES
	 ('doc101','John','Carter','1975-04-14',1,'s1'),
	 ('doc102','Susan','Lewis','1978-11-05',1,'s2'),
	 ('doc103','Neela','Rasgotra','1984-02-11',5,'s3'),
	 ('doc104','Doug','Ross','1970-11-30',9,'s4');

INSERT INTO ClinicDB.Patient (patient_id,first_name,last_name,dob,medical_insurance,preferred_pharmacy) VALUES
	 ('pat001','John','Doe','1990-06-15','Blue Shield','CVS'),
	 ('pat002','Emily','Rogers','1989-07-20','United Health','Walgreens');

INSERT INTO ClinicDB.Appointment (appt_id,`date`,patient_id,doc_id,duration,appt_summary) VALUES
	 ('14986def-7877-4aa0-b','2025-07-26 08:00:00','pat001','doc102',NULL,NULL),
	 ('34cb068a-755b-4cb1-8','2025-07-26 09:00:00','pat001','doc101',NULL,NULL),
	 ('3b343763-e80d-4d46-9','2025-07-26 10:00:00','pat002','doc101',NULL,NULL),
	 ('3f46e14a-ff79-4762-8','2025-07-29 13:00:00','pat002','doc102',NULL,NULL),
	 ('4fab6e6b-515f-482d-8','2025-08-02 14:00:00','pat001','doc101',NULL,NULL),
	 ('d0bc7019-87a2-465e-8','2025-07-28 10:00:00','pat002','doc102',NULL,NULL);

ALTER TABLE ClinicDB.Appointment
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'PENDING';

UPDATE ClinicDB.Appointment
SET status = 'PENDING';

INSERT INTO Prescription (medicine_name, prescription_date, pid, did) VALUES
    ('Amoxicillin 500mg', '2025-01-15', 'pat001', 'doc101'),
    ('Ibuprofen 200mg', '2025-06-16', 'pat002', 'doc102'),
    ('Atorvastatin 10mg', '2025-03-17', 'pat001', 'doc103'),
    ('Metformin 500mg', '2025-07-18', 'pat002', 'doc104'),
    ('Lisinopril 10mg', '2025-06-19', 'pat001', 'doc102'),
    ('Amlodipine 5mg', '2025-07-20', 'pat002', 'doc101');

/*
INSERT INTO ClinicDB.Admin (admin_id,dept_id,first_name,last_name,dob) VALUES
	 ('admin001',1,'Sarah','Newton','1970-01-01'),
	 ('admin002',2,'Jason','Lee','1980-10-20');
 */
