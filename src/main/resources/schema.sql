drop table if exists member_detail;
drop table if exists member;

create table member (
    member_id SERIAL PRIMARY KEY,
    renewal_date DATE NULL
);

create table member_detail (
    member_id SERIAL NOT NULL,
    name VARCHAR(128) NOT NULL,
    emergency_contact VARCHAR(20) NOT NULL,
    road_address VARCHAR(255) NOT NULL,
    detail_address VARCHAR(255) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member,
    PRIMARY KEY (member_id)
);
