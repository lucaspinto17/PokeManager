-- Database initialization script

-- SCRIPT DE REFERÊNCIA - O SQLite CRIA AUTOMATICAMENTE!
-- TABELA DE Pokemon
CREATE TABLE IF NOT EXISTS pokemon (
	id INTEGER PRIMARY KEY,
	name TEXT NOT NULL,
	type1 TEXT NOT NULL,
	type2 TEXT,
	height REAL,
	weight REAL,
	base_experience INTEGER,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TABELA PARA TIMES FAVORITOS
CREATE TABLE IF NOT EXISTS pokemon_team (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	team_name TEXT NOT NULL DEFAULT 'Meu Time',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TABELA PARA MEMBROS DO TIME (máximo 6 por time)
CREATE TABLE IF NOT EXISTS team_members (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	team_id INTEGER,
	pokemon_id INTEGER,
	position INTEGER, -- Posição no time (1-6)
	added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (team_id) REFERENCES pokemon_team(id) ON DELETE CASCADE,
	FOREIGN KEY (pokemon_id) REFERENCES pokemon(id),
	UNIQUE (team_id, position)
);

-- INSERIR TIME PADRÃO
INSERT OR IGNORE INTO pokemon_team (id, team_name) VALUES (1, 'Meu Time Favorito');
