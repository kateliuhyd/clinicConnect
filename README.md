**Title:**
✨ Full-Stack ClinicConnect Web App: Database Design, REST API, and SPA Front-End

---

##  Overview

This Pull Request delivers a complete end-to-end ClinicConnect system, including:

1. **Enhanced Database Schema**

   * `Department`, `Specialization`, `Doctor`, `Patient`, `Appointment`, `Medical_Record`, `Prescription`, `Review`, `User` tables
   * Referential integrity via foreign keys
   * Seed data for departments, specializations, users, doctors, patients, appointments, prescriptions, reviews, and medical records

2. **Backend (Spring Boot + JDBC)**

   * **DAOs** for each entity (`AppointmentDAO`, `DoctorDAO`, `PatientDAO`, `DepartmentDAO`, `SpecializationDAO`, `MedicalRecordDAO`, `PrescriptionDAO`, `VisitSummaryDAO`, `UserDAO`)
   * **Models** matching every table, with proper camelCase fields, constructors, getters/setters
   * **Controllers** exposing REST endpoints under `/api`:

     * **Public**:

       * `GET /departments`, `GET /specializations`, `GET /doctors/department/{deptId}`
     * **Patient**:

       * `POST /appointments` (book),
       * `GET /getAppointments?patientId=…`,
       * `GET /getVisitSummary?patientId=…`,
       * `GET /getPrescriptions?patientId=…`
     * **Doctor**:

       * `GET /doctor-schedule?doctorId=…`,
       * `GET /appointment-requests?doctorId=…`,
       * `POST /appointment-requests/{id}/status?status=…`,
       * `GET /medical-record?patientId=…`
     * **Admin** (under `/api/admin`):

       * CRUD for patients, doctors, departments, specializations

3. **Frontend (jQuery Single-Page App)**

   * **Client-side router** in `app.js` handling hashes (`#login`, `#patient`, `#doctor`, `#admin-patients`, `#admin-doctors`, etc.)
   * **Pages** under `static/pages`:

     * `login.html`, `patient.html`, `book-appointment.html`, `appointment.html`, `prescription.html`, `visit.html`,
     * `doctor.html`, `doctor-schedule.html`, `doctor-requests.html`, `doctor-history.html`,
     * `admin-patients.html`, `admin-doctors.html`, `admin-departments.html` (optional), `admin-specializations.html` (optional)
   * **Page scripts** initialize via `initXYZPage()` functions:

     * Fetch data with AJAX, populate tables/cards, handle form submits, button clicks

4. **Error Handling & Validation**

   * Controllers return appropriate HTTP codes (201, 400, 401, 404, 409)
   * DAOs wrap JDBC in try/catch, close resources in finally
   * Front-end shows alerts or in-page error messages on failure

---

##  Key Changes

| Layer           | Files / Modules                                                                                                  | Summary                                                                              |
| --------------- | ---------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------ |
| **Database**    | `schema.sql`, `data.sql`                                                                                         | Added `Specialization`, `Medical_Record`, `status` & `appt_summary` to `Appointment` |
| **Models**      | `*.model.*`                                                                                                      | CamelCase fields; no-arg + all-args constructors; full getters/setters               |
| **DAOs**        | `*.dao.*`                                                                                                        | JDBCConfiguration; methods for all CRUD + lookup queries                             |
| **Controllers** | `UserController`, `AppointmentController`, `PrescriptionController`, `VisitController`, `AdminController`        | REST endpoints per spec; cross-origin enabled                                        |
| **Front-end**   | `app.js`, `login.js`, `booking.js`, `appointment.js`, `prescription.js`, `visit.js`, `doctor-*.js`, `admin-*.js` | SPA navigation; AJAX calls; dynamic DOM updates                                      |
| **Assets**      | Bootstrap 5, FontAwesome, jQuery 3.6.0                                                                           | CDN includes with correct integrity hashes                                           |

---

##  API Endpoints

### Public

* `GET /api/departments`
* `GET /api/specializations`
* `GET /api/doctors/department/{deptId}`

### Authentication

* `POST /api/user/login/validate` → `{ userId, userType }`

### Patient

* `POST /api/appointments` → create (409 if slot taken)
* `GET /api/getAppointments?patientId={}`
* `GET /api/getVisitSummary?patientId={}`
* `GET /api/getPrescriptions?patientId={}`

### Doctor

* `GET /api/doctor-schedule?doctorId={}`
* `GET /api/appointment-requests?doctorId={}`
* `POST /api/appointment-requests/{id}/status?status={CONFIRMED|REJECTED}`
* `GET /api/medical-record?patientId={}`

### Admin

* `GET/POST/DELETE /api/admin/patients`
* `GET/POST/DELETE /api/admin/doctors`
* `GET/POST /api/admin/departments`
* `GET/POST /api/admin/specializations`

---

##  Testing & Verification

1. **Database**:

   * Run `schema.sql` then `data.sql` in MySQL
   * Verify tables and sample rows exist

2. **Backend**:

   * `mvn spring-boot:run` or `gradle bootRun`
   * Use Postman or `curl` to hit key endpoints:

     ```bash
     curl localhost:8080/api/doctor-schedule?doctorId=doc101
     curl localhost:8080/api/medical-record?patientId=pat001
     ```

3. **Front-end**:


   * Open `http://localhost:8080/`
   * Log in as doctor (`doc101` / `pass001`), view schedule, requests, history + medical record
   * Log in as patient (`pat001` / `pw001`), book appointment, view visits & prescriptions
   * As admin, manage patients & doctors

---

##  Screenshots

| Patient Dashboard | Doctor Schedule | Admin Doctor Management |
| :---------------: | :-------------: | :---------------------: |
| ![Patient Dashboard](https://github.com/user-attachments/assets/7c8cdd8a-5767-45a3-8f48-3360515cd2b7)|  ![Doctor's Portal](https://github.com/user-attachments/assets/903fd5f9-4ec4-4211-80ab-9bc55d274fc4)|![Admin Dashboard](https://github.com/user-attachments/assets/4be273f4-c90a-49a9-bf6d-82ca753b0b11)|

---
