use bgbilling;

alter table contract_charge auto_increment = 3456789;

alter table contract_charge modify column comment text;
