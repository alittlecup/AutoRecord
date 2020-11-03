package com.hbl.recordactivity

import androidx.room.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Entity
data class UserInfo(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val username: String,
    @ColumnInfo(name = "schoolnum")
    val schoolNum: String,
    @ColumnInfo(name = "passward")
    val passward: String,

    @ColumnInfo(name = "eaisess")
    var eaiSess: String = "",
    @ColumnInfo(name = "eaisesstime")
    var eaiSessTime: String = "",

    @ColumnInfo(name = "uukey")
    var uukey: String = "",

    @ColumnInfo(name = "uukeyTime")
    var uukeyTime: String = "",
) {
    fun isLogin(): Boolean {
        if (eaiSess.isEmpty() || uukey.isEmpty()) {
            return false
        }
        if (uukeyTime.isEmpty() || eaiSessTime.isEmpty()) {
            return false
        }
        if (isOutOfToday(eaiSessTime) || isOutOfToday(uukeyTime)) {
            return false
        }
        return true

    }

    private fun formatTime(time: String): LocalDate {
        var day: String = time.split("T")[0]
        val formatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA)
        return LocalDate.parse(day, formatter)
    }

    private fun isOutOfToday(time: String): Boolean {
        var formatTime = formatTime(time)
        return formatTime.isBefore(LocalDate.now())
    }

}

@Dao
interface UserDao {
    @Query("Select * From UserInfo")
    fun getAll(): List<UserInfo>

    @Query("select * from UserInfo where name =:name")
    fun findByName(name: String): UserInfo

    @Query("delete from UserInfo where name=:name")
    fun deleteByName(name: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserINfo(userInfo: UserInfo)
}

@Database(entities = [UserInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserDao
}
