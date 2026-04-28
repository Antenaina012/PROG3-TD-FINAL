INSERT INTO collectivity (id, unique_number, name, location, speciality) VALUES
                                                                             ('col-1', '1', 'Mpanorina', 'Ambatondrazaka', 'Riziculture'),
                                                                             ('col-2', '2', 'Dobo voalohany', 'Ambatondrazaka', 'Pisciculture'),
                                                                             ('col-3', '3', 'Tantely mamy', 'Brickaville', 'Apiculture');

INSERT INTO member (id, first_name, last_name, birth_date, gender, enrolment_date, address, profession, phone_number, email) VALUES
                                                                                                                                 ('C1-M1', 'Membre 1', 'Nom 1', '1980-02-01', 'MALE', '2025-01-01', 'Lot II V M Ambato', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg'),
                                                                                                                                 ('C1-M2', 'Membre 2', 'Nom 2', '1982-03-05', 'MALE', '2025-01-01', 'Lot II F Ambato', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg'),
                                                                                                                                 ('C1-M3', 'Membre 3', 'Nom 3', '1992-03-10', 'MALE', '2025-01-01', 'Lot II J Ambato', 'Collecteur', '0331234567', 'member.3@fed-agri.mg'),
                                                                                                                                 ('C1-M4', 'Membre 4', 'Nom 4', '1988-05-22', 'FEMALE', '2025-01-01', 'Lot A K 50 Ambato', 'Distributeur', '0381234567', 'member.4@fed-agri.mg');

-- Rôles dans Col-1
INSERT INTO membership (member_id, collectivity_id, role) VALUES
                                                              ('C1-M1', 'col-1', 'PRESIDENT'),
                                                              ('C1-M2', 'col-1', 'VICE_PRESIDENT'),
                                                              ('C1-M3', 'col-1', 'SECRETARY'),
                                                              ('C1-M4', 'col-1', 'TREASURER');

-- Parrainages Col-1
INSERT INTO referee (sponsor_id, sponsored_id) VALUES
                                                   ('C1-M1', 'C1-M3'), ('C1-M2', 'C1-M3'),
                                                   ('C1-M1', 'C1-M4'), ('C1-M2', 'C1-M4');

INSERT INTO financial_account (id, collectivity_id, type, label, balance) VALUES
    ('C1-A-CASH', 'col-1', 'CASH', 'Caisse Principale', 0);

INSERT INTO transaction (amount, transaction_date, member_id, collectivity_id, account_id, label) VALUES
    (100000, '2026-01-01 10:00:00', 'C1-M1', 'col-1', 'C1-A-CASH', 'Paiement Janvier');
