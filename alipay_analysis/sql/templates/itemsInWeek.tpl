SELECT *
FROM Alipay
WHERE julianday(date('now', 'weekday 0','{daysFromNow} days'))+1 - julianday(tradeBeginingDate)<= 7
  AND julianday(date('now', 'weekday 0','{daysFromNow} days')) - julianday(tradeBeginingDate) >= 0
ORDER BY date(tradeBeginingDate) ASC