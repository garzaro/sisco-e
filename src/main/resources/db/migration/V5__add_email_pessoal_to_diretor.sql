ALTER TABLE siscoescola.tb_diretor
    ADD COLUMN email_pessoal CHARACTER VARYING(120);

ALTER TABLE siscoescola.tb_diretor
    ADD CONSTRAINT uk_tb_diretor_email_pessoal UNIQUE (email_pessoal);

ALTER TABLE siscoescola.tb_diretor_aud
    ADD COLUMN email_pessoal CHARACTER VARYING(120);
