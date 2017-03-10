SELECT
date(tradeBeginingDate),sum(price)
FROM Alipay
WHERE julianday(date('now', 'weekday 0')) - julianday(tradeBeginingDate)< 6 and fundState = '已支出' and (tradeState='交易成功' or tradeState= '等待确认收货')
group by date(tradeBeginingDate)