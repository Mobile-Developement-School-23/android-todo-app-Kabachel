package com.example.todoapp.domain.entity

/**
 * @author Данила Шабанов on 09.06.2023
 */
internal enum class PriorityType(priority: String) {
    LOW("Нет"),
    MEDIUM("Низкий"),
    HIGH("!! Высокий"),
}