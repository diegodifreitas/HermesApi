/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Diego Dulval
 * Created: 11/05/2017
 */

CREATE TABLE divulgation(
    div_id BIGSERIAL,
    div_title VARCHAR(256) NOT NULL,
    div_description VARCHAR(2048) NOT NULL,
    div_type VARCHAR(20) NOT NULL,
    div_created_at TIMESTAMP NOT NULL,
    PRIMARY KEY(div_id)
);

CREATE TABLE file(
    fil_id BIGSERIAL,
    fil_name VARCHAR(128) NOT NULL,
    fil_url VARCHAR(128) NOT NULL
    fil_divulgation_fk BIGINT,
    PRIMARY KEY(fil_id),
    CONSTRAINT file_fivulgation_fk FOREIGN KEY(file_divulgation_fk) 
    REFERENCES divulgation(div_id) ON UPDATE CASCADE ON DELETE CASCADE
)

CREATE TABLE user_account(
    use_id BIGSERIAL,
    use_name VARCHAR(256) NOT NULL,
    use_password VARCHAR(2048) NOT NULL,
    PRIMARY KEY(use_id)
);

