-- ============================
-- TABLE : Collectivities
-- ============================
CREATE TABLE collectivities (
                                id              VARCHAR(50) PRIMARY KEY,
                                location        VARCHAR(255) NOT NULL,
                                federation_approval BOOLEAN NOT NULL,
                                created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================
-- TABLE : Members
-- ============================
CREATE TABLE members (
                         id              VARCHAR(50) PRIMARY KEY,
                         first_name      VARCHAR(100) NOT NULL,
                         last_name       VARCHAR(100) NOT NULL,
                         birth_date      DATE NOT NULL,
                         gender          VARCHAR(10) CHECK (gender IN ('MALE','FEMALE')),
                         address         VARCHAR(255),
                         profession      VARCHAR(100),
                         phone_number    BIGINT,
                         email           VARCHAR(255) UNIQUE,
                         occupation      VARCHAR(50) CHECK (occupation IN ('JUNIOR','SENIOR','SECRETARY','TREASURER','VICE PRESIDENT','PRESIDENT')),
                         registration_fee_paid BOOLEAN NOT NULL,
                         membership_dues_paid BOOLEAN NOT NULL,
                         collectivity_id VARCHAR(50) REFERENCES collectivities(id) ON DELETE CASCADE
);

-- ============================
-- TABLE : Collectivity Structure
-- ============================
CREATE TABLE collectivity_structure (
                                        id              SERIAL PRIMARY KEY,
                                        collectivity_id VARCHAR(50) REFERENCES collectivities(id) ON DELETE CASCADE,
                                        president_id    VARCHAR(50) REFERENCES members(id),
                                        vice_president_id VARCHAR(50) REFERENCES members(id),
                                        treasurer_id    VARCHAR(50) REFERENCES members(id),
                                        secretary_id    VARCHAR(50) REFERENCES members(id)
);

-- ============================
-- TABLE : Referees (parrains)
-- ============================
CREATE TABLE member_referees (
                                 member_id       VARCHAR(50) REFERENCES members(id) ON DELETE CASCADE,
                                 referee_id      VARCHAR(50) REFERENCES members(id),
                                 relationship    VARCHAR(50), -- famille, ami, collègue, etc.
                                 PRIMARY KEY (member_id, referee_id)
);
