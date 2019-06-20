package shopthing.controller.util;

public class PopupUtil {
    private static String messageType;
    private static String messageText;

    public static String getMessageType() {
        return messageType;
    }

    public static void setMessageType(String messageType) {
        PopupUtil.messageType = messageType;
    }

    public static String getMessageText() {
        return messageText;
    }

    public static void setMessageText(String messageText) {
        PopupUtil.messageText = messageText;
    }

}
