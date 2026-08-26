ALTER TABLE siscoescola.tb_contrato_net
    ADD COLUMN IF NOT EXISTS velocidade CHARACTER VARYING(255) NOT NULL DEFAULT '';

ALTER TABLE siscoescola.tb_contrato_net
    ALTER COLUMN velocidade DROP DEFAULT;
