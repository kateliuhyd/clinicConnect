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
    address_line VARCHAR(100),
    city VARCHAR(50),
    state VARCHAR(50),
    zip_code VARCHAR(50),
    country VARCHAR(50), 
    dept_id INT NOT NULL,
    specialization CHAR(20) NOT NULL,
    FOREIGN KEY (dept_id) REFERENCES Department(dept_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    FOREIGN KEY (doc_id) REFERENCES User(user_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
 TABLE Patient (
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
    address_line VARCHAR(100),
    city VARCHAR(50),
    state VARCHAR(50),
    zip_code VARCHAR(50),
    country VARCHAR(50), 
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

CREATE TABLE Appointment (
    appt_id VARCHAR(20) PRIMARY KEY,
    date TIMESTAMP,
    patient_id VARCHAR(20),
    doc_id VARCHAR(20),
    duration TIMESTAMP,
    appt_summary VARCHAR(1000),
    FOREIGN KEY (patient_id) REFERENCES Patient(patient_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (doc_id) REFERENCES Doctor(doc_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
