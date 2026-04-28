-- Nettoyage
DROP TABLE IF EXISTS "transaction" CASCADE;
DROP TABLE IF EXISTS "financial_account" CASCADE;
DROP TABLE IF EXISTS "membership" CASCADE;
DROP TABLE IF EXISTS "referee" CASCADE;
DROP TABLE IF EXISTS "collectivity" CASCADE;
DROP TABLE IF EXISTS "member" CASCADE;

DROP TYPE IF EXISTS gender_type CASCADE;
DROP TYPE IF EXISTS role_type CASCADE;
DROP TYPE IF EXISTS account_type CASCADE;

-- Types
CREATE TYPE gender_type AS ENUM ('MALE', 'FEMALE');
CREATE TYPE role_type AS ENUM ('PRESIDENT', 'VICE_PRESIDENT', 'TREASURER', 'SECRETARY', 'SENIOR', 'JUNIOR');
CREATE TYPE account_type AS ENUM ('CASH', 'ORANGE_MONEY', 'MVOLA', 'AIRTEL_MONEY', 'BANK');

-- Table Member (Alignée sur Member.java)
CREATE TABLE member (
                        id VARCHAR(50) PRIMARY KEY,
                        first_name VARCHAR(100) NOT NULL,
                        last_name VARCHAR(100) NOT NULL,
                        birth_date DATE NOT NULL,
                        gender gender_type NOT NULL,
                        enrolment_date TIMESTAMP NOT NULL,
                        address VARCHAR(255),
                        profession VARCHAR(100),
                        phone_number VARCHAR(20),
                        email VARCHAR(100),
                        registration_fee_paid BOOLEAN DEFAULT FALSE,
                        membership_dues_paid BOOLEAN DEFAULT FALSE
);

-- Table Collectivity
CREATE TABLE collectivity (
                              id VARCHAR(50) PRIMARY KEY,
                              unique_number VARCHAR(50) UNIQUE,
                              name VARCHAR(100) UNIQUE,
                              location VARCHAR(100) NOT NULL,
                              speciality VARCHAR(100) NOT NULL,
                              creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Membership (Historique et rôles)
CREATE TABLE membership (
                            member_id VARCHAR(50) REFERENCES member(id),
                            collectivity_id VARCHAR(50) REFERENCES collectivity(id),
                            role role_type DEFAULT 'JUNIOR',
                            begin_date DATE DEFAULT '2026-01-01',
                            end_date DATE, -- Pour getIdsOfActualBelongingCollectivities()
                            PRIMARY KEY (member_id, collectivity_id)
);

-- Table Referee (Parrainage)
CREATE TABLE referee (
                         sponsor_id VARCHAR(50) REFERENCES member(id),
                         sponsored_id VARCHAR(50) REFERENCES member(id),
                         PRIMARY KEY (sponsor_id, sponsored_id)
);

-- Table Financial Account
CREATE TABLE financial_account (
                                   id VARCHAR(50) PRIMARY KEY,
                                   collectivity_id VARCHAR(50) REFERENCES collectivity(id),
                                   type account_type NOT NULL,
                                   label VARCHAR(100),
                                   balance DECIMAL(15, 2) DEFAULT 0
);

-- Table Transaction
CREATE TABLE transaction (
                             id SERIAL PRIMARY KEY,
                             amount DECIMAL(15, 2) NOT NULL,
                             transaction_date TIMESTAMP NOT NULL,
                             member_id VARCHAR(50) REFERENCES member(id),
                             collectivity_id VARCHAR(50) REFERENCES collectivity(id),
                             account_id VARCHAR(50) REFERENCES financial_account(id),
                             label VARCHAR(255)
);



CREATE TABLE membership_fee (
                                id VARCHAR(50) PRIMARY KEY,
                                collectivity_id VARCHAR(50) REFERENCES collectivity(id),
                                label VARCHAR(255) NOT NULL,
                                amount DECIMAL(15, 2) NOT NULL,
                                frequency VARCHAR(50) NOT NULL -- WEEKLY, MONTHLY, etc.
);