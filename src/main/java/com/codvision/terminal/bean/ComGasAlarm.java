package com.codvision.terminal.bean;

import com.codvision.terminal.bean.alarms.NewAlarm;
import lombok.Data;

@Data
    public class ComGasAlarm extends NewAlarm {
        private String currentVal;//告警值
}
