/* Wat zijn de 10 meest populaire gesproken talen in films? */
SELECT
  `language`,
  COUNT(`language`) AS `occurrences`
FROM
  languages AS l
  LEFT JOIN language_movie AS lm ON l.id = lm.language_id
GROUP BY
  `language`
ORDER BY
  `occurrences` DESC
LIMIT
  10;
  