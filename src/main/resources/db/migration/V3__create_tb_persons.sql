CREATE TABLE tb_persons (
  id UUID NOT NULL,
  created_at TIMESTAMP with time zone NOT NULL,
  updated_at TIMESTAMP with time zone NOT NULL,
  name VARCHAR(200) NOT NULL,
  document_number VARCHAR(50) NOT NULL,
  user_id UUID NOT NULL,
  CONSTRAINT pk_tb_persons PRIMARY KEY (id),
  CONSTRAINT fk_person_user FOREIGN KEY (user_id) REFERENCES tb_users (id)
);