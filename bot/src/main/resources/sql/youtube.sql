/* Bestaat de film die je op youtube wil bekijken?
author Fadi
*/
SELECT CASE WHEN EXISTS (
	SELECT *
	FROM movies m
	WHERE m.title LIKE ?
)
THEN 1
Else 0 END
