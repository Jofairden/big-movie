UPDATE
  bigmovie.location_movie l
SET
  l.location = REPLACE(l.location, '{{SUSPENDED}}', '')
WHERE
  l.location LIKE "{{SUSPENDED}}%";
