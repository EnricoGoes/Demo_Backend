CREATE DATABASE IF NOT EXISTS Projeto_Othon;
USE Projeto_Othon;

CREATE TABLE IF NOT EXISTS genero(
	id_genero INT AUTO_INCREMENT PRIMARY KEY,
    genero_nome VARCHAR(50) NOT NULL, 
    STATUS BIT DEFAULT 1
);
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    login varchar(100) not null unique,
    senha varchar(100) not null,
    create_at timestamp DEFAULT CURRENT_TIMESTAMP,
    last_login timestamp null
);
CREATE TABLE IF NOT EXISTS livro (
    id_livro INT AUTO_INCREMENT PRIMARY KEY,  -- Identificador único do livro
    titulo VARCHAR(255) NOT NULL,       -- Título do livro
    autor VARCHAR(255) NOT NULL,        -- Autor do livro
    editora VARCHAR(255),               -- Editora do livro
    ano_publicacao YEAR,                -- Ano de publicação
    isbn VARCHAR(20),                   -- ISBN do livro (pode ser um valor único)
    num_paginas INT,                    -- Número de páginas
    sinopse TEXT,                       -- Sinopse do livro
    idioma VARCHAR(50),                 -- Idioma do livro
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Data de cadastro do livro
    preco DECIMAL(10, 2),				-- Preço do livro (se relevante)
    genero_id_genero INT,				-- Gênero do livro
    FOREIGN KEY (genero_id_genero)
    REFERENCES genero(id_genero) ON DELETE SET NULL ON UPDATE CASCADE
);

-- Inserindo alguns gêneros aleatórios
INSERT INTO genero (genero_nome, STATUS) VALUES 
('Ficção Científica', 1),
('Fantasia', 1),
('Romance', 1),
('Terror', 1),
('Suspense', 1);

-- Inserindo um usuário aleatório
INSERT INTO usuario (login, senha) VALUES 
('usuario1', 'senha123'),
('usuario2', 'minhaSenha'),
('usuario3', 'seguranca2025');

-- Inserindo livros aleatórios
INSERT INTO livro (titulo, autor, editora, ano_publicacao, isbn, num_paginas, sinopse, idioma, preco, genero_id_genero) VALUES 
('O Senhor dos Anéis', 'J.R.R. Tolkien', 'HarperCollins', '1954', '9780261102385', 1216, 'Uma épica jornada pela Terra Média.', 'Português', 99.90, 2),
('Duna', 'Frank Herbert', 'Aleph', '1965', '9788528620116', 680, 'A história do jovem Paul Atreides e seu destino em Arrakis.', 'Português', 79.90, 1);
