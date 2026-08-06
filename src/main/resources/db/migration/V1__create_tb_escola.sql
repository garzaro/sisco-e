CREATE SCHEMA IF NOT EXISTS siscoescola;

CREATE TABLE IF NOT EXISTS siscoescola.tb_escola (
    id_escola UUID PRIMARY KEY,
    nome_escola CHARACTER VARYING(120) NOT NULL,
    codigo_escola CHARACTER VARYING(120) NOT NULL,
    municipio CHARACTER VARYING(120) NOT NULL,
    estado CHARACTER VARYING(2) NOT NULL,
    cep CHARACTER VARYING(8),
    logradouro CHARACTER VARYING(255),
    bairro CHARACTER VARYING(255),
    tipo_escola CHARACTER VARYING(30) NOT NULL,
    is_ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_cadastro TIMESTAMP NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    CONSTRAINT uk_tb_escola_codigo_escola UNIQUE (codigo_escola)
);
