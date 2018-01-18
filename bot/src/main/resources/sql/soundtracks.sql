/* Welke soundtrack is het meeste gebruikt in films? (zelf bedacht) */
SELECT 
		COUNT(*) AS occurrences,
		s.title AS soundtrack_title
FROM movie_soundtrack ms, soundtracks s
WHERE s.id = ms.soundtrack_id
GROUP BY ms.soundtrack_id
ORDER BY COUNT(*) desc
LIMIT 1