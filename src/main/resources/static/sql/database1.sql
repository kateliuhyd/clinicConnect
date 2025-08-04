DROP DATABASE IF EXISTS ClinicDB;
CREATE DATABASE ClinicDB;
USE ClinicDB;

CREATE TABLE Department (
                            dept_id INT PRIMARY KEY,
                            dept_name VARCHAR(20),
                            dept_desc VARCHAR(100),
                            numDoctors INT
);

CREATE TABLE User (
                      user_id VARCHAR(20) PRIMARY KEY,
                      password VARCHAR(50) NOT NULL,
                      user_type VARCHAR(10) NOT NULL
);

CREATE TABLE Doctor (
                        doc_id VARCHAR(20) PRIMARY KEY,
                        first_name VARCHAR(50) NOT NULL,
                        last_name VARCHAR(50) NOT NULL,
                        dob DATE,
                        dept_id INT NOT NULL,
                        specialization_id VARCHAR(20),
                        FOREIGN KEY (specialization_id) REFERENCES Specialization(specialization_id)
                            ON DELETE SET NULL
                            ON UPDATE SET NULL,
                        FOREIGN KEY (dept_id) REFERENCES Department(dept_id)
                            ON UPDATE CASCADE
                            ON DELETE CASCADE,
                        FOREIGN KEY (doc_id) REFERENCES User(user_id)
                            ON UPDATE CASCADE
                            ON DELETE CASCADE
);

CREATE TABLE Specialization (
                                specialization_id VARCHAR(20) PRIMARY KEY,
                                specialization_name VARCHAR(50) NOT NULL
);

CREATE TABLE Patient (
                         patient_id VARCHAR(20) PRIMARY KEY,
                         first_name VARCHAR(50) NOT NULL,
                         last_name VARCHAR(50) NOT NULL,
                         dob DATE,
                         address_line VARCHAR(100),
                         city VARCHAR(50),
                         state VARCHAR(50),
                         zip_code VARCHAR(50),
                         country VARCHAR(50),
                         medical_insurance VARCHAR(50) NOT NULL,
                         preferred_pharmacy VARCHAR(200) NOT NULL,
                         FOREIGN KEY (patient_id) REFERENCES User(user_id)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE
);


CREATE TABLE Admin (
                       admin_id VARCHAR(20) PRIMARY KEY,
                       dept_id INT,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       dob DATE,
                       FOREIGN KEY (dept_id) REFERENCES Department(dept_id)
                           ON DELETE SET NULL
                           ON UPDATE SET NULL,
                       FOREIGN KEY (admin_id) REFERENCES User(user_id)
                           ON UPDATE CASCADE
                           ON DELETE CASCADE
);

CREATE TABLE Prescription (
                              medicine_name VARCHAR(100),
                              prescription_date DATE,
                              pid VARCHAR(20),
                              did VARCHAR(20),
                              FOREIGN KEY (pid) REFERENCES Patient(patient_id)
                                  ON DELETE CASCADE
                                  ON UPDATE CASCADE,
                              FOREIGN KEY (did) REFERENCES Doctor(doc_id)
                                  ON DELETE NO ACTION
                                  ON UPDATE CASCADE
);

CREATE TABLE Medical_Record (
                                pid VARCHAR(20),
                                medicine_list VARCHAR(500),
                                FOREIGN KEY (pid) REFERENCES Patient(patient_id)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE
);

CREATE TABLE Review (
                        pid VARCHAR(20),
                        did VARCHAR(20),
                        rid VARCHAR(20),
                        content VARCHAR(100),
                        rating INT,
                        PRIMARY KEY (rid),
                        FOREIGN KEY (pid) REFERENCES Patient(patient_id)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE,
                        FOREIGN KEY (did) REFERENCES Doctor(doc_id)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE
);

USE ClinicDB;

CREATE TABLE Appointment (
                             appt_id CHAR(36) NOT NULL PRIMARY KEY,
                             `date` DATETIME NOT NULL,
                             patient_id VARCHAR(20) NOT NULL,
                             doc_id VARCHAR(20) NOT NULL,
                             duration INT NULL,
                             appt_summary TEXT NULL,
                             status ENUM('PENDING','CONFIRMED','CANCELLED') NOT NULL DEFAULT 'PENDING',
                             FOREIGN KEY (patient_id)
                                 REFERENCES Patient(patient_id)
                                 ON UPDATE CASCADE
                                 ON DELETE CASCADE,
                             FOREIGN KEY (doc_id)
                                 REFERENCES Doctor(doc_id)
                                 ON UPDATE CASCADE
                                 ON DELETE CASCADE
);


-- 1. Insert into User
INSERT INTO ClinicDB.User (user_id, password, user_type) VALUES
                                                             ('admin001', 'adm001', 'admin'),
                                                             ('admin002', 'adm002', 'admin'),
                                                             ('doc101', 'pass001', 'doctor'),
                                                             ('doc102', 'pass002', 'doctor'),
                                                             ('doc103', 'pass003', 'doctor'),
                                                             ('doc104', 'pass004', 'doctor'),
                                                             ('pat001', 'pw001', 'patient'),
                                                             ('pat002', 'pw002', 'patient');


-- 2. Insert into Patient
INSERT INTO ClinicDB.Patient (patient_id, first_name, last_name, dob, address_line, city, state, zip_code, country, medical_insurance, preferred_pharmacy) VALUES
                                                                                                                                                               ('pat001', 'John', 'Doe', '1990-06-15', '200 Elm St', 'San Jose', 'CA', '95112', 'USA', 'Blue Shield', 'CVS'),
                                                                                                                                                               ('pat002', 'Emily', 'Rogers', '1989-07-20', '201 Elm St', 'Sunnyvale', 'CA', '94087', 'USA', 'United Health', 'Walgreens');


-- 3. Insert into Department
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


-- 4. Insert into Admin
INSERT INTO ClinicDB.Admin
(admin_id, dept_id, first_name, last_name, dob)
VALUES
    ('admin001', 1, 'Alice', 'Wong', '1985-04-12'),
    ('admin002', 2, 'Bob',   'Lee',   '1979-11-30');


-- 5. Insert into Doctor
INSERT INTO ClinicDB.Doctor (doc_id, first_name, last_name, dob, dept_id, specialization_id) VALUES
                                                                                                 ('doc101','John','Carter','1975-04-14',1,'sp01'),
                                                                                                 ('doc102','Susan','Lewis','1978-11-05',1,'sp02'),
                                                                                                 ('doc103','Neela','Rasgotra','1984-02-11',5,'sp03'),
                                                                                                 ('doc104','Doug','Ross','1970-11-30',9,'sp04');


-- 6. Insert into Appointment
INSERT INTO ClinicDB.Appointment (appt_id, `date`, patient_id, doc_id, duration, appt_summary,status) VALUES
                                                                                                          ('14986def-7877-4aa0-b','2025-07-26 08:00:00','pat001','doc102',NULL,NULL,PENDING),
                                                                                                          ('34cb068a-755b-4cb1-8','2025-07-26 09:00:00','pat001','doc101',NULL,NULL,PENDING),
                                                                                                          ('3b343763-e80d-4d46-9','2025-07-26 10:00:00','pat002','doc101',NULL,NULL,PENDING),
                                                                                                          ('3f46e14a-ff79-4762-8','2025-07-29 13:00:00','pat002','doc102',NULL,NULL,PENDING),
                                                                                                          ('4fab6e6b-515f-482d-8','2025-08-02 14:00:00','pat001','doc101',NULL,NULL,PENDING),
                                                                                                          ('d0bc7019-87a2-465e-8','2025-07-28 10:00:00','pat002','doc102',NULL,NULL,PENDING);


-- 7. Insert into Specialization
INSERT INTO ClinicDB.Specialization (specialization_id, specialization_name) VALUES
                                                                                 ('sp01', 'Interventional'),
                                                                                 ('sp02', 'Electrophysiology'),
                                                                                 ('sp03', 'Cosmetic Derm'),
                                                                                 ('sp04', 'Audiology');

-- 8. Insert into Medical_Record
INSERT INTO ClinicDB.Medical_Record (pid, medicine_list) VALUES
                                                             ('pat001', 'Aspirin, Lisinopril'),
                                                             ('pat002', 'Ibuprofen');

-- 9. Insert into Prescription
INSERT INTO ClinicDB.Prescription (medicine_name, prescription_date, pid, did) VALUES
                                                                                   ('Amoxicillin 500mg', '2025-07-15', 'pat001', 'doc101'),
                                                                                   ('Ibuprofen 200mg', '2025-07-16', 'pat002', 'doc102'),
                                                                                   ('Amlodipine 5mg', '2025-07-20', 'pat002', 'doc104');

-- 10. Insert into Review
INSERT INTO ClinicDB.Review (pid, did, rid, content, rating) VALUES
                                                                 ('pat001', 'doc101', 'rev001', 'Very helpful and kind.', 5),
                                                                 ('pat002', 'doc102', 'rev002', 'Explained everything clearly.', 4);



