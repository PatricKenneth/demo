CREATE TABLE tb_users (
  id UUID NOT NULL,
  created_at TIMESTAMP with time zone NOT NULL,
  updated_at TIMESTAMP with time zone NOT NULL,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  CONSTRAINT pk_tb_users PRIMARY KEY (id)
);