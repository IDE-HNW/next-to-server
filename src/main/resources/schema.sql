drop table if exists member;
drop table if exists member_detail;

create table member_detail (
    detail_id BIGSERIAL PRIMARY KEY,
    age SMALLINT NOT NULL,
    emergency_contact VARCHAR(20) NOT NULL,
    road_address VARCHAR(255) NOT NULL,
    detail_address VARCHAR(255) NOT NULL
);

create table member (
    member_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    renewal_date DATE NULL,
    detail_id BIGINT NOT NULL,
    FOREIGN KEY (detail_id) references member_detail on delete cascade
);
