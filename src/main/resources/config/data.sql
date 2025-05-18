DELETE FROM users;
DELETE FROM empleados;
INSERT INTO users (nombre_usuario,contraseña) VALUES
    ('admin','$2a$12$qXiIJYJfWJfOfcT4Y.KyaOadu2UzNBAcs1oYkGrt8qXGIMwE3eJdm',true),
    ('emilis246','$2a$12$jSF56a/No8aZA/3ANkGpWOwEd4mpCyVgwdA4MO0ZIiQoZoZwswFxS',false);