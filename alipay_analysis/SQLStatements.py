from SQLStatement import SQLStatement

SQLStatements = {
    'itemsInThisWeek':
        SQLStatement('itemsInWeek', {'daysFromNow': 0}),
    'didiIncomeInThisWeek':
        SQLStatement('didiItemsInWeek', {'daysFromNow': 0}),
}
