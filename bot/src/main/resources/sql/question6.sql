/* Wat zijn de 10 meest populaire gesproken talen in films? */
SELECT l.`language`, COUNT(l.`language`)
FROM language_movie l
GROUP BY l.`language`
ORDER BY COUNT(l.`language`) DESC
LIMIT 10;