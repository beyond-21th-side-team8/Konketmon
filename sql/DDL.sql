create database konketmon;

create table konketmon(
                          id int AUTO_INCREMENT,
                          name varchar(100),
                          ascii_art text,
                          hp int,
                          PRIMARY KEY (id)
);

CREATE table user(
                     id varchar(255),
                     pw varchar(255),
                     hp int,
                     is_saved boolean DEFAULT FALSE,
                     PRIMARY KEY (id)

);

create table konketdex(
                          id int AUTO_INCREMENT,
                          user_id varchar(255),
                          konket_id int,
                          PRIMARY KEY (id),
                          FOREIGN KEY (user_id) REFERENCES user (id)
                              ON DELETE CASCADE ,
                          FOREIGN KEY (konket_id) REFERENCES konketmon(id)
                              ON DELETE CASCADE

);