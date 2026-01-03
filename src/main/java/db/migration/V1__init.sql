select constraint_name from information_schema.constraint_column_usage
where table_name = 'usuario_acesso' and column_name = 'acesso_id'
and constraint_name <> 'unique_acesso_user';

SELECT
    c.conname       AS constraint_name,
    c.contype       AS constraint_type,
    pg_get_constraintdef(c.oid, true) AS constraint_sql
FROM pg_constraint c
JOIN pg_class t ON c.conrelid = t.oid
WHERE t.relname = 'usuarios_acesso';

ALTER TABLE usuarios_acesso drop CONSTRAINT "uk8bak9jswon2id2jbunuqlfl9e"
