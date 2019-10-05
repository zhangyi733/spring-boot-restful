CREATE TABLE IF NOT EXISTS student
(
  id          uuid NOT NULL CONSTRAINT pkey PRIMARY KEY,
  seq_id BIGSERIAL NOT NULL CONSTRAINT student_seq_id UNIQUE,
  first_name        TEXT NOT NULL,
  last_name      TEXT NOT NULL,
  gpa      NUMERIC NOT NULL,
  timestamp       TIMESTAMP WITH TIME ZONE,
  created_by  TEXT,
  created_on  TIMESTAMP WITH TIME ZONE DEFAULT now(),
  modified_by TEXT,
  modified_on TIMESTAMP WITH TIME ZONE DEFAULT now()
);

