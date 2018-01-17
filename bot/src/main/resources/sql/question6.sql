/* Wat zijn de 10 meest populaire gesproken talen in films? */
SELECT l.`language`, COUNT(l.`language`)
FROM language_movie lm
INNER JOIN languages l ON l.id = lm.language_id
GROUP BY l.`language`
ORDER BY COUNT(l.`language`) DESC
LIMIT 10;