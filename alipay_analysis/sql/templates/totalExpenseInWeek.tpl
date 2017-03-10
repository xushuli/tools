SELECT *
FROM Alipay
WHERE julianday(date('now', 'weekday 0','{daysFromNow} days')) - julianday(tradeBeginingDate)< 6
  AND fundState = '已支出'
  AND (tradeState='交易成功'
       OR tradeState= '等待确认收货');


SELECT sum(price) - b.income
FROM Alipay a,
  ( SELECT sum(price) income
   FROM Alipay
   WHERE julianday(date('now', 'weekday 0','{daysFromNow} days')) - julianday(tradeBeginingDate) < 6
     AND fundState = '已收入' --交易不成功的退款
 ) b
WHERE julianday(date('now', 'weekday 0','{daysFromNow} days')) - julianday(a.tradeBeginingDate) < 6
  AND a.fundState = '已支出'