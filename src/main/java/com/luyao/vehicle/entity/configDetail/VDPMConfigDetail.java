package com.luyao.vehicle.entity.configDetail;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class VDPMConfigDetail {
    private String circuit;
    private String carModel;
    private String trainNumber;
    private String vehicleNumber;
    private String wheelDiameter;
    private String speedGearNumber;
    private String crateNumber;
    private String softVersion;
    private String hardVersion;
    private String configVersion;
    private String mainNumber;
    private String otherNumber;
    private String levelPressure;
    private String emptyWeight;
    private String totalDistance;
    private String overWeight;
    private String windingEarlyWarning;
    private String windingWarning;
    private String batteryEarlyWarning;
    private String batteryWarning;
    private String dataSamplingRate;
    private String dataLength;
    private String sensorSensitivity;
    private String minAnalysisVelocity;
    private String balance1;
    private String balance2;
    private String balance3;
    private String lateralQuality;
    private String verticalQuality;
}
