CREATE TABLE IF NOT EXISTS siscoescola.tb_provedor_net (
    uuid_provedor UUID PRIMARY KEY,
    nome_provedor CHARACTER VARYING(120) NOT NULL,
    cnpj CHARACTER VARYING(20) NOT NULL,
    telefone CHARACTER VARYING(20) NOT NULL
);
