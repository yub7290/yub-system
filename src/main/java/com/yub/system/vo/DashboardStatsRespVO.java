package com.yub.system.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsRespVO {
    private int courseCount;
    private int studentCount;
    private int examCount;
    private int accessCount;
}
