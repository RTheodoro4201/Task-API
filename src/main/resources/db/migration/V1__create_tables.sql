CREATE TABLE tasks (
                       id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title       VARCHAR(200)                             NOT NULL,
                       description TEXT,
                       status      ENUM('TODO', 'IN_PROGRESS', 'DONE')     NOT NULL DEFAULT 'TODO',
                       priority    ENUM('LOW', 'MEDIUM', 'HIGH')            NOT NULL DEFAULT 'MEDIUM',
                       due_date    DATE,
                       created_at  DATETIME                                 NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at  DATETIME                                 NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);