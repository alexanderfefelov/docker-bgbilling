use bgbilling;

alter table address_house auto_increment = 50000;

alter table address_house modify column comment text;
