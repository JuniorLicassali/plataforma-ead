ALTER TABLE oauth2_authorization 
ADD COLUMN user_code_value blob DEFAULT NULL,
ADD COLUMN user_code_issued_at timestamp DEFAULT NULL,
ADD COLUMN user_code_expires_at timestamp DEFAULT NULL,
ADD COLUMN user_code_metadata blob DEFAULT NULL,
ADD COLUMN device_code_value blob DEFAULT NULL,
ADD COLUMN device_code_issued_at timestamp DEFAULT NULL,
ADD COLUMN device_code_expires_at timestamp DEFAULT NULL,
ADD COLUMN device_code_metadata blob DEFAULT NULL;