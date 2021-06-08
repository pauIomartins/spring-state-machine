CREATE TABLE task (
    id INTEGER NOT NULL AUTO_INCREMENT,
    user_id INTEGER NULL,
    warehouse_id VARCHAR(60) NOT NULL,
    workflow_id VARCHAR(100) NULL,
    status VARCHAR(60) NOT NULL,

    PRIMARY KEY (id)
);