SELECT *
FROM Alipay
WHERE julianday(date('now','8 hours','weekday 0','{daysFromNow} days'))+1 - julianday(tradeBeginingDate)<= 7
  AND julianday(date('now','8 hours','weekday 0','{daysFromNow} days'))+1 - julianday(tradeBeginingDate) >= 0
ORDER BY date(tradeBeginingDate) ASC