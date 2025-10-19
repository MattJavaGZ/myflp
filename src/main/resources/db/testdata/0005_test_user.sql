insert into
    users (first_name, last_name, email, password, activ_key, work_station_id, activ, date_added)
values
    ('Mateusz', 'Kowalski', 'matt@gmailx.com', '{noop}admin', 'sdf435345jn', 3,true, '2025-07-11 14:30:00'),
    ('User','User', 'user@user.pl', '{noop}user', 'sdf4352323345jn', 2, true, '2025-08-11 14:30:00');

insert into
    user_roles (user_id, role_id)
values
    (1, 1),
    (2,2);