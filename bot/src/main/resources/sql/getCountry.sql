SELECT c.country
FROM countries c
WHERE c.country LIKE ?
LIMIT 1;