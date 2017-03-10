--必要字段版
SELECT
Alipay.tradeBeginingDate,
Alipay.tradeCounterPart,
Alipay.prodName,
Alipay.price,
Alipay.io,
Alipay.tradeState,
Alipay.comment,
Alipay.fundState
FROM Alipay
WHERE julianday(date('now', 'weekday 0')) - julianday(tradeBeginingDate)< 6 and fundState = '已支出';

SELECT *
FROM Alipay
WHERE julianday(date('now', 'weekday 0')) - julianday(tradeBeginingDate)< 6;

