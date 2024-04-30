CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
create table partners (
    id uuid primary key,
    name varchar(100) not null,
    created_at timestamp default now(),
    updated_at timestamp default now()
);

create table events (
    id uuid primary key,
    name varchar(100) not null,
    description varchar(200),
    date timestamp,
    total_spots bigint not null default 0,
    total_spots_reserved bigint not null default 0,
    is_published boolean not null default false,
    partner_id uuid not null,
    created_at timestamp default now(),
    updated_at timestamp default now(),
    constraint fk_partner
        foreign key (partner_id)
            references partners(id)
);

create table event_sections (
    id uuid primary key,
    price_in_cents bigint not null,
    description varchar(200),
    total_spots bigint not null,
    total_spots_reserved bigint not null,
    is_published boolean not null default false,
    event_id uuid not null,
    created_at timestamp default now(),
    updated_at timestamp default now(),
    constraint fk_partner
        foreign key (event_id)
            references events(id)
);

create table event_spots (
    id uuid primary key,
    location varchar(5) not null,
    is_published boolean not null default false,
    is_reserved boolean not null default false,
    event_section_id uuid not null,
    created_at timestamp default now(),
    updated_at timestamp default now(),

    constraint fk_partner
        foreign key (event_section_id)
            references event_sections(id)
);