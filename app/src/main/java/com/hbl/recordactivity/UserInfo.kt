package com.hbl.recordactivity

import androidx.lifecycle.LiveData
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
    val password: String,

    @ColumnInfo(name = "eaisess")
    var eaiSess: String = "",
    @ColumnInfo(name = "eaisesstime")
    var eaiSessTime: String = "",

    @ColumnInfo(name = "uukey")
    var uukey: String = "",

    @ColumnInfo(name = "uukeyTime")
    var uukeyTime: String = "",

    @ColumnInfo(name = "clockDay")
    var clockDay: String = ""
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

    fun todayClock(): Boolean {
        if (clockDay.isEmpty()) {
            return false
        }
        var now = LocalDate.now()
        if (clockDay == now.toString())
            return true
        return false
    }

    //Wed, 03-Nov-2021 12:25:45 GMT
    private fun formatTime(time: String): LocalDate {
        val formatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("EEE, dd-MMM-yyyy HH:mm:ss zzz", Locale.ENGLISH)
        return LocalDate.parse(time, formatter)
    }

    private fun isOutOfToday(time: String): Boolean {
        var formatTime = formatTime(time)
        return formatTime.isBefore(LocalDate.now())
    }

}

@Dao
interface UserDao {
    @Query("Select * From UserInfo")
    fun getAll(): LiveData<List<UserInfo>>

    @Query("select * from UserInfo where name =:name")
    fun findByName(name: String): UserInfo

    @Query("delete from UserInfo where name=:name")
    fun deleteByName(name: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfo(userInfo: UserInfo)
}

@Database(entities = [UserInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserDao
}
