/* Welke soundtrack is het meeste gebruikt in films? (zelf bedacht) */
SELECT
  title,
  COUNT(title) AS `occurrences`
  /* note: there is many permutations (such as all upper-case) so the count is much higher than expected */
FROM
	movie_soundtrack AS ms
	INNER JOIN soundtracks AS s ON s.id = ms.soundtrack_id
GROUP BY
  title
ORDER BY
  `occurrences` DESC
LIMIT
  1;
