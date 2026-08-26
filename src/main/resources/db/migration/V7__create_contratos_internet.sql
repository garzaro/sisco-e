CREATE TABLE IF NOT EXISTS siscoescola.tb_contrato_net (
    uuid_contrato_net UUID PRIMARY KEY,
    uuid_escola UUID NOT NULL,
    uuid_provedor UUID NOT NULL,
    data_contratacao DATE NOT NULL,
    data_fim_contrato DATE,
    valor_mensal NUMERIC(10,2) NOT NULL,
    status CHARACTER VARYING(20) NOT NULL,
    data_cadastro TIMESTAMP WITH TIME ZONE NOT NULL,
    data_atualizacao TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_contratos_internet_escola_provedor_data UNIQUE (uuid_escola, uuid_provedor, data_contratacao),
    CONSTRAINT fk_contrato_net_escola FOREIGN KEY (uuid_escola)
        REFERENCES siscoescola.tb_escola (uuid_escola) ON DELETE CASCADE,
    CONSTRAINT fk_contrato_net_provedor FOREIGN KEY (uuid_provedor)
        REFERENCES siscoescola.tb_provedor_net (uuid_provedor) ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_contrato_net_escola_uuid ON siscoescola.tb_contrato_net (uuid_escola);
CREATE INDEX IF NOT EXISTS idx_contrato_net_provedor_uuid ON siscoescola.tb_contrato_net (uuid_provedor);
CREATE INDEX IF NOT EXISTS idx_contrato_net_status ON siscoescola.tb_contrato_net (status);
