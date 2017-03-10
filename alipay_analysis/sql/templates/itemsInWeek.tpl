SELECT *
FROM Alipay
WHERE julianday(date('now', 'weekday 0','{daysFromNow} days')) - julianday(tradeBeginingDate)<= 6
  AND julianday(date('now', 'weekday 0','{daysFromNow} days')) - julianday(tradeBeginingDate) >=0