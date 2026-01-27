alter table pagamento 
add column asaas_payment_id varchar(150) unique;

alter table pagamento 
add column asaas_invoice_url varchar(255);

alter table usuario 
add column asaas_customer_id varchar(150) unique;