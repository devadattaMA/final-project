-- ============================================
-- ES234211 Programming Fundamental
-- Simple Game Application - Database Schema
-- Database: Microsoft SQL Server
-- ============================================

-- Create the database
CREATE DATABASE game_project;
GO

USE game_project;
GO

-- Create the players table (one table only)
CREATE TABLE players (
    id       INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    wins     INT DEFAULT 0,
    losses   INT DEFAULT 0,
    draws    INT DEFAULT 0,
    score    INT DEFAULT 0
);
GO

-- Insert sample players (password: 12345)
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student1', '12345', 0, 0, 0, 0);
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student2', '12345', 0, 0, 0, 0);
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student3', '12345', 0, 0, 0, 0);
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student4', '12345', 0, 0, 0, 0);
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student5', '12345', 0, 0, 0, 0);
GO

-- Verify the table was created correctly
SELECT * FROM players;
GO
