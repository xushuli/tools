SELECT date(tradeBeginingDate),sum(price)
FROM Alipay
WHERE julianday(date('now','8 hours','weekday 0','{daysFromNow} days'))+1 - julianday(tradeBeginingDate)<= 7
  AND julianday(date('now','8 hours','weekday 0','{daysFromNow} days'))+1 - julianday(tradeBeginingDate) >= 0
  AND tradeCounterPart LIKE '%工商%'
GROUP BY date(tradeBeginingDate)