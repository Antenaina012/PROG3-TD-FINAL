
-- Types
CREATE TYPE gender AS ENUM ('MALE', 'FEMALE');
CREATE TYPE occupation AS ENUM ('JUNIOR', 'SENIOR', 'SECRETARY', 'TREASURER', 'VICE_PRESIDENT', 'PRESIDENT');
CREATE TYPE account_type AS ENUM ('CASH', 'MOBILE_MONEY', 'BANK');

-- Tables
CREATE TABLE member (
                        id VARCHAR(50) PRIMARY KEY,
                        first_name VARCHAR(100) NOT NULL,
                        last_name VARCHAR(100) NOT NULL,
                        birth_date DATE NOT NULL,
                        gender gender NOT NULL,
                        registration_date DATE DEFAULT CURRENT_DATE,
                        occupation occupation DEFAULT 'JUNIOR'
);

CREATE TABLE collectivity (
                              id VARCHAR(50) PRIMARY KEY,
                              name VARCHAR(100) UNIQUE NOT NULL,
                              unique_number VARCHAR(50) UNIQUE NOT NULL,
                              speciality VARCHAR(100),
                              creation_date DATE NOT NULL
);

CREATE TABLE membership (
                            member_id VARCHAR(50) REFERENCES member(id),
                            collectivity_id VARCHAR(50) REFERENCES collectivity(id),
                            PRIMARY KEY (member_id, collectivity_id)
);

CREATE TABLE financial_account (
                                   id VARCHAR(50) PRIMARY KEY,
                                   label VARCHAR(100) NOT NULL,
                                   type account_type NOT NULL,
                                   collectivity_id VARCHAR(50) REFERENCES collectivity(id)
);

CREATE TABLE transaction (
                             id SERIAL PRIMARY KEY,
                             amount DECIMAL(15, 2) NOT NULL,
                             transaction_date TIMESTAMP NOT NULL,
                             member_id VARCHAR(50) REFERENCES member(id),
                             collectivity_id VARCHAR(50) REFERENCES collectivity(id),
                             account_id VARCHAR(50) REFERENCES financial_account(id),
                             label VARCHAR(255)
);