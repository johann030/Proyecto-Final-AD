DROP DATABASE IF EXISTS johann06;
CREATE DATABASE Johann06;
USE Johann06;

CREATE TABLE grupos(
id_grupo INTEGER PRIMARY KEY NOT NULL,
nombre_grupo VARCHAR(100) NOT NULL,
aula INTEGER NOT NULL
);

CREATE TABLE alumnos(
NIA INTEGER PRIMARY KEY NOT NULL,
nombre VARCHAR(100) NOT NULL,
apellidos VARCHAR(100) NOT NULL,
genero VARCHAR(30) NOT NULL,
fecha_nacimiento DATE NOT NULL,
ciclo VARCHAR(100) NOT NULL,
curso VARCHAR(100) NOT NULL,
id_grupo INTEGER NOT NULL,
FOREIGN KEY (id_grupo) REFERENCES grupos(id_grupo)
);