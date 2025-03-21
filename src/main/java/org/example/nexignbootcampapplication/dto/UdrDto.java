package org.example.nexignbootcampapplication.dto;


/**
 * DTO для формирования UDR отчёта.
 */
public class UdrDto {

    private String msisdn;
    private CallDuration incomingCall;
    private CallDuration outcomingCall;

    public UdrDto() {
    }

    public UdrDto(String msisdn, CallDuration incomingCall, CallDuration outcomingCall) {
        this.msisdn = msisdn;
        this.incomingCall = incomingCall;
        this.outcomingCall = outcomingCall;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public CallDuration getIncomingCall() {
        return incomingCall;
    }

    public void setIncomingCall(CallDuration incomingCall) {
        this.incomingCall = incomingCall;
    }

    public CallDuration getOutcomingCall() {
        return outcomingCall;
    }

    public void setOutcomingCall(CallDuration outcomingCall) {
        this.outcomingCall = outcomingCall;
    }

    /**
     * Вложенный класс для представления длительности вызова.
     */
    public static class CallDuration {
        private String totalTime;

        public CallDuration() {
        }

        public CallDuration(String totalTime) {
            this.totalTime = totalTime;
        }

        public String getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }
    }
}
