/* Welke films hebben de hoogste score met de minste stemmen? */
SELECT
  title,
  votes,
  score
FROM
  movies
WHERE
  votes = (
    SELECT
      MIN(votes)
    FROM
      movies
  )
  AND score =(
    SELECT
      MAX(score)
    FROM
      movies
  );
