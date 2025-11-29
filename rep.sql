CREATE TABLE Animal (
    id_animal INT PRIMARY KEY,
    puce VARCHAR(50),
    espece VARCHAR(50),
    nom VARCHAR(50),
    date_naissance DATE,
    race VARCHAR(50),
    date_arrivee DATE,
    statut VARCHAR(50),
    regime VARCHAR(100),
    test VARCHAR(100)
);

CREATE TABLE Box (
    id_box INT PRIMARY KEY,
    type VARCHAR(10),
    capacite INT
);

CREATE TABLE Famille (
    id_famille INT PRIMARY KEY,
    nom VARCHAR(50),
    adresse VARCHAR(200),
    telephone VARCHAR(20)
);

CREATE TABLE Activite (
    id_activite INT PRIMARY KEY,
    type VARCHAR(50),
    commentaire TEXT,
    nb_minimum INT
);

CREATE TABLE Creneau (
    id_creneau INT PRIMARY KEY,
    heure_debut TIME,
    heure_fin TIME
);

CREATE TABLE Benevole (
    id_benevole INT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    telephone VARCHAR(20)
);

CREATE TABLE Soin (
    id_soin INT PRIMARY KEY,
    id_animal INT NOT NULL,
    type VARCHAR(50),
    date_soin DATE,
    commentaire TEXT,
    FOREIGN KEY (id_animal) REFERENCES Animal(id_animal)
);

CREATE TABLE Historique_Box (
    id_animal INT,
    date_debut DATE,
    date_fin DATE,
    id_box INT,
    PRIMARY KEY (id_animal, date_debut),
    FOREIGN KEY (id_animal) REFERENCES Animal(id_animal),
    FOREIGN KEY (id_box) REFERENCES Box(id_box)
);

CREATE TABLE Historique_Famille (
    id_animal INT,
    date_debut DATE,
    date_fin DATE,
    id_famille INT,
    type_famille VARCHAR(30),
    PRIMARY KEY (id_animal, date_debut),
    FOREIGN KEY (id_animal) REFERENCES Animal(id_animal),
    FOREIGN KEY (id_famille) REFERENCES Famille(id_famille)
);

CREATE TABLE Planning_Animal (
    id_animal INT,
    id_creneau INT,
    id_activite INT,
    id_benevole INT,
    PRIMARY KEY (id_animal, id_creneau, id_activite, id_benevole),
    FOREIGN KEY (id_animal) REFERENCES Animal(id_animal),
    FOREIGN KEY (id_creneau) REFERENCES Creneau(id_creneau),
    FOREIGN KEY (id_activite) REFERENCES Activite(id_activite),
    FOREIGN KEY (id_benevole) REFERENCES Benevole(id_benevole)
);