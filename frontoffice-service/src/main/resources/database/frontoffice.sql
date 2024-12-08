CREATE TABLE IF NOT EXISTS domain_events (
    id INT(10) NOT NULL AUTO_INCREMENT,
    event_id CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    version INT(10) NOT NULL,
    body JSON NOT NULL,
    occurred_on DATETIME(3) NOT NULL,
    published_on DATETIME(3) NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS teachers (
    id CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS teacher_courses (
    teacher_id CHAR(36) NOT NULL,
    course_id CHAR(36) NOT NULL,
    PRIMARY KEY (teacher_id, course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
