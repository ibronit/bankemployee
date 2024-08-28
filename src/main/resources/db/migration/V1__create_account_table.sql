CREATE TABLE account (
    account_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    balance NUMERIC NOT NULL CHECK (balance >= 0)
);