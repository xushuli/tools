SELECT date(tradeBeginingDate),
       sum(price)
FROM Alipay
WHERE julianday(date('now','8 hours','weekday 0','{daysFromNow} days'))+1 - julianday(tradeBeginingDate)<= 7
  AND julianday(date('now','8 hours','weekday 0','{daysFromNow} days'))+1 - julianday(tradeBeginingDate) >= 0
  AND fundState = '已支出'
  AND tradeState in ('等待确认收货','等待对方发货','交易成功')
GROUP BY date(tradeBeginingDate)
ORDER BY date(tradeBeginingDate) ASC