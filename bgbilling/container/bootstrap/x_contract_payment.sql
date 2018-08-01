use bgbilling;

alter table contract_payment auto_increment = 3456789;

alter table contract_payment modify column comment text;
