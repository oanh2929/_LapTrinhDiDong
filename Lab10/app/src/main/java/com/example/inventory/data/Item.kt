package com.example.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items") // Thêm dòng này để tạo bảng
data class Item(
    @PrimaryKey(autoGenerate = true) // Thêm dòng này để mỗi món đồ có 1 ID riêng (1, 2, 3...)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int
)