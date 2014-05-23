PRAGMA foreign_keys = ON;
PRAGMA journal_mode = MEMORY;

CREATE TABLE "level" (
  "id" integer PRIMARY KEY AUTOINCREMENT NOT NULL,
  "name" varchar(100) NOT NULL UNIQUE,
  "completed" bit(1) NOT NULL DEFAULT '0'
);

CREATE TABLE "game_object" (
  "id" integer PRIMARY KEY AUTOINCREMENT NOT NULL,
  "level_id" integer NOT NULL REFERENCES "level" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
  "type" text  NOT NULL,
  "x" double NOT NULL DEFAULT '0',
  "y" double NOT NULL DEFAULT '0',
  "rot" double NOT NULL DEFAULT '0',
  "color" char(7) DEFAULT '#FF0000'
);

CREATE INDEX "level_name" ON "level" ("name");
CREATE INDEX "game_object_type" ON "game_object" ("type");
CREATE INDEX "game_object_FK_game_object_level" ON "game_object" ("level_id");
