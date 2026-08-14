CREATE TABLE IF NOT EXISTS siscoescola.contratos_internet (
    id UUID PRIMARY KEY,
    escola_id UUID NOT NULL,
    provedor_id UUID NOT NULL,
    data_contratacao DATE NOT NULL,
    valor_mensal NUMERIC(10,2) NOT NULL,
    status CHARACTER VARYING(20) NOT NULL,
    data_cadastro TIMESTAMP NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    CONSTRAINT uk_contratos_internet_escola_provedor_data UNIQUE (escola_id, provedor_id, data_contratacao),
    CONSTRAINT fk_contratos_internet_escola FOREIGN KEY (escola_id)
        REFERENCES siscoescola.tb_escola (id_escola) ON DELETE CASCADE,
    CONSTRAINT fk_contratos_internet_provedor FOREIGN KEY (provedor_id)
        REFERENCES siscoescola.provedores_internet (id_provedor) ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_contratos_internet_escola_id ON siscoescola.contratos_internet (escola_id);
CREATE INDEX IF NOT EXISTS idx_contratos_internet_provedor_id ON siscoescola.contratos_internet (provedor_id);
CREATE INDEX IF NOT EXISTS idx_contratos_internet_status ON siscoescola.contratos_internet (status);
