ALTER TABLE siscoescola.contratos_internet
    ADD COLUMN velocidade VARCHAR(100) NOT NULL DEFAULT '';

ALTER TABLE siscoescola.contratos_internet
    ALTER COLUMN velocidade DROP DEFAULT;
