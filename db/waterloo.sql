--
-- Database: `waterloo`
--

CREATE TABLE role (
    id bigint NOT NULL AUTO_INCREMENT,
    role_name varchar(20) NOT NULL,
    description varchar(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user (
    id bigint NOT NULL AUTO_INCREMENT,
    name varchar(20) NOT NULL,
    password varchar(20) NOT NULL,
    status varchar(20) NOT NULL,
    in_correct_password_count int DEFAULT 0,
    role_id bigint DEFAULT 1,
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

INSERT INTO role (id, role_name, description) VALUES (1, "USER", "Normal Customer");

INSERT INTO user (id, name, password, status, in_correct_password_count, role_id)  VALUES
(1, "userone","a8c1587547303196e23d2d8611d6a084044d089b3de8bd0990d3dec5d3b2c3fc", "ACTIVE", 0, 1);
