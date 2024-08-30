CREATE TABLE transaction (
    transaction_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    from_account_id UUID NOT NULL,
    to_account_id UUID NOT NULL,
    amount NUMERIC NOT NULL CHECK (amount >= 0),
    CONSTRAINT fk_from_account FOREIGN KEY (from_account_id) REFERENCES account(account_id),
    CONSTRAINT fk_to_account FOREIGN KEY (to_account_id) REFERENCES account(account_id)
);