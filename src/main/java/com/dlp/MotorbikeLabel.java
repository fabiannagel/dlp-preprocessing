package com.dlp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MotorbikeLabel {

    // only driver
    D_HELMET("DHelmet", 0),
    D_NO_HELMET("DNoHelmet", 1),

    // driver, 1 passenger
    D_HELMET_P0_HELMET("DHelmetP0Helmet", 2),
    D_HELMET_P0_NO_HELMET("DHelmetP0NoHelmet", 3),
    D_NO_HELMET_P0_NO_HELMET("DNoHelmetP0NoHelmet", 4),
    D_HELMET_P1_HELMET("DHelmetP1Helmet", 5),
    D_NO_HELMET_P1_HELMET("DNoHelmetP1Helmet", 6),
    D_HELMET_P1_NO_HELMET("DHelmetP1NoHelmet", 7),
    D_NO_HELMET_P1_NO_HELMET("DNoHelmetP1NoHelmet", 8),

    // driver, 2 passengers (P0, P1)
    D_HELMET_P0_HELMET_P1_HELMET("DHelmetP0HelmetP1Helmet", 9),
    D_HELMET_P0_NO_HELMET_P1_HELMET("DHelmetP0NoHelmetP1Helmet", 10),
    D_HELMET_P0_NO_HELMET_P1_NO_HELMET("DHelmetP0NoHelmetP1NoHelmet", 11),
    D_NO_HELMET_P0_NO_HELMET_P1_HELMET("DNoHelmetP0NoHelmetP1Helmet", 12),
    D_NO_HELMET_P0_NO_HELMET_P1_NO_HELMET("DNoHelmetP0NoHelmetP1NoHelmet", 13),
    D_NO_HELMET_P0_HELMET_P1_NO_HELMET("DNoHelmetP0HelmetP1NoHelmet", 14),

    // driver, 2 passengers (P1, P2)
    D_HELMET_P1_HELMET_P2_HELMET("DHelmetP1HelmetP2Helmet", 15),
    D_HELMET_P1_NO_HELMET_P2_HELMET("DHelmetP1NoHelmetP2Helmet", 16),
    D_HELMET_P1_HELMET_P2_NO_HELMET("DHelmetP1HelmetP2NoHelmet", 17),
    D_HELMET_P1_NO_HELMET_P2_NO_HELMET("DHelmetP1NoHelmetP2NoHelmet", 18),
    D_NO_HELMET_P1_HELMET_P2_HELMET("DNoHelmetP1HelmetP2Helmet", 19),
    D_NO_HELMET_P1_NO_HELMET_P2_HELMET("DNoHelmetP1NoHelmetP2Helmet", 20),
    D_NO_HELMET_P1_NO_HELMET_P2_NO_HELMET("DNoHelmetP1NoHelmetP2NoHelmet", 21),

    // driver, 3 passengers (P0, P1, P2)
    D_HELMET_P0_HELMET_P1_HELMET_P2_HELMET("DHelmetP0HelmetP1HelmetP2Helmet", 22),
    D_HELMET_P0_HELMET_P1_NO_HELMET_P2_HELMET("DHelmetP0HelmetP1NoHelmetP2Helmet", 23),
    D_HELMET_P0_HELMET_P1_NO_HELMET_P2_NO_HELMET("DHelmetP0HelmetP1NoHelmetP2NoHelmet", 24),
    D_HELMET_P0_NO_HELMET_P1_HELMET_P2_HELMET("DHelmetP0NoHelmetP1HelmetP2Helmet", 25),
    D_HELMET_P0_NO_HELMET_P1_NO_HELMET_P2_HELMET("DHelmetP0NoHelmetP1NoHelmetP2Helmet", 26),
    D_HELMET_P0_NO_HELMET_P1_NO_HELMET_P2_NO_HELMET("DHelmetP0NoHelmetP1NoHelmetP2NoHelmet", 27),
    D_NO_HELMET_P0_NO_HELMET_P1_NO_HELMET_P2_NO_HELMET("DNoHelmetP0NoHelmetP1NoHelmetP2NoHelmet", 28),
    D_NO_HELMET_P0_NO_HELMET_P1_NO_HELMET_P2_HELMET("DNoHelmetP0NoHelmetP1NoHelmetP2Helmet", 29),
    D_HELMET_P1_NO_HELMET_P2_NO_HELMET_P3_NO_HELMET("DHelmetP1NoHelmetP2NoHelmetP3NoHelmet", 30),
    D_HELMET_P1_NO_HELMET_P2_NO_HELMET_P3_HELMET("DHelmetP1NoHelmetP2NoHelmetP3Helmet", 31),
    D_NO_HELMET_P1_NO_HELMET_P2_NO_HELMET_P3_NO_HELMET("DNoHelmetP1NoHelmetP2NoHelmetP3NoHelmet", 32),
    D_NO_HELMET_P0_NO_HELMET_P1_NO_HELMET_P2_NO_HELMET_P3_NO_HELMET("DNoHelmetP0NoHelmetP1NoHelmetP2NoHelmetP3NoHelmet", 33),
    D_HELMET_P0_NO_HELMET_P1_NO_HELMET_P2_NO_HELMET_P3_HELMET("DHelmetP0NoHelmetP1NoHelmetP2NoHelmetP3Helmet", 34),
    D_HELMET_P0_NO_HELMET_P1_NO_HELMET_P2_NO_HELMET_P3_NO_HELMET("DHelmetP0NoHelmetP1NoHelmetP2NoHelmetP3NoHelmet", 35);


    private String identifier;
    private int classNumber;

    MotorbikeLabel(String identifier, int number) {
        this.identifier = identifier;
        this.classNumber = number;
    }

    public static MotorbikeLabel fromIdentifier(String identifier) {
        List<MotorbikeLabel> motorbikeLabels = Arrays.stream(MotorbikeLabel.values()).filter(ml -> ml.getIdentifier().equals(identifier)).collect(Collectors.toList());

        assert motorbikeLabels.size() == 1;

        return motorbikeLabels.get(0);
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getClassNumber() {
        return classNumber;
    }
}
