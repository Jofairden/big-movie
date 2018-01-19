/* Wat zijn de kortste films met een waardering van 8.5 of hoger? */
SELECT
  title,
  running_time,
  score
FROM
  movies
WHERE
  score >= ?
  AND running_time = (
    SELECT
      MIN(running_time)
    FROM
      movies
  );
