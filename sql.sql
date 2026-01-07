-- Create Database
CREATE DATABASE hospital;
USE hospital;

-- ============================
--      PATIENTS TABLE
-- ============================
CREATE TABLE patients (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    age INT,
    disease VARCHAR(50)
);

-- ============================
--      DOCTORS TABLE
-- ============================
CREATE TABLE doctors (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    specialization VARCHAR(50)
);

-- ============================
--      APPOINTMENTS TABLE
-- ============================
CREATE TABLE appointments (
    patientId INT NOT NULL,
    doctorId INT NOT NULL,
    date VARCHAR(20) NOT NULL,

    CONSTRAINT fk_patient
        FOREIGN KEY (patientId)
        REFERENCES patients(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_doctor
        FOREIGN KEY (doctorId)
        REFERENCES doctors(id)
        ON DELETE CASCADE
);
select * from appointments; 







