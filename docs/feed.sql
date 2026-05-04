-- ============================================================================
-- DONNÉES DE TEST SELON LE PDF (Pages 11-20)
-- Évaluation du jeudi 23 avril 2026
-- ============================================================================

-- ============================================================================
-- 1. COLLECTIVITÉS (Tableau 1 - Page 11)
-- ============================================================================
INSERT INTO collectivity (id, unique_number, name, location, speciality, federation_approval, authorization_date) VALUES
    ('col-1', '1', 'Mpanorina', 'Ambatondrazaka', 'Riziculture', TRUE, '2025-06-01'),
    ('col-2', '2', 'Dobo voalohany', 'Ambatondrazaka', 'Pisciculture', TRUE, '2025-07-01'),
    ('col-3', '3', 'Tantely mamy', 'Brickaville', 'Apiculture', TRUE, '2025-08-01');

-- ============================================================================
-- 2. MEMBRES (Tableaux 2, 3, 4 - Pages 12-14)
-- ============================================================================

-- Membres de la collectivité 1 (Tableau 2)
INSERT INTO member (id, first_name, last_name, birth_date, gender, enrolment_date, address, profession, phone_number, email) VALUES
    ('C1-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE', '2025-01-01', 'Lot II V M Ambato', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg'),
    ('C1-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE', '2025-01-01', 'Lot II F Ambato', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg'),
    ('C1-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE', '2025-01-01', 'Lot II J Ambato', 'Collecteur', '0331234567', 'member.3@fed-agri.mg'),
    ('C1-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', '2025-01-01', 'Lot A K 50 Ambato', 'Distributeur', '0381234567', 'member.4@fed-agri.mg'),
    ('C1-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE', '2025-01-01', 'Lot UV 80 Ambato', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg'),
    ('C1-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', '2025-01-01', 'Lot UV 6 Ambato', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg'),
    ('C1-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE', '2025-01-01', 'Lot UV 7 Ambato', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg'),
    ('C1-M8', 'Prénom membre 8', 'Nom membre 8', '1975-08-20', 'MALE', '2025-01-01', 'Lot UV 8 Ambato', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg');

-- Membres de la collectivité 2 (Tableau 3)
-- Note: Les mêmes membres peuvent appartenir à plusieurs collectivités
INSERT INTO member (id, first_name, last_name, birth_date, gender, enrolment_date, address, profession, phone_number, email) VALUES
    ('C2-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE', '2025-01-01', 'Lot II V M Ambato', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg'),
    ('C2-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE', '2025-01-01', 'Lot II F Ambato', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg'),
    ('C2-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE', '2025-01-01', 'Lot II J Ambato', 'Collecteur', '0331234567', 'member.3@fed-agri.mg'),
    ('C2-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', '2025-01-01', 'Lot A K 50 Ambato', 'Distributeur', '0381234567', 'member.4@fed-agri.mg'),
    ('C2-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE', '2025-01-01', 'Lot UV 80 Ambato', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg'),
    ('C2-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', '2025-01-01', 'Lot UV 6 Ambato', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg'),
    ('C2-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE', '2025-01-01', 'Lot UV 7 Ambato', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg'),
    ('C2-M8', 'Prénom membre 8', 'Nom membre 8', '1975-08-20', 'MALE', '2025-01-01', 'Lot UV 8 Ambato', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg');

-- Membres de la collectivité 3 (Tableau 4)
INSERT INTO member (id, first_name, last_name, birth_date, gender, enrolment_date, address, profession, phone_number, email) VALUES
    ('C3-M1', 'Prénom membre 9', 'Nom membre 9', '1988-01-02', 'MALE', '2025-01-01', 'Lot 33 J Antsirabe', 'Apiculteur', '034034567', 'member.9@fed-agri.mg'),
    ('C3-M2', 'Prénom membre 10', 'Nom membre 10', '1982-03-05', 'MALE', '2025-01-01', 'Lot 2 J Antsirabe', 'Agriculteur', '0338634567', 'member.10@fed-agri.mg'),
    ('C3-M3', 'Prénom membre 11', 'Nom membre 11', '1992-03-12', 'MALE', '2025-01-01', 'Lot 8 KM Antsirabe', 'Collecteur', '0338234567', 'member.11@fed-agri.mg'),
    ('C3-M4', 'Prénom membre 12', 'Nom membre 12', '1988-05-10', 'FEMALE', '2025-01-01', 'Lot A K 50 Antsirabe', 'Distributeur', '0382334567', 'member.12@fed-agri.mg'),
    ('C3-M5', 'Prénom membre 13', 'Nom membre 13', '1999-08-11', 'MALE', '2025-01-01', 'Lot UV 80 Antsirabe', 'Apiculteur', '0373365567', 'member.13@fed-agri.mg'),
    ('C3-M6', 'Prénom membre 14', 'Nom membre 14', '1998-08-09', 'FEMALE', '2025-01-01', 'Lot UV 6 Antsirabe', 'Apiculteur', '0378234567', 'member.14@fed-agri.mg'),
    ('C3-M7', 'Prénom membre 15', 'Nom membre 15', '1998-01-13', 'MALE', '2025-01-01', 'Lot UV 7 Antsirabe', 'Apiculteur', '0374914567', 'member.15@fed-agri.mg'),
    ('C3-M8', 'Prénom membre 16', 'Nom membre 16', '1975-08-02', 'MALE', '2025-01-01', 'Lot UV 8 Antsirabe', 'Apiculteur', '0370634567', 'member.16@fed-agri.mg');

-- ============================================================================
-- 3. MEMBERSHIPS (Appartenance aux collectivités - Tableaux 2, 3, 4)
-- ============================================================================

-- Collectivité 1 (Tableau 2)
INSERT INTO membership (member_id, collectivity_id, role, begin_date) VALUES
    ('C1-M1', 'col-1', 'PRESIDENT', '2026-01-01'),
    ('C1-M2', 'col-1', 'VICE_PRESIDENT', '2026-01-01'),
    ('C1-M3', 'col-1', 'SECRETARY', '2026-01-01'),
    ('C1-M4', 'col-1', 'TREASURER', '2026-01-01'),
    ('C1-M5', 'col-1', 'SENIOR', '2026-01-01'),
    ('C1-M6', 'col-1', 'SENIOR', '2026-01-01'),
    ('C1-M7', 'col-1', 'SENIOR', '2026-01-01'),
    ('C1-M8', 'col-1', 'SENIOR', '2026-01-01');

-- Collectivité 2 (Tableau 3)
INSERT INTO membership (member_id, collectivity_id, role, begin_date) VALUES
    ('C2-M1', 'col-2', 'SENIOR', '2026-01-01'),
    ('C2-M2', 'col-2', 'SENIOR', '2026-01-01'),
    ('C2-M3', 'col-2', 'SENIOR', '2026-01-01'),
    ('C2-M4', 'col-2', 'SENIOR', '2026-01-01'),
    ('C2-M5', 'col-2', 'PRESIDENT', '2026-01-01'),
    ('C2-M6', 'col-2', 'VICE_PRESIDENT', '2026-01-01'),
    ('C2-M7', 'col-2', 'SECRETARY', '2026-01-01'),
    ('C2-M8', 'col-2', 'TREASURER', '2026-01-01');

-- Collectivité 3 (Tableau 4)
INSERT INTO membership (member_id, collectivity_id, role, begin_date) VALUES
    ('C3-M1', 'col-3', 'PRESIDENT', '2026-01-01'),
    ('C3-M2', 'col-3', 'VICE_PRESIDENT', '2026-01-01'),
    ('C3-M3', 'col-3', 'SECRETARY', '2026-01-01'),
    ('C3-M4', 'col-3', 'TREASURER', '2026-01-01'),
    ('C3-M5', 'col-3', 'SENIOR', '2026-01-01'),
    ('C3-M6', 'col-3', 'SENIOR', '2026-01-01'),
    ('C3-M7', 'col-3', 'SENIOR', '2026-01-01'),
    ('C3-M8', 'col-3', 'SENIOR', '2026-01-01');

-- ============================================================================
-- 4. PARRAINAGES (Referee - Tableaux 2, 3, 4)
-- ============================================================================

-- Parrainages collectivité 1 (Tableau 2)
INSERT INTO referee (sponsor_id, sponsored_id, relation_nature) VALUES
    ('C1-M1', 'C1-M3', 'collègue'),
    ('C1-M2', 'C1-M3', 'collègue'),
    ('C1-M1', 'C1-M4', 'collègue'),
    ('C1-M2', 'C1-M4', 'collègue'),
    ('C1-M1', 'C1-M5', 'ami'),
    ('C1-M2', 'C1-M5', 'ami'),
    ('C1-M1', 'C1-M6', 'famille'),
    ('C1-M2', 'C1-M6', 'famille'),
    ('C1-M1', 'C1-M7', 'collègue'),
    ('C1-M2', 'C1-M7', 'collègue'),
    ('C1-M6', 'C1-M8', 'ami'),
    ('C1-M7', 'C1-M8', 'ami');

-- Parrainages collectivité 2 (Tableau 3)
INSERT INTO referee (sponsor_id, sponsored_id, relation_nature) VALUES
    ('C1-M1', 'C2-M3', 'collègue'),
    ('C1-M2', 'C2-M3', 'collègue'),
    ('C1-M1', 'C2-M4', 'collègue'),
    ('C1-M2', 'C2-M4', 'collègue'),
    ('C1-M1', 'C2-M5', 'ami'),
    ('C1-M2', 'C2-M5', 'ami'),
    ('C1-M1', 'C2-M6', 'famille'),
    ('C1-M2', 'C2-M6', 'famille'),
    ('C1-M1', 'C2-M7', 'collègue'),
    ('C1-M2', 'C2-M7', 'collègue'),
    ('C1-M6', 'C2-M8', 'ami'),
    ('C1-M7', 'C2-M8', 'ami');

-- Parrainages collectivité 3 (Tableau 4)
INSERT INTO referee (sponsor_id, sponsored_id, relation_nature) VALUES
    ('C1-M1', 'C3-M1', 'collègue'),
    ('C1-M2', 'C3-M1', 'collègue'),
    ('C1-M1', 'C3-M2', 'collègue'),
    ('C1-M2', 'C3-M2', 'collègue'),
    ('C3-M1', 'C3-M3', 'ami'),
    ('C3-M2', 'C3-M3', 'ami'),
    ('C3-M1', 'C3-M4', 'famille'),
    ('C3-M2', 'C3-M4', 'famille'),
    ('C3-M1', 'C3-M5', 'collègue'),
    ('C3-M2', 'C3-M5', 'collègue'),
    ('C3-M1', 'C3-M6', 'ami'),
    ('C3-M2', 'C3-M6', 'ami'),
    ('C3-M1', 'C3-M7', 'collègue'),
    ('C3-M2', 'C3-M7', 'collègue'),
    ('C3-M1', 'C3-M8', 'famille'),
    ('C3-M2', 'C3-M8', 'famille');

-- ============================================================================
-- 5. COMPTES FINANCIERS (Page 16)
-- ============================================================================

-- Collectivité 1
INSERT INTO financial_account (id, collectivity_id, type, label, holder_name, mobile_service, mobile_number, balance) VALUES
    ('C1-A-CASH', 'col-1', 'CASH', 'Caisse Principale', NULL, NULL, NULL, 0),
    ('C1-A-MOBILE-1', 'col-1', 'MOBILE_MONEY', 'Compte Orange Money', 'Mpanorina', 'ORANGE_MONEY', '0370489612', 0);

-- Collectivité 2
INSERT INTO financial_account (id, collectivity_id, type, label, holder_name, mobile_service, mobile_number, balance) VALUES
    ('C2-A-CASH', 'col-2', 'CASH', 'Caisse Principale', NULL, NULL, NULL, 0),
    ('C2-A-MOBILE-1', 'col-2', 'MOBILE_MONEY', 'Compte Orange Money', 'Dobo voalohany', 'ORANGE_MONEY', '0320489612', 0);

-- Collectivité 3
INSERT INTO financial_account (id, collectivity_id, type, label, holder_name, mobile_service, mobile_number, balance) VALUES
    ('C3-A-CASH', 'col-3', 'CASH', 'Caisse Principale', NULL, NULL, NULL, 0);

-- ============================================================================
-- 6. COTISATIONS (Membership Fees - Tableaux 5, 6, 7 - Pages 15)
-- ============================================================================

INSERT INTO membership_fee (id, collectivity_id, label, amount, frequency, eligible_from, status) VALUES
    ('cot-1', 'col-1', 'Cotisation annuelle', 100000, 'ANNUALLY', '2026-01-01', 'ACTIVE'),
    ('cot-2', 'col-2', 'Cotisation annuelle', 100000, 'ANNUALLY', '2026-01-01', 'ACTIVE'),
    ('cot-3', 'col-3', 'Cotisation annuelle', 50000, 'ANNUALLY', '2026-01-01', 'ACTIVE');

-- ============================================================================
-- 7. TRANSACTIONS / PAIEMENTS (Tableaux 8, 9, 10, 11 - Pages 17-20)
-- ============================================================================

-- Transactions collectivité 1 (Tableau 8/9)
INSERT INTO transaction (id, amount, transaction_date, member_id, collectivity_id, account_id, label) VALUES
    ('txn-c1-m1', 100000, '2026-01-01 10:00:00', 'C1-M1', 'col-1', 'C1-A-CASH', 'Paiement cotisation'),
    ('txn-c1-m2', 100000, '2026-01-01 10:05:00', 'C1-M2', 'col-1', 'C1-A-CASH', 'Paiement cotisation'),
    ('txn-c1-m3', 100000, '2026-01-01 10:10:00', 'C1-M3', 'col-1', 'C1-A-CASH', 'Paiement cotisation'),
    ('txn-c1-m4', 100000, '2026-01-01 10:15:00', 'C1-M4', 'col-1', 'C1-A-CASH', 'Paiement cotisation'),
    ('txn-c1-m5', 100000, '2026-01-01 10:20:00', 'C1-M5', 'col-1', 'C1-A-CASH', 'Paiement cotisation'),
    ('txn-c1-m6', 100000, '2026-01-01 10:25:00', 'C1-M6', 'col-1', 'C1-A-CASH', 'Paiement cotisation'),
    ('txn-c1-m7', 60000, '2026-01-01 10:30:00', 'C1-M7', 'col-1', 'C1-A-CASH', 'Paiement partiel cotisation'),
    ('txn-c1-m8', 90000, '2026-01-01 10:35:00', 'C1-M8', 'col-1', 'C1-A-CASH', 'Paiement partiel cotisation');

-- Transactions collectivité 2 (Tableau 10/11)
INSERT INTO transaction (id, amount, transaction_date, member_id, collectivity_id, account_id, label) VALUES
    ('txn-c2-m1', 60000, '2026-01-01 11:00:00', 'C2-M1', 'col-2', 'C2-A-CASH', 'Paiement partiel cotisation'),
    ('txn-c2-m2', 90000, '2026-01-01 11:05:00', 'C2-M2', 'col-2', 'C2-A-CASH', 'Paiement partiel cotisation'),
    ('txn-c2-m3', 100000, '2026-01-01 11:10:00', 'C2-M3', 'col-2', 'C2-A-CASH', 'Paiement cotisation'),
    ('txn-c2-m4', 100000, '2026-01-01 11:15:00', 'C2-M4', 'col-2', 'C2-A-CASH', 'Paiement cotisation'),
    ('txn-c2-m5', 100000, '2026-01-01 11:20:00', 'C2-M5', 'col-2', 'C2-A-CASH', 'Paiement cotisation'),
    ('txn-c2-m6', 100000, '2026-01-01 11:25:00', 'C2-M6', 'col-2', 'C2-A-CASH', 'Paiement cotisation'),
    ('txn-c2-m7', 40000, '2026-01-01 11:30:00', 'C2-M7', 'col-2', 'C2-A-MOBILE-1', 'Paiement via Mobile Money'),
    ('txn-c2-m8', 60000, '2026-01-01 11:35:00', 'C2-M8', 'col-2', 'C2-A-MOBILE-1', 'Paiement via Mobile Money');

-- Collectivité 3: Aucun paiement effectué (liste vide selon PDF page 20)
