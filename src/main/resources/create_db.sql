-- USUARIO
INSERT INTO USUARIO(id, login, nome, senha) VALUES(1, 'admin','Administrador', '$2a$10$sFKmbxbG4ryhwPNx/l3pgOJSt.fW1z6YcUnuE2X8APA/Z3NI/oSpq');

-- PERFIL
INSERT INTO PERFIL(id, nome) VALUES(1, 'Administrador');

-- PERMISSAO
INSERT INTO PERMISSAO(id, nome) VALUES(1, 'admin');
INSERT INTO PERMISSAO(id, nome) VALUES(2, 'hello-get');

-- PERFIL_PERMISSAO
INSERT INTO PERFIL_PERMISSAO(perfil_id, permissao_id) VALUES(1, 1);
INSERT INTO PERFIL_PERMISSAO(perfil_id, permissao_id) VALUES(1, 2);

-- USUARIO_PERFIL
INSERT INTO USUARIO_PERFIL(usuario_id, perfil_id) VALUES(1, 1);