package com.tanran.model.media.dtos;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDto {

    private Short type;
    private Date stime;
    private Date etime;

    private List<String> time;
}
