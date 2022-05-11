DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS memberships;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Roles table
CREATE TABLE roles (
   id UUID NOT NULL DEFAULT uuid_generate_v1() PRIMARY KEY,
   name VARCHAR(150) NOT NULL
);

CREATE INDEX idx_roles_id ON roles (id);

ALTER TABLE roles ADD CONSTRAINT uk_roles_name UNIQUE (name);


-- pre-defined roles
INSERT INTO roles (name) VALUES ('Developer');
INSERT INTO roles (name) VALUES ('Product Owner');
INSERT INTO roles (name) VALUES ('Tester');

-- Memberships table
CREATE TABLE memberships (
     role_id UUID NOT NULL,
     user_id UUID NOT NULL,
     team_id UUID NOT NULL,
     CONSTRAINT pk_membership PRIMARY KEY (user_id, team_id),
     CONSTRAINT fk_membership_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE INDEX idx_memberships_team_id_user_id ON memberships (team_id, user_id);