SELECT sum(price) - b.income
FROM Alipay a,
  (SELECT sum(price) income
   FROM Alipay
   WHERE julianday(date('now', 'weekday 0','{daysFromNow} days'))+1 - julianday(tradeBeginingDate)<= 7
     AND julianday(date('now', 'weekday 0','{daysFromNow} days')) - julianday(tradeBeginingDate) >= 0
     AND fundState = '已收入' --交易不成功的退款
 ) b
WHERE julianday(date('now', 'weekday 0','{daysFromNow} days'))+1 - julianday(tradeBeginingDate)<= 7
  AND julianday(date('now', 'weekday 0','{daysFromNow} days')) - julianday(tradeBeginingDate) >= 0
  AND a.fundState = '已支出'