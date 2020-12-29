package com.networks.pms.common.util;

public enum KV {
    FCSToTL("0"),
    TLToFCS("1"),
    CheckIn("0"),
    CheckOut("1"),
    GuestInfo("2"),
    RoomChange("3"),
    ClassOfService("4"),
    DoNotDisturb("5"),
    MesgLamp("6"),
    CreateJob("7"),
    JobEnquiry("8"),
    OperaRoomStatus("9"),
    FcsRoomStatus("10"),
    CallDetail("CDR"),
    SendSuccess("1"),
    SendFailure("2"),
    NotSend("0"),
    TLConnected("1"),//连接成功
    TLConnecting("0"),//0:链接中
    TLDisConnected("2"),// 2：链接失败
    TLCallTicketType("1"),//话单类型
    TLCallTicketCalling("2"),//主叫
    TLCallTicketCalled("3"),//被叫
    TLCallTicketDuration("4"),//耗时
    TLCallTicketDateTime("5"),//时间类型
    SanYaHaiTangWanId("5720001"),
    FcsInterface("0"),//0:fcs 1:opera 2:hotSOS 3:preFcs 4:preOpera 5:preFcs && preOpera 6: fcs and TL
    OperaInterface("1"),//  FCS("fcs","0"), OPERA("opera","1"),HOTSOS("hotSOS","2"),PFCS("preFcs","3"),PREOPERA("preOpera","4")  OPERA(FMF)("opera","7")
    HotSOSInterface("2"),
    PreFcsInterface("3"),
    PreOperaInterface("4"),
    PreFcsAndPreOperaInterface("5"),
    FcsAndTLInterface("6"),
    OperaFMFInterface("7"),// OPERA(FMF)("opera","7")
    FcsConnecting("0"),//0:链接中(包括从新连接) 1：链接成功  2：链接失败
    FcsConnected("1"),
    FcsDisConnected("2"),
    PmsNotSendToCloud("0"),
    PmsSendSucceedToCloud("1"),
    PmsSendFailureToCloud("2"),
    HotelCallNumberFromFcsEx("1"),
    FcsCreateJobName("create_job"),
    FcsJobEnquiryName("job_enquiry"),
    CheckInName("Check_In"),
    CheckOutName("Check_Out"),
    GuestInfoName("GuestInfo"),
    RoomChangeName("RoomChange"),
    WorkOrderNotHandle("0"),//状态 0：未发送 1：发送工单系统成功 2：发送工单系统失败 3：发送云端成功 4：发送云端失败
    WorkOrderSendWorkSystemSucceed("1"),
    WorkOrderSendWorkSystemFailure("2"),
    WorkOrderSendCloudSucceed("3"),
    WorkOrderSendCloudFailure("4"),
    SendMessageToCloudByHttp("1"),
    SendMessageToCloudByMQ("2"),
    SendMessageToCloudByMQUpdate("3"),
    InitPortStatusName("init_port_status"),
    UpdatePortStatusName("update_port_status"),
    RoomStatus("room_status"),
    ThrowMinibar("minibar");


    private String message;

    KV(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
