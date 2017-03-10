SELECT
	*
FROM
	Alipay
where julianday(date('now', 'weekday 0','{daysFromNow} days')) - julianday(tradeBeginingDate)< 6
and tradeCounterPart like '%工商%'