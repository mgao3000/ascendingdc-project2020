insert into car_dealer (name, description) values
('Dealer_A', 'Dealer A Description'),
('Dealer_B', 'Dealer B Description'),
('Dealer_C', 'Dealer C Description'),
('Dealer_D', 'Dealer D Description'),
('Dealer_E', 'Dealer E Description'),
('Dealer_F', 'Dealer F Description'),
('Dealer_G', 'Dealer G Description')
;
commit;

insert into cars (model, maker, price, address, car_dealer_id) values
('Camry', 'Toyota', 24000, '999 Washington Ave, Fairfax, VA 22030', 2),
('Prius', 'Toyota', 28500, '998 fish road, Fairfax, VA 22030', 2),
('Accord', 'Honda', 27500, '997 well pl, Fairfax, VA 22030', 1),
('Sienna', 'Toyota', 34000, '996 Washington Ave, Fairfax, VA 22030', 3),
('Highlander', 'Toyota', 37000, '995 Washington Ave, Fairfax, VA 22030', 3),
('Camry', 'Toyota', 27600, '994 Washington Ave, Fairfax, VA 22030', 4),
('X5', 'BMW', 74000, '993 Washington Ave, Fairfax, VA 22030', 3),
('MDX', 'Acura', 51000, '992 Washington Ave, Fairfax, VA 22030', 4),
('Camry', 'Toyota', 14000, '991 Washington Ave, Fairfax, VA 22030', 5),
('Impreza', 'Subaru', 24000, '990 Washington Ave, Fairfax, VA 22030', 6),
('Camry', 'Toyota', 28700, '989 Washington Ave, Fairfax, VA 22030', 7),
('F-150', 'Ford', 70000, '988 Washington Ave, Fairfax, VA 22030', 7),
('Pacific', 'Chrysler', 44000, '987 Washington Ave, Fairfax, VA 22030', 5),
('GLS 450', 'Mercedes-Benz', 124000, '986 Washington Ave, Fairfax, VA 22030', 7)
;
commit;
