package com.example.chaipaisa.Dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.chaipaisa.models.UserDetails

@Dao
interface UserdetailsDao {
    @Insert
    suspend fun insert_payment_details(userDetails: UserDetails)

    @Query("Select * from userdetails")
    suspend fun selectallpayments():List<UserDetails>

    @Query("Select * from userdetails where upi_id =:upi_id")
    suspend fun selectallpayments(upi_id:String):List<UserDetails>

    @Query("DELETE FROM userdetails WHERE id IN (:ids)")
    suspend fun deletetransaction_id(ids:List<String>)

    @Query("SELECT SUM(amount) AS total_amount FROM userdetails WHERE upi_id = :upiId")
    suspend fun getTotalAmountByUpiId(upiId: String): Int

}