import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.jodatime.date
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import javax.sql.DataSource
import org.sucram.currencyconverter.domain.Transaction

internal object Transactions : LongIdTable() {

    val userId: Column<Long> = long("user_id")
    val originCurrency: Column<String> = varchar("origin_currency", 3)
    val originAmount: Column<Double> = double("origin_amount")
    val destinationCurrency: Column<String> = varchar("destination_currency", 3)
    val destinationAmount: Column<Double> = double("destination_amount")
    val exchangeRate: Column<Double> = double("exchange_rate")
    val date: Column<DateTime> = date("date") // datetime("date")

    fun toDomain(row: ResultRow): Transaction {
        return Transaction(
            id = row[id].value,
            userId = row[userId],
            originCurrency = row[originCurrency],
            originAmount = row[originAmount],
            destinationCurrency =row[destinationCurrency],
            destinationAmount =row[destinationAmount],
            exchangeRate =row[exchangeRate],
            date =row[date].toDate()
        )
    }
}

class TransactionRepository(private val dataSource: DataSource) {

    init {
        transaction(Database.connect(dataSource))
        {
            SchemaUtils.create(Transactions)
        }
    }

    fun findByUserId(id: Long): List<Transaction> {
        return transaction(Database.connect(dataSource))
        {
            Transactions.select { Transactions.userId eq id }
                .map { Transactions.toDomain(it) }

        }
    }

    fun create(transaction: Transaction): Long {
        return transaction(Database.connect(dataSource))
        {
            addLogger(StdOutSqlLogger)

            Transactions.insertAndGetId  { row ->
                row[userId] = transaction.userId
                row[originCurrency] = transaction.originCurrency
                row[originAmount] = transaction.originAmount
                row[destinationCurrency] = transaction.destinationCurrency
                row[destinationAmount] = transaction.destinationAmount
                row[exchangeRate] = transaction.exchangeRate
                row[date] = DateTime()
            }
        }.value
    }
}