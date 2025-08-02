# ClinicConnect

A web-based appointment management system for doctors and patients, built with Spring Boot, MySQL, and vanilla HTML/JavaScript.

## Table of Contents

- [Overview](#overview)  
- [Features](#features)  
- [Tech Stack](#tech-stack)  
- [Getting Started](#getting-started)  
  - [Prerequisites](#prerequisites)  
  - [Clone & Setup](#clone--setup)  
  - [Database Initialization](#database-initialization)  
  - [Run the App](#run-the-app)  
- [API Reference](#api-reference)  
- [Front-End Routing](#front-end-routing)  
- [Project Structure](#project-structure)  
- [Team](#team)  
- [License](#license)

---

## Overview

ClinicConnect allows:

- **Patients** to register, book appointments, view/cancel them, and review past visits.  
- **Doctors** to view their schedule, accept/reject appointment requests, and manage patient history & prescriptions.

> **Note:** This is _not_ a React application. The frontend uses plain HTML, Bootstrap 5, and jQuery.

---

## Features

1. **Patient Portal**  
   - Log in / log out  
   - Browse departments & doctors  
   - Book, view, or cancel appointments  
   - View visit summaries  

2. **Doctor Portal**  
   - View confirmed appointment schedule  
   - Accept or reject incoming requests  
   - Lookup a patient’s visit history  
   - Upload new prescriptions  

3. **Admin Tools** (future scope)  
   - Department, doctor, and user management  

---

## Tech Stack

- **Backend**: Java 17, Spring Boot, Spring MVC, Spring JDBC  
- **Database**: MySQL 8+  
- **Frontend**: HTML5, Bootstrap 5, jQuery (no React)  
- **Build & Packaging**: Maven  
- **Version Control**: Git & GitHub  

---

## Getting Started

### Prerequisites

- Java 17 or newer  
- Maven 3.6+  
- MySQL 8+  

### Clone & Setup

```bash
git clone https://github.com/kateliuhyd/clinic-connect.git
cd clinic-connect
