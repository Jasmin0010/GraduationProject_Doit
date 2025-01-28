CREATE TABLE user_entity (
    id VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);


CREATE TABLE todo (
    id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255),
    title VARCHAR(255),
    done BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

-- ========================================
ALTER TABLE todo
ADD COLUMN date VARCHAR(255);

ALTER TABLE todo
ADD COLUMN created VARCHAR(255);

ALTER TABLE todo
ADD COLUMN updated VARCHAR(255);

CREATE SEQUENCE worksheetentity_sequence
    START WITH 1
    INCREMENT BY 100;

CREATE TABLE worksheetentity (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('worksheetentity_sequence'),
    user_id VARCHAR(255) NOT NULL,
    title VARCHAR(255),
    content TEXT,
    date VARCHAR(255) NOT NULL,
    time_spent VARCHAR(255),
    status VARCHAR(255),
    created VARCHAR(255),
    updated VARCHAR(255)
);

CREATE SEQUENCE workoutvideo_sequence
    START WITH 1
    INCREMENT BY 100;

CREATE TABLE workoutvideo (
    id BIGINT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    video_id VARCHAR(255),
    platform VARCHAR(255),
    created VARCHAR(255),
    PRIMARY KEY (id)
);

ALTER TABLE user_entity
ADD COLUMN role VARCHAR(255);

ALTER TABLE user_entity
ADD COLUMN height VARCHAR(255);

ALTER TABLE user_entity
ADD COLUMN weight VARCHAR(255);