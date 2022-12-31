package com.luyao.vehicle.entity.configDetail;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class BSMU2BearingConfigDetail {
    private String beeringType;
    private String bearingShaftDiameter;
    private String bearingOutDiameter;
    private String bearingMidDiameter;
    private String rollerDiameter;
    private String numRoller;
    private String rowRoller;
    private String contactAngle;
    private String ratioNeighbor;
    private String ratioWheel;
    private String numLargeTeeth;
    private String numSmallTeeth;
    private String mev_outCage;
    private String mev_inCage;
    private String mev_outRing;
    private String mev_inRing;
    private String mev_singleRoller;
    private String mev_doubleRoller;
    private String mev_gear_tread;
    private String mev_nearTeeth;
    private String th_tempRiseWarning;
    private String th_tempRiseAlarm;
    private String th_tempWarning;
    private String th_tempAlarm;
    private String th_outCageAlarm1;
    private String th_outCageAlarm2;
    private String th_outCageAlarm3;
    private String th_inCageAlarm1;
    private String th_inCageAlarm2;
    private String th_inCageAlarm3;
    private String th_outRingAlarm1;
    private String th_outRingAlarm2;
    private String th_outRingAlarm3;
    private String th_inRingAlarm1;
    private String th_inRingAlarm2;
    private String th_inRingAlarm3;
    private String th_singleRollerAlarm1;
    private String th_singleRollerAlarm2;
    private String th_singleRollerAlarm3;
    private String th_doubleRollerAlarm1;
    private String th_doubleRollerAlarm2;
    private String th_doubleRollerAlarm3;
    private String th_treadAlarm1;
    private String th_treadAlarm2;
    private String th_treadAlarm3;
    private String th_nearGearAlarm1;
    private String th_nearGearAlarm2;
    private String th_nearGearAlarm3;
    private String minSpeed_kmh;
    private String demudulateGainCoef;
    private String speedMultiFreqCoef;
    private String highPassFilterGain;
    private String bandPassFilterGain;
    private String detectFilterGain;
    private String lowPassFilterGain;
    private String lowPassFilterGain_8;
    private String lowPassFilterGain_58;
    private String lowPassFilterFreq_58;
    private String originalInterval;
    private String packSizeEachTime;
    private String packSizeAll;
    private String gainCoef_58;
    private String numFFTPoint;
    private String isSimulateSpeed;
    private String originalSampleRate;
    private String originalSampleDuration;
    private String originalSampleLabel;
}
