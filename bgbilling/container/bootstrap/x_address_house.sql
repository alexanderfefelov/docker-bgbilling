use bgbilling;

alter table address_house auto_increment = 50000;

alter table address_house modify column comment text;

create index idx_address_house_001 on address_house (box_index);
