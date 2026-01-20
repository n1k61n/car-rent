package com.example.carrent.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDashboardStatsDto {

    private long totalBookings;
    private long activeBookings;
    private long completedBookings;
    private BigDecimal totalSpent;
}
