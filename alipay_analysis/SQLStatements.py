from SQLStatement import SQLStatement

SQLStatements = {
    'itemsInWeek':
        lambda week=0: SQLStatement('itemsInWeek', {'daysFromNow': -week * 7}),
    'didiIncomeInWeek':
        lambda week=0: SQLStatement(
            'didiItemsInWeek', {'daysFromNow': -week * 7}),
    'everyDayExpenseInWeek':
        lambda week=0: SQLStatement(
            'everyDayExpenseInWeek', {'daysFromNow': -week * 7}),
    'totalExpenseInWeek':
        lambda week=0: SQLStatement(
            'totalExpenseInWeek', {'daysFromNow': -week * 7}),
}
