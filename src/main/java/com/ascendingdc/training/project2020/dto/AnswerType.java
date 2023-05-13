package com.ascendingdc.training.project2020.dto;

public enum AnswerType {
    NUMBER_ANSWER(1),
    STRING_TYPE(2),

    DEFAULT_ANSWER(3);

//    private String answerText;
    private int answerInt;

    AnswerType(int answerIntValue) {
        this.answerInt = answerIntValue;
    }

//    public static AnswerType getAnswerTypeFromString(String answerText) {
//        for(AnswerType answerType : AnswerType.values()) {
//            if(answerType.answerText.equalsIgnoreCase(answerText)) {
//                return answerType;
//            }
//        }
//        return DEFAULT_ANSWER;
//    }

    public static AnswerType getAnswerTypeFromIntValue(int answerInt) {
        for(AnswerType answerType : AnswerType.values()) {
            if(answerType.answerInt == answerInt) {
                return answerType;
            }
        }
        return DEFAULT_ANSWER;
    }

}
