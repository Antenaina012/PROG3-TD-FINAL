-- Nettoyage
DROP TABLE IF EXISTS "transaction" CASCADE;
DROP TABLE IF EXISTS "financial_account" CASCADE;
DROP TABLE IF EXISTS "membership" CASCADE;
DROP TABLE IF EXISTS "referee" CASCADE;
DROP TABLE IF EXISTS "collectivity" CASCADE;
DROP TABLE IF EXISTS "member" CASCADE;
DROP TABLE IF EXISTS "membership_fee" CASCADE;

DROP TYPE IF EXISTS gender_type CASCADE;
DROP TYPE IF EXISTS role_type CASCADE;
DROP TYPE IF EXISTS account_type CASCADE;
DROP TYPE IF EXISTS bank_name_type CASCADE;
DROP TYPE IF EXISTS mobile_service_type CASCADE;

-- Types ENUM
CREATE TYPE gender_type AS ENUM ('MALE', 'FEMALE');
CREATE TYPE role_type AS ENUM ('PRESIDENT', 'VICE_PRESIDENT', 'TREASURER', 'SECRETARY', 'SENIOR', 'JUNIOR');
CREATE TYPE account_type AS ENUM ('CASH', 'BANK', 'MOBILE_MONEY');
CREATE TYPE bank_name_type AS ENUM ('BRED', 'MCB', 'BMOI', 'BOA', 'BGFI', 'AFG', 'ACCES_BANQUE', 'BAOBAB', 'SIPEM');
CREATE TYPE mobile_service_type AS ENUM ('ORANGE_MONEY', 'MVOLA', 'AIRTEL_MONEY');
CREATE TYPE frequency_type AS ENUM ('WEEKLY', 'MONTHLY', 'ANNUALLY', 'PUNCTUALLY');
CREATE TYPE activity_status_type AS ENUM ('ACTIVE', 'INACTIVE');

-- Table Member (Alignée sur Member.java et PDF)
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
    email VARCHAR(100)
);

-- Table Collectivity (Selon PDF section A)
CREATE TABLE collectivity (
    id VARCHAR(50) PRIMARY KEY,
    unique_number VARCHAR(50) UNIQUE,
    name VARCHAR(100) UNIQUE,
    location VARCHAR(100) NOT NULL,
    speciality VARCHAR(100) NOT NULL,
    federation_approval BOOLEAN DEFAULT FALSE,
    authorization_date TIMESTAMP,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Membership (Historique et rôles)
CREATE TABLE membership (
    member_id VARCHAR(50) REFERENCES member(id),
    collectivity_id VARCHAR(50) REFERENCES collectivity(id),
    role role_type DEFAULT 'JUNIOR',
    begin_date DATE DEFAULT CURRENT_DATE,
    end_date DATE, -- NULL = membership actif
    PRIMARY KEY (member_id, collectivity_id, begin_date)
);

-- Table Referee (Parrainage - Selon PDF section B-2)
CREATE TABLE referee (
    sponsor_id VARCHAR(50) REFERENCES member(id),
    sponsored_id VARCHAR(50) REFERENCES member(id),
    relation_nature VARCHAR(100), -- famille, amis, collègues, etc. (section B-2)
    PRIMARY KEY (sponsor_id, sponsored_id)
);

-- Table Financial Account (Selon PDF section D)
CREATE TABLE financial_account (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50) REFERENCES collectivity(id),
    type account_type NOT NULL,
    label VARCHAR(100),
    holder_name VARCHAR(100),
    
    -- Champs pour compte bancaire
    bank_name bank_name_type,
    bank_code VARCHAR(5), -- 5 chiffres
    bank_branch_code VARCHAR(5), -- 5 chiffres (guichet)
    bank_account_number VARCHAR(11), -- 11 chiffres
    bank_account_key VARCHAR(2), -- 2 chiffres (clé RIB)
    
    -- Champs pour mobile money
    mobile_service mobile_service_type,
    mobile_number VARCHAR(20),
    
    balance DECIMAL(15, 2) DEFAULT 0
);

-- Table Transaction (Selon PDF section C)
CREATE TABLE transaction (
    id VARCHAR(50) PRIMARY KEY,
    amount DECIMAL(15, 2) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    member_id VARCHAR(50) REFERENCES member(id),
    collectivity_id VARCHAR(50) REFERENCES collectivity(id),
    account_id VARCHAR(50) REFERENCES financial_account(id),
    label VARCHAR(255)
);

-- Table Membership Fee (Selon PDF section C)
CREATE TABLE membership_fee (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50) REFERENCES collectivity(id),
    label VARCHAR(255) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    frequency frequency_type NOT NULL,
    eligible_from DATE,
    status activity_status_type DEFAULT 'ACTIVE'
);