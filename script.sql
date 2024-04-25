CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
create table partners (
    id uuid primary key,
    name varchar(100) not null
)