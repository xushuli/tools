from .SQLStatement import SQLStatement

SQLStatements = {
    'itemsInWeek':
        lambda week=0: SQLStatement(
            'itemsInWeek', {'daysFromNow': -int(week) * 7}),

    'didiItemsInWeek':
        lambda week=0: SQLStatement(
            'didiItemsInWeek', {'daysFromNow': -int(week) * 7}),

    'didiIncomeInWeek':
        lambda week=0: SQLStatement(
            'didiIncomeInWeek', {'daysFromNow': -int(week) * 7}),

    'didiEveryDayIncomeInWeek':
        lambda week=0: SQLStatement(
            'didiEveryDayIncomeInWeek', {'daysFromNow': -int(week) * 7}),

    'everyDayExpenseInWeek':
        lambda week=0: SQLStatement(
            'everyDayExpenseInWeek', {'daysFromNow': -int(week) * 7}),

    'totalExpenseInWeek':
        lambda week=0: SQLStatement(
            'totalExpenseInWeek', {'daysFromNow': -int(week) * 7}),
}
