create table if not exists postcode_refs (
    id int not null primary key,
    source_id int,
    postcode varchar(20),
    latitude numeric,
    longitude numeric,
    checksum bigint
);
