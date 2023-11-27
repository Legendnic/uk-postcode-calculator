create table if not exists results (
    id int not null primary key,
    postcode1 varchar(20),
    postcode2 varchar(20),
    distance text,
    ref_postcode_id1 int,
    ref_postcode_id2 int,
    constraint fk_ref_postcode_id1 foreign key (ref_postcode_id1) references postcode_refs(id)
    on delete cascade,
    constraint fk_ref_postcode_id2 foreign key (ref_postcode_id2) references postcode_refs(id)
    on delete cascade
);
