-- Ajuste seguro das sequences
SELECT setval('seq_usuario',
    GREATEST(1, COALESCE((SELECT MAX(id) FROM usuario), 1))
);

SELECT setval('seq_pessoa',
    GREATEST(1, COALESCE((SELECT MAX(id) FROM pessoa_fisica), 1))
);

SELECT setval('seq_acesso',
    GREATEST(1, COALESCE((SELECT MAX(id) FROM acesso), 1))
);

-- Acesso
INSERT INTO acesso (id, descricao)
VALUES (nextval('seq_acesso'), 'ROLE_USER');

-- Pessoa Física
INSERT INTO pessoa_fisica (
    id,
    nome,
    email,
    telefone,
    tipo_pessoa,
    cpf,
    data_nascimento
)
VALUES (
    nextval('seq_pessoa'),
    'Usuário Teste',
    'teste@email.com',
    '11999999999',
    'FISICA',
    '12345678900',
    CURRENT_DATE
);

-- Usuário
INSERT INTO usuario (
    id,
    login,
    senha,
    data_atual_senha,
    pessoa_id
)
VALUES (
    nextval('seq_usuario'),
    'teste',
    '$2a$10$NN9.EPEWxKI8iagJ0Z5pgePX.zW4yFZjxC9e/pViAyioeTz1CoF9G',
    CURRENT_DATE,
    (SELECT id FROM pessoa_fisica WHERE cpf = '12345678900')
);

-- Vínculo usuário x acesso
INSERT INTO usuarios_acesso (usuario_id, acesso_id)
VALUES (
    (SELECT id FROM usuario WHERE login = 'teste'),
    (SELECT id FROM acesso WHERE descricao = 'ROLE_USER')
);
