CREATE TABLE rules (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    url VARCHAR(2048) NOT NULL,
    status VARCHAR(15) NOT NULL CHECK(status IN ('ACTIVE', 'INACTIVE')),
    shared_secret VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);