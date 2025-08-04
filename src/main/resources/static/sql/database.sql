CREATE TABLE Department (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(20),
    dept_desc VARCHAR(100),
    numDoctors INT
);

CREATE TABLE Address (
    addr_id VARCHAR(20) PRIMARY KEY,
    address_line VARCHAR(100),
    city VARCHAR(50),
    state VARCHAR(50),
    zip_code VARCHAR(50),
    country VARCHAR(50) 
);

CREATE TABLE User (
    user_id VARCHAR(20) PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    user_type VARCHAR(10) NOT NULL,
    addr_id VARCHAR(20) NOT NULL,
    FOREIGN KEY (addr_id) REFERENCES Address(addr_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Specialization (
    specialization_id VARCHAR(20) PRIMARY KEY,
    specialization_name VARCHAR(50) NOT NULL
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

CREATE TABLE Patient (
    patient_id VARCHAR(20) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    dob DATE,
    medical_insurance VARCHAR(50) NOT NULL,
    preferred_pharmacy VARCHAR(200) NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES User(user_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);





CREATE TABLE Prescription (
    pid VARCHAR(20),
    medicine_name VARCHAR(100),
    prescription_date DATE,
    did VARCHAR(20),
    PRIMARY KEY (pid, medicine_name, prescription_date),
    FOREIGN KEY (pid) REFCREATE TABLE Admin (
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
        );ERENCES Patient(patient_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (did) REFERENCES Doctor(doc_id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

CREATE TABLE Review (
    pid VARCHAR(20),
    did VARCHAR(20),
    rid VARCHAR(20),
    content VARCHAR(100),
    rating INT,
    review_date DATE,
    PRIMARY KEY (rid),
    FOREIGN KEY (pid) REFERENCES Patient(patient_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (did) REFERENCES Doctor(doc_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

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

CREATE INDEX idx_Doctor_dept_id on Doctor(dept_id);
CREATE INDEX idx_Appointment_doc_id_date on Appointment(doc_id, date);
CREATE INDEX idx_Review_did_date on Review(did);
