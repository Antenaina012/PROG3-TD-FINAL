-- I. LES COLLECTIVITÉS
INSERT INTO collectivity (id, name, unique_number, speciality, creation_date) VALUES
                                                                                  ('col-1', 'Collectivité de Riziculture', 'COL-001', 'Riziculture', '2025-01-01'),
                                                                                  ('col-2', 'Collectivité Maraîchère', 'COL-002', 'Maraîchage', '2026-01-01');

-- II. LES MEMBRES (Mélange de membres anciens et nouveaux pour les tests)
INSERT INTO member (id, first_name, last_name, birth_date, gender, registration_date, occupation) VALUES
-- Membres de la Collectivité 2 (Tableau 10)
('C2-M1', 'Jean', 'Rakoto', '1990-05-15', 'MALE', '2025-01-01', 'PRESIDENT'),
('C2-M2', 'Lova', 'Andria', '1992-08-20', 'FEMALE', '2025-01-01', 'TREASURER'),
('C2-M3', 'Sitraka', 'Ranaivo', '1985-02-10', 'MALE', '2025-03-01', 'SECRETARY'),
('C2-M4', 'Mamy', 'Rasoa', '1988-11-30', 'FEMALE', '2025-06-01', 'VICE_PRESIDENT'),
('C2-M5', 'Hery', 'Rabe', '1995-04-12', 'MALE', '2025-06-01', 'JUNIOR'),
('C2-M6', 'Feno', 'Andrianina', '1993-09-05', 'MALE', '2025-07-01', 'SENIOR'),
('C2-M7', 'Tahina', 'Razafy', '1991-12-25', 'FEMALE', '2026-01-01', 'JUNIOR'),
('C2-M8', 'Iary', 'Solofo', '1994-02-14', 'MALE', '2026-01-01', 'JUNIOR');

-- III. LES COMPTES FINANCIERS DE LA COL-2
INSERT INTO financial_account (id, label, type, collectivity_id) VALUES
                                                                     ('C2-A-CASH', 'Caisse Centrale', 'CASH', 'col-2'),
                                                                     ('C2-A-MOBILE-1', 'Compte Mvola Principal', 'MOBILE_MONEY', 'col-2');

-- IV. LES LIENS D'APPARTENANCE
INSERT INTO membership (member_id, collectivity_id) VALUES
                                                        ('C2-M1', 'col-2'), ('C2-M2', 'col-2'), ('C2-M3', 'col-2'), ('C2-M4', 'col-2'),
                                                        ('C2-M5', 'col-2'), ('C2-M6', 'col-2'), ('C2-M7', 'col-2'), ('C2-M8', 'col-2');

-- V. TOUTES LES TRANSACTIONS (Paiements du Tableau 10)
-- On utilise le format YYYY-MM-DD pour la date
INSERT INTO transaction (amount, transaction_date, member_id, collectivity_id, account_id, label) VALUES
                                                                                                      (60000, '2026-01-01 08:00:00', 'C2-M1', 'col-2', 'C2-A-CASH', 'Droit d''adhésion'),
                                                                                                      (90000, '2026-01-01 08:15:00', 'C2-M2', 'col-2', 'C2-A-CASH', 'Droit d''adhésion'),
                                                                                                      (100000, '2026-01-01 08:30:00', 'C2-M3', 'col-2', 'C2-A-CASH', 'Droit d''adhésion'),
                                                                                                      (100000, '2026-01-01 09:00:00', 'C2-M4', 'col-2', 'C2-A-CASH', 'Cotisation annuelle'),
                                                                                                      (100000, '2026-01-01 09:30:00', 'C2-M5', 'col-2', 'C2-A-CASH', 'Cotisation annuelle'),
                                                                                                      (100000, '2026-01-01 10:00:00', 'C2-M6', 'col-2', 'C2-A-CASH', 'Cotisation annuelle'),
                                                                                                      (40000,  '2026-01-01 10:15:00', 'C2-M7', 'col-2', 'C2-A-MOBILE-1', 'Paiement partiel'),
                                                                                                      (60000,  '2026-01-01 10:30:00', 'C2-M8', 'col-2', 'C2-A-MOBILE-1', 'Droit d''adhésion');