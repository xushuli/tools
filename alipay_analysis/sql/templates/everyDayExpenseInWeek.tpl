SELECT date(tradeBeginingDate),
       sum(price)
FROM Alipay
WHERE julianday(date('now', 'weekday 0','{daysFromNow} days'))+1 - julianday(tradeBeginingDate)<= 7
  AND julianday(date('now', 'weekday 0','{daysFromNow} days')) - julianday(tradeBeginingDate) >= 0
  AND fundState = '已支出'
  AND (tradeState='交易成功'
       OR tradeState= '等待确认收货')
GROUP BY date(tradeBeginingDate)
ORDER BY date(tradeBeginingDate) ASC